import service.VehicleRentService;
import service.VehicleRentServiceImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VehicleRentApp {
    public static void main(String[] args) {

        VehicleRentService vehicleRentService = new VehicleRentServiceImpl();


        try(BufferedReader br = new BufferedReader(new FileReader("src/main/resources/"+args[0]))) {

            String line = br.readLine();
            while (line!= null) {
                System.out.println(line);
                String[] inputs = line.split(" ");

                String action = inputs[0];
                if (action.equals("ADD_BRANCH")) {

                    System.out.println(vehicleRentService.addBranch(inputs[1], new HashSet<>(
                            Arrays.asList(inputs[2].split(",")))));

                } else if(action.equals("ADD_VEHICLE")) {

                    System.out.println(vehicleRentService.addVehicle(inputs[1], inputs[2], inputs[3],
                            Integer.parseInt(inputs[4])));

                } else if(action.equals("BOOK")) {

                    System.out.println(vehicleRentService.bookVehicle(inputs[1], inputs[2], Integer.parseInt(inputs[3]),
                            Integer.parseInt(inputs[4])));

                } else if (action.equals("DISPLAY_VEHICLES")) {
                    System.out.println(vehicleRentService.getAvailableVehicle(inputs[1], Integer.parseInt(inputs[2]),
                            Integer.parseInt(inputs[3])));

                } else {
                    System.out.println("Bad input given "+ action);
                    System.out.println();
                }

                line = br.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
