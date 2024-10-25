package seedu.address.logic.commands;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    /**
     * Provides the action property for use in a {@code TableView}.
     * This method is needed for binding the action to the table column.
     *
     * @return the action as a {@code StringProperty}.
     */
    public StringProperty actionProperty() {
        return new SimpleStringProperty(this, "action", COMMAND_WORD);
    }

    /**
     * Provides the format example property for use in a {@code TableView}.
     * This method is needed for binding the format example to the table column.
     *
     * @return the format and example usage as a {@code StringProperty}.
     */
    public StringProperty formatExampleProperty() {
        return new SimpleStringProperty(this, "formatExample", MESSAGE_USAGE);
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false, false);
    }
}
