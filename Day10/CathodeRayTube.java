package Day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CathodeRayTube {

    private static int X = 1;
    private static int cycleCounter = 0;
    private static int sum = 0;

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Paths.get("Day10/input.txt"));
        for (String line : input) {
            if (line.contains("addx")) {
                int value = Integer.parseInt(line.split(" ")[1]);
                cycleCounter++;
                checkCycleNumber();
                cycleCounter++;
                checkCycleNumber();
                X += value;
            } else {
                cycleCounter++;
                checkCycleNumber();
            }
        }
        System.out.println(sum);
    }

    private static void checkCycleNumber() {
        if (Arrays.asList(20, 60, 100, 140, 180, 220).contains(cycleCounter)) {
            sum += cycleCounter * X;
        }
    }
}
