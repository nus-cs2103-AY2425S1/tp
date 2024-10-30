package tuteez.logic.commands;

import java.util.List;

import tuteez.commons.core.index.Index;
import tuteez.logic.Messages;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.person.Person;

/**
 * Adds or deletes a lesson for a student using its identified index in the displayed person list.
 */
public abstract class LessonCommand extends Command {
    public static final String COMMAND_WORD = "lesson";
    public static final String COMMAND_WORD_ALT = "lsn"; // "l" or "lsn" ?

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (short form: " + COMMAND_WORD_ALT + ")"
            + ": Adds or deletes a lesson for the student identified by the index number in the displayed student list."
            + "\nParameters: INDEX (must be a positive integer) -a LESSON or "
            + "-d LESSON_INDEX (must be a positive integer)\n"
            + "Example to add lesson: " + COMMAND_WORD + " 1 -a Monday 0900-1100\n"
            + "Example to delete lesson: " + COMMAND_WORD + " 1 -d 1";

    public static final String MESSAGE_CLASHING_LESSON = "This time slot clashes with the following lessons:";

    protected final Index personIndex;

    /**
     * Creates a LessonCommand with the specified {@code personIndex}.
     * @param personIndex Index of the person in the displayed person list to apply command
     */
    public LessonCommand(Index personIndex) {
        this.personIndex = personIndex;
    }

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    protected Person getPersonFromModel(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return lastShownList.get(personIndex.getZeroBased());
    }
}
