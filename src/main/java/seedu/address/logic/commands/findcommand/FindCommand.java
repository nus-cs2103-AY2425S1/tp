package seedu.address.logic.commands.findcommand;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.keywordspredicate.TraitContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive and allows partial matching.
 */
public abstract class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons based on the specified keywords "
            + "(case-insensitive) after the prefix representing the field, "
            + "and displays them as a list with index numbers.\n"
            + "Use 'n/' to search by name, 'a/' to search by address, 'p/' to search by phone, "
            + "'e/' to search by email, 't/' to search by tag, 'w/' to search by wedding and 'tk/' to search by task \n"
            + "Parameters: PREFIX/ KEYWORDS [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + " alice charlie";

    protected final TraitContainsKeywordsPredicate<?> predicate;

    public FindCommand(TraitContainsKeywordsPredicate<?> predicate) {
        this.predicate = predicate;
    }

    @Override
    public abstract CommandResult execute(Model model);

    @Override
    public abstract boolean equals(Object other);

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
