package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Saves the sort preference to UserPrefs
 */
public class SaveSortPreferenceCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Your preference has been saved. "
            + "Relaunch, the application to view the changes";

    public static final String COMMAND_WORD = "save_sort";

    public static final String ALT_COMMAND_WORD = "svp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves your desired sorting preference as priority "
            + "or by last seen date based on the parameter.\n"
            + "Parameters: high/low/recent/distant\n"
            + "Example: " + COMMAND_WORD + " high";

    private final String sortPreference;

    /**
     * Creates a SaveSortPreferenceCommand to save the given {@code sortPreference}
     * @param sortPreference The {@code String} sortPreference that is being passed in
     */
    public SaveSortPreferenceCommand(String sortPreference) {
        this.sortPreference = sortPreference;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setSortPreference(sortPreference);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        // instance handles nulls
        if (!(o instanceof SaveSortPreferenceCommand)) {
            return false;
        }

        SaveSortPreferenceCommand e = (SaveSortPreferenceCommand) o;
        return sortPreference.equals(e.sortPreference);
    }
}
