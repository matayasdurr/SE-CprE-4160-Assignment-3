import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Processes 3D sensor data with configurable limits and writes stats to a file.
 */
public class SensorDataProcessor {

    private double[][][] data;
    private double[][] limit;

    public SensorDataProcessor(double[][][] data, double[][] limit) {
        this.data = data;
        this.limit = limit;
    }

    /**
     * Calculates the average of a 1D array.
     */
    private double average(double[] array) {
        if (array == null || array.length == 0) return 0;

        double sum = 0;
        for (double v : array) {
            sum += v;
        }
        return sum / array.length;
    }

    /**
     * Processes and transforms sensor data, writing output to "RacingStatsData.txt".
     */
    public void calculate(double d) {
        int i, j, k;
        double[][][] data2 = new double[data.length][data[0].length][data[0][0].length];

        try (BufferedWriter out = new BufferedWriter(new FileWriter("RacingStatsData.txt"))) {

            for (i = 0; i < data.length; i++) {
                for (j = 0; j < data[0].length; j++) {
                    for (k = 0; k < data[0][0].length; k++) {

                        double original = data[i][j][k];
                        double adjusted = original / d - Math.pow(limit[i][j], 2.0);
                        data2[i][j][k] = adjusted;

                        double avgData2 = average(data2[i][j]);
                        double avgData = average(data[i][j]);

                        if (avgData2 > 10 && avgData2 < 50) {
                            break;
                        }

                        if (Math.max(original, adjusted) > original) {
                            break;
                        }

                        double cubeOriginal = Math.pow(Math.abs(original), 3);
                        double cubeAdjusted = Math.pow(Math.abs(adjusted), 3);

                        if (cubeOriginal < cubeAdjusted && avgData < adjusted && (i + 1) * (j + 1) > 0) {
                            data2[i][j][k] *= 2;
                        }
                    }
                }
            }

            // Write data2 contents to the output file
            for (i = 0; i < data2.length; i++) {
                for (j = 0; j < data2[0].length; j++) {
                    for (k = 0; k < data2[0][0].length; k++) {
                        out.write(data2[i][j][k] + "\t");
                    }
                    out.write("\n");
                }
            }

        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
