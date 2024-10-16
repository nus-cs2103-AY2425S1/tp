package tuteez.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import tuteez.commons.core.index.Index;
import tuteez.logic.Messages;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.person.Person;

/**
 * Represents a remark command with hidden internal logic and the ability to be executed.
 */
public abstract class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";
    public static final String MESSAGE_USAGE = "placeholder";

    protected final Index index;

    /**
     * Creates a RemarkCommand with the specified {@code index}.
     *
     * @param index of the person in the filtered person list to apply command
     */
    public RemarkCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    protected Person getPersonFromModel(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return lastShownList.get(index.getZeroBased());
    }

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;
}
