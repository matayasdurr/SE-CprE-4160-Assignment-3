class Motorcycle extends Vehicle implements Drivable {
    private boolean hasSidecar;

    Motorcycle(String brand, int speed, boolean hasSidecar) {
        super(brand, speed);
        this.hasSidecar = hasSidecar;
    }

    @Override
    void displayInfo() {
        System.out.println(this);
    }

    @Override
    public void drive() {
        System.out.println(getBrand() + " Motorcycle is driving.");
    }

    @Override
    public String toString() {
        return getBrand() + " Motorcycle - Speed: " + getSpeed() + "km/h, Sidecar: " + hasSidecar;
    }
}
