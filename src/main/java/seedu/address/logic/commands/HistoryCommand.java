package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contactrecord.ContactRecordList;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Finds and lists the call history of the elderly in address book.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List call history of the person identified by the index number or NRIC in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) or NRIC (must be government issued)\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " S6253285H";
    public static final String MESSAGE_SHOW_HISTORY_SUCCESS = "Call history of person: %1$s";
    private final Index targetIndex;
    private final Nric targetNric;

    /**
     * Creates a HistoryCommand to find the call history of the person at the specified {@code Index}
     *
     * @param targetIndex The {@code Index} of the person to be found.
     */
    public HistoryCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetNric = null;
    }

    /**
     * Creates a HistoryCommand to find the call history of the person with the specified {@code Nric}.
     *
     * @param targetNric The {@code Nric} of the person to be found.
     */
    public HistoryCommand(Nric targetNric) {
        this.targetNric = targetNric;
        this.targetIndex = null;
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
        Person personFound;

        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personFound = lastShownList.get(targetIndex.getZeroBased());
        } else {
            personFound = model.getPersonByNric(targetNric);
            if (personFound == null) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NRIC);
            }
        }

        ContactRecordList callHistory = model.getCallHistory(personFound);
        if (callHistory.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EMPTY_CALL_HISTORY);
        }

        model.updateDisplayedList(callHistory);

        return new CommandResult(String.format(MESSAGE_SHOW_HISTORY_SUCCESS, personFound.getName()),
                false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HistoryCommand)) {
            return false;
        }

        HistoryCommand otherHistoryCommand = (HistoryCommand) other;
        return (targetIndex != null && targetIndex.equals(otherHistoryCommand.targetIndex))
                || (targetNric != null && targetNric.equals(otherHistoryCommand.targetNric));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("targetNric", targetNric)
                .toString();
    }
}
