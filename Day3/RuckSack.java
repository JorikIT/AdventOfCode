package Day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RuckSack {

    public static void main(String[] args) throws IOException {
        AtomicInteger prioritiesPartOne = new AtomicInteger();
        List<Character> alphabet = "abcdefghijklmnopqrstuvwxyz".chars().mapToObj(c -> (char) c).toList();
        Files.readAllLines(Paths.get("Day3/input.txt")).forEach(line -> {
            String compartmentsOne = line.substring(0, line.length() / 2);
            String compartmentsTwo = line.substring(line.length() / 2);
            prioritiesPartOne.addAndGet(sumPriorities(compartmentsOne, compartmentsTwo, alphabet));
        });

        final AtomicInteger counter = new AtomicInteger();
        AtomicInteger prioritiesPartTwo = new AtomicInteger();
        final Collection<List<String>> groups = Files.readAllLines(Paths.get("Day3/input.txt"))
                .stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / 3))
                .values();
        groups.forEach(group -> prioritiesPartTwo.addAndGet(sumPrioritiesPartTwo(group.get(0), group.get(1), group.get(2), alphabet)));
        System.out.println(prioritiesPartTwo);
    }

    private static int sumPriorities(String compartmentsOne, String compartmentsTwo, List<Character> alphabet) {
        AtomicInteger sumPriorities = new AtomicInteger();
        List<Character> charsCompartmentsOne = compartmentsOne.chars().mapToObj(c -> (char) c).toList();
        List<Character> charsCompartmentsTwo = compartmentsTwo.chars().mapToObj(c -> (char) c).toList();

        charsCompartmentsOne.forEach(letter -> {
            if (charsCompartmentsTwo.contains(letter)) {
                sumPriorities.set(Character.isLowerCase(letter) ? (alphabet.indexOf(letter) + 1) : (alphabet.indexOf(Character.toLowerCase(letter)) + 27));
            }
        });
        return sumPriorities.get();
    }

    private static int sumPrioritiesPartTwo(String ruckSackOne, String ruckSackTwo, String ruckSackThree, List<Character> alphabet) {
        AtomicInteger sum = new AtomicInteger();
        List<Character> charsRuckSackOne = ruckSackOne.chars().mapToObj(c -> (char) c).toList();
        List<Character> charsRuckSackTwo = ruckSackTwo.chars().mapToObj(c -> (char) c).toList();
        List<Character> charsRuckSackThree = ruckSackThree.chars().mapToObj(c -> (char) c).toList();

        charsRuckSackOne.forEach(letter -> {
            if (charsRuckSackTwo.contains(letter) && charsRuckSackThree.contains(letter)) {
                sum.set(Character.isLowerCase(letter) ? (alphabet.indexOf(letter) + 1) : (alphabet.indexOf(Character.toLowerCase(letter)) + 27));
            }
        });
        return sum.get();
    }
}
