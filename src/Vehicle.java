/**
 * Abstract class representing a generic Vehicle.
 */
abstract class Vehicle {
    private String brand;
    private int speed;

    Vehicle(String brand, int speed) {
        this.brand = brand;
        this.speed = speed;
    }

    public String getBrand() {
        return brand;
    }

    public int getSpeed() {
        return speed;
    }

    abstract void displayInfo();
}
