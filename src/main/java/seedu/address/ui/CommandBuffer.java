package seedu.address.ui;

import java.util.ArrayList;

/**
 * UI component that will store the previously executed commands in a session.
 */
public class CommandBuffer {
    private ArrayList<String> commandHistory;
    private int commandPointer;

    /**
     * Creates a CommandBuffer
     */
    public CommandBuffer() {
        commandHistory = new ArrayList<>();
        commandPointer = 0;
    }

    /**
     * On the entering of a new command, add that command to the buffer
     * @param command String command to be stored. NOTE, the command does have to be valid.
     */
    public void addCommand(String command) {
        // isBlank() is used to check for whitespace, null and empty string
        if (command.isBlank()) {
            return;
        }
        commandHistory.add(command);
        commandPointer = commandHistory.size() - 1;
    }

    /**
     * Handle Down input
     */
    public String handleDownInput() {
        if (commandHistory.isEmpty()) {
            return "";
        }
        if (commandPointer == commandHistory.size() - 1) {
            return commandHistory.get(commandPointer);
        }
        commandPointer += 1;
        return commandHistory.get(commandPointer);
    }

    /**
     * Handle Up input
     */
    public String handleUpInput(boolean isCurrentViewingHistory) {
        if (commandHistory.isEmpty()) {
            return "";
        }
        if (commandPointer == 0) {
            return commandHistory.get(0);
        }
        if (isCurrentViewingHistory) {
            commandPointer -= 1;
        }
        return commandHistory.get(commandPointer);
    }
}
