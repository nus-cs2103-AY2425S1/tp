package seedu.address.ui;
import java.util.ArrayList;

/**
 * Container for previous commands
 */
public class CommandHistory {
    private ArrayList<String> commandHistory = new ArrayList<>();
    private int currentHistoryIndex = -1;

    /**
     * Adds a command to the ArrayList
     */
    public void addCommand(String command) {
        commandHistory.add(command);
        currentHistoryIndex = commandHistory.size(); // Reset index to point after the latest command
    }

    /**
     * Retrieves the previous command from the ArrayList, outputs null if already at the front of the arraylist
     */
    public String getPreviousCommand() {
        if (currentHistoryIndex > 0) {
            currentHistoryIndex--;
            return commandHistory.get(currentHistoryIndex);
        }
        return null; // No previous command
    }

    /**
     * Retrieves the next command from the ArrayList, outputs null if already at the end of the arraylist
     */
    public String getNextCommand() {
        if (currentHistoryIndex < commandHistory.size() - 1) {
            currentHistoryIndex++;
            return commandHistory.get(currentHistoryIndex);
        }
        if (currentHistoryIndex == commandHistory.size() - 1) {
            currentHistoryIndex++;
        }
        return ""; // No next command
    }
}
