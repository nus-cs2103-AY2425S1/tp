package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contactrecord.ContactRecord;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Marks a person as contacted.
 * Identified using it's displayed index or NRIC from the address book.
 */
public class MarkCommand extends Command {
    public static final String COMMAND_WORD = "mark";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the person, identified by the index number used in the displayed person list or NRIC, "
            + "as contacted.\n"
            + "Note: If date parameter is not provided, the current date will be used.\n"
            + "Parameters: INDEX (must be a positive integer) or NRIC (must be government issued) "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_NOTES + "NOTES]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "2021-10-10 "
            + PREFIX_NOTES + "Feeling sad "
            + "or " + COMMAND_WORD + " S6253285H "
            + PREFIX_NOTES + "Feeling happy";
    public static final String MESSAGE_MARK_PERSON_SUCCESS = "Marked Person: %1$s\nFor contact on %2$s";

    private final Index targetIndex;
    private final Nric targetNric;
    private final ContactRecord contactRecord;

    /**
     * Creates a MarkCommand to mark person at the specified {@code Index} as called.
     *
     * @param targetIndex The {@code Index} of the person to be marked.
     */
    public MarkCommand(Index targetIndex, ContactRecord contactRecord) {
        this.targetIndex = targetIndex;
        this.targetNric = null;
        this.contactRecord = contactRecord;
    }

    /**
     * Creates a MarkCommand to mark person with the specified {@code Nric} as called.
     *
     * @param targetNric The {@code Nric} of the person to be marked.
     */
    public MarkCommand(Nric targetNric, ContactRecord contactRecord) {
        this.targetNric = targetNric;
        this.targetIndex = null;
        this.contactRecord = contactRecord;
    }

    /**
     * Provides the action property for use in a {@code TableView}.
     * This method is needed for binding the action to the table column.
     *
     * @return the action as a {@code StringProperty}.
     */
    public StringProperty actionProperty() {
        return new SimpleStringProperty(this, "action", COMMAND_WORD);
    }

    /**
     * Provides the format example property for use in a {@code TableView}.
     * This method is needed for binding the format example to the table column.
     *
     * @return the format and example usage as a {@code StringProperty}.
     */
    public StringProperty formatExampleProperty() {
        return new SimpleStringProperty(this, "formatExample", MESSAGE_USAGE);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getSortedFilteredPersonList();
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

        if (model.isHistoryView()) {
            throw new CommandException(Messages.MESSAGE_USAGE_RESTRICTED_IN_HISTORY_VIEW);
        }

        model.markAsContacted(personToMark, contactRecord);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_MARK_PERSON_SUCCESS,
                Messages.format(personToMark),
                contactRecord));
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
        return ((targetIndex != null && targetIndex.equals(otherMarkCommand.targetIndex))
                || (targetNric != null && targetNric.equals(otherMarkCommand.targetNric)))
                && contactRecord.equals(otherMarkCommand.contactRecord);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("targetNric", targetNric)
                .toString();
    }
}
