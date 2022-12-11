package Day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class NoSpaceLeft {

    private static final String CD = "$ cd ";
    private static final String dirUp = "$ cd ..";
    private static final String LS = "$ ls";
    public static final String DIR = "dir";

    private static List<Folder> allFolders;

    private static Folder currentFolder;

    public static void main(String[] args) throws IOException {

        List<String> input = Files.readAllLines(Paths.get("Day7/input.txt"));
        allFolders = new ArrayList<>();
        allFolders.add(createRootFolder());
        currentFolder = allFolders.get(0);
        input.remove(0);

        createTree(input);
        sumSubFolders(allFolders.get(0));

        List<Folder> filteredList = allFolders.stream().filter(folder -> folder.getTotalSize() < 100_000).toList();
        int sum = filteredList.stream().mapToInt(Folder::getTotalSize).sum();
        System.out.println(sum);
    }

    private static void sumSubFolders(Folder folder) {
        if (folder.getSubFolders() != null && !folder.getSubFolders().isEmpty()) {
            for (Folder subFolder : folder.getSubFolders()) {
                sumSubFolders(subFolder);
                folder.setTotalSize(folder.getTotalSize() + subFolder.getTotalSize());
            }
        }
    }

    private static void createTree(List<String> input) {
        for (String line : input) {
            if (line.contains(DIR)) {
                currentFolder.getSubFolders().add(createFolder(line)); // add new subfolder
            } else if (switchCommand(line)) {
                switchToFolder(line);
            } else if (isFileName(line)) {
                currentFolder.setTotalSize(currentFolder.getTotalSize() + parseFileSize(line));
                currentFolder.getFileSizes().add(parseFileSize(line)); // add new file
            } else if (line.equals(dirUp)) {
                currentFolder = currentFolder.getParent();
            }
        }
    }

    private static boolean isFileName(String line) {
        return !line.contains(CD) && !line.contains(LS) && !line.contains(DIR);
    }

    private static boolean switchCommand(String line) {
        return line.contains(CD) && !line.equals(dirUp);
    }

    private static void switchToFolder(String line) {
        List<Folder> subFolders = currentFolder.getSubFolders();
        for (Folder folder : subFolders) {
            if (folder.getName().equals(filterDirNameSwitch(line))) {
                folder.setParent(currentFolder);
                currentFolder = folder;
            }
        }
    }

    private static Folder createFolder(String line) {
        Folder folder = new Folder(filterDirNameCreate(line));
        allFolders.add(folder);
        return folder;
    }

    private static String filterDirNameCreate(String dirName) {
        return dirName.substring(dirName.lastIndexOf(dirName.split(" ")[1]));
    }

    private static String filterDirNameSwitch(String dirName) {
        return dirName.substring(dirName.lastIndexOf(dirName.split(" ")[2]));
    }

    private static Integer parseFileSize(String line) {
        return Integer.parseInt(line.split(" ")[0]);
    }

    private static Folder createRootFolder() {
        Folder folder = new Folder("/");
        folder.setSubFolders(new ArrayList<>());
        folder.setFileSizes(new ArrayList<>());
        return folder;
    }

    static class Folder {
        private String name;
        private Folder parent;
        private List<Folder> subFolders;
        private List<Integer> fileSizes;
        private int totalSize;
        public Folder(String name) {
            setName(name);
            setParent(currentFolder);
            this.subFolders = new ArrayList<>();
            this.fileSizes = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Folder getParent() {
            return parent;
        }

        public void setParent(Folder parent) {
            this.parent = parent;
        }

        public List<Folder> getSubFolders() {
            return subFolders;
        }

        public void setSubFolders(List<Folder> subFolders) {
            this.subFolders = subFolders;
        }

        public List<Integer> getFileSizes() {
            return fileSizes;
        }

        public void setFileSizes(List<Integer> fileSizes) {
            this.fileSizes = fileSizes;
        }

        public int getTotalSize() {
            return totalSize;
        }

        public void setTotalSize(int totalSize) {
            this.totalSize = totalSize;
        }

    }
}
