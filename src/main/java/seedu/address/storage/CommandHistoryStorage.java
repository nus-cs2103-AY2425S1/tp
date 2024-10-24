package seedu.address.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Storage to store the past commands.
 */
public class CommandHistoryStorage {
    private static Path commandHistoryFilePath = Paths.get("data", "commandHistory.txt");
    private static ArrayList<String> lines = new ArrayList<>();
    private int currentLineNumber;
    private int previousTotalLines;
    /**
     * Creates a {@code CommandHistoryStorage} and initializing the text file with the line number.
     */
    public CommandHistoryStorage() {
        clearFile();
        this.currentLineNumber = countLinesInFile();
        this.previousTotalLines = currentLineNumber;
    }

    /**
     * Updates the current line number.
     */
    public void updateCommandHistoryLines() {
        if (this.previousTotalLines < countLinesInFile()) {
            this.currentLineNumber = countLinesInFile();
            this.previousTotalLines = currentLineNumber;
        }
        initHistory();
    }

    /**
     * Writes the given text to a file with the specified filename.
     *
     * @param text the content to write into the file
     */
    public static void writeToFile(String text) {
        try {
            Path parentDir = commandHistoryFilePath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(commandHistoryFilePath.toFile(), true))) {
                bw.write(text);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the text file.
     */
    public static void clearFile() {
        try {
            FileWriter writer =
                    new FileWriter(String.valueOf(commandHistoryFilePath), false);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get next command within the arraylist.
     */
    public String getNextCommand() {
        updateCommandHistoryLines();
        if (currentLineNumber < 0) {
            assert (false);
        }

        if (lines.isEmpty()) {
            return "";
        }

        if (this.currentLineNumber < lines.size()) {
            this.currentLineNumber += 1;
        } else {
            //Display nothing if no more next command.
            return "";
        }

        String command = lines.get(getZeroBasedNextLineNumber(currentLineNumber));

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
        updateCommandHistoryLines();
        int lastLineNumber = countLinesInFile();
        if (lastLineNumber == 0) {
            return "";
        }

        if (currentLineNumber > lines.size()) {
            assert (false);
        }

        String command = lines.get(getZeroBasedPreviousLineNumber(currentLineNumber));

        if (this.currentLineNumber > 1) {
            this.currentLineNumber -= 1;
        }

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
    public static int countLinesInFile() {
        int lineCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(commandHistoryFilePath)))) {
            while (br.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }

    /**
     * Populate the arraylist with the command history.
     */
    public static void initHistory() {
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

    public void setCommandHistoryFilePath(Path path) {
        commandHistoryFilePath = path;
    }
}
