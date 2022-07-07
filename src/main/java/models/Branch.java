package models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class Branch {
    String id;
    Set<String> vehicleTypes;
    Set<Vehicle> vehicleList;
}
