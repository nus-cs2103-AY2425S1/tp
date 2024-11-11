package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.EqualUtil.nullSafeEquals;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_EVENT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDEES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_ATTENDEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;

/**
 * Updates an Event in the address book.
 */
public class UpdateCommand extends Command {
    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates an event in the address book. "
            + "Multiple changes can be applied in a single command.\n"
            + "Parameters (index is one-based): "
            + PREFIX_INDEX + "<EVENT INDEX> "
            + "[" + PREFIX_NAME + "<NEW EVENT NAME>] "
            + "[" + PREFIX_START_DATE + "<yyyy-mm-dd>] "
            + "[" + PREFIX_END_DATE + "<yyyy-mm-dd>] "
            + "[" + PREFIX_ATTENDEES + "<PERSON INDICES> ] "
            + "[" + PREFIX_LOCATION + "<NEW LOCATION NAME> ] "
            + "[" + PREFIX_REMOVE_ATTENDEE + "<PERSON INDICES> ] \n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_INDEX + "3 "
            + PREFIX_NAME + "New Year's Party "
            + PREFIX_START_DATE + "2025-01-01 "
            + PREFIX_END_DATE + "2025-01-02 "
            + PREFIX_ATTENDEES + "1 2 4 5 "
            + PREFIX_LOCATION + "Marine Parade Road #12-34 "
            + PREFIX_REMOVE_ATTENDEE + "3 6";

    public static final String MESSAGE_SUCCESS = "Event has been updated: %1$s";

    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS = "The event index provided is invalid";

    private final String newName;
    private final LocalDate newStartDate;
    private final LocalDate newEndDate;
    private final Address newLocation;
    private final Set<Index> addIndices;
    private final Set<Index> removeIndices;
    private final Index indexToUpdate;

    /**
     * Creates an UpdateCommand to update an event to the specified {@code Event}
     */
    public UpdateCommand(String newName,
                         LocalDate newStartDate,
                         LocalDate newEndDate,
                         Address newLocation,
                         Set<Index> addIndices,
                         Set<Index> removeIndices,
                         Index indexToUpdate) {
        this.newName = newName;
        this.newStartDate = newStartDate;
        this.newEndDate = newEndDate;
        this.newLocation = newLocation;
        this.indexToUpdate = indexToUpdate;
        this.addIndices = addIndices;
        this.removeIndices = removeIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // TODO: add handling for integer overflow here,
        //  OR specify in UG that integers for index beyond max int are considered out of bounds.
        if (indexToUpdate.getZeroBased() >= model.getEventListLength()
                || indexToUpdate.getZeroBased() < 0) {
            throw new CommandException(MESSAGE_INDEX_OUT_OF_BOUNDS);
        }

        ObservableList<Event> eventList = model.getEventList();
        ObservableList<Person> personList = model.getAddressBook().getPersonList();

        Event oldEvent = eventList.get(indexToUpdate.getZeroBased());
        Event newEvent;

        // check if updated date is valid
        if (newStartDate == null && newEndDate != null) {
            checkValidDates(oldEvent.getStartDate(), newEndDate);
        } else if (newStartDate != null && newEndDate == null) {
            checkValidDates(newStartDate, oldEvent.getEndDate());
        } else if (newStartDate != null && newEndDate != null) {
            checkValidDates(newStartDate, newEndDate);
        }

        // Add and remove attendees
        Set<Person> changedAttendees = getChangedAttendees(oldEvent, personList);
        newEvent = new Event(
                newName.isEmpty() ? oldEvent.getEventName() : newName,
                newStartDate == null ? oldEvent.getStartDate() : newStartDate,
                newEndDate == null ? oldEvent.getEndDate() : newEndDate,
                newLocation == null ? oldEvent.getLocation() : newLocation,
                changedAttendees);

        assert newEvent != null;

        if (model.hasEvent(newEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.updateEvent(newEvent, indexToUpdate.getZeroBased());
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                Messages.formatEvent(newEvent)));
    }

    private Set<Person> getChangedAttendees(Event oldEvent, ObservableList<Person> personList)
            throws CommandException {
        Set<Person> oldAttendees = oldEvent.getAttendees();
        Set<Person> changedAttendees = new HashSet<>(oldAttendees);
        if (addIndices != null) {
            for (Index i : addIndices) {
                try {
                    Person p = personList.get(i.getZeroBased());
                    changedAttendees.add(p);
                } catch (IndexOutOfBoundsException e) {
                    throw new CommandException("Attendee index out of bounds.");
                }
            }
        }
        if (removeIndices != null) {
            for (Index i : removeIndices) {
                try {
                    Person p = personList.get(i.getZeroBased());
                    changedAttendees.remove(p);
                } catch (IndexOutOfBoundsException e) {
                    throw new CommandException("Attendee index out of bounds.");
                }
            }
        }
        return changedAttendees;
    }

    /**
     * Checks if two dates, start date and end date, are valid.
     * @param startDate the starting date
     * @param endDate the ending date
     * @throws CommandException if the end date occurs before the start date
     */
    private void checkValidDates(LocalDate startDate, LocalDate endDate) throws CommandException {
        if (endDate.isBefore(startDate)) {
            throw new CommandException(MESSAGE_INVALID_DATES);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateCommand)) {
            return false;
        }

        UpdateCommand otherUpdateCommand = (UpdateCommand) other;

        return nullSafeEquals(newName, otherUpdateCommand.newName)
                && nullSafeEquals(indexToUpdate, otherUpdateCommand.indexToUpdate)
                && nullSafeEquals(newStartDate, otherUpdateCommand.newStartDate)
                && nullSafeEquals(newEndDate, otherUpdateCommand.newEndDate)
                && nullSafeEquals(newLocation, otherUpdateCommand.newLocation)
                && nullSafeEquals(addIndices, otherUpdateCommand.addIndices)
                && nullSafeEquals(removeIndices, otherUpdateCommand.removeIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("newName", newName)
                .add("newStartDate", newStartDate)
                .add("newEndDate", newEndDate)
                .add("newLocation", newLocation)
                .add("indexToUpdate", indexToUpdate)
                .add("addIndices", addIndices)
                .add("removeIndices", removeIndices)
                .toString();
    }
}
