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
    public static final String COMMAND_WORD_ALT = "rmk";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (short form: " + COMMAND_WORD_ALT + ")"
            + ": Adds or deletes a remark for the student identified by the index number in the displayed student list."
            + "\nParameters: INDEX (must be a positive integer) -a REMARK or "
            + "-d REMARK_INDEX (must be a positive integer)\n"
            + "Example to add remark: " + COMMAND_WORD + " 1 -a This is a new remark\n"
            + "Example to delete remark: " + COMMAND_WORD + " 1 -d 2";

    protected final Index personIndex;

    /**
     * Creates a RemarkCommand with the specified {@code index}.
     *
     * @param personIndex Index of the person in the filtered person list to apply command
     */
    public RemarkCommand(Index personIndex) {
        requireNonNull(personIndex);

        this.personIndex = personIndex;
    }

    protected Person getPersonFromModel(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return lastShownList.get(personIndex.getZeroBased());
    }

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;
}
