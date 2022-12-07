package Day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TuningTrouble {

    public static void main(String[] args) throws IOException {

        List<Character> characters = Files.readString(Path.of("Day6/input.txt")).chars().mapToObj(c -> (char) c).toList();
        tune(characters, 3);
        tune(characters, 13);
    }

    private static void tune(List<Character> characters, int marker) {
        for (int i = marker, j = 0; i < characters.size(); i++, j++) {
            boolean unique = unique(characters.subList(j, i + 1));
            if (unique) {
                System.out.println(i + 1);
                break;
            }
        }
    }

    private static boolean unique(List<Character> characters) {
        return characters.size() == characters.stream().distinct().toList().size();
    }
}
