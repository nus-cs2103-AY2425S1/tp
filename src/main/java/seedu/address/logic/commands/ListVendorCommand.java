package seedu.address.logic.commands;

import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VENDORS;

public class ListVendorCommand extends ListCommand {

    public static final String MESSAGE_LIST_VENDOR_SUCCESS = "Vendor list shown successfully";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredVendorList(PREDICATE_SHOW_ALL_VENDORS);
        return new CommandResult(MESSAGE_LIST_VENDOR_SUCCESS);
    }
}
