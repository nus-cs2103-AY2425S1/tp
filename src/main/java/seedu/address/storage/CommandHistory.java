package seedu.address.storage;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommandHistory {
    private static final Path commandHistoryFilePath = Paths.get("data", "commandHistory.txt");

    /**
     * Writes the given text to a file with the specified filename.
     *
     * @param text the content to write into the file
     */
    public static void writeToFile(String text) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(String.valueOf(commandHistoryFilePath), true))) {
            bw.write(text);
            bw.newLine();
            System.out.println("Successfully written to the file.");
        } catch (IOException e) {
            System.out.println("Error writing the file: " + e.getMessage());
        }
    }

    /**
     * Reads and prints the content of a file with the specified filename.
     */
    public static void readFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(commandHistoryFilePath)))) {
            String line;
            System.out.println("Reading from the file:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}
