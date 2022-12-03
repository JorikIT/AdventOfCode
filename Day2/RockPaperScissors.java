package Day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class RockPaperScissors {

    public static void main(String[] args) throws IOException {

        AtomicInteger score = new AtomicInteger();
        Files.readAllLines(Paths.get("/Users/Jorik/Documents/IntelliJ/AdventOfCode/Day2/puzzleInput.txt")).forEach(input -> {
            String[] s = input.split(" ");
            score.addAndGet(sumScore(s[0], s[1]));
        });
    }

    private static int sumScore(String opponent, String you) {
        String rock = "A"; // win = 7, draw = 4, lose = 1
        String paper = "B"; // win = 8, draw = 5, lose = 2
        // scissor win = 9, draw = 6, lose = 3
        String win = "Z";
        String draw = "Y";

        if (opponent.equals(rock)) {
            return you.equals(win) ? 8 : you.equals(draw) ? 4 : 3;
        }
        if (opponent.equals(paper)) {
            return you.equals(win) ? 9 : you.equals(draw) ? 5 : 1;
        }
        return you.equals(win) ? 7 : you.equals(draw) ? 6 : 2;
    }
}
