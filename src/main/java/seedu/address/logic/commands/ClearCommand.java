package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

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

    public static final String MESSAGE_SUCCESS = "EduConnect has been cleared of specified tags!";

    private final PersonContainsKeywordsPredicate predicate;

    public ClearCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult executeCommand(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(x -> !predicate.test(x));
        List<Person> remainingPersons = model.getFilteredPersonList();

        AddressBook newAddressBook = new AddressBook();
        newAddressBook.setPersons(remainingPersons);

        model.setAddressBook(newAddressBook);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
