package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;

import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

public class DeleteMeetUpCommand {

    public static final String COMMAND_WORD = "deletem";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the details of the meetup in the address book. "
            + "Existing meetup will be deleted from the address book.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DELETE_MEETUP_SUCCESS = "Deleted Meetup: %1$s";
    public static final String MESSAGE_MEETUP_NOT_DELETED = "Please check for missing fields or invalid format.";

    private final Index targetIndex;

}
