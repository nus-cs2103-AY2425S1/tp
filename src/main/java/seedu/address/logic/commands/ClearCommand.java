package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all persons whose tags contain any of "
            + "the specified tags and keywords (case-insensitive)\n"
            + "Parameters: /TAG KEYWORD [/MORE_TAGS MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " /class 7A";
    public static final String MESSAGE_SUCCESS = "EduConnect has been cleared of specified tags!";

    private final NameContainsKeywordsPredicate predicate;

    public ClearCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
