package seedu.address.logic.commands;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.tag.TagContainsKeywordsPredicate;



/**
 * Finds and lists all persons in the address book whose tags contain any of the specified keywords.
 * Keyword matching is case-insensitive.
 */
public class FindTagCommand extends Command {
    public static final String COMMAND_WORD = "findtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friend work";

    private static final Logger logger = LogsCenter.getLogger(FindTagCommand.class);

    private final TagContainsKeywordsPredicate predicate;

    public FindTagCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        logger.info("Executing FindTagCommand...");

        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTagCommand // instanceof handles nulls
                && predicate.equals(((FindTagCommand) other).predicate)); // state check
    }
}

