package tuteez.logic.commands;

import java.util.List;

import tuteez.commons.core.index.Index;
import tuteez.logic.Messages;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.person.Person;

/**
 * Adds or deletes a lesson for a student using its identified index in the displayed student list.
 */
public abstract class LessonCommand extends Command {
    public static final String COMMAND_WORD_ADD = "addlesson";
    public static final String COMMAND_WORD_ADD_ALT = "addlsn";
    public static final String COMMAND_WORD_DELETE = "deletelesson";
    public static final String COMMAND_WORD_DELETE_ALT = "dellsn";

    protected final Index personIndex;

    /**
     * Creates a LessonCommand with the specified {@code personIndex}.
     * @param personIndex Index of the student in the student person list to apply command
     */
    public LessonCommand(Index personIndex) {
        this.personIndex = personIndex;
    }

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    protected Person getPersonFromModel(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (personIndex.getZeroBased() < 0 || personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return lastShownList.get(personIndex.getZeroBased());
    }
}
