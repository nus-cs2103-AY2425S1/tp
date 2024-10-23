package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Schedule;
import seedu.address.model.tag.Tag;

/**
 * Overwrites the details of an existing person's schedule in the address book.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Schedules an event for the provided contact.\n"
            + "The name of the schedule, if provided, must be alphanumeric.\n"
            + "If no date is provided, the existing schedule will be removed.\n"
            + "If a date is provided, it must be of the format yyyy-MM-dd.\n"
            + "If a time is provided, it must be of the format HH:mm.\n"
            + "Parameters: INDEX, SCHEDULE_NAME, DATE, TIME"
            + "Example: " + COMMAND_WORD + " 1 sn/appointment sd/2024-10-21 st/16:00";

    public static final String MESSAGE_MAKE_SCHEDULE_SUCCESS = "Scheduled an event for %s: %s";
    public static final String MESSAGE_CLEAR_SCHEDULE_SUCCESS = "Cleared scheduled for %s";
    public static final String MESSAGE_FAILURE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE);
    public static final String MESSAGE_SCHEDULE_UNCHANGED =
            "There was no change done to the existing schedule, if any.";

    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    private final Index index;
    private final ScheduleCommand.ScheduleDescriptor scheduleDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param scheduleDescriptor details to edit the person with
     */
    public ScheduleCommand(Index index, ScheduleDescriptor scheduleDescriptor) {
        requireNonNull(index);
        requireNonNull(scheduleDescriptor);

        this.index = index;
        this.scheduleDescriptor = new ScheduleCommand.ScheduleDescriptor(scheduleDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Schedule scheduleToEdit = personToEdit.getSchedule();
        Schedule editedSchedule = createEditedSchedule(scheduleToEdit, scheduleDescriptor);
        Person editedPerson = createPersonWithEditedSchedule(personToEdit, editedSchedule);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (editedSchedule.equals(scheduleToEdit)) {
            logger.fine(MESSAGE_SCHEDULE_UNCHANGED);
            throw new CommandException(MESSAGE_SCHEDULE_UNCHANGED);
        }

        if (editedSchedule.toString().isEmpty()) {
            return new CommandResult(String.format(
                    MESSAGE_CLEAR_SCHEDULE_SUCCESS,
                    editedPerson.getName().toString()
            ));
        } else {
            return new CommandResult(String.format(
                    MESSAGE_MAKE_SCHEDULE_SUCCESS,
                    editedPerson.getName().toString(), editedSchedule.toString()
            ));
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Schedule createEditedSchedule(
            Schedule scheduleToEdit,
            ScheduleCommand.ScheduleDescriptor scheduleDescriptor
    ) {
        assert scheduleToEdit != null;

        String updatedName = scheduleDescriptor.getScheduleName().orElse(scheduleToEdit.scheduleName);
        String updatedDateString = scheduleDescriptor.getDateString().orElse(scheduleToEdit.dateString);
        String updatedTimeString = scheduleDescriptor.getTimeString().orElse(scheduleToEdit.timeString);

        Schedule editedSchedule = new Schedule(updatedName, updatedDateString, updatedTimeString);

        // if all fields of the command is empty, refers to a clear schedule command
        if (!(scheduleDescriptor.getDateString().isPresent()
                || scheduleDescriptor.getScheduleName().isPresent()
                || scheduleDescriptor.getTimeString().isPresent())) {
            editedSchedule = new Schedule("", "", "");
        }

        return editedSchedule;
    }

    private static Person createPersonWithEditedSchedule(
            Person personToEdit,
            Schedule editedSchedule
    ) {
        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Set<Tag> tags = personToEdit.getTags();

        return new Person(name, phone, email, address, editedSchedule, tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleCommand)) {
            return false;
        }

        ScheduleCommand otherScheduleCommand = (ScheduleCommand) other;
        return index.equals(otherScheduleCommand.index)
                && scheduleDescriptor.equals(otherScheduleCommand.scheduleDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("scheduleDescriptor", scheduleDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the schedule of a person with. Each non-empty field value will replace the
     * corresponding field value of the schedule.
     */
    public static class ScheduleDescriptor {
        private LocalDate date;
        private LocalTime time;

        private String scheduleName;
        private String dateString;
        private String timeString;

        public ScheduleDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public ScheduleDescriptor(ScheduleCommand.ScheduleDescriptor toCopy) {
            setScheduleName(toCopy.scheduleName);
            setDateString(toCopy.dateString);
            setTimeString(toCopy.timeString);
            setDate(toCopy.date);
            setTime(toCopy.time);
        }

        public void setScheduleName(String scheduleName) {
            this.scheduleName = scheduleName;
        }

        public Optional<String> getScheduleName() {
            return Optional.ofNullable(scheduleName);
        }

        public void setDateString(String dateString) {
            this.dateString = dateString;
        }

        public Optional<String> getDateString() {
            return Optional.ofNullable(dateString);
        }

        public void setTimeString(String timeString) {
            this.timeString = timeString;
        }

        public Optional<String> getTimeString() {
            return Optional.ofNullable(timeString);
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setTime(LocalTime time) {
            this.time = time;
        }

        public Optional<LocalTime> getTime() {
            return Optional.ofNullable(time);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ScheduleCommand.ScheduleDescriptor)) {
                return false;
            }

            ScheduleCommand.ScheduleDescriptor otherScheduleDescriptor = (ScheduleCommand.ScheduleDescriptor) other;
            return Objects.equals(scheduleName, otherScheduleDescriptor.scheduleName)
                    && Objects.equals(dateString, otherScheduleDescriptor.dateString)
                    && Objects.equals(timeString, otherScheduleDescriptor.timeString)
                    && Objects.equals(date, otherScheduleDescriptor.date)
                    && Objects.equals(time, otherScheduleDescriptor.time);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("scheduleName", scheduleName)
                    .add("dateString", dateString)
                    .add("timeString", timeString)
                    .add("date", date)
                    .add("time", time)
                    .toString();
        }
    }
}
