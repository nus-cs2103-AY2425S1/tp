package seedu.address.storage;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.input.KeyCode;

/**
 * Stores the history of commands entered by the user.
 */
public class CommandHistory {
    private static CommandHistory instance = null;
    private static Logger logger = Logger.getLogger("CommandHistory");

    private ArrayList<String> listOfCommands;
    private int index;


    /**
     * Constructor for CommandHistory.
     */
    private CommandHistory() {
        this.listOfCommands = new ArrayList<>();
        this.index = 0;
    }

    /**
     * Returns the instance of the CommandHistory class. Instantiates a new instance if not instantiated.
     *
     * @return instance of the {@code CommandHistory} class
     */
    public static CommandHistory getInstance() {
        if (instance == null) {
            instance = new CommandHistory();
        }

        return instance;
    }

    /**
     * Adds a new command to the {@code CommandHistory} instance.
     *
     * @param command command to add.
     */
    public void addCommand(String command) {
        if (command == null) {
            return;
        }

        assert(command != null && !command.isEmpty());

        logger.log(Level.INFO, "Adding command into command history");
        listOfCommands.add(command);
        index = listOfCommands.size();
    }

    /**
     * Returns the requested command by the user based on user's key input. The valid {@KeyCode} inputs are the up
     * and down arrow keys.
     *
     * @param key {@KeyCode} input.
     * @return command corresponding to the input requested by the user.
     */
    public String getPastCommand(KeyCode key) {
        assert(key.isArrowKey() && (key == KeyCode.UP || key == KeyCode.DOWN));

        // list of commands is empty
        if (listOfCommands.size() == 0) {
            logger.log(Level.INFO, "No history of commands, returning empty string");
            return "";
        }

        logger.log(Level.INFO, "Returning requested command");
        switch (key) {
        case UP:
            return getPreviousCommand();
        case DOWN:
            return getNextCommand();
        default:
            return "";
        }
    }

    /**
     * Returns the previous command in the command history. This corresponds to clicking the up arrow key.
     *
     * @return previous command in the command history.
     */
    private String getPreviousCommand() {
        // No commands left in command history
        if (index == 0) {
            logger.log(Level.INFO, "No more commands preceding this, returning current command.");
            return listOfCommands.get(index);
        }

        logger.log(Level.INFO, "Returning previous command");
        String command = listOfCommands.get(index - 1);
        index--;

        return command;
    }

    /**
     * Returns the next command in the command history. This corresponds to clicking the down arrow key.
     *
     * @return next command in the command history.
     */
    private String getNextCommand() {
        // At last parsed command in command history
        if (index >= listOfCommands.size() - 1) {
            logger.log(Level.INFO, "Returning current command");
            index = listOfCommands.size();
            return "";
        }

        logger.log(Level.INFO, "Returning next command");
        String command = listOfCommands.get(index + 1);
        index++;
        return command;
    }

    /**
     * Clears the command history.
     */
    public void clearListOfCommands() {
        this.listOfCommands.clear();
        this.index = 0;
    }
}
