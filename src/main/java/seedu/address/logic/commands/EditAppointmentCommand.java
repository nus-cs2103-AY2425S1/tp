package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Edits the details of an existing appointment in the appointment list.
 */
public class EditAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "editappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the appointment identified "
            + "by the index number used in the displayed appointment list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DATE + "yyyy-MM-dd] "
            + "[" + PREFIX_FROM + "HH:mm] "
            + "[" + PREFIX_TO + "HH:mm]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_FROM + "14:00 "
            + PREFIX_TO + "16:00";

    public static final String MESSAGE_EDIT_APPOINTMENT_SUCCESS = "Edited appointment with %s:\n%s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditAppointmentDescriptor editAppointmentDescriptor;

    private Appointment appointmentToEdit;
    private Appointment editedAppointment;

    private int sourceIndex;

    /**
     * @param index of the appointment in the filtered appointment list to edit
     * @param editAppointmentDescriptor details to edit the appointment with
     */
    public EditAppointmentCommand(Index index, EditAppointmentDescriptor editAppointmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editAppointmentDescriptor);

        this.index = index;
        this.editAppointmentDescriptor = editAppointmentDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        SortedList<Appointment> lastShownList = (SortedList<Appointment>) model.getFilteredAppointmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        appointmentToEdit = lastShownList.get(index.getZeroBased());
        try {
            editedAppointment = createEditedAppointment();
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }

        checkForConflictingAppointments(model);

        sourceIndex = lastShownList.getSourceIndex(index.getZeroBased());
        model.setAppointment(sourceIndex, editedAppointment);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);

        String feedback = String.format(MESSAGE_EDIT_APPOINTMENT_SUCCESS, editedAppointment.name(),
                Messages.format(editedAppointment));
        return new CommandResult(feedback, true, false, false);
    }

    private Appointment createEditedAppointment() {
        LocalDate updatedDate = editAppointmentDescriptor.getDate().orElse(appointmentToEdit.date());
        LocalTime updatedStartTime = editAppointmentDescriptor.getStartTime().orElse(appointmentToEdit.startTime());
        LocalTime updatedEndTime = editAppointmentDescriptor.getEndTime().orElse(appointmentToEdit.endTime());

        return new Appointment(appointmentToEdit.name(), updatedDate, updatedStartTime, updatedEndTime);
    }

    private void checkForConflictingAppointments(Model model) throws CommandException {
        List<Appointment> conflicts = model.getConflictingAppointments(appointmentToEdit, editedAppointment);
        if (conflicts.isEmpty()) {
            return;
        }

        throw new CommandException(
                String.format(Messages.MESSAGE_CONFLICTING_APPOINTMENTS, Messages.format(conflicts)));
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public String undo(Model model, CommandHistory pastCommands) {
        model.setAppointment(sourceIndex, appointmentToEdit);
        pastCommands.remove();
        return String.format(UndoCommand.MESSAGE_UNDO_EDIT_APPOINTMENT, appointmentToEdit.name(),
                Messages.format(appointmentToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAppointmentCommand otherEditAppointmentCommand)) {
            return false;
        }

        return index.equals(otherEditAppointmentCommand.index)
                && editAppointmentDescriptor.equals(otherEditAppointmentCommand.editAppointmentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editAppointmentDescriptor", editAppointmentDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the appointment with. Each non-empty field value will replace the
     * corresponding field value of the appointment.
     */
    public static class EditAppointmentDescriptor {
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(date, startTime, endTime);
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setStartTime(LocalTime startTime) {
            this.startTime = startTime;
        }

        public Optional<LocalTime> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        public void setEndTime(LocalTime endTime) {
            this.endTime = endTime;
        }

        public Optional<LocalTime> getEndTime() {
            return Optional.ofNullable(endTime);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAppointmentDescriptor otherEditAppointmentDescriptor)) {
                return false;
            }

            return Objects.equals(date, otherEditAppointmentDescriptor.date)
                    && Objects.equals(startTime, otherEditAppointmentDescriptor.startTime)
                    && Objects.equals(endTime, otherEditAppointmentDescriptor.endTime);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("date", date)
                    .add("startTime", startTime)
                    .add("endTime", endTime)
                    .toString();
        }
    }
}
