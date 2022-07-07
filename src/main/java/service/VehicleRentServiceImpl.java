package service;

import models.Booking;
import models.Branch;
import models.Vehicle;

import java.awt.print.Book;
import java.util.*;

public class VehicleRentServiceImpl implements VehicleRentService {

    private PriceStrategy priceStrategy;

    private Map<String, Branch> branchMap = new Hashtable<>();
    private Map<String, Map<String, List<Booking>>> branchBookingMap = new HashMap<>();
    int count=1;

    @Override
    public Boolean addBranch(String id, Set<String> vehicleTypes) {
        if (branchMap.containsKey(id)) {
//            System.out.println("Can not create new branch with id="+id+" ,Existing Branchs ="+branchMap.values());
            return false;
        }
        Branch branch = new Branch(id, vehicleTypes, new TreeSet<>());
        branchMap.put(id, branch);
        branchBookingMap.put(id, new HashMap<>());
        return true;
    }

    @Override
    public Boolean addVehicle(String branchId, String type, String vehicleId, Integer price) {
        if (!branchMap.containsKey(branchId)) {
//            System.out.println("Branch with id="+branchId+" does not exist.");
            return false;
        }
        Branch branch = branchMap.get(branchId);
        if(!branch.getVehicleTypes().contains(type)) {
//            System.out.println("vehicle type="+type+" is not valid for branch="+branchId);
            return false;
        }
        branch.getVehicleList().add(new Vehicle(vehicleId, type, price));
        branchBookingMap.get(branchId).put(vehicleId, new ArrayList<>());
        return true;
    }

    @Override
    public double bookVehicle(String branchId, String type, Integer startTime, Integer endTime) {
        if (!branchMap.containsKey(branchId)) {
//            System.out.println("Branch with id="+branchId+" does not exist.");
            return -1;
        }
        Branch branch = branchMap.get(branchId);
        if(!branch.getVehicleTypes().contains(type)) {
//            System.out.println("vehicle type="+type+" is not valid for branch="+branchId);
            return -1;
        }
        Map<String, List<Booking>> bookingMap = branchBookingMap.get(branchId);

        Integer totalVehicleType = 0;
        Integer available=0;
        for (Vehicle it1 : branch.getVehicleList()) {
            if (it1.getType().equals(type)) {
                totalVehicleType++;
                if (bookingMap.getOrDefault(it1.getId(), new ArrayList<>()).stream()
                        .noneMatch(it -> checkOverlap(it, startTime, endTime)))
                    available++;
            }
        }

        boolean surgeFlag = available * 5 < totalVehicleType * 2;

        if (surgeFlag) {
            priceStrategy = new MoreBookingSurgePriceStrategy(new PriceStrategyImpl());
        } else {
            priceStrategy = new PriceStrategyImpl();
        }

        for (Vehicle item : branch.getVehicleList()) {
            if (!item.getType().equals(type)) {
                continue;
            }
            List<Booking> bookings = bookingMap.getOrDefault(item.getId(), new ArrayList<>());
            if (bookings.stream().noneMatch(it -> checkOverlap(it, startTime, endTime))) {
                Booking booking = new Booking(count++, branchId, item.getId(), startTime, endTime,
                        0.0);
                double price = priceStrategy.getPrice(booking, item);
                booking.setPrice(price);
                bookings.add(booking);
                bookingMap.put(item.getId(), bookings);
                return booking.getPrice();
            }
        }
        return -1;
    }

    private boolean checkOverlap(Booking it, Integer startTime, Integer endTime) {
        return Math.max(it.getStartTime(), startTime) < Math.min(endTime, it.getEndTime());
    }

    @Override
    public List<Vehicle> getAvailableVehicle(String branchId, Integer startTime, Integer endTime) {
        List<Vehicle> ans = new ArrayList<>();
        Branch branch = branchMap.get(branchId);
        Map<String, List<Booking>> bookingMap = branchBookingMap.get(branchId);

        for (Vehicle item : branch.getVehicleList()) {
            List<Booking> bookings = bookingMap.getOrDefault(item.getId(), new ArrayList<>());
            Boolean match = bookings.stream().anyMatch(it -> checkOverlap(it, startTime, endTime));
            if (match == false) {
                ans.add(item);
            }
        }
        return ans;
    }
}
