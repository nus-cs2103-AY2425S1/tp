package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;

import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive), and displays them as a list with index numbers.\n"
            + "Searching by goods categories is optional.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "[" + PREFIX_CATEGORY + "CATEGORY]\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie "
            + PREFIX_CATEGORY + "Consumables";

    private final Predicate<Person> predicate;

    private final GoodsCategories goodsCategory;

    /**
     * Constructs a find command with a person predicate only.
     */
    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
        this.goodsCategory = null;
    }

    /**
     * Constructs a find command with a specified goods category.
     */
    public FindCommand(Predicate<Person> predicate, GoodsCategories goodsCategory) {
        requireNonNull(predicate);
        this.predicate = predicate;
        this.goodsCategory = goodsCategory;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (goodsCategory == null) {
            model.updateFilteredPersonList(predicate);

        } else {
            Predicate<Person> hasCategory = person -> model
                    .getGoods()
                    .getReceiptList()
                    .stream()
                    .filter(goodsReceipt -> goodsReceipt.isFromSupplier(person.getName()))
                    .anyMatch(goodsReceipt -> goodsReceipt
                            .getGoods()
                            .getCategory()
                            .equals(goodsCategory));
            model.updateFilteredPersonList(person -> predicate.test(person) || hasCategory.test(person));
        }

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
        return predicate.equals(otherFindCommand.predicate)
                && Objects.equals(goodsCategory, otherFindCommand.goodsCategory);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .add("goodsCategory", goodsCategory)
                .toString();
    }
}
