public class AlgorithmAnalysis {

    public static void analyze(int W, int[] val, int[] wt, Algorithms algorithm) {
        System.out.println("Dataset sorted using " + algorithm.name());

        System.gc(); 
        long startTime = System.nanoTime();
        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        int maxValue = algorithm.maxValue(val, wt, W);

        long endTime = System.nanoTime();
        long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        long executionTime = endTime - startTime;
        long memoryUsage = endMemory - startMemory;

        System.out.println("Execution time: " + executionTime / (double) 1000000 + " ms");
        System.out.println("Memory usage: " + memoryUsage / 1024 + " KB");
        System.out.println("Max value: " + maxValue);
    }
}