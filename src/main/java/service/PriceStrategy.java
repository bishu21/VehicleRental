package service;

import models.Booking;
import models.Vehicle;

public interface PriceStrategy {
    double getPrice(Booking booking, Vehicle vehicle);
}
