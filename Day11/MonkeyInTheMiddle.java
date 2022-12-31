package Day11;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MonkeyInTheMiddle {

    private static final int round = 0;
    private static final List<Monkey> monkeys = new ArrayList<>();
    private static final List<Integer> divisors = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        final AtomicInteger counter = new AtomicInteger();
        final Collection<List<String>> monkeysCollection = Files.readAllLines(Paths.get("Day11/input.txt"))
                .stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / 7))
                .values();
        List<List<String>> monkeysList = new ArrayList<>(monkeysCollection);
        initializeMonkeys(monkeysList);

        for (int i = round; i < 10_000; i++) {
            inspectItems();
        }
        monkeys.sort(Comparator.comparing(Monkey::getInspectedItems).reversed());
        System.out.println(BigInteger.valueOf(monkeys.get(0).getInspectedItems()).multiply(BigInteger.valueOf(monkeys.get(1).getInspectedItems())));
    }

    private static void inspectItems() {
        BigInteger product = divisors.stream()
                .map(BigInteger::valueOf)
                .reduce(BigInteger.ONE, BigInteger::multiply);  // get common divisor
        for (Monkey monkey : monkeys) {
            Iterator<Long> iterator = monkey.items.iterator();
            while (iterator.hasNext()) {
                Long item = iterator.next();
                long itemWorryLvl = calculateWorryLevel(item, monkey.operation);
//                int itemWorryLvlAfterInspection = itemWorryLvl / 3;   this is part one
                itemWorryLvl %= product.intValue();
                int monkeyToReceive = itemWorryLvl % monkey.testNumber == 0 ? monkey.monkeys[0] : monkey.monkeys[1];
                monkeys.get(monkeyToReceive).items.add(itemWorryLvl);
                iterator.remove();
                monkey.inspectedItems++;
            }
        }
    }

    private static long calculateWorryLevel(long old, String operation) {
        String calculationType = !operation.substring(2).contains("old") ? operation.substring(0, 1) : "old";
        return switch (calculationType) {
            case "*" -> old * (long)Integer.parseInt(operation.substring(2));
            case "+" -> old + (long)Integer.parseInt(operation.substring(2));
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
        List<Long> itemValues = new ArrayList<>();
        for (String i : items) {
            itemValues.add((long)Integer.parseInt(i.trim()));
        }
        String operation = m.get(2).substring(23);
        int testNumber = Integer.parseInt(m.get(3).replaceAll("[^0-9]", ""));
        int[] receivingMonkeys = {Integer.parseInt(m.get(4).replaceAll("[^0-9]", "")), Integer.parseInt(m.get(5).replaceAll("[^0-9]", ""))};
        monkeys.add(new Monkey(itemValues, operation, testNumber, receivingMonkeys));
        divisors.add(testNumber); // part two only
    }


    private static class Monkey {
        List<Long> items;
        String operation;
        Integer testNumber;
        int[] monkeys;
        long inspectedItems = 0;

        public Monkey(List<Long> items, String operation, Integer testNumber, int[] monkeys) {
            this.items = items;
            this.operation = operation;
            this.testNumber = testNumber;
            this.monkeys = monkeys;
        }

        public long getInspectedItems() {
            return inspectedItems;
        }
    }
}
