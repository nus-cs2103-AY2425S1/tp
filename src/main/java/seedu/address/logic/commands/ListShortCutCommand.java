package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * List all order in the addressbook
 */
public class ListShortCutCommand extends Command {

    public static final String COMMAND_WORD = "listShortCut";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all shortcuts from the address book";

    public static final String MESSAGE_SUCCESS = "Shortcut Mappings\n";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS + formatShortCuts(model.getShortCutList().toString()));
    }

    /**
     * Formats shortcuts into a easily viewable list
     * @return String of formatted shortcuts
     */
    public String formatShortCuts(String shortcuts) {
        shortcuts = shortcuts.replaceAll("[\\[\\]]", "");

        // Split by comma to separate individual shortcuts
        String[] shortcutArray = shortcuts.split(", ");

        // Use a StringBuilder to construct the formatted result
        StringBuilder formattedShortcuts = new StringBuilder();

        // Iterate over the shortcut array and append each to a new line
        for (String shortcut : shortcutArray) {
            formattedShortcuts.append(shortcut).append("\n");
        }

        // Return the final formatted string
        return formattedShortcuts.toString().trim();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListOrderCommand)) {
            return false;
        }

        return true;
    }
}
