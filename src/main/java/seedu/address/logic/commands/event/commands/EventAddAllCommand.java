package seedu.address.logic.commands.event.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.person.Person;
import seedu.address.model.role.Attendee;
import seedu.address.model.role.Sponsor;
import seedu.address.model.role.Vendor;
import seedu.address.model.role.Volunteer;

/**
 * Adds all contacts from searchmode to an event.
 */
public class EventAddAllCommand extends Command {
    public static final String COMMAND_WORD = "add-all";
    public static final String COMMAND_WORD_SHORT_FORM = "aa";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " EVENT INDEX: Adds contacts listed in searchmode "
            + "to event. e.g. add-all 2";
    private Index targetIndex;

    public EventAddAllCommand(Index index) {
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model, EventManager eventManager) throws CommandException {
        requireNonNull(eventManager);
        List<Event> events = eventManager.getEventList();

        if (targetIndex.getZeroBased() >= events.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        HashSet<Index> attendees = new HashSet<>();
        HashSet<Index> volunteers = new HashSet<>();
        HashSet<Index> vendors = new HashSet<>();
        HashSet<Index> sponsors = new HashSet<>();
        addContactsToEventRoles(model, attendees, volunteers, vendors, sponsors);
        return new AddPersonToEventCommand(this.targetIndex, attendees, volunteers, vendors, sponsors)
                .execute(model, eventManager);
    }

    private void addContactsToEventRoles(Model model, HashSet<Index> attendees, HashSet<Index> volunteers,
                                         HashSet<Index> vendors, HashSet<Index> sponsors) throws CommandException {
        ObservableList<Person> persons = model.getFilteredPersonList();
        if (persons.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EMPTY_CONTACTS_ON_ADDALL);
        }
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            Index indexToAdd = Index.fromZeroBased(i);
            if (person.hasRole(new Attendee())) {
                attendees.add(indexToAdd);
            }
            if (person.hasRole(new Volunteer())) {
                volunteers.add(indexToAdd);
            }
            if (person.hasRole(new Vendor())) {
                vendors.add(indexToAdd);
            }
            if (person.hasRole(new Sponsor())) {
                sponsors.add(indexToAdd);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventAddAllCommand)) {
            return false;
        }

        EventAddAllCommand otherEventAddAllCommand = (EventAddAllCommand) other;
        return targetIndex.equals(otherEventAddAllCommand.targetIndex);
    }
}
