package service;

import models.Booking;
import models.Vehicle;

public abstract class SurgePriceStrategy implements PriceStrategy {
    private PriceStrategy priceStrategy;

    public SurgePriceStrategy(PriceStrategy priceStrategy) {
        this.priceStrategy = priceStrategy;
    }

    @Override
    public double getPrice(Booking booking, Vehicle vehicle) {
        return priceStrategy.getPrice(booking, vehicle);
    }

}
