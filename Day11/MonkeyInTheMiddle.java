package Day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MonkeyInTheMiddle {

    private static final int round = 0;
    private static final List<Monkey> monkeys = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        final AtomicInteger counter = new AtomicInteger();
        final Collection<List<String>> monkeysCollection = Files.readAllLines(Paths.get("Day11/input.txt"))
                .stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / 7))
                .values();
        List<List<String>> monkeysList = new ArrayList<>(monkeysCollection);
        initializeMonkeys(monkeysList);

        for (int i = round; i < 20; i++) {
            inspectItems();
        }
        monkeys.sort(Comparator.comparing(Monkey::getInspectedItems).reversed());
        System.out.println(monkeys.get(0).getInspectedItems() * monkeys.get(1).getInspectedItems());
    }

    private static void inspectItems() {
        for (Monkey monkey : monkeys) {
            Iterator<Integer> iterator = monkey.items.iterator();
            while (iterator.hasNext()) {
                Integer item = iterator.next();
                int itemWorryLvl = calculateWorryLevel(item, monkey.operation);
                int itemWorryLvlAfterInspection = itemWorryLvl / 3;
                int monkeyToReceive = itemWorryLvlAfterInspection % monkey.testNumber == 0 ? monkey.monkeys[0] : monkey.monkeys[1];
                monkeys.get(monkeyToReceive).items.add(itemWorryLvlAfterInspection);
                iterator.remove();
                monkey.inspectedItems++;
            }
        }
    }

    private static int calculateWorryLevel(int old, String operation) {
        String calculationType = !operation.substring(2).contains("old") ? operation.substring(0, 1) : "old";
        return switch (calculationType) {
            case "*" -> old  * Integer.parseInt(operation.substring(2));
            case "+" -> old + Integer.parseInt(operation.substring(2));
            case "old" -> old * old;
            default -> old;
        };
    }

    private static void initializeMonkeys(List<List<String>> monkeysInput) {
        monkeysInput.forEach(MonkeyInTheMiddle::createMonkey);
    }

    private static void createMonkey(List<String> m) {
        String item = m.get(1).substring(18);
        List<String> items = item.contains(",") ? Arrays.stream(item.split(",")).toList() : List.of(item);
        List<Integer> itemValues = new ArrayList<>();
        for (String i : items) {
            itemValues.add(Integer.parseInt(i.trim()));
        }
        String operation = m.get(2).substring(23);
        int testNumber = Integer.parseInt(m.get(3).replaceAll("[^0-9]", ""));
        int[] receivingMonkeys = {Integer.parseInt(m.get(4).replaceAll("[^0-9]", "")), Integer.parseInt(m.get(5).replaceAll("[^0-9]", ""))};
        monkeys.add(new Monkey(itemValues, operation, testNumber, receivingMonkeys));
    }


    private static class Monkey {
        List<Integer> items;
        String operation;
        Integer testNumber;
        int[] monkeys;
        int inspectedItems = 0;

        public Monkey(List<Integer> items, String operation, Integer testNumber, int[] monkeys) {
            this.items = items;
            this.operation = operation;
            this.testNumber = testNumber;
            this.monkeys = monkeys;
        }

        public int getInspectedItems() {
            return inspectedItems;
        }
    }
}
