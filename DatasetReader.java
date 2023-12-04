import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class DatasetReader {
    public static int[] read(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));

        int W = Integer.parseInt(lines.get(0).split(": ")[1]);
        int[] val = Arrays.stream(lines.get(1).split(": ")[1].split(", "))
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] wt = Arrays.stream(lines.get(2).split(": ")[1].split(", "))
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] result = new int[1 + val.length + wt.length];
        result[0] = W;
        System.arraycopy(val, 0, result, 1, val.length);
        System.arraycopy(wt, 0, result, 1 + val.length, wt.length);

        return result;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "Datasets/data_100.txt"; // Change the file path accordingly

        int[] data = read(filePath);

        int W = data[0];
        int[] val = Arrays.copyOfRange(data, 1, 1 + (data.length - 1) / 2);
        int[] wt = Arrays.copyOfRange(data, 1 + (data.length - 1) / 2, data.length);

        System.out.println("W: " + W);
        System.out.println("val: " + Arrays.toString(val));
        System.out.println("wt: " + Arrays.toString(wt));
    }
}
