package Day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CathodeRayTube {

    private static int X = 1;
    //    private static int cycleCounter = 0; // part one
    private static int currentIndex = 0;
    //    private static int sum = 0; // part one
    private static StringBuilder currentCTRRow = new StringBuilder();
    private static final List<String> rows = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Paths.get("Day10/input.txt"));
        for (String line : input) {
            if (line.contains("addx")) {
                addX(line);
            } else {
//                    cycleCounter++; // part one
                drawCTRRow();
                currentIndex++;
//                    checkCycleNumber(); // part one
            }
        }
//        System.out.println(sum); // part one
        for (String row : rows) {
            System.out.println(row);
        }
    }

    private static void addX(String line) {
        int value = Integer.parseInt(line.split(" ")[1]);
//        cycleCounter++; // part one
        drawCTRRow();
        currentIndex++;
//        checkCycleNumber(); // part one
//        cycleCounter++; // part one
        drawCTRRow();
        currentIndex++;
//        checkCycleNumber(); // part one
        X += value;  // parte one
    }

    private static void drawCTRRow() {
        if (Arrays.asList(X - 1, X, X + 1).contains(currentIndex)) {
            currentCTRRow.append("#");
        } else {
            currentCTRRow.append(".");
        }
        if (currentCTRRow.length() == 40) {
            rows.add(currentCTRRow.toString());
            currentCTRRow = new StringBuilder();
            currentIndex = -1;
        }
    }
//    private static void checkCycleNumber() { // part one
//        if (Arrays.asList(20, 60, 100, 140, 180, 220).contains(cycleCounter)) {
//            sum += cycleCounter * X;
//        }
//    }
}
