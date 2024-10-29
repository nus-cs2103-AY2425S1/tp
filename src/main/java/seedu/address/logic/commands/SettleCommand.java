package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.OwedAmount;
import seedu.address.model.student.PaidAmount;
import seedu.address.model.student.SettleAmount;
import seedu.address.model.student.Student;


/**
 * Updates owedAmount and paidAmount of a student in the address book by provided amount.
 */
public class SettleCommand extends Command {

    public static final String COMMAND_WORD = "settle";
    public static final String COMMAND_WORD_RANDOM_CASE = "sEttLe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates owed amount and paid amount of the student "
            + "identified by the index number used in the displayed Student list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_AMOUNT + "AMOUNT";

    public static final String MESSAGE_SETTLE_SUCCESS = "Payment of %.2f has been settled for %s";
    public static final String MESSAGE_INVALID_AMOUNT = "Entered amount is more than amount owed";

    private final Index index;
    private final SettleAmount amount;

    /**
     * @param index The index of the student in the filtered student list.
     * @param amount The amount to settle for the student.
     */
    public SettleCommand(Index index, SettleAmount amount) {
        requireNonNull(index);
        assert amount.value > 0 : "assertion failed: amount must be positive";

        this.index = index;
        this.amount = amount;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToUpdate = lastShownList.get(index.getZeroBased());
        Student updatedStudent = createUpdatedStudent(studentToUpdate);

        model.setStudent(studentToUpdate, updatedStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

        return new CommandResult(String.format(MESSAGE_SETTLE_SUCCESS, amount, studentToUpdate.getName()));
    }

    /**
     * Creates a new student instance with updated paid and owed amounts based on the provided amount.
     *
     * @param studentToUpdate The student whose amounts need to be updated.
     * @return A new Student object with updated payment details.
     * @throws CommandException If the amount to settle exceeds the owed amount.
     */
    public Student createUpdatedStudent(Student studentToUpdate) throws CommandException {
        assert studentToUpdate != null;

        PaidAmount updatedPaidAmount = updatePaidAmount(studentToUpdate.getPaidAmount(), amount);
        OwedAmount updatedOwedAmount = updateOwedAmount(studentToUpdate.getOwedAmount(), amount);

        return new Student(
                studentToUpdate.getName(),
                studentToUpdate.getPhone(),
                studentToUpdate.getEmail(),
                studentToUpdate.getAddress(),
                studentToUpdate.getSchedule(),
                studentToUpdate.getSubject(),
                studentToUpdate.getRate(),
                updatedPaidAmount,
                updatedOwedAmount
                );
    }

    private static PaidAmount updatePaidAmount(PaidAmount paidAmount, SettleAmount amountPaid) {
        return paidAmount.updateValue(amountPaid);
    }

    private static OwedAmount updateOwedAmount(OwedAmount owedAmount, SettleAmount amountPaid) throws CommandException {
        if (owedAmount.isGreater(amountPaid)) {
            throw new CommandException(MESSAGE_INVALID_AMOUNT);
        }
        return owedAmount.decreaseValue(amountPaid);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SettleCommand)) {
            return false;
        }

        SettleCommand otherSettleCommand = (SettleCommand) other;
        return index.equals(otherSettleCommand.index)
                && amount == otherSettleCommand.amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("amount", amount)
                .toString();
    }
}
