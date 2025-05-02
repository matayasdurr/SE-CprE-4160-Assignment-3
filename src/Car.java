class Car extends Vehicle implements Drivable {
    private int numberOfDoors;

    Car(String brand, int speed, int numberOfDoors) {
        super(brand, speed);
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    void displayInfo() {
        System.out.println(this);
    }

    @Override
    public void drive() {
        System.out.println(getBrand() + " Car is driving.");
    }

    @Override
    public String toString() {
        return getBrand() + " Car - Speed: " + getSpeed() + "km/h, Doors: " + numberOfDoors;
    }
}
