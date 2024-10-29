package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.History;
import seedu.address.model.person.Person;
/**
 * Add a history entry to an existing person
 */
public class LogCommand extends Command {
    public static final String COMMAND_WORD = "log";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add activity log of the person identified "
            + "by the index number used in the last person listing. "
            + "If the date field is left empty the date on the entry will be on today by default (System date).\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "[date]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LOG + "Met for price negotiation."
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_DATE + LocalDate.now().toString() + "Offered discount";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Log command not implemented yet";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Date: %2$s, Message: %3$s";
    public static final String MESSAGE_ADD_HISTORY_SUCCESS = "Added history to Person: %1$s";
    private final Index index;
    private final LocalDate date;
    private final String message;

    /**
     * @param index   of the person in the filtered person list to add history log
     * @param date    of the activity to be logged
     * @param message of the activity
     */
    public LogCommand(Index index, LocalDate date, String message) {
        requireAllNonNull(index, date, message);
        this.index = index;
        this.date = date;
        this.message = message;
    }

    /**
     * @param index   of the person in the filtered person list to add history log
     *                the date is set to system time today by default
     * @param message of the activity
     */
    public LogCommand(Index index, String message) {
        requireAllNonNull(index, message);
        this.index = index;
        this.date = LocalDate.now();
        this.message = message;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = lastShownList.get(index.getZeroBased());
        History editedHistory = History.addActivity(personToEdit.getHistory(), date, message);
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), personToEdit.getBirthday(),
                personToEdit.getTags(),
                personToEdit.getDateOfCreation(),
                editedHistory,
                personToEdit.getPropertyList());
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_HISTORY_SUCCESS,
                Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LogCommand)) {
            return false;
        }

        LogCommand e = (LogCommand) other;
        return index.equals(e.index)
                && date.equals(e.date)
                && message.equals(e.message);
    }

    /**
     * Generates a command execution success message based on whether
     * the remark is added to or removed from
     * {@code personToEdit}.
     */
    public String generateSuccessMessage(Person personToEdit) {
        return String.format(Messages.format(personToEdit));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("date", date)
                .add("message", message)
                .toString();
    }
}
