package Day12;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class HillClimbing {

    private static Point startPosition;
    private static Point bestSignal;
    private static final Map<Point, Integer> river = new HashMap<>();
    private static final Map<Point, Integer> shortestPathMap = new HashMap<>();
    private static final List<Point> positions = new ArrayList<>();
    private static int LENGTH;
    private static int HEIGHT;

    public static void main(String[] args) throws IOException {

        List<String> input = Files.readAllLines(Paths.get("Day12/input.txt"));
        LENGTH = input.get(0).length();
        HEIGHT = input.size();
        Map<Character, Integer> numericAlphabet = fillNumericAlphabetMap();

        int line = 0;
        for (String s : input) {
            List<Character> chars = s.chars().mapToObj(c -> (char) c).toList();
            for (int index = 0; index < LENGTH; index++) {
                fillRiverMap(numericAlphabet, line, chars, index);
            }
            line++;
        }
        checkPath(startPosition, bestSignal);
    }

    private static void fillRiverMap(Map<Character, Integer> numericAlphabet, int line, List<Character> chars, int index) {
        if (String.valueOf(chars.get(index)).equals("S")) {
            startPosition = new Point(line, index);
            river.put(new Point(line, index), 0); // S = 'a' = '0'
        } else if (String.valueOf(chars.get(index)).equals("E")) {
            bestSignal = new Point(line, index);
            river.put(new Point(line, index), 26);  // "E" = 'z' = 26
        } else {
            river.put(new Point(line, index), numericAlphabet.get(chars.get(index))); // get numeric value of char
        }
    }

    public static void checkPath(Point start, Point bestSignal) {

        positions.add(start); // positions to check path from
        shortestPathMap.put(start, 0); // shortest road to start position is 0

        while (positions.size() > 0) {
            Point current = positions.remove(0);

            if (current.x != 0) { // prevent NPE for getting -1
                Point up = new Point(current.x - 1, current.y);
                findShortestPath(current, up);
            }
            if (current.x != HEIGHT - 1) {
                Point down = new Point(current.x + 1, current.y);
                findShortestPath(current, down);
            }
            if (current.y != 0) {
                Point left = new Point(current.x, current.y - 1);
                findShortestPath(current, left);
            }
            if (current.y != LENGTH - 1) {
                Point right = new Point(current.x, current.y + 1);
                findShortestPath(current, right);
            }
        }
        System.out.println(shortestPathMap.get(bestSignal)); // get the shortest path to the best signal wave
    }

    public static void findShortestPath(Point current, Point direction) {
        int riverHeight = river.get(direction); // get height of direction wave
        if (riverHeight - river.get(current) <= 1) { // check if movement is possible
            int valueShortestPath = shortestPathMap.get(current) + 1;
            if (shortestPathMap.getOrDefault(direction, Integer.MAX_VALUE) > valueShortestPath) { // if path to dir is unvisited or higher than current path to dir
                positions.add(direction); // add direction again to list to override previous values for movements
//                shortestPathMap.put(direction, valueShortestPath); // update the shortest path to dir from start. Part One
                shortestPathMap.put(direction, riverHeight == 0 ? 0 : valueShortestPath); // don't plus when dir is 0 / 'a'.
            }
        }
    }

    private static Map<Character, Integer> fillNumericAlphabetMap() {
        List<Character> alphabet = "abcdefghijklmnopqrstuvwxyz".chars().mapToObj(c -> (char) c).toList();
        Map<Character, Integer> numericAlphabet = new HashMap<>();
        alphabet.forEach(character -> numericAlphabet.put(character, alphabet.indexOf(character)));
        return numericAlphabet;
    }
}
