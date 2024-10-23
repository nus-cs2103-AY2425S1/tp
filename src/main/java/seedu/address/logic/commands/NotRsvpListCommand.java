package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.person.RsvpedPredicate;

/**
 * Lists all persons in the address book who have not yet RSVPed to the user.
 */
public class NotRsvpListCommand extends Command {

    public static final String COMMAND_WORD = "notrsvplist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all persons who have not yet RSVPed.";

    public static final String MESSAGE_SUCCESS = "Listed all persons who have not yet RSVPed.";

    public static final Predicate<Person> SHOW_ALL_NOT_RSVPED_PERSONS = new RsvpedPredicate(RsvpStatus.PENDING);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(SHOW_ALL_NOT_RSVPED_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
