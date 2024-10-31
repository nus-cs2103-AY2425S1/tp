package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.person.AttendanceStatus;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutorial;

/**
 * Resets attendance of a person in the address book.
 */
public class ResetCommand extends TutorialCommand {

    public static final String COMMAND_WORD = "reset";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Resets attendance for the contact "
            + "by the index number used in the last person listing and for the tutorial number inputted. "
            + "Parameters: \n"
            + "INDEX (must be a positive integer) \n"
            + "tut/TUTORIAL in the format of \n"
            + "1) A positive number between 1-12 \n"
            + "2) A list of numbers eg. [1,3,5] \n"
            + "3) A range of two numbers eg. 3-6 \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "tut/1-5";

    /**
     * @param index The index of the person to be marked.
     * @param tutorial single tutorial to set as not taken place.
     */
    public ResetCommand(Index index, Tutorial tutorial) {
        super(index, tutorial);
    }

    /**
     * Constructor for resetting multiple tutorials.
     * @param index of the person in the display list.
     * @param tutorials list of tutorials to set as not taken place.
     */
    public ResetCommand(Index index, List<Tutorial> tutorials) {
        super(index, tutorials);
    }

    @Override
    protected AttendanceStatus getStatus() {
        return AttendanceStatus.NOT_TAKEN_PLACE;
    }

    @Override
    protected String getUnnecessaryMessage() {
        return Messages.MESSAGE_RESET_UNNECESSARY;
    }

    /**
     * Generates a command execution success message for the tutorials that were marked as not taken place.
     * {@code personToEdit}.
     */
    protected String generateSuccessMessage(Person personToEdit, String resetTutorials) {
        return String.format(Messages.MESSAGE_RESET_SUCCESS, Messages.format(personToEdit), resetTutorials);
    }
}
