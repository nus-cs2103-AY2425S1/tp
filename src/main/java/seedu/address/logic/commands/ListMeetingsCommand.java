package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Command to list all meetings in the address book.
 */
public class ListMeetingsCommand extends ListCommand {

    /**
     * The keyword used to trigger the listing of meetings in the database.
     */
    public static final String KEY_WORD = "meetings";

    /**
     * Executes the command to list all meetings in the address book.
     *
     * @param model The {@code Model} which contains the application data and logic.
     * @return A {@code CommandResult} containing the feedback message for the user after the command is executed.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Logic to display meetings
        model.setDisplayMeetings();
        return new CommandResult(String.format(ListCommand.MESSAGE_SUCCESS, KEY_WORD));
    }
}
