import java.util.Arrays;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DatasetGeneration {
    static Random random = new Random();
    static final String folderPath = "Datasets/";

    public static int[] generateData(int n) {
        int[] val = new int[n];
        int[] wt = new int[n];

        for (int i = 0; i < n; i++) {
            val[i] = random.nextInt(10, 1000 + 1);
            wt[i] = random.nextInt(10, 1000 + 1);
        }

        int sum = Arrays.stream(wt).sum();
        int W = (int) Math.floor(0.5 * sum);

        int[] data = new int[val.length + wt.length + 1];
        data[0] = W;
        System.arraycopy(val, 0, data, 1, val.length);
        System.arraycopy(wt, 0, data, val.length + 1, wt.length);

        return data;
    }

    public static void save(String filename, int[] data) {
        int W = data[0];
        int[] val = Arrays.copyOfRange(data, 1, data.length / 2 + 1);
        int[] wt = Arrays.copyOfRange(data, data.length / 2 + 1, data.length);

        try (PrintWriter writer = new PrintWriter(new FileWriter(folderPath + filename))) {
            writer.println("W: " + W);
            writer.println("val: " + Arrays.toString(val).replace("[", "").replace("]", ""));
            writer.println("wt: " + Arrays.toString(wt).replace("[", "").replace("]", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        int[] n = {100, 1000, 10000};

        for (int size : n) {
            int[] generatedData = generateData(size);
            save("data_" + size + ".txt", generatedData);

            System.out.println("Generated data: " + Arrays.toString(generatedData));

        }
    }

}
