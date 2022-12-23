package Day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class RopeBridge {

    private static int[][] ropePositions;
    private static final Set<String> positions = new HashSet<>();

    public static void main(String[] args) throws IOException {

        initializeStartPositions();
        List<String> input = Files.readAllLines(Paths.get("Day9/input.txt"));
        for (String line : input) {
            String direction = line.split(" ")[0];
            int steps = Integer.parseInt(line.split(" ")[1]);

            for (int i = 0; i < steps; i++) {
                updatePosition(direction);
                for (int j = 1; j < ropePositions.length; j++) {
                    if (!connected(j)) {
                        moveTail(j);
                    }
                }
            }
        }
        System.out.println(positions.size());
    }

    private static void initializeStartPositions() {
        // for part one set only fill with two arrays
        ropePositions = new int[][]{{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}};
        positions.add("0 0");
    }

    private static void updatePosition(String move) {
        switch (move) {
            case "R" -> ropePositions[0][1]++;
            case "L" -> ropePositions[0][1]--;
            case "U" -> ropePositions[0][0]++;
            case "D" -> ropePositions[0][0]--;
        }
    }

    public static void moveTail(int i) {
        if (ropePositions[i - 1][0] == ropePositions[i][0]) {
            ropePositions[i][1] = ropePositions[i - 1][1] > ropePositions[i][1] ? ropePositions[i][1] + 1 : ropePositions[i][1] - 1;
        } else if (ropePositions[i - 1][1] == ropePositions[i][1]) {
            ropePositions[i][0] = ropePositions[i - 1][0] > ropePositions[i][0] ? ropePositions[i][0] + 1 : ropePositions[i][0] - 1;
        } else {
            ropePositions[i][1] = ropePositions[i - 1][1] > ropePositions[i][1] ? ropePositions[i][1] + 1 : ropePositions[i][1] - 1;
            ropePositions[i][0] = ropePositions[i - 1][0] > ropePositions[i][0] ? ropePositions[i][0] + 1 : ropePositions[i][0] - 1;
        }
        // for part one add position [0][0] and [0][1]
        positions.add(ropePositions[9][0] + " " + ropePositions[9][1]);
    }

    public static boolean connected(int i) {
        return Math.abs(ropePositions[i - 1][0] - ropePositions[i][0]) <= 1 && Math.abs(ropePositions[i - 1][1] - ropePositions[i][1]) <= 1;
    }

}
