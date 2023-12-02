import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Algorithm Initialization
        Algorithms DPUnboundedKnapsack = new DPUnboundedKnapsack();
        // Algorithms BnBUnboundedKnapsack = new BnBUnboundedKnapsack(); // todo

        // Dataset Preparation
        final String folderPath = "Datasets/";
        String[] types = { "kecil_", "sedang_", "besar_" };
        int[] sizes = { 100, 1000, 10000 };

        for (String type : types) {
            String filename = type + ".txt";
            System.out.println("Processing file: " + filename);

            try {
                int[] array = DatasetReader.read(folderPath + filename);

                // Dataset Analysis
                System.out.println("Analyzing: " + filename);
                System.out.println("---------------------------------------");
                DPUnboundedKnapsack.maxValue(array, sizes, 0); // todo
                System.out.println(" ");
                // BnBUnboundedKnapsack.maxValue(array, sizes, 0); // todo
                // System.out.println("---------------------------------------");

            } catch (IOException e) {
                System.err.println("Error: Unable to access file: " + filename);
            }

            System.out.println(" ");
            System.out.println(" ");
        }

    }
}
