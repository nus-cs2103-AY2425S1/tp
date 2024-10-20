package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.OwedAmount;
import seedu.address.model.student.Paid;
import seedu.address.model.student.Student;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

public class SettleCommand extends Command{

    public static final String COMMAND_WORD = "settle";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates owed amount and paid amount of the student "
            + "identified by the index number used in the displayed Student list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_AMOUNT + "AMOUNT] ";

    public static final String MESSAGE_SETTLE_SUCCESS = "";

    private final Index index;
    private final double amount;

    public SettleCommand(Index index, double amount) {
        requireNonNull(index);

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

        return new CommandResult("settle");
    }

    public Student createUpdatedStudent(Student studentToUpdate) {
        assert studentToUpdate != null;

        Paid updatedPaidAmount = updatePaidAmount(studentToUpdate.getPaid(), amount);
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

    private static Paid updatePaidAmount(Paid paidAmount, double amountPaid) {
        return paidAmount.updateValue(amountPaid);
    }

    private static OwedAmount updateOwedAmount(OwedAmount owedAmount, double amountPaid) {
        return owedAmount.decreaseValue(amountPaid);
    }
}
