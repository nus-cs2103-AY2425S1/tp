package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.person.HasCategoryPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive), and displays them as a list with index numbers. "
            + "Searching by goods categories is optional.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]... "
            + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie "
            + PREFIX_CATEGORY + "CONSUMABLES";

    private final List<String> keywords;
    private final Set<GoodsCategories> categoriesSet;

    /**
     * Constructs a find command with a list of keywords and categories to search for.
     */
    public FindCommand(List<String> keywords) {
        this.keywords = keywords;
        this.categoriesSet = new HashSet<>();
    }

    /**
     * Constructs a find command with a list of keywords and categories to search for.
     */
    public FindCommand(List<String> keywords, Set<GoodsCategories> categoriesSet) {
        this.keywords = keywords;
        this.categoriesSet = categoriesSet;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        NameContainsKeywordsPredicate nameContainsKeywordsPredicate =
                new NameContainsKeywordsPredicate(keywords);

        HasCategoryPredicate hasCategoryPredicate =
                new HasCategoryPredicate(model, categoriesSet);

        Predicate<Person> combinedPredicate =
                nameContainsKeywordsPredicate.or(hasCategoryPredicate);

        model.updateFilteredPersonList(combinedPredicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                        model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand otherFindCommand)) {
            return false;
        }

        return Objects.equals(keywords, otherFindCommand.keywords)
                && Objects.equals(categoriesSet, otherFindCommand.categoriesSet);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .add("categoriesSet", categoriesSet)
                .toString();
    }
}
