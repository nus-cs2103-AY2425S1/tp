package seedu.eventtory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eventtory.logic.commands.util.PredicatePreviewUtil.getPreviewOfFilteredVendors;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_VENDOR;

import javafx.collections.transformation.FilteredList;
import seedu.eventtory.commons.util.ToStringBuilder;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.model.vendor.VendorContainsKeywordsPredicate;
import seedu.eventtory.ui.UiState;

/**
 * Finds and lists all vendors in EventTory whose fields contain any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindVendorCommand extends FindCommand {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all vendors whose fields contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + PREFIX_VENDOR + " KEYWORD [MORE_KEYWORDS]... \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_VENDOR + " alice bob charlie\n";
    public static final String MESSAGE_FIND_VENDOR_FAILURE_INVALID_VIEW =
            "You have to be viewing a vendor list to use the find vendor command";


    private final VendorContainsKeywordsPredicate predicate;

    public FindVendorCommand(VendorContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        UiState uiState = model.getUiState().getValue();
        if (uiState.isVendorDetails() || uiState.isEventList()) {
            throw new CommandException(MESSAGE_FIND_VENDOR_FAILURE_INVALID_VIEW);
        }

        FilteredList<Vendor> vendors = getPreviewOfFilteredVendors(model, predicate);
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
