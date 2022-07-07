package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Booking {
    Integer id;
    String branchId;
    String vehicleId;
    Integer startTime;
    Integer endTime;
    double price;
}
