package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.student.Student;


/**
 * Displays total money earned, as well as total money
 * owed
 */
public class IncomeCommand extends Command {
    public static final String COMMAND_WORD = "income";
    public static final String COMMAND_WORD_RANDOM_CASE = "InCome";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        double totalOwed = 0;
        double totalPaid = 0;
        ObservableList<Student> studentList = model.getFilteredStudentList();

        for (Student student: studentList) {
            totalOwed += student.getOwedAmount().value;
            totalPaid += student.getPaid().value;
        }
        return new CommandResult("Total Paid: " + totalPaid + "   Total Owed: " + totalOwed);
    }
}
