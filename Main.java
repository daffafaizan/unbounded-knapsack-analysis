import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Algorithm Initialization
        Algorithms DPUnboundedKnapsack = new DPUnboundedKnapsack();
        Algorithms BnBUnboundedKnapsack = new BnBUnboundedKnapsack();

        // Dataset Preparation
        final String folderPath = "Datasets/";
        int[] sizes = {100, 1000, 10000};

        for (int size : sizes) {
            String filename = "data_" + size + ".txt";
            System.out.println("Processing file: " + filename);

            try {
                int[] data = DatasetReader.read(folderPath + filename);

                // Accessing the values
                int W = data[0];
                int[] val = Arrays.copyOfRange(data, 1, 1 + (data.length - 1) / 2);
                int[] wt = Arrays.copyOfRange(data, 1 + (data.length - 1) / 2, data.length);

                // Dataset Analysis
                System.out.println("Analyzing: " + filename);
                System.out.println("---------------------------------------");

                // Dynamic Programming
                System.out.println("Max value obtained using " + DPUnboundedKnapsack.name());
                System.out.println("Max value: " + DPUnboundedKnapsack.maxValue(W, val, wt));
                AlgorithmAnalysis.analyze(W, val, wt, DPUnboundedKnapsack);

                System.out.println(" ");

                // Branch and Bound
                System.out.println("Max value obtained using " + BnBUnboundedKnapsack.name());
                System.out.println("Max value: " + BnBUnboundedKnapsack.maxValue(W, val, wt));
                AlgorithmAnalysis.analyze(W, val, wt, BnBUnboundedKnapsack);

                System.out.println("---------------------------------------");

            } catch (IOException e) {
                System.err.println("Error: Unable to access file: " + filename);
            }

            System.out.println(" ");
            System.out.println(" ");
        }
    }
}
