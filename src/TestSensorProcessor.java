public class TestSensorProcessor {
    public static void main(String[] args) {
        double[][][] data = {
            { {1.0, 2.0}, {3.0, 4.0} },
            { {5.0, 6.0}, {7.0, 8.0} }
        };

        double[][] limit = {
            { 1.0, 1.5 },
            { 2.0, 2.5 }
        };

        SensorDataProcessor processor = new SensorDataProcessor(data, limit);
        processor.calculate(2.0);
    }
}
