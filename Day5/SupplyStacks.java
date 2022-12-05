package Day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SupplyStacks {

    public static void main(String[] args) throws IOException {

        Map<Integer, List<String>> cratesMap = new HashMap<>();
        Files.readAllLines(Paths.get("Day5/crates.txt"))
                .forEach(line -> {
                            int index = 0;
                            while (index < line.length()) {
                                if (cratesMap.get((index / 4) + 1) == null) {
                                    cratesMap.put((index / 4) + 1, new ArrayList<>(List.of(filteredCrateChar(line, index))));
                                } else {
                                    cratesMap.get((index / 4) + 1).add(filteredCrateChar(line, index));
                                }
                                index += 4;
                            }
                        }
                );

        cratesMap.values().forEach(list -> {
            list.remove(list.size() - 1);
            list.removeIf(String::isEmpty);
        });

        Files.readAllLines(Paths.get("Day5/movements.txt"))
                .forEach(move -> {
                    List<String> movement = filterDigits(move);
                    moveCrates(cratesMap, Integer.parseInt(movement.get(0)), Integer.parseInt(movement.get(1)), Integer.parseInt(movement.get(2)));
                });
    }

    private static String filteredCrateChar(String line, int index) {
        return line.substring(index, Math.min(index + 4, line.length())).replaceAll("[^a-zA-Z0-9]", "");
    }

    private static List<String> filterDigits(String line) {
        return Arrays.stream(line.split(" ")).filter(s -> s.matches("[0-9]+")).toList();
    }

    private static void moveCrates(Map<Integer, List<String>> cratesMap, int amount, int from, int to) {
        for (int i = 0; i < amount; i++) {
            String crateToMove = cratesMap.get(from).get(0);
            cratesMap.get(to).add(0, crateToMove);
            cratesMap.get(from).remove(crateToMove);
        }
    }
}
