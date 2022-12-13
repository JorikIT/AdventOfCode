package Day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreetopTreeHouse {

    public static void main(String[] args) throws IOException {

        int visibleTrees = 0;
        int bestView;

        List<String> input = Files.readAllLines(Paths.get("Day8/input.txt"));

        visibleTrees = getVisibleTrees(visibleTrees, input);
        bestView = getBestView(input);

        int rowLength = input.get(0).length();
        int visibleTreesEdge = (input.size() * 2) + (rowLength * 2) - 4;
        int sum = visibleTrees + visibleTreesEdge;
        System.out.println(sum);
        System.out.println("best view: " + bestView);
    }

    private static int getBestView(List<String> input) {
        int bestView = 0;

        for (int i = 0; i < input.size(); i++) {
            List<Character> horizontalTrees = new ArrayList<>(input.get(i).chars().mapToObj(c -> (char) c).toList()); // get horizontal trees.

            for (int j = 0; j < horizontalTrees.size() - 1; j++) {
                List<Character> horizontalTreesLeft = new ArrayList<>(horizontalTrees.subList(0, j)); // get horizontal trees at the left.
                List<Character> horizontalTreesRight = new ArrayList<>(horizontalTrees.subList(j + 1, horizontalTrees.size())); // get horizontal trees at the right.

                List<Character> verticalTrees = getColumnTrees(input, j); // get all vertical trees for horizontalIndex without horizontalIndex
                List<Character> verticalTreesTop = new ArrayList<>(verticalTrees.subList(0, i)); // get all vertical trees for horizontalIndex without horizontalIndex
                List<Character> verticalTreesBottom = new ArrayList<>(verticalTrees.subList(i + 1, verticalTrees.size())); // get all vertical trees for horizontalIndex without horizontalIndex
                // get horizontalIndex to consider
                int scenicScore = getScenicScore(horizontalTrees.get(j), verticalTreesBottom, verticalTreesTop, horizontalTreesLeft, horizontalTreesRight);
                bestView = Math.max(bestView, scenicScore);
            }
        }

        return bestView;
    }

    private static int getScenicScore(Character character, List<Character> verticalTreesBottom, List<Character> verticalTreesTop,
                                      List<Character> horizontalTreesLeft, List<Character> horizontalTreesRight) {

        Collections.reverse(verticalTreesTop);
        Collections.reverse(horizontalTreesLeft);

        int verticalUp = sight(character, verticalTreesTop);
        int verticalDown = sight(character, verticalTreesBottom);
        int horizontalLeft = sight(character, horizontalTreesLeft);
        int horizontalRight = sight(character, horizontalTreesRight);

        return verticalUp * verticalDown * horizontalLeft * horizontalRight;
    }

    private static int sight(Character height, List<Character> trees) {
        int sight = 0;
        if (!trees.isEmpty()) {
            for (Character c : trees) {
                if (Character.getNumericValue(height) > Character.getNumericValue(c)) {
                    sight++;
                }
                else {
                    sight++;
                    break;
                }
            }
        }
        return sight;
    }

    private static int getVisibleTrees(int visibleTrees, List<String> input) {
        for (int i = 1; i < input.size() - 1; i++) {
            List<Character> horizontalTrees = new ArrayList<>(input.get(i).chars().mapToObj(c -> (char) c).toList()); // get horizontal trees.

            for (int j = 1; j < horizontalTrees.size() - 1; j++) {
                List<Character> horizontalTreesLeft = new ArrayList<>(horizontalTrees.subList(0, j)); // get horizontal trees at the left.
                List<Character> horizontalTreesRight = new ArrayList<>(horizontalTrees.subList(j + 1, horizontalTrees.size())); // get horizontal trees at the right.
                List<Character> verticalTrees = getColumnTrees(input, j); // get all vertical trees for horizontalIndex without horizontalIndex
                List<Character> verticalTreesTop = new ArrayList<>(verticalTrees.subList(0, i)); // get all vertical trees for horizontalIndex without horizontalIndex
                List<Character> verticalTreesBottom = new ArrayList<>(verticalTrees.subList(i + 1, verticalTrees.size())); // get all vertical trees for horizontalIndex without horizontalIndex
                // get horizontalIndex to consider
                if (isVisible(horizontalTrees.get(j), horizontalTreesLeft) || isVisible(horizontalTrees.get(j), horizontalTreesRight)
                        || isVisible(horizontalTrees.get(j), verticalTreesTop) || isVisible(horizontalTrees.get(j), verticalTreesBottom)) {
                    visibleTrees++;
                }
            }

        }
        return visibleTrees;
    }

    private static List<Character> getColumnTrees(List<String> input, int index) {
        List<Character> columnTrees = new ArrayList<>();
        input.forEach(row -> {
            List<Character> characters = row.chars().mapToObj(c -> (char) c).toList();
            columnTrees.add(characters.get(index));
        });
        return columnTrees;
    }

    private static boolean isVisible(Character currentTree, List<Character> line) {
        int current = Character.getNumericValue(currentTree);
        List<Integer> heightList = new ArrayList<>(line.stream().map(Character::getNumericValue).toList());
        Integer max = Collections.max(heightList);
        return current > max;
    }
}
