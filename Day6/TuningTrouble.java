package Day6;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class TuningTrouble {

    public static final int DEFAULT_BUFFER_SIZE = 8192;

    public static void main(String[] args) throws IOException {

        InputStream input = Files.newInputStream(Path.of("Day6/input.txt"), new StandardOpenOption[]{StandardOpenOption.READ});

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int length;
        while ((length = input.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        List<Character> characters = result.toString().chars().mapToObj(c -> (char) c).toList();
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
        int size = characters.size();
        return characters.stream().distinct().toList().size() == size;
    }
}
