package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.person.AttendanceStatus;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutorial;

/**
 * Marks attendance of an existing person in the address book.
 */
public class MarkCommand extends TutorialCommand {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks attendance for the contact "
            + "by the index number used in the last person listing and for the tutorial number inputted. \n"
            + "Parameters: \n"
            + "INDEX (must be a positive integer) \n"
            + "tut/TUTORIAL in the format of \n"
            + "1) A positive number between 1-12 \n"
            + "2) A list of numbers eg. [1,3,5] \n"
            + "3) A range of two numbers eg. 3-6 \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "tut/1-5";

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
     * Generates a command execution success message based on whether
     * the remark is added to or removed from
     * {@code personToEdit}.
     */
    @Override
    protected String generateSuccessMessage(Person personToEdit, String markedTutorials) {
        return String.format(Messages.MESSAGE_MARK_SUCCESS, Messages.format(personToEdit), markedTutorials);
    }
}
