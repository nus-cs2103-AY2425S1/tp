package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all persons whose tags contain any of "
            + "the specified tags and keywords (case-insensitive)\n"
            + "Parameters: /TAG KEYWORD [/MORE_TAGS MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " /name John";

    public static final String MESSAGE_SUCCESS = "EduConnect has been cleared of %d matching entries!";

    public static final String MESSAGE_CLEAR_ALL = "EduConnect has been cleared of all entries!";

    public static final String MESSAGE_NO_ACTION = "No possible entries in EduConnect to clear!";

    private final PersonContainsKeywordsPredicate predicate;

    public ClearCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult executeCommand(Model model) throws CommandException {
        requireNonNull(model);

        int originalSize = model.getAddressBook().getPersonList().size();
        model.updateFilteredPersonList(x -> !predicate.test(x));
        List<Person> remainingPersons = model.getFilteredPersonList();
        int newSize = remainingPersons.size();

        assert newSize <= originalSize : "New person list should not be bigger than original after clear!";

        if (newSize == originalSize) {
            throw new CommandException(MESSAGE_NO_ACTION);
        }

        AddressBook newAddressBook = new AddressBook();
        newAddressBook.setPersons(remainingPersons);

        model.setAddressBook(newAddressBook);

        if (newSize == 0) {
            return new CommandResult(MESSAGE_CLEAR_ALL);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, originalSize - newSize));
    }
}
