package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Payment;
import seedu.address.model.person.Person;

/**
 * Marks the payment for a person as true
 */
public class MarkPaidCommand extends Command {
    public static final String COMMAND_WORD = "markpaid";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks that the student has paid fees for the month"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_MARKED_PAID_SUCCESS = "Mark fees paid for Person %1$s";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";
    private final Index targetIndex;

    /**
     * Marks whether an existing person has paid their fees for the month
     * @param targetIndex
     */
    public MarkPaidCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToMarkPayment = lastShownList.get(targetIndex.getZeroBased());
        Person markedPerson = new Person(personToMarkPayment.getName(), personToMarkPayment.getPhone(),
                personToMarkPayment.getEmail(), personToMarkPayment.getAddress(),
                new Payment(true), personToMarkPayment.getAttendance(), personToMarkPayment.getTags());

        model.setPerson(personToMarkPayment, markedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_MARKED_PAID_SUCCESS, Messages.format(markedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof MarkPaidCommand)) {
            return false;
        }

        MarkPaidCommand e = (MarkPaidCommand) other;
        return targetIndex.equals(e.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
