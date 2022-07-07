package service;

import models.Booking;
import models.Vehicle;

class PriceStrategyImpl implements PriceStrategy {

    @Override
    public double getPrice(Booking booking, Vehicle vehicle) {
        return vehicle.getPrice() * (booking.getEndTime() - booking.getStartTime());
    }
}
