package seedu.address.logic.commands.event.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.person.Person;


/**
 * View the list of persons who are in an event.
 */
public class FindEventCommand extends Command {
    public static final String COMMAND_WORD = "find-event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " EVENT INDEX: View list of persons "
            + "in the event identified by the "
            + "index number used in the event list.\n"
            + "e.g. find-event 1";

    public static final String MESSAGE_SUCCESS = "Listing contacts of event: %1$s";

    private final Index targetIndex;

    /**
     * Creates a FindEventCommand.
     */
    public FindEventCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, EventManager eventManager) throws CommandException {
        requireNonNull(eventManager);
        List<Event> events = eventManager.getEventList();

        if (targetIndex.getZeroBased() >= events.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToView = events.get(targetIndex.getZeroBased());

        model.updateFilteredPersonList(eventManager.getPersonInEventPredicate(eventToView));

        updateContactsUiWithEventSpecificRoles(model, eventToView);

        return new CommandResult(String.format(MESSAGE_SUCCESS, eventToView.getName()));
    }

    private static void updateContactsUiWithEventSpecificRoles(Model model, Event eventToView) {
        ObservableList<Person> persons = model.getFilteredPersonList();
        ArrayList<Person> tempListOfPersons = new ArrayList<>();
        for (Person person : persons) {
            Person personWithEventSpecificRoles = new Person(person.getName(), person.getPhone(), person.getEmail(),
                    person.getAddress(), person.getTelegramUsername(), eventToView.makeRoles(person));
            personWithEventSpecificRoles.addObserver(person.getObserver());
            tempListOfPersons.add(personWithEventSpecificRoles);
        }
        for (Person person : tempListOfPersons) {
            person.showContactWithEventSpecificRoles();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindEventCommand)) {
            return false;
        }

        FindEventCommand otherFindEventCommand = (FindEventCommand) other;
        return targetIndex.equals(otherFindEventCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
