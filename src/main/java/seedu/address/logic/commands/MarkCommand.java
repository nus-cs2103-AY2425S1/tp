package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Marks a person as contacted.
 * Identified using it's displayed index from the address book.
 */
public class MarkCommand extends Command {
    public static final String COMMAND_WORD = "mark";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the person identified by the index number used in the displayed person list as contacted.\n"
            + "Parameters: INDEX (must be a positive integer) or NRIC (must be government issued)\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " S6253285H";
    public static final String MESSAGE_MARK_PERSON_SUCCESS = "Marked Person: %1$s";

    private final Index targetIndex;
    private final Nric targetNric;

    public MarkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetNric = null;
    }

    public MarkCommand(Nric targetNric) {
        this.targetNric = targetNric;
        this.targetIndex = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToMark;

        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToMark = lastShownList.get(targetIndex.getZeroBased());
        } else {
            personToMark = model.getPersonByNric(targetNric);
            if (personToMark == null) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NRIC);
            }
        }
        model.markAsContacted(personToMark);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_MARK_PERSON_SUCCESS, Messages.format(personToMark)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkCommand)) {
            return false;
        }

        MarkCommand otherMarkCommand = (MarkCommand) other;
        return targetIndex.equals(otherMarkCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
