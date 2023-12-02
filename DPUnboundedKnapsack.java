// Adapted from https://www.geeksforgeeks.org/unbounded-knapsack-repetition-items-allowed/

public class DPUnboundedKnapsack implements Algorithms {

    @Override
    public int maxValue(int[] val, int[] wt, int W) {
        int n = val.length;
        int[] dp = new int[W + 1];

        for (int i = 0; i <= W; i++) {
            for (int j = 0; j < n; j++) {
                if (wt[j] <= i) {
                    dp[i] = Math.max(dp[i], dp[i - wt[j]] + val[j]);
                }
            }
        }

        return dp[W];
    }

    public void main(String[] args) {
        int knapsackCapacity = 50;
        int[] itemValues = { 60, 100, 120 };
        int[] itemWeights = { 10, 20, 30 };

        int maxValue = maxValue(itemValues, itemWeights, knapsackCapacity);

        System.out.println("Max value: " + maxValue);
    }
}