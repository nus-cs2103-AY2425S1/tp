package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;

import java.time.LocalDate;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;

/**
 * Edits an existing event in the address book.
 */
public class EditEventCommand extends Command {

    public static final String COMMAND_WORD = "edit_event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of an existing event. "
            + "Parameters: EVENT_ID "
            + "[" + PREFIX_EVENT_NAME + "EVENT_NAME] "
            + "[" + PREFIX_EVENT_DESCRIPTION + "EVENT_DESCRIPTION] "
            + "[" + PREFIX_EVENT_START_DATE + "EVENT_START_DATE] "
            + "[" + PREFIX_EVENT_END_DATE + "EVENT_END_DATE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_NAME + "Updated Meeting "
            + PREFIX_EVENT_DESCRIPTION + "Updated description "
            + PREFIX_EVENT_START_DATE + "2024-10-02 "
            + PREFIX_EVENT_END_DATE + "2024-10-11\n"
            + "Dates must be in YYYY-MM-DD format!";

    public static final String MESSAGE_SUCCESS = "Event edited: %1$s";
    public static final String MESSAGE_EVENT_NOT_FOUND = "This event does not exist in the address book";
    public static final String MESSAGE_NO_CHANGES = "No changes specified for the event";

    private final int eventId;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * Creates an EditEventCommand to edit the specified {@code Event}
     */
    public EditEventCommand(int eventId, EditEventDescriptor editEventDescriptor) {
        requireNonNull(editEventDescriptor);
        this.eventId = eventId;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasEventById(eventId)) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }

        Event eventToEdit = model.getEventById(eventId);
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        if (eventToEdit.equals(editedEvent)) {
            throw new CommandException(MESSAGE_NO_CHANGES);
        }

        model.setEvent(eventToEdit, editedEvent);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(editedEvent)), true);
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        EventName updatedName = editEventDescriptor.getName().orElse(eventToEdit.getEventName());
        EventDescription updatedDescription = editEventDescriptor.getDescription().orElse(eventToEdit.getEventDescription());
        EventDuration updatedDuration = editEventDescriptor.getDuration().orElse(eventToEdit.getEventDuration());

        return new Event(updatedName, updatedDescription, updatedDuration, eventToEdit.getEventId());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditEventCommand)) {
            return false;
        }

        EditEventCommand otherEditEventCommand = (EditEventCommand) other;
        return eventId == otherEditEventCommand.eventId
                && editEventDescriptor.equals(otherEditEventCommand.editEventDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventId", eventId)
                .add("editEventDescriptor", editEventDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class EditEventDescriptor {
        private EventName name;
        private EventDescription description;
        private EventDuration duration;

        public EditEventDescriptor() {}

        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setName(toCopy.name);
            setDescription(toCopy.description);
            setDuration(toCopy.duration); // This will now work correctly
        }

        public boolean isAnyFieldEdited() {
            return name != null || description != null || duration != null;
        }

        public void setName(EventName name) {
            this.name = name;
        }

        public Optional<EventName> getName() {
            return Optional.ofNullable(name);
        }

        public void setDescription(EventDescription description) {
            this.description = description;
        }

        public Optional<EventDescription> getDescription() {
            return Optional.ofNullable(description);
        }

        // New method to set duration using an EventDuration object
        public void setDuration(EventDuration duration) {
            this.duration = duration;
        }

        // Existing method to set duration using start and end dates
        public void setDuration(LocalDate startDate, LocalDate endDate) {
            if (startDate != null && endDate != null) {
                this.duration = new EventDuration(startDate, endDate);
            }
        }

        public Optional<EventDuration> getDuration() {
            return Optional.ofNullable(duration);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            EditEventDescriptor otherDescriptor = (EditEventDescriptor) other;
            return getName().equals(otherDescriptor.getName())
                    && getDescription().equals(otherDescriptor.getDescription())
                    && getDuration().equals(otherDescriptor.getDuration());
        }
    }

}
