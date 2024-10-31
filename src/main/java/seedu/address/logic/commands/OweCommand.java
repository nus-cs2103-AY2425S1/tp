package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUR;

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
 * Updates the amount of tuition fee owed by an existing student in the address book.
 */
public class OweCommand extends Command {

    public static final String COMMAND_WORD = "owe";
    public static final String COMMAND_WORD_RANDOM_CASE = "owE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the amount the student owes "
            + "by the index number used in the displayed Student list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_HOUR + "HOUR OWED (must be a positive multiple of 0.5)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_HOUR + "2 ";

    public static final String MESSAGE_UPDATE_OWED_AMOUNT_SUCCESS = "%1$s owed another $%2$.2f";

    private final Index index;
    private final double hourOwed;

    /**
     * @param index of the student in the filtered student list to update owedAmount.
     * @param hourOwed number of hours the student owes.
     */
    public OweCommand(Index index, double hourOwed) {
        this.index = index;
        this.hourOwed = hourOwed;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToEdit = lastShownList.get(index.getZeroBased());
        Student editedStudent = createEditedStudent(studentToEdit, hourOwed);
        double amountOwed = calculateOwed(studentToEdit, hourOwed);
        String name = editedStudent.getName().toString();

        model.setStudent(studentToEdit, editedStudent);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);

        return new CommandResult(String.format(MESSAGE_UPDATE_OWED_AMOUNT_SUCCESS, name, amountOwed));
    }

    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit}
     * updated with {@code newOwedAmount}
     */
    private static Student createEditedStudent(Student studentToEdit, double hour) throws CommandException {
        assert studentToEdit != null;

        Name updatedName = studentToEdit.getName();
        Phone updatedPhone = studentToEdit.getPhone();
        Email updatedEmail = studentToEdit.getEmail();
        Address updatedAddress = studentToEdit.getAddress();
        Schedule updatedSchedule = studentToEdit.getSchedule();
        Subject updatedSubject = studentToEdit.getSubject();
        Rate updatedRate = studentToEdit.getRate();
        PaidAmount updatedPaidAmount = studentToEdit.getPaidAmount();
        OwedAmount updatedOwedAmount = updateOwedAmount(studentToEdit, hour);

        return new Student(
                updatedName, updatedPhone, updatedEmail, updatedAddress, updatedSchedule, updatedSubject, updatedRate,
                updatedPaidAmount, updatedOwedAmount
        );
    }

    /**
     * Updates the owed amount of the student based on the hours the student owed.
     */
    private static OwedAmount updateOwedAmount(Student student, double hour) throws CommandException {
        assert student != null && hour % 0.5 == 0;
        double updatedOwedAmount = student.getOwedAmount().value + calculateOwed(student, hour);
        String updatedOwedAmountString = String.format("%.2f", updatedOwedAmount);
        if (!OwedAmount.isValidOwedAmount(updatedOwedAmountString)) {
            throw new CommandException(Messages.MESSAGE_LIMIT);
        }
        return new OwedAmount(updatedOwedAmountString);
    }

    private static double calculateOwed(Student student, double hour) throws CommandException {
        double owedAmount = student.getRate().value * hour;
        return BigDecimal.valueOf(owedAmount)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OweCommand)) {
            return false;
        }

        OweCommand otherOweCommand = (OweCommand) other;
        return index.equals(otherOweCommand.index)
                && hourOwed == otherOweCommand.hourOwed;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("hourOwed",
                        hourOwed)
                .toString();
    }
}
