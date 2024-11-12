package seedu.address.logic.commands.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.property.AddressContainsKeywordsPredicate;
import seedu.address.model.property.LandlordNameContainsKeywordsPredicate;

/**
 * Finds and lists all properties in property list whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all properties whose names or addresses contain"
            + " any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "You should only find by name or address but not both.\n"
            + "Parameters: n/KEYWORD [MORE_KEYWORDS]... OR a/KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Jake\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ADDRESS + "Sembawang";

    private final AddressContainsKeywordsPredicate addressPredicate;
    private final LandlordNameContainsKeywordsPredicate landlordPredicate;
    private boolean isAddress;

    /**
     * Creates a find command {@code FindCommand}with a predicate of {@code AddressContainsKeywordsPredicate}
     * @param predicate a predicate that checks if address matches the keywords provided.
     */
    public FindCommand(AddressContainsKeywordsPredicate predicate) {
        this.addressPredicate = predicate;
        this.landlordPredicate = null;
        this.isAddress = true;
    }

    /**
     * Creates a find command {@code FindCommand}with a predicate of {@code LandlordNameContainsKeywordsPredicate}
     * @param predicate a predicate that checks if landlord name matches the keywords provided.
     */
    public FindCommand(LandlordNameContainsKeywordsPredicate predicate) {
        this.landlordPredicate = predicate;
        this.addressPredicate = null;
        this.isAddress = false;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (isAddress) {
            model.updateFilteredPropertyList(addressPredicate);
        } else {
            model.updateFilteredPropertyList(landlordPredicate);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_PROPERTIES_LISTED_OVERVIEW, model.getFilteredPropertyList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand e = (FindCommand) other;

        if (e.isAddress && isAddress) {
            return addressPredicate.equals(e.addressPredicate);
        } else if (!e.isAddress && !isAddress) {
            return landlordPredicate.equals(e.landlordPredicate);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (isAddress) {
            return new ToStringBuilder(this)
                    .add("addressPredicate", addressPredicate)
                    .toString();
        } else {
            return new ToStringBuilder(this)
                    .add("landlordPredicate", landlordPredicate)
                    .toString();
        }
    }
}
