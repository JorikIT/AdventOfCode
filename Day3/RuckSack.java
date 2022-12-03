package Day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RuckSack {

    public static void main(String[] args) throws IOException {
        AtomicInteger score = new AtomicInteger();
        List<Character> alphabet = "abcdefghijklmnopqrstuvwxyz".chars().mapToObj(c -> (char) c).toList();
        Files.readAllLines(Paths.get("Day3/input.txt")).forEach(line -> {
            String compartmentsOne = line.substring(0, line.length() / 2);
            String compartmentsTwo = line.substring(line.length() / 2);
            score.addAndGet(sumPriorities(compartmentsOne, compartmentsTwo, alphabet));
        });
    }

    private static int sumPriorities(String compartmentsOne, String compartmentsTwo, List<Character> alphabet) {
        AtomicInteger score = new AtomicInteger();
        List<Character> charsCompartmentsOne = compartmentsOne.chars().mapToObj(c -> (char) c).toList();
        List<Character> charsCompartmentsTwo = compartmentsTwo.chars().mapToObj(c -> (char) c).toList();

        charsCompartmentsOne.forEach(letter -> {
            if (charsCompartmentsTwo.contains(letter)) {
                score.set(Character.isLowerCase(letter) ? (alphabet.indexOf(letter) + 1) : (alphabet.indexOf(Character.toLowerCase(letter)) + 27));
            }
        });
        return score.get();
    }
}
