package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts persons in the address book based on the given keyword.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts persons in the address book by the given field. "
            + "Currently supported fields: deadline, name\n"
            + "Example: " + COMMAND_WORD + " deadline OR " + COMMAND_WORD + " name";

    public static final String MESSAGE_SUCCESS = "Sorted all persons by %1$s";
    public static final String MESSAGE_NO_PERSONS = "No persons available to sort."; // Message for empty list
    public static final String MESSAGE_INVALID_KEYWORD = "Invalid sort keyword! Supported keywords: deadline, name.";

    private final String sortByKeyword;

    /**
     * @param sortByKeyword The field to sort by (e.g., "deadline" or "name").
     */
    public SortCommand(String sortByKeyword) {
        this.sortByKeyword = sortByKeyword.trim().toLowerCase();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getAddressBook().getPersonList().isEmpty()) {
            return new CommandResult(MESSAGE_NO_PERSONS);
        }

        Comparator<Person> comparator;
        switch (sortByKeyword) {
        case "deadline":
            comparator = Comparator.comparing(person -> person.getDeadline().value);
            break;
        case "name":
            comparator = Comparator.comparing(person -> person.getName().fullName.toLowerCase());
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_KEYWORD);
        }

        model.sortByComparator(comparator);
        return new CommandResult(String.format(MESSAGE_SUCCESS, sortByKeyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherCommand = (SortCommand) other;
        return sortByKeyword.equals(otherCommand.sortByKeyword);
    }

    @Override
    public int hashCode() {
        return sortByKeyword.hashCode();
    }
}

