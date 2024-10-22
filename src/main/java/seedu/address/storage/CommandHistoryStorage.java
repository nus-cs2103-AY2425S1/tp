package seedu.address.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Storage to store the past commands.
 */
public class CommandHistoryStorage {
    private static final Path commandHistoryFilePath = Paths.get("data", "commandHistory.txt");
    private static ArrayList<String> lines = new ArrayList<>();

    private int currentLineNumber;

    /**
     * Creates a {@code CommandHistoryStorage} and initializing the text file with the line number.
     */
    public CommandHistoryStorage() {
        //Initialize currentLineNumber to last line
        this.currentLineNumber = countLinesInFile();
        initHistory();
    }

    /**
     * Updates the current line number.
     */
    public void updateCommandHistoryLines() {
        this.currentLineNumber = countLinesInFile();
        initHistory();
    }

    /**
     * Writes the given text to a file with the specified filename.
     *
     * @param text the content to write into the file
     */
    public static void writeToFile(String text) {
        try (BufferedWriter bw =
                     new BufferedWriter(new FileWriter(String.valueOf(commandHistoryFilePath), true))) {
            bw.write(text);
            bw.newLine();
            System.out.println("Successfully written to the file.");
        } catch (IOException e) {
            System.out.println("Error writing the file: " + e.getMessage());
        }
    }

    /**
     * Clears the text file.
     */
    public static void clearFile() {
        try {
            FileWriter writer =
                    new FileWriter(String.valueOf(commandHistoryFilePath), false); // 'false' to overwrite the file
            writer.write(""); // write an empty string to clear the file
            writer.close();
            System.out.println("File cleared successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while clearing the file.");
            e.printStackTrace();
        }
    }

    /**
     * Get next command within the arraylist.
     */
    public String getNextCommand() {
        if (currentLineNumber < 0) {
            assert (false);
        }

        System.out.println("NEXT CURRENT LINE NUMBER BEFORE: " + currentLineNumber);

        if (this.currentLineNumber < lines.size()) {
            this.currentLineNumber += 1;
        }

        String command = lines.get(getZeroBasedNextLineNumber(currentLineNumber));
        System.out.println("NEXT CURRENT LINE NUMBER AFTER: " + currentLineNumber);

        return command;
    }

    /**
     * Get zero-based index for arraylist retrieval.
     *
     * @param currentLineNumber current number parsed by system.
     * @return currentLineNumber in zero-based index for the arraylist.
     */
    private int getZeroBasedNextLineNumber(int currentLineNumber) {
        if (currentLineNumber >= lines.size()) {
            return lines.size() - 1;
        } else {
            return currentLineNumber - 1;
        }
    }

    /**
     * Get previous command within the arraylist.
     */
    public String getPreviousCommand() {
        int lastLineNumber = countLinesInFile() - 1;
        if (currentLineNumber > lastLineNumber) {
            assert (false);
        }

        System.out.println("PREV CURRENT LINE NUMBER BEFORE: " + currentLineNumber);

        if (this.currentLineNumber > 1) {
            this.currentLineNumber -= 1;
        }

        String command = lines.get(getZeroBasedPreviousLineNumber(currentLineNumber));
        System.out.println("PREV CURRENT LINE NUMBER AFTER: " + currentLineNumber);
        return command;
    }

    /**
     * Get zero-based index for arraylist retrieval.
     *
     * @param currentLineNumber current number parsed by system.
     * @return currentLineNumber in zero-based index for the arraylist.
     */
    private int getZeroBasedPreviousLineNumber(int currentLineNumber) {
        if (currentLineNumber < 1) {
            return 0;
        } else {
            return currentLineNumber - 1;
        }
    }

    /**
     * Counts the total number of lines in the file.
     * 1-based indexing.
     *
     * @return the total number of lines in the file
     */
    private static int countLinesInFile() {
        int lineCount = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(commandHistoryFilePath)))) {
            while (br.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            System.out.println("Error counting lines in the file: " + e.getMessage());
        }
        return lineCount;
    }

    /**
     * Populate the arraylist with the command history.
     */
    private static void initHistory() {
        lines.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(commandHistoryFilePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
