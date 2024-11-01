package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all guests and vendors.\n";
    public static final String MESSAGE_TOTAL_GUEST = "There %s %d guest%s.\n";
    public static final String MESSAGE_TOTAL_VENDOR = "There %s %d vendor%s.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ObservableList<Person> guestList = model.getFilteredGuestList();
        ObservableList<Person> vendorList = model.getFilteredVendorList();

        int guestCount = guestList.size();
        int vendorCount = vendorList.size();

        String guestMessage = String.format(MESSAGE_TOTAL_GUEST,
                guestCount == 1 ? "is" : "are",
                guestCount,
                guestCount == 1 ? "" : "s");

        String vendorMessage = String.format(MESSAGE_TOTAL_VENDOR,
                vendorCount == 1 ? "is" : "are",
                vendorCount,
                vendorCount == 1 ? "" : "s");

        return new CommandResult(MESSAGE_SUCCESS + guestMessage + vendorMessage);
    }
}
