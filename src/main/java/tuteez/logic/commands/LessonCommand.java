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
    public static final String COMMAND_WORD_ADD = "addlesson";
    public static final String COMMAND_WORD_ADD_ALT = "addlsn";
    public static final String COMMAND_WORD_DELETE = "deletelesson";
    public static final String COMMAND_WORD_DELETE_ALT = "dellsn";

    public static final String MESSAGE_USAGE = "Add lessons by index in displayed student list: " + COMMAND_WORD_ADD
            + " (short form: " + COMMAND_WORD_ADD_ALT + ")"
            + " INDEX l/LESSON [l/LESSON]...\n"
            + "Example: " + COMMAND_WORD_ADD + " 1 l/monday 0900-1100 l/wednesday 1400-1600\n"
            + "\n"
            + "Delete lessons by index in displayed student list: " + COMMAND_WORD_DELETE
            + " (short form: " + COMMAND_WORD_DELETE_ALT + ")"
            + " INDEX li/LESSON_INDEX [li/LESSON_INDEX]...\n"
            + "Example: " + COMMAND_WORD_DELETE + " 1 li/2 li/4";

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
