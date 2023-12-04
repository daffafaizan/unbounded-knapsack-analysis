public class AlgorithmAnalysis {

    public static void analyze(int W, int[] val, int[] wt, Algorithms algorithm) {
        System.out.println("Max value obtained using " + algorithm.name());

        System.gc(); 
        long startTime = System.nanoTime();
        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        int maxValue = algorithm.maxValue(W, val, wt);

        long endTime = System.nanoTime();
        long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        long executionTime = endTime - startTime;
        long memoryUsage = endMemory - startMemory;

        System.out.println("Execution time: " + executionTime / (double) 1000000 + " ms");
        System.out.println("Memory usage: " + memoryUsage / 1024 + " KB");
        System.out.println("Max value: " + maxValue);
    }

    public static void main(String[] args) {
        Algorithms BnBUnboundedKnapsack = new BnBUnboundedKnapsack();
        int W = 1074;
        int[] val = { 1867, 97, 2077, 994, 2194, 2686, 2263, 1138, 22, 1600, 163, 1687, 2275, 2419, 724, 2269, 1228, 682, 1351, 1360, 1150, 637, 256, 2962, 307, 406, 586, 652, 1564, 1540, 1003, 1126, 1849, 52, 2890, 565, 2608, 1300, 1633, 1270, 346, 715, 1120, 166, 622, 1912, 328, 2854, 2908, 2731, 826, 451, 940, 1543, 1030, 145, 2149, 2899, 1873, 49, 1984, 2185, 1855, 385, 1459, 955, 1798, 2464, 262, 421, 1534, 2416, 2314, 1021, 2311, 2119, 2953, 2392, 172, 2512, 1645, 2323, 2287, 808, 847, 940, 2578, 865, 2368, 34, 2245, 1075, 1111, 2905, 1726, 1186, 481, 892, 748, 1453 };
        int[] wt = { 49, 373, 115, 233, 145, 379, 239, 309, 395, 281, 149, 111, 119, 319, 379, 235, 397, 325, 47, 39, 251, 165, 95, 11, 21, 321, 33, 123, 33, 377, 237, 395, 119, 5, 239, 213, 263, 113, 327, 261, 1, 395, 249, 317, 141, 311, 333, 145, 87, 157, 15, 157, 329, 7, 353, 139, 71, 371, 355, 397, 87, 197, 241, 231, 355, 149, 5, 165, 123, 223, 353, 179, 365, 95, 105, 17, 339, 201, 353, 387, 167, 39, 139, 183, 355, 303, 323, 309, 255, 5, 15, 225, 171, 299, 119, 159, 231, 375, 215, 73 };

        analyze(W, val, wt, BnBUnboundedKnapsack);
    }
}