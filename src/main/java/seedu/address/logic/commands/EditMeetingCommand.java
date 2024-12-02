package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
// import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.MeetingNotFoundException;
import seedu.address.model.person.exceptions.TimeClashException;

/**
 * Edits the details of an existing meeting in the address book.
 */
public class EditMeetingCommand extends Command {

    public static final String COMMAND_WORD = "editm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the meeting identified "
            + "by the index number used in the displayed meeting list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_START_TIME + "START TIME] "
            + "[" + PREFIX_END_TIME + "END TIME] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_LOCATION + "Discussion Room 3 "
            + PREFIX_START_TIME + "09-10-2024 13:00 "
            + PREFIX_END_TIME + "09-10-2024 14:00";

    public static final String MESSAGE_EDIT_MEETING_SUCCESS = "Edited Meeting: %1$s";
    public static final String MESSAGE_MEETING_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEETING = "This meeting already exists.";

    private final Index index;
    private final EditMeetingDescriptor editMeetingDescriptor;

    /**
     * @param index of the meeting in the meeting list to edit
     * @param editMeetingDescriptor details to edit the meeting with
     */
    public EditMeetingCommand(Index index, EditMeetingDescriptor editMeetingDescriptor) {
        requireNonNull(index);
        requireNonNull(editMeetingDescriptor);

        this.index = index;
        this.editMeetingDescriptor = new EditMeetingDescriptor(editMeetingDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (index.getZeroBased() >= model.getMeetingSize()) {
            System.out.println("INDEX VALUE: " + index.getZeroBased() + ", MEETING SIZE: " + model.getMeetingSize());
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_INDEX);
        }

        Meeting meetingToEdit = model.getMeeting(index.getZeroBased());
        Meeting editedMeeting = createEditedMeeting(meetingToEdit, editMeetingDescriptor);

        // Check if the new name exists or not
        Name newName = editedMeeting.getPersonToMeet();
        Person personBeingEdited = null;
        for (Person person : model.getFilteredPersonList()) {
            if (person.getName().equals(meetingToEdit.getPersonToMeet())) {
                personBeingEdited = person;
            }
        }

        Person personToEditMeeting = null;
        boolean personExists = false;
        for (Person person : model.getFilteredPersonList()) {
            if (person.getName().equals(newName)) {
                personExists = true;
                personToEditMeeting = person;
            }
        }

        // If person doesn't exist, throw exception
        if (!personExists) {
            throw new CommandException("The person you have stated does not exist.");
        }

        // If same meeting or existing meeting given, throw exception
        if (!meetingToEdit.equals(editedMeeting) && model.hasMeeting(editedMeeting)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        }

        try {
            // Find out if the meeting is for a new person, if yes add meeting, if no edit meeting
            if (meetingToEdit.getPersonToMeet().equals(editedMeeting.getPersonToMeet())) {
                // Same person; edit meeting
                model.setMeeting(personToEditMeeting, meetingToEdit, editedMeeting);
            } else {
                // Different person; delete for old, add for new
                model.deleteMeeting(personBeingEdited, meetingToEdit);
                model.addMeeting(personToEditMeeting, editedMeeting);
            }
        } catch (MeetingNotFoundException | TimeClashException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting));
    }

    /**
     * Creates and returns a {@code Meeting} with the details of {@code meetingToEdit}
     * edited with {@code editMeetingDescriptor}.
     */
    private static Meeting createEditedMeeting(Meeting meetingToEdit, EditMeetingDescriptor editMeetingDescriptor)
            throws CommandException {
        assert meetingToEdit != null;

        Name updatedName = editMeetingDescriptor.getName().orElse(meetingToEdit.getPersonToMeet());
        LocalDateTime updatedStartTime = editMeetingDescriptor.getStartTime().orElse(meetingToEdit.getStartTime());
        LocalDateTime updatedEndTime = editMeetingDescriptor.getEndTime().orElse(meetingToEdit.getEndTime());
        String updatedLocation = editMeetingDescriptor.getLocation().orElse(meetingToEdit.getLocation());

        if (!Meeting.isValidStartAndEndTime(updatedStartTime, updatedEndTime)) {
            throw new CommandException(Meeting.MESSAGE_CONSTRAINTS_TIME);
        }

        return new Meeting(updatedName, updatedStartTime, updatedEndTime, updatedLocation);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditMeetingCommand)) {
            return false;
        }

        EditMeetingCommand otherEditMeetingCommand = (EditMeetingCommand) other;
        return index.equals(otherEditMeetingCommand.index)
                && editMeetingDescriptor.equals(otherEditMeetingCommand.editMeetingDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editMeetingDescriptor", editMeetingDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the meeting with. Each non-empty field value will replace the
     * corresponding field value of the meeting.
     */
    public static class EditMeetingDescriptor {
        private Name name;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String location;

        public EditMeetingDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditMeetingDescriptor(EditMeetingDescriptor toCopy) {
            setName(toCopy.name);
            setStartTime(toCopy.startTime);
            setEndTime(toCopy.endTime);
            setLocation(toCopy.location);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, startTime, endTime, location);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public Optional<LocalDateTime> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

        public Optional<LocalDateTime> getEndTime() {
            return Optional.ofNullable(endTime);
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Optional<String> getLocation() {
            return Optional.ofNullable(location);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMeetingDescriptor)) {
                return false;
            }

            EditMeetingDescriptor otherEditMeetingDescriptor = (EditMeetingDescriptor) other;
            return Objects.equals(name, otherEditMeetingDescriptor.name)
                    && Objects.equals(startTime, otherEditMeetingDescriptor.startTime)
                    && Objects.equals(endTime, otherEditMeetingDescriptor.endTime)
                    && Objects.equals(location, otherEditMeetingDescriptor.location);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("startTime", startTime)
                    .add("endTime", endTime)
                    .add("location", location)
                    .toString();
        }
    }
}
