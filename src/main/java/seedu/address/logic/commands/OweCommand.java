package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUR;

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

public class OweCommand extends Command {
 
    public static final String COMMAND_WORD = "owe";
    public static final String COMMAND_WORD_RANDOM_CASE = "owE";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the amount the student owes "
            + "by the index number used in the displayed Student list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_HOUR + "HOUR OWED\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_HOUR + "2 ";
    
    public static final String MESSAGE_UPDATE_OWED_AMOUNT_SUCCESS = "Updated Student owedAmount: %1$s";
    public static final String MESSAGE_MISSING_HOUR = "Number of hours owed must be provided.";
    
    private final Index index;
    private final String newOwedAmount;
    
    /**
     * @param index of the student in the filtered student list to update owedAmount.
     * @param newOwedAmount new OwedAmount value.
     */
    public OweCommand(Index index, String newOwedAmount) {
        this.index = index;
        this.newOwedAmount = newOwedAmount;
    }
    
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();
        
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }
        
        Student studentToEdit = lastShownList.get(index.getZeroBased());
        Student editedStudent = createUpdatedOwedAmountStudent(studentToEdit, newOwedAmount);
        
        model.setStudent(studentToEdit, editedStudent);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        
        return new CommandResult(String.format(MESSAGE_UPDATE_OWED_AMOUNT_SUCCESS, Messages.format(editedStudent)));
    }
    
    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit}
     * updated with {@code newOwedAmount}
     */
    private static Student createUpdatedOwedAmountStudent(Student studentToEdit, String newOwedAmount) {
        assert studentToEdit != null;
        
        Name updatedName = studentToEdit.getName();
        Phone updatedPhone = studentToEdit.getPhone();
        Email updatedEmail = studentToEdit.getEmail();
        Address updatedAddress = studentToEdit.getAddress();
        Schedule updatedSchedule = studentToEdit.getSchedule();
        Subject updatedSubject = studentToEdit.getSubject();
        Rate updatedRate = studentToEdit.getRate();
        PaidAmount updatedPaidAmount = studentToEdit.getPaidAmount();
        OwedAmount updatedOwedAmount = studentToEdit.getOwedAmount().addOwedAmount(newOwedAmount);
        
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
        if (!(other instanceof OweCommand)) {
            return false;
        }
        
        OweCommand otherOweCommand = (OweCommand) other;
        return index.equals(otherOweCommand.index)
                && newOwedAmount.equals(otherOweCommand.newOwedAmount);
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("newOwedAmount",
                        newOwedAmount)
                .toString();
    }
}
