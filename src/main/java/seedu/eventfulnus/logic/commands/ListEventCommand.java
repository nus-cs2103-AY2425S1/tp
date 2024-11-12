package seedu.eventfulnus.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eventfulnus.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import seedu.eventfulnus.model.Model;

/**
 * Lists all events in the address book to the user.
 */
public class ListEventCommand extends Command {

    public static final String COMMAND_WORD = "listevent";

    public static final String MESSAGE_SUCCESS = "Listed all events";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
