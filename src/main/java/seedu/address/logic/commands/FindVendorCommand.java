package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.util.PredicatePreviewUtil.filterVendorsPreview;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.vendor.NameContainsKeywordsPredicate;
import seedu.address.model.vendor.Vendor;

/**
 * Finds and lists all vendors in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindVendorCommand extends FindCommand {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all vendors whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + PREFIX_VENDOR + " KEYWORD [MORE_KEYWORDS]... \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_VENDOR + " alice bob charlie\n";


    private final NameContainsKeywordsPredicate predicate;

    public FindVendorCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        FilteredList<Vendor> vendors = filterVendorsPreview(model, predicate);

        if (vendors.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_VENDORS_FOUND);
        }

        model.updateFilteredVendorList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_VENDORS_LISTED_OVERVIEW, model.getFilteredVendorList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindVendorCommand)) {
            return false;
        }

        FindVendorCommand otherFindCommand = (FindVendorCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
