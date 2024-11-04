package seedu.eventtory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eventtory.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import seedu.eventtory.model.Model;
import seedu.eventtory.ui.UiState;

/**
 * Lists all events in EventTory to the user.
 */
public class ListEventCommand extends ListCommand {

    public static final String MESSAGE_LIST_EVENT_SUCCESS = "Event list shown successfully";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setUiState(UiState.EVENT_LIST);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_LIST_EVENT_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof ListEventCommand);
    }
}
