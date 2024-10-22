package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.person.RsvpedPredicate;


/**
 * Lists all persons in the address book who have RSVPed to the user.
 */
public class RsvpListCommand extends Command {

    public static final String COMMAND_WORD = "rsvplist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all persons who have RSVPed.";

    public static final String MESSAGE_SUCCESS = "Listed all persons who have RSVPed.";

    public static final Predicate<Person> SHOW_ALL_RSVPED_PERSONS = new RsvpedPredicate(RsvpStatus.COMING);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(SHOW_ALL_RSVPED_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
