package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddScheduleCommand.MESSAGE_DUPLICATE_SCHEDULE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Meeting;

/**
 * Edits an existing schedule identified by its index from the address book.
 */
public class EditScheduleCommand extends Command {

    public static final String COMMAND_WORD = "edit-schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the event identified by the index number used in the displayed schedule list.\n"
            + "Parameters: INDEX c/CONTACT_INDEX [n/NAME] [d/DATE] [t/TIME]\n" // Update the usage message
            + "Example: " + COMMAND_WORD + " 1 c/1 n/Team Meeting d/11-10-2024 t/1500";

    public static final String MESSAGE_EDIT_SCHEDULE_SUCCESS = "Edited Event: %1$s on %2$s %3$s";
    public static final String MESSAGE_INVALID_SCHEDULE_INDEX = "The schedule index provided is invalid.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index targetIndex; // The index of the schedule in the displayed schedule list
    private final EditScheduleDescriptor editScheduleDescriptor;

    /**
     * Creates an EditScheduleCommand to edit the specified schedule.
     *
     * @param targetIndex            The index of the schedule in the displayed schedule list.
     * @param editScheduleDescriptor The details to edit the schedule with. Each non-empty field
     *                               value will replace the corresponding field value of the schedule.
     * @throws NullPointerException if either {@code targetIndex} or {@code editScheduleDescriptor}
     *                              is null.
     */
    public EditScheduleCommand(Index targetIndex, EditScheduleDescriptor editScheduleDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(editScheduleDescriptor);

        this.targetIndex = targetIndex;
        this.editScheduleDescriptor = editScheduleDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Meeting meetingToEdit = getMeeting(model);
        String updatedName = editScheduleDescriptor.getName().orElse(meetingToEdit.getMeetingName());
        LocalDate updatedDate = editScheduleDescriptor.getDate().orElse(meetingToEdit.getMeetingDate());
        LocalTime updatedTime = editScheduleDescriptor.getTime().orElse(meetingToEdit.getMeetingTime());
        List<Index> contactIndexes = editScheduleDescriptor.getContactIndexes().orElse(List.of());

        List<UUID> newContactUids = contactIndexes.stream()
                .map(index -> model.getFilteredPersonList().get(index.getZeroBased()).getUid())
                .collect(Collectors.toList());

        List<UUID> updatedContactUids = getUpdatedContactUids(meetingToEdit, newContactUids);

        Meeting updatedMeeting = new Meeting(
                updatedContactUids,
                updatedName,
                updatedDate,
                updatedTime
        );

        // Check for conflicting schedules
        if (model.hasMeeting(updatedMeeting) && updatedMeeting.isSameMeeting(meetingToEdit)) {
            throw new CommandException(MESSAGE_DUPLICATE_SCHEDULE);
        }

        model.setMeeting(meetingToEdit, updatedMeeting);

        return new CommandResult(String.format(MESSAGE_EDIT_SCHEDULE_SUCCESS,
                updatedName, updatedDate.toString(), updatedTime.toString()));
    }

    private static List<UUID> getUpdatedContactUids(Meeting meetingToEdit, List<UUID> newContactUids) {
        List<UUID> previousContactUids = meetingToEdit.getContactUids();

        List<UUID> updatedContactUids = new ArrayList<>();
        for (UUID uid : previousContactUids) {
            if (!newContactUids.contains(uid)) {
                updatedContactUids.add(uid);
            }
        }
        for (UUID uid : newContactUids) {
            if (!previousContactUids.contains(uid)) {
                updatedContactUids.add(uid);
            }
        }

        // check if updated contact list is empty, if so, revert to previous contact list
        updatedContactUids = updatedContactUids.isEmpty() ? previousContactUids : updatedContactUids;
        return updatedContactUids;
    }

    private Meeting getMeeting(Model model) throws CommandException {
        List<Meeting> lastShownMeetingList = model.getWeeklySchedule();
        List<Person> lastShownPersonList = model.getFilteredPersonList();

        List<Index> contactIndex = editScheduleDescriptor.getContactIndexes().orElseThrow();

        // check if any of the contact indexes are invalid
        for (Index i : contactIndex) {
            if (i.getZeroBased() >= lastShownPersonList.size() || i.getZeroBased() < 0) {
                throw new CommandException("One or more contact indexes are invalid.");
            }
        }

        if (targetIndex.getZeroBased() >= lastShownMeetingList.size()) {
            throw new CommandException(MESSAGE_INVALID_SCHEDULE_INDEX);
        }

        return lastShownMeetingList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditScheduleCommand otherEditScheduleCommand)) {
            return false;
        }

        return targetIndex.equals(otherEditScheduleCommand.targetIndex)
                && editScheduleDescriptor.equals(otherEditScheduleCommand.editScheduleDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("editScheduleDescriptor", editScheduleDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the schedule with. Each non-empty field value will replace the
     * corresponding field value of the schedule.
     */
    public static class EditScheduleDescriptor {
        private String name;
        private LocalDate date; // Use LocalDate for date representation
        private LocalTime time; // Use LocalTime for time representation
        private List<Index> contactIndex;

        public EditScheduleDescriptor() {
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name.fullName;
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Optional<LocalTime> getTime() {
            return Optional.ofNullable(time);
        }

        public void setTime(LocalTime time) {
            this.time = time;
        }

        public Optional<List<Index>> getContactIndexes() {
            return Optional.ofNullable(contactIndex);
        }

        public void setContactIndex(List<Index> contactIndex) {
            this.contactIndex = contactIndex;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditScheduleDescriptor otherDescriptor)) {
                return false;
            }

            return getName().equals(otherDescriptor.getName())
                    && getDate().equals(otherDescriptor.getDate())
                    && getTime().equals(otherDescriptor.getTime())
                    && getContactIndexes().equals(otherDescriptor.getContactIndexes());
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("date", date)
                    .add("time", time)
                    .add("contactIndex", contactIndex)
                    .toString();
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, date, time, contactIndex);
        }
    }
}
