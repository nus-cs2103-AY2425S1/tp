package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name or tags contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names or tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters:\n"
            + " - Find by name: " + COMMAND_WORD + " NAME [MORE_NAMES]...\n"
            + " - Find by tag: " + COMMAND_WORD + " t/TAG [t/MORE_TAGS]...\n"
            + " - Find by both: " + COMMAND_WORD + " NAME [MORE_NAMES]... t/TAG [t/MORE_TAGS]...\n"
            + "Examples:\n"
            + " 1. " + COMMAND_WORD + " alice bob charlie\n"
            + " 2. " + COMMAND_WORD + " " + PREFIX_TAG + "friend " + PREFIX_TAG + "owesMoney\n"
            + " 3. " + COMMAND_WORD + " alice " + PREFIX_TAG + "isRich";

    private final NameContainsKeywordsPredicate namePredicate;
    private final TagContainsKeywordsPredicate tagPredicate;

    /**
     * Creates a {@code FindCommand} with the specified name and tag predicates.
     *
     * @param namePredicate The predicate used to match persons by name.
     * @param tagPredicate The predicate used to match persons by tag.
     */
    public FindCommand(NameContainsKeywordsPredicate namePredicate, TagContainsKeywordsPredicate tagPredicate) {
        this.namePredicate = namePredicate;
        this.tagPredicate = tagPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(namePredicate.or(tagPredicate));
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return namePredicate.equals(otherFindCommand.namePredicate)
                && tagPredicate.equals(otherFindCommand.tagPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicates", " " + namePredicate + ", " + tagPredicate)
                .toString();
    }
}
