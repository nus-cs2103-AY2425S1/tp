package bizbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import bizbook.commons.core.index.Index;
import bizbook.commons.util.ToStringBuilder;
import bizbook.logic.Messages;
import bizbook.logic.commands.exceptions.CommandException;
import bizbook.model.Model;
import bizbook.model.person.Person;

/**
 * Views the details of a specfied person using it's displayed index from the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View more details of "
            + "a particular person in the contact list.\n"
            + "Parameters: INDEX (Must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_PERSON_SUCCESS = "Fetched details of person: %1$s";

    private final Index targetIndex;

    public ViewCommand(Index index) {
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> personList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= personList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = personList.get(targetIndex.getZeroBased());
        model.getFocusedPerson().set(person);

        return new CommandResult(String.format(MESSAGE_VIEW_PERSON_SUCCESS, targetIndex.getOneBased()), false, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;
        return this.targetIndex.equals(otherViewCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

}
