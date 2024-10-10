package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class MarkpaidCommand extends Command {
    public static final String COMMAND_WORD = "markpaid";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks that the student has paid fees for the month"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_MARKED_PAID_SUCCESS = "Mark fees paid for Person %1$s";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";
    private final Index targetIndex;

    public MarkpaidCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        model.markFeesPaid(personToEdit);
        return new CommandResult(String.format(MESSAGE_MARKED_PAID_SUCCESS, Messages.format(personToEdit)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof MarkpaidCommand)) {
            return false;
        }

        MarkpaidCommand e = (MarkpaidCommand) other;
        return targetIndex.equals(e.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
