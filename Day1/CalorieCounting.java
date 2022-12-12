package Day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CalorieCounting {

    public static void main(String[] args) throws IOException {

        Map<Integer, Integer> caloriesByElves = new HashMap<>();
        AtomicInteger elve = new AtomicInteger(0);
        caloriesByElves.put(elve.intValue(), 0);

        Files.readAllLines(Paths.get("Day1/input.txt"))
                .forEach(line -> {
                    if (line.isEmpty()) {
                        caloriesByElves.put(elve.incrementAndGet(), 0);
                    } else {
                        int calories = Integer.parseInt(line);
                        caloriesByElves.put(elve.intValue(), caloriesByElves.get(elve.intValue()) + calories);
                    }
                });

        // part two
        List<Integer> values = new ArrayList<>(caloriesByElves.values());
        values.sort(Comparator.comparing(Integer::intValue).reversed());
        System.out.println(values.subList(0,3).stream()
                .mapToInt(Integer::intValue)
                .sum());
    }
}
