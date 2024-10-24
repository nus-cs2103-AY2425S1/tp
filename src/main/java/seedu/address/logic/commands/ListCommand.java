package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all persons in the address book. "
            + "Parameters: [s/SORT_FIELD] [r/]\n"
            + "SORT_FIELD: name or email\n"
            + "Example: " + COMMAND_WORD + " s/name r/";

    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_INVALID_SORT_FIELD = "Invalid sort field. "
            + "Please use either 'name' or 'email'";

    private final String sortField;
    private final boolean isReverse;

    /**
     * Constructs a new ListCommand with specified sorting parameters.
     *
     * @param sortField The field to sort by. Valid values are "firstname", "lastname", or "email".
     *                  If null, no sorting will be applied.
     * @param isReverse If true, the sort order will be reversed (descending).
     *                  If false, the sort order will be ascending.
     *                  This parameter is only relevant if sortField is not null.
     */
    public ListCommand(String sortField, boolean isReverse) {
        this.sortField = sortField == null ? "name" : sortField.toLowerCase();
        this.isReverse = isReverse;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (sortField != null) {
            Comparator<Person> comparator = getComparator(sortField);

            if (isReverse) {
                comparator = comparator.reversed();
            }

            model.sortFilteredPersonList(comparator);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    private Comparator<Person> getComparator(String field) throws CommandException {
        switch (field.toLowerCase()) {
        case "name":
            return Comparator.comparing(person -> person.getName().fullName);
        case "email":
            return Comparator.comparing(person -> person.getEmail().value);
        default:
            throw new CommandException(MESSAGE_INVALID_SORT_FIELD);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && sortField.equals(((ListCommand) other).sortField)
                && isReverse == ((ListCommand) other).isReverse); // state check
    }
}
