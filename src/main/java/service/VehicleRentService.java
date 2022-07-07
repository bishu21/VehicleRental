package service;

import models.Vehicle;

import java.util.List;
import java.util.Set;

public interface VehicleRentService {
    Boolean addBranch(String id, Set<String> vehicleTypes);
    Boolean addVehicle(String branchId, String type, String vehicleId, Integer price);
    double bookVehicle(String brnachId, String type, Integer startTime, Integer endTime);
    List<Vehicle> getAvailableVehicle(String branchId, Integer startTime, Integer endTime);
}
