package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.RsvpedPredicate;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class NotRsvpListCommand extends Command {

    public static final String COMMAND_WORD = "notrsvplist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all persons who have not yet RSVPed.";

    public static final String MESSAGE_SUCCESS = "Listed all persons who have not yet RSVPed.";

    public static final Predicate<Person> SHOW_ALL_NOT_RSVPED_PERSONS = new RsvpedPredicate().negate();

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(SHOW_ALL_NOT_RSVPED_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
