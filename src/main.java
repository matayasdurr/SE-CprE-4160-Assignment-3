import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Drivable> vehicles = new ArrayList<>();
        vehicles.add(new Car("Toyota", 120, 4));
        vehicles.add(new Motorcycle("Harley-Davidson", 100, false));

        VehicleService service = new VehicleService();

        for (Drivable v : vehicles) {
            v.drive();
            service.updateVehicleDetails(v);
        }
    }
}
