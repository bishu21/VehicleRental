package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vehicle implements Comparable<Vehicle>{
    String id;
    String type;
    double price; /// price per 1 hr

    @Override
    public int compareTo(Vehicle o) {
        return this.price-o.price > 0 ? 1 : -1;
    }
}
