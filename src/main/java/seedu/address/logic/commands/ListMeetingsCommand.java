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

    /**
     * Compares this ListMeetingsCommand to another object for equality.
     * Two ListMeetingsCommand objects are considered equal if they are of the same class at runtime.
     *
     * @param other the object to compare with this ListMeetingsCommand
     * @return true if the specified object is equal to this ListMeetingsCommand; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof ListMeetingsCommand;
    }
}
