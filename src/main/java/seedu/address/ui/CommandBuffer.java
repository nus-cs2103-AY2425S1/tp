package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * UI component that will store the previously executed commands in a session.
 */
public class CommandBuffer {
    private static final Logger logger = LogsCenter.getLogger(CommandBuffer.class);
    private ArrayList<String> commandHistory = new ArrayList<>();
    private int commandPointer = 0;

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
        logger.info("Added  \n" + command + "\nAdded at index " + commandPointer);
    }

    /**
     * Handle Up input
     */
    public void handleUpInput() {
        // Do nothing for now
    }

    /**
     * Handle Down input
     */
    public void handleDownInput() {
        // Do nothing for now
    }
}
