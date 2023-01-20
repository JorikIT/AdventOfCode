package Day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class DistressSignal {

    private static List<Integer> correctOrderIndices = new ArrayList<>();
    private static List<String> correctOrderIndicesString = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        List<String> input = Files.readAllLines(Paths.get("Day13/input.txt"));
        input.removeIf(String::isEmpty);
        List<String> leftStrings = new ArrayList<>();
        List<String> rightStrings = new ArrayList<>();

        leftStrings.add(input.get(0));
        splitInput(input, leftStrings, rightStrings);

        for (int i = 0; i < leftStrings.size(); i++) {
            String left = leftStrings.get(i);
            String right = rightStrings.get(i);
            if (compareStrings(left, right)) {
                correctOrderIndices.add(i + 1);
                correctOrderIndicesString.add(left);
            }
        }
        System.out.println(correctOrderIndices.stream().mapToInt(Integer::intValue).sum());
    }

    private static void splitInput(List<String> input, List<String> leftStrings, List<String> rightStrings) {
        for (int i = 1; i < input.size(); i++) {
            if (i % 2 == 0) {
                leftStrings.add(input.get(i));
            } else {
                rightStrings.add(input.get(i));
            }
        }
    }

    private static boolean compareStrings(String left, String right) {

        boolean correctOrder = true;
        boolean evenLists = false;

//        List<Object> itemsLeft = parseItems(left);
//        List<Object> itemsRight = parseItems(right);

//        long countLeft = left.chars().filter(Character::isDigit).count();
//        long countRight = right.chars().filter(Character::isDigit).count();
//        if (countLeft == 0 && countRight == 0) {
//            long bracketsLeft = left.chars().filter(ch -> ch == '[').count();
//            long bracketsRight = right.chars().filter(ch -> ch == '[').count();
//            if (bracketsLeft < bracketsRight) {
//                return true;
//            } else if (bracketsLeft > bracketsRight) {
//                return false;
//            } else {
//                evenLists = true;
//            }
//        }
        List<Object> objectsLeft = parseInput(left);
        List<Object> objectsRight = parseInput(right);

        for (int i = 0; i < objectsLeft.size() && i < objectsRight.size(); i++) {
            if (!correctOrder) {
                break;
            }
            if (evenLists) {
                continue;
            }
            Object objectLeft = objectsLeft.get(i);
            Object objectRight = objectsRight.get(i);
            // if not correct order do nothing. If left is empty it's automatically in the right order.
            if (objectLeft instanceof ArrayList && objectRight instanceof ArrayList) {
                int sizeLeft = ((ArrayList<?>) objectLeft).size();
                int sizeRight = ((ArrayList<?>) objectRight).size();
                if (sizeLeft == 0 && sizeRight == 0) {
                    continue;
                }
                correctOrder = compareLists((List<Integer>) objectLeft, (List<Integer>) objectRight);
                if (correctOrder) {
                    return true;
                }
            }
            if (objectLeft instanceof ArrayList && objectRight instanceof Integer) {
                ArrayList o = (ArrayList) objectsLeft.get(i);
                if (o.isEmpty()) {
                    return true;
                }
                correctOrder = compareLists((List<Integer>) objectsLeft.get(i), List.of((Integer) objectsRight.get(i)));
                if (correctOrder) {
                    return true;
                }
            }
            if (objectLeft instanceof Integer && objectRight instanceof ArrayList) {
                ArrayList<Object> o = (ArrayList<Object>) objectsRight.get(i);
                if (o.isEmpty()) {
                    return false;
                }
                correctOrder = compareLists(List.of((Integer) objectLeft), (List<Integer>) objectRight);
                if (correctOrder) {
                    return true;
                }
            }
            if (objectLeft instanceof Integer && objectRight instanceof Integer) {
                if ((Integer) objectLeft < (Integer) objectRight) {
                    return true;
                }
                if ((Integer) objectLeft > (Integer) objectRight) {
                    return false;
                }
                correctOrder = compareNumbers((Integer) objectLeft, (Integer) objectRight);
            }
        }

        return correctOrder;
    }

    private static List<Object> parseItems(String line, int index) {

        List<Object> objects = new ArrayList<>();
        char c = line.charAt(index);
        while (c != ']')
        {
            if (c == ',') {
                index++;
            }
            else if (c == '[') {
                objects.add(parseItems(line, index));
            }
            else {
                objects.add(parse(line, index));
            }
            c = line.charAt(index);
        }
        index++;

        return objects;
    }

    public static Integer parse(String line, int index)
    {
        int comma = line.indexOf(",", index);
        comma = comma == -1 ? line.length() : comma;
        int closingBracket = line.indexOf("]", index);
        closingBracket = closingBracket == -1 ? line.length() : closingBracket;
        String numStr = line.substring(index, Math.min(comma, closingBracket));
        index += numStr.length();
        return Integer.parseInt(numStr);
    }

    private static List<Object> parseInput(String input) {

        List<Object> listToCompare = new ArrayList<>();
        boolean openArray = false;
        List<Object> tempListLeft = new ArrayList<>();
        for (int i = 1; i < input.length(); i++) {
            String index = String.valueOf(input.charAt(i));
            if (index.equals("[")) {
                if (!openArray) {
                    openArray = true;
                    tempListLeft = new ArrayList<>();
                } else {
                    if (tempListLeft.size() > 0) {
                        listToCompare.addAll(tempListLeft);
                        tempListLeft = new ArrayList<>();
                    }
                }
            } else if (Character.isDigit(input.charAt(i))) {
                String s = input.substring(i).split(",")[0];
                int num = Integer.parseInt(s.replaceAll("\\D+", ""));
                if (openArray) {
                    tempListLeft.add(num);
                } else {
                    listToCompare.add(num);
                }
                if (num == 10) {
                    i++;
                }
            } else if (index.equals("]")) {
                if (openArray) {
                    listToCompare.add(tempListLeft);
                    openArray = false;
                }
            }
        }
        return listToCompare;
    }

    private static boolean compareLists(List<Integer> left, List<Integer> right) {

        boolean correctOrder = true;
        if (left.isEmpty()) {
            return true;
        } else if (right.isEmpty()) { // wrong order
            return false;
        }
        for (int i = 0; i < left.size() && i < right.size(); i++) {
            List<Integer> subLeft = left.subList(i, left.size());
            List<Integer> subRight = right.subList(i, right.size());
            if (subLeft.isEmpty()) {
                return true;
            } else if (subRight.isEmpty()) {
                return false;
            }
            if (left.get(i) < right.get(i)) {
                return true;
            }
            if (left.get(i) > right.get(i)) {
                return false;
            }
        }

        return correctOrder;
    }

    private static boolean compareNumbers(int left, int right) {
        return left <= right;
    }
}
