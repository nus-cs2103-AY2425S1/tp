package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWED_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAID_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.OwedAmount;
import seedu.address.model.student.PaidAmount;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Rate;
import seedu.address.model.student.Schedule;
import seedu.address.model.student.Student;
import seedu.address.model.student.Subject;

/**
 * Edits the details of an existing student in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_WORD_RANDOM_CASE = "EdiT";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the student identified "
            + "by the index number used in the displayed Student list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_SCHEDULE + "SCHEDULE] "
            + "[" + PREFIX_SUBJECT + "SUBJECT] "
            + "[" + PREFIX_RATE + "RATE] "
            + "[" + PREFIX_PAID_AMOUNT + "PAID] "
            + "[" + PREFIX_OWED_AMOUNT + "OWED] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_SCHEDULE + "Saturday-1000-1200 "
            + PREFIX_SUBJECT + "Mathematics "
            + PREFIX_RATE + "300 "
            + PREFIX_PAID_AMOUNT + "600 "
            + PREFIX_OWED_AMOUNT + "300 ";
    public static final String MESSAGE_EDIT_STUDENT_SUCCESS = "Edited Student: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This Student already exists in the address book.";


    private final Index index;
    private final EditStudentDescriptor editStudentDescriptor;

    /**
     * @param index of the student in the filtered student list to edit
     * @param editStudentDescriptor details to edit the student with
     */
    public EditCommand(Index index, EditStudentDescriptor editStudentDescriptor) {
        requireNonNull(index);
        requireNonNull(editStudentDescriptor);

        this.index = index;
        this.editStudentDescriptor = new EditStudentDescriptor(editStudentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToEdit = lastShownList.get(index.getZeroBased());
        Student editedStudent = createEditedStudent(studentToEdit, editStudentDescriptor);

        if (!studentToEdit.isSameStudent(editedStudent) && model.hasStudent(editedStudent)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        model.setStudent(studentToEdit, editedStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        List<Student> clashingStudents = model.getClashingStudents(editedStudent);
        if (clashingStudents.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_EDIT_STUDENT_SUCCESS, Messages.format(editedStudent)));
        } else {
            return new CommandResult(
                    String.format(MESSAGE_EDIT_STUDENT_SUCCESS, Messages.format(editedStudent))
                    + Messages.getWarningMessageForClashes(clashingStudents.size(), clashingStudents)
            );
        }

    }

    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit}
     * edited with {@code editStudentDescriptor}.
     */
    private static Student createEditedStudent(Student studentToEdit, EditStudentDescriptor editStudentDescriptor) {
        assert studentToEdit != null;

        Name updatedName = editStudentDescriptor.getName().orElse(studentToEdit.getName());
        Phone updatedPhone = editStudentDescriptor.getPhone().orElse(studentToEdit.getPhone());
        Email updatedEmail = editStudentDescriptor.getEmail().orElse(studentToEdit.getEmail());
        Address updatedAddress = editStudentDescriptor.getAddress().orElse(studentToEdit.getAddress());
        Schedule updatedSchedule = editStudentDescriptor.getSchedule().orElse(studentToEdit.getSchedule());
        Subject updatedSubject = editStudentDescriptor.getSubject().orElse(studentToEdit.getSubject());
        Rate updatedRate = editStudentDescriptor.getRate().orElse(studentToEdit.getRate());
        PaidAmount updatedPaidAmount = editStudentDescriptor.getPaidAmount().orElse(studentToEdit.getPaidAmount());
        OwedAmount updatedOwedAmount = editStudentDescriptor.getOwedAmount().orElse(studentToEdit.getOwedAmount());

        return new Student(
                updatedName, updatedPhone, updatedEmail, updatedAddress, updatedSchedule, updatedSubject, updatedRate,
                updatedPaidAmount, updatedOwedAmount
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editStudentDescriptor.equals(otherEditCommand.editStudentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editStudentDescriptor", editStudentDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the student with. Each non-empty field value will replace the
     * corresponding field value of the student.
     */
    public static class EditStudentDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Schedule schedule;
        private Subject subject;
        private Rate rate;
        private PaidAmount paidAmount;
        private OwedAmount owedAmount;

        public EditStudentDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditStudentDescriptor(EditStudentDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setSchedule(toCopy.schedule);
            setSubject(toCopy.subject);
            setRate(toCopy.rate);
            setPaidAmount(toCopy.paidAmount);
            setOwedAmount(toCopy.owedAmount);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, schedule, subject, rate, paidAmount,
                    owedAmount);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setSchedule(Schedule schedule) {
            this.schedule = schedule;
        }

        public Optional<Schedule> getSchedule() {
            return Optional.ofNullable(schedule);
        }
        public void setSubject(Subject subject) {
            this.subject = subject;
        }

        public Optional<Subject> getSubject() {
            return Optional.ofNullable(subject);
        }

        public void setRate(Rate rate) {
            this.rate = rate;
        }

        public Optional<Rate> getRate() {
            return Optional.ofNullable(rate);
        }

        public void setPaidAmount(PaidAmount paidAmount) {
            this.paidAmount = paidAmount;
        }
        public Optional<PaidAmount> getPaidAmount() {
            return Optional.ofNullable(paidAmount);
        }

        public void setOwedAmount(OwedAmount owedAmount) {
            this.owedAmount = owedAmount;
        }

        public Optional<OwedAmount> getOwedAmount() {
            return Optional.ofNullable(owedAmount);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditStudentDescriptor)) {
                return false;
            }

            EditStudentDescriptor otherEditStudentDescriptor = (EditStudentDescriptor) other;
            return Objects.equals(name, otherEditStudentDescriptor.name)
                    && Objects.equals(phone, otherEditStudentDescriptor.phone)
                    && Objects.equals(email, otherEditStudentDescriptor.email)
                    && Objects.equals(address, otherEditStudentDescriptor.address)
                    && Objects.equals(schedule, otherEditStudentDescriptor.schedule)
                    && Objects.equals(subject, otherEditStudentDescriptor.subject)
                    && Objects.equals(rate, otherEditStudentDescriptor.rate)
                    && Objects.equals(paidAmount, otherEditStudentDescriptor.paidAmount)
                    && Objects.equals(owedAmount, otherEditStudentDescriptor.owedAmount);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("schedule", schedule)
                    .add("subject", subject)
                    .add("rate", rate)
                    .add("paidAmount", paidAmount)
                    .add("owedAmount", owedAmount)
                    .toString();
        }

    }
}
