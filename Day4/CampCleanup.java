package Day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CampCleanup {

    public static void main(String[] args) throws IOException {

        AtomicInteger sum = new AtomicInteger();
        Files.readAllLines(Paths.get("Day4/input.txt")).forEach(line -> {
            List<String> assignments = Arrays.stream(line.split(",")).toList();
            String assignmentElfOne = assignments.get(0);
            String assignmentElfTwo = assignments.get(1);
            int minRangeOne = Integer.parseInt(assignmentElfOne.split("-")[0]);
            int maxRangeOne = Integer.parseInt(assignmentElfOne.split("-")[1]);
            int minRangeTwo = Integer.parseInt(assignmentElfTwo.split("-")[0]);
            int maxRangeTwo = Integer.parseInt(assignmentElfTwo.split("-")[1]);

            if (minRangeOne <= minRangeTwo && maxRangeOne >= maxRangeTwo || minRangeTwo <= minRangeOne && maxRangeTwo >= maxRangeOne) {
                sum.getAndIncrement();
            }
        });
    }
}


