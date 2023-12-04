import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BnBUnboundedKnapsack implements Algorithms {

    private int W;
    private int[] val;
    private int[] wt;

    private int n;
    private int[][] M;
    private int[] best_solution;
    private int best_value;

    @Override
    public String name() {
        return "Branch and Bound";
    }

    // Helper Functions
    public void procedure_one() {
        List<Integer> N = new ArrayList<>();
        for (int i = 0; i < this.n; i++) {
            N.add(i);
        }

        int j = 0;
        while (j < N.size() - 1) {
            int k = j + 1;
            while (k < N.size()) {
                if (Math.floor(this.wt[N.get(k)] / this.wt[N.get(j)]) * this.val[N.get(j)] >= this.val[N.get(k)]) {
                    N.remove(k);
                } else if (Math.floor(this.wt[N.get(j)] / this.wt[N.get(k)]) * this.val[N.get(k)] >= this.val[N.get(j)]) {
                    N.remove(j);
                    k = N.size();
                } else {
                    k++;
                }
            }
            j++;
        }

        int[] newVal = new int[N.size()];
        int[] newWt = new int[N.size()];
        
        for (int i = 0; i < N.size(); i++) {
            newVal[i] = this.val[N.get(i)];
            newWt[i] = this.wt[N.get(i)];
        }

        this.n = N.size();
        this.val = newVal;
        this.wt = newWt;
    }

    private int[] rearrangeArray(int[] array, Integer[] indices) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[indices[i]];
        }
    
        return result;
    }

    private void bubbleSort(Integer[] arr, double[] ratios) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (ratios[arr[j]] < ratios[arr[j + 1]]) {
                    // Swap indices
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    
    public void sortItemsByRatio() {
        Integer[] indices = new Integer[this.n];
        for (int i = 0; i < this.n; i++) {
            indices[i] = i;
        }
    
        double[] ratios = new double[this.n];
        for (int i = 0; i < this.n; i++) {
            ratios[i] = (double) this.val[i] / this.wt[i];
        }
    
        bubbleSort(indices, ratios);
    
        this.val = rearrangeArray(this.val, indices);
        this.wt = rearrangeArray(this.wt, indices);
    }

    public int upperBound(int WPrime, int VN, int i) {
        int n = this.n;
    
        if (i + 2 < n) {    
            int v1 = this.val[i];
            int w1 = this.wt[i];
            int v2 = this.val[i + 1];
            int w2 = this.wt[i + 1];
            int v3 = this.val[i + 2];
            int w3 = this.wt[i + 2];
    
            int zPrime = VN + (WPrime / w2) * v2;
            int WDoublePrime = WPrime - (WPrime / w2) * w2;
            int UPrime = zPrime + (WDoublePrime * v3 / w3);
    
            int WDoublePrimeAdjusted = WDoublePrime + (int) Math.ceil((1.0 / w1) * (w2 - WDoublePrime)) * w1;
            int UDoublePrime = zPrime + (int) Math.floor((WDoublePrimeAdjusted * v2 / w2) - Math.ceil((1.0 / w1) * (w2 - WDoublePrime)) * v1);
    
            int result = Math.max(UPrime, UDoublePrime);
            return result;
        } else {
            return VN;
        }
    }
    
    // Tahapan
    public Object[] initialize() {
        this.procedure_one();
        this.sortItemsByRatio();

        this.M = new int[val.length][W + 1];
        this.best_solution = new int[this.n];
        this.best_value = 0;

        int[] x = new int[this.n];
        int i = 0;
        x[0] = this.W / this.wt[0];
        int VN = this.val[0] * x[0];
        int WPrime = this.W - this.wt[0] * x[0];

        int U = upperBound(WPrime, VN, i);
        this.best_value = VN;
        System.arraycopy(x, 0, this.best_solution, 0, this.n);

        int[] m = new int[this.n];
        for (int p = 0; p < this.n; p++) {
            int minW = Integer.MAX_VALUE;
            for (int j = p + 1; j < this.n; j++) {
                int w = this.wt[j];
                if (w < minW) {
                    minW = w;
                }
            }
            m[p] = minW;
        }

        return new Object[]{x, i, VN, WPrime, U, m};

    }

    public Object[] develop(int[] x, int i, int VN, int WPrime, int U, int[] m) {
        final int finalWPrime = WPrime;

        while (true) {
            if (WPrime < m[i]) {
                if (this.best_value < VN) {
                    this.best_value = VN;
                    this.best_solution = Arrays.copyOf(x, x.length);
                    if (this.best_value == U) {
                        return new Object[]{x, i, VN, WPrime, "finish"};
                    }
                }
                return new Object[]{x, i, VN, WPrime, "backtrack"};
            } else {
                int min_j = -1;
                for (int j = i + 1; j < this.n; j++) {
                    if (this.wt[j] <= finalWPrime && (min_j == -1 || this.wt[j] < this.wt[min_j])) {
                        min_j = j;
                    }
                }

                if (min_j == -1 || VN + upperBound(WPrime, VN, min_j) <= this.best_value) {
                    return new Object[]{x, i, VN, WPrime, "backtrack"};
                }

                if (this.M[i][WPrime] >= VN) {
                    return new Object[]{x, i, VN, WPrime, "backtrack"};
                }

                x[min_j] = WPrime / this.wt[min_j];
                VN += this.val[min_j] * x[min_j];
                WPrime -= this.wt[min_j] * x[min_j];
                this.M[i][WPrime] = VN;
                i = min_j;
            }
        }
    }

    
    public Object[] backtrack(int[] x, int i, int VN, int WPrime, int[] m) {
        while (true) {
            int max_j = -1;
            for (int j = 0; j <= i; j++) {
                if (x[j] > 0) {
                    max_j = j;
                }
            }

            if (max_j == -1) {
                return new Object[]{x, i, VN, WPrime, "finish"};
            }

            i = max_j;
            x[i] -= 1;
            VN -= this.val[i];
            WPrime += this.wt[i];

            if (WPrime < m[i]) {
                continue;
            }
            if (VN + Math.floor(WPrime * this.val[i + 1] / this.wt[i + 1]) <= this.best_value) {
                VN -= this.val[i] * x[i];
                WPrime += this.wt[i] * x[i];
                x[i] = 0;
                continue;
            }

            if (WPrime >= m[i]) {
                return new Object[]{x, i, VN, WPrime, "develop"};
            }
        }
    }

    public Object[] replace(int[] x, int i, int VN, int WPrime, int[] m) {
        int j = i;
        int h = j + 1;
    
        while (true) {
            if (this.best_value >= VN + Math.floor(WPrime * this.val[h] / this.wt[h])) {
                return new Object[]{x, i, VN, WPrime, "backtrack"};
            }
    
            if (this.wt[h] >= this.wt[j]) {
                if (this.wt[h] == this.wt[j] || this.wt[h] > WPrime || this.best_value >= VN + this.val[h]) {
                    h++;
                    continue;
                }
    
                this.best_value = VN + this.val[h];
                this.best_solution = Arrays.copyOf(x, x.length);
                x[h] = 1;
    
                if (this.best_value == this.upperBound(WPrime, VN, h)) {
                    return new Object[]{x, i, VN, WPrime, "finish"};
                }
    
                j = h;
                h++;
    
                continue;
            } else {
                if (WPrime - this.wt[h] < m[h - 1]) {
                    h++;
    
                    continue;
                }
    
                i = h;
                x[i] = WPrime / this.wt[i];
                VN += this.val[i] * x[i];
                WPrime -= this.wt[i] * x[i];
    
                return new Object[]{x, i, VN, WPrime, "develop"};
            }
        }
    }
    
    public Object[] branchAndBound() {
        int[] x, m;
        int i, VN, WPrime, U;

        Object[] initializationResult = initialize();
        x = (int[]) initializationResult[0];
        i = (int) initializationResult[1];
        VN = (int) initializationResult[2];
        WPrime = (int) initializationResult[3];
        U = (int) initializationResult[4];
        m = (int[]) initializationResult[5];

        String nextStep = "develop";
    
        while (true) {
            switch (nextStep) {
                case "develop":
                    Object[] developResult = develop(x, i, VN, WPrime, U, m);

                    x = (int[]) developResult[0];
                    i = (int) developResult[1];
                    VN = (int) developResult[2];
                    WPrime = (int) developResult[3];
                    nextStep = (String) developResult[4];
                    break;
    
                case "backtrack":
                    Object[] backtrackResult = backtrack(x, i, VN, WPrime, m);

                    x = (int[]) backtrackResult[0];
                    i = (int) backtrackResult[1];
                    VN = (int) backtrackResult[2];
                    WPrime = (int) backtrackResult[3];
                    nextStep = (String) backtrackResult[4];

                    break;
    
                case "finish":
                    return new Object[]{Arrays.copyOf(this.best_solution, this.best_solution.length), this.best_value};
    
                case "replace":
                    Object[] replaceResult = replace(x, i, VN, WPrime, m);

                    x = (int[]) replaceResult[0];
                    i = (int) replaceResult[1];
                    VN = (int) replaceResult[2];
                    WPrime = (int) replaceResult[3];
                    nextStep = (String) replaceResult[4];
                    break;
            }
        }
    }
    
    @Override
    public int maxValue(int W, int[] val, int[] wt) {
        this.W = W;
        this.val = val;
        this.wt = wt;

        this.n = val.length;

        Object[] solution = branchAndBound();
        int[] best_solution = (int[]) solution[0];
        int best_value = (int) solution[1];

        return best_value;
    }

    public void main(String[] args) {
        int knapsackCapacity = 50;
        int[] itemValues = { 60, 100, 120 };
        int[] itemWeights = { 10, 20, 30 };

        int maxValue = maxValue(knapsackCapacity, itemValues, itemWeights);

        System.out.println("Max value: " + maxValue);
    }
}