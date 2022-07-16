import org.junit.jupiter.api.Test;
import service.VehicleRentService;
import service.VehicleRentServiceImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VehicleRentTest {

    @Test
    public void testMultiThreading() {
        VehicleRentService vehicleRentService = new VehicleRentServiceImpl();
        ExecutorService executorService = Executors.newFixedThreadPool(2);


        try(BufferedReader br = new BufferedReader(new FileReader("src/main/resources/input.txt"))) {

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
                    Future<Double> result = executorService.submit(() -> vehicleRentService.bookVehicle(inputs[1], inputs[2], Integer.parseInt(inputs[3]),
                            Integer.parseInt(inputs[4])));

                    Future<Double> result1 = executorService.submit(() -> vehicleRentService.bookVehicle(inputs[1],
                            inputs[2], 4,
                            5));
                    System.out.println(result.get());
                    System.out.println(result1.get());

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
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
