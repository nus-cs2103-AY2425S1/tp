package seedu.address.logic.commands;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.TelegramHandleContainsKeywordsPredicate;

/**
 * Finds and lists all persons in the address book whose telegram handle is any of the specified keywords.
 * Keyword matching is case-insensitive.
 */
public class FindTelegramHandleCommand extends Command {
    public static final String COMMAND_WORD = "findtele";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose telegram handle contain any "
        + "of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " berner";

    private static final Logger logger = LogsCenter.getLogger(FindTagCommand.class);

    private final TelegramHandleContainsKeywordsPredicate predicate;

    public FindTelegramHandleCommand(TelegramHandleContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        logger.info("Executing FindTelegramHandleCommand...");

        model.updateFilteredPersonList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindTelegramHandleCommand // instanceof handles nulls
            && predicate.equals(((FindTelegramHandleCommand) other).predicate)); // state check
    }
}
