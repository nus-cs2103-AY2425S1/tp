package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;

/**
 * View all information of specified contact
 */
public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "Contact Opened.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds one contact whose telegram handle "
            + "matches the specified telegram handle (case-insensitive) and displays a new page which "
            + "contains all the contact's information.\n"
            + "Parameters: TELEGRAM_HANDLE \n"
            + "Example: " + COMMAND_WORD + "t/TELEGRAM_HANDLE";

    private final NameContainsKeywordsPredicate predicate;

    public ViewCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // TO ADD FUNCTIONALITY HERE
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
