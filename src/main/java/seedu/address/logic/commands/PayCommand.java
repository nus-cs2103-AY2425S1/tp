package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUR;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import seedu.address.commons.core.index.Index;
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
 * Updates the amount of tuition fee paid by an existing student in the address book.
 */
public class PayCommand extends Command {

    public static final String COMMAND_WORD = "pay";
    public static final String COMMAND_WORD_RANDOM_CASE = "PaY";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the payment made by the student identified "
            + "by the index number used in the displayed Student list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_HOUR + "HOURS PAID\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_HOUR + "3 ";

    public static final String MESSAGE_EDIT_STUDENT_SUCCESS = "%1$s paid $%2$.2f";

    private final Index index;
    private final double hour;

    /**
     * @param index of the student in the filtered student list to edit
     * @param hour number of hours the student paid
     */
    public PayCommand(Index index, double hour) {
        requireNonNull(index);

        this.index = index;
        this.hour = hour;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToEdit = lastShownList.get(index.getZeroBased());
        double amountPaid = calculatePaid(studentToEdit, hour);
        Student editedStudent = createEditedStudent(studentToEdit, amountPaid);
        String name = editedStudent.getName().toString();

        model.setStudent(studentToEdit, editedStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_STUDENT_SUCCESS, name, amountPaid));


    }

    /**
     * Creates and returns a {@code Student} with the paid attribute of {@code studentToEdit}
     * updated with {@code amountPaid}.
     */
    private static Student createEditedStudent(Student studentToEdit, double amountPaid) throws CommandException {
        assert studentToEdit != null;

        Name updatedName = studentToEdit.getName();
        Phone updatedPhone = studentToEdit.getPhone();
        Email updatedEmail = studentToEdit.getEmail();
        Address updatedAddress = studentToEdit.getAddress();
        Schedule updatedSchedule = studentToEdit.getSchedule();
        Subject updatedSubject = studentToEdit.getSubject();
        Rate updatedRate = studentToEdit.getRate();
        PaidAmount updatedPaid = updatePaidAmount(studentToEdit, amountPaid);
        OwedAmount updatedOwedAmount = studentToEdit.getOwedAmount();

        return new Student(
                updatedName, updatedPhone, updatedEmail, updatedAddress, updatedSchedule, updatedSubject, updatedRate,
                updatedPaid, updatedOwedAmount
        );
    }

    /**
     * Updates the paid amount of the student based on the hours the student paid.
     */
    private static PaidAmount updatePaidAmount(Student student, double amountPaid) throws CommandException {
        assert student != null && amountPaid > 0;
        double updatedPaid = student.getPaidAmount().value + amountPaid;
        if (!PaidAmount.isValidPaidAmount(updatedPaid)) {
            throw new CommandException(Messages.MESSAGE_LIMIT);
        }
        String amount = String.format("%.2f", updatedPaid);

        return new PaidAmount(amount);
    }

    private static double calculatePaid(Student student, double hour) throws CommandException {
        double paid = student.getRate().value * hour;
        if (!PaidAmount.isValidPaidAmount(paid)) {
            throw new CommandException(Messages.MESSAGE_LIMIT);
        }
        return BigDecimal.valueOf(paid)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PayCommand)) {
            return false;
        }

        PayCommand otherPayCommand = (PayCommand) other;
        return index.equals(otherPayCommand.index)
                && hour == otherPayCommand.hour;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("hour", hour)
                .toString();
    }

}

