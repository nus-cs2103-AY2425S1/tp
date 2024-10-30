package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.person.Guest;
import seedu.address.model.person.Person;

/**
 * Displays the current number of guests and vendors.
 *
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_SUCCESS_GUEST = "There %s %d guest%s. (%d pending, %d coming, %d not coming)\n";
    public static final String MESSAGE_SUCCESS_VENDOR = "There %s %d vendor%s.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ObservableList<Person> guestList = model.getFilteredGuestList();
        ObservableList<Person> vendorList = model.getFilteredVendorList();

        int guestCount = guestList.size();
        int guestsPending = (int) guestList.stream()
                .filter(person -> rsvpPredicate(person, "P"))
                .count();

        int guestsComing = (int) guestList.stream()
                .filter(person -> rsvpPredicate(person, "A"))
                .count();

        int guestsNotComing = (int) guestList.stream()
                .filter(person -> rsvpPredicate(person, "D"))
                .count();

        int vendorCount = vendorList.size();

        String guestMessage = String.format(MESSAGE_SUCCESS_GUEST,
                guestCount == 1 ? "is" : "are",
                guestCount,
                guestCount == 1 ? "" : "s",
                guestsPending, guestsComing, guestsNotComing);

        String vendorMessage = String.format(MESSAGE_SUCCESS_VENDOR,
                vendorCount == 1 ? "is" : "are",
                vendorCount,
                vendorCount == 1 ? "" : "s");

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(guestMessage + vendorMessage);
    }

    /**
     * Predicate function used to filter the guestList.
     * If each person matches the rsvp status, returns true.
     * Else, returns false.
     */
    private boolean rsvpPredicate(Person p, String rsvpStatus) {
        assert(p instanceof Guest);
        Guest g = (Guest) p;
        if (g.getRsvp().rsvp.equals(rsvpStatus)) {
            return true;
        }
        return false;
    }
}
