package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.person.AttendanceStatus;
import seedu.address.model.person.Tutorial;

/**
 * Marks attendance of an existing person in the address book.
 */
public class MarkCommand extends TutorialCommand {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks attendance for the contact "
            + "by the index number used in the last person listing and for the tutorial number(s) inputted.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer or wildcard *) "
            + "tut/TUTORIAL\n"
            + "Example: " + COMMAND_WORD + " 1 " + "tut/1-5, "
            + COMMAND_WORD + " 1 " + "tut/1,4,6";

    /**
     * @param index of the person in the display list
     * @param tutorial single tutorial to mark attendance for
     */
    public MarkCommand(Index index, Tutorial tutorial) {
        super(index, tutorial);
    }

    /**
     * Constructor for marking multiple tutorials.
     * @param index of the person in the display list
     * @param tutorials list of tutorials to mark attendance for
     */
    public MarkCommand(Index index, List<Tutorial> tutorials) {
        super(index, tutorials);
    }

    @Override
    protected AttendanceStatus getStatus() {
        return AttendanceStatus.PRESENT;
    }

    @Override
    protected String getUnnecessaryMessage() {
        return Messages.MESSAGE_MARK_UNNECESSARY;
    }

    /**
     * returns a success message for the tutorials that which were marked.
     */
    @Override
    protected String getSuccessMessage() {
        return Messages.MESSAGE_MARK_SUCCESS;
    }
}
