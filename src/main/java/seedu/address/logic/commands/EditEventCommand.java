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
     * Creates an EditEventCommand to edit the specified {@code Event}.
     *
     * @param eventId ID of the event to edit.
     * @param editEventDescriptor Details to edit the event with.
     */
    public EditEventCommand(int eventId, EditEventDescriptor editEventDescriptor) {
        requireNonNull(editEventDescriptor);
        this.eventId = eventId;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    /**
     * Executes the edit event command and updates the event in the model.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display.
     * @throws CommandException if the event ID does not exist or no changes are specified.
     */
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
     * Creates and returns an {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     *
     * @param eventToEdit The event to edit.
     * @param editEventDescriptor Details to edit the event with.
     * @return A new event instance with updated details.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        EventName updatedName = editEventDescriptor.getName().orElse(eventToEdit.getEventName());
        EventDescription updatedDescription = editEventDescriptor.getDescription()
                .orElse(eventToEdit.getEventDescription());
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

        /**
         * Copy constructor.
         * A defensive copy of {@code toCopy} is used internally.
         *
         * @param toCopy The descriptor to copy from.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setName(toCopy.name);
            setDescription(toCopy.description);
            setDuration(toCopy.duration);
        }

        /**
         * Returns true if any field is edited.
         *
         * @return true if any field is edited, false otherwise.
         */
        public boolean isAnyFieldEdited() {
            return name != null || description != null || duration != null;
        }

        /**
         * Sets the name of the event.
         *
         * @param name The new name of the event.
         */
        public void setName(EventName name) {
            this.name = name;
        }

        /**
         * Returns the name of the event.
         *
         * @return An Optional containing the name of the event if present.
         */
        public Optional<EventName> getName() {
            return Optional.ofNullable(name);
        }

        /**
         * Sets the description of the event.
         *
         * @param description The new description of the event.
         */
        public void setDescription(EventDescription description) {
            this.description = description;
        }

        /**
         * Returns the description of the event.
         *
         * @return An Optional containing the description of the event if present.
         */
        public Optional<EventDescription> getDescription() {
            return Optional.ofNullable(description);
        }

        /**
         * Sets the duration of the event using an EventDuration object.
         *
         * @param duration The new duration of the event.
         */
        public void setDuration(EventDuration duration) {
            this.duration = duration;
        }

        /**
         * Sets the duration of the event using start and end dates.
         *
         * @param startDate The start date of the event.
         * @param endDate The end date of the event.
         */
        public void setDuration(LocalDate startDate, LocalDate endDate) {
            if (startDate != null && endDate != null) {
                this.duration = new EventDuration(startDate, endDate);
            }
        }

        /**
         * Returns the duration of the event.
         *
         * @return An Optional containing the duration of the event if present.
         */
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
