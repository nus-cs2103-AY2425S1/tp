package seedu.eventtory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eventtory.model.Model.PREDICATE_SHOW_ALL_VENDORS;

import seedu.eventtory.model.Model;
import seedu.eventtory.ui.UiState;

/**
 * Lists all vendors in EventTory to the user.
 */
public class ListVendorCommand extends ListCommand {

    public static final String MESSAGE_LIST_VENDOR_SUCCESS = "Vendor list shown successfully";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setUiState(UiState.VENDOR_LIST);
        model.updateFilteredVendorList(PREDICATE_SHOW_ALL_VENDORS);
        return new CommandResult(MESSAGE_LIST_VENDOR_SUCCESS);
    }
}
