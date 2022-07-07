package service;

import models.Booking;
import models.Vehicle;

public class MoreBookingSurgePriceStrategy extends SurgePriceStrategy {
    public MoreBookingSurgePriceStrategy(PriceStrategy priceStrategy) {
        super(priceStrategy);
    }

    public double getPrice(Booking booking, Vehicle vehicle) {
        double price = super.getPrice(booking, vehicle);
        price += 0.1 * price;
        return price;
    }

}
