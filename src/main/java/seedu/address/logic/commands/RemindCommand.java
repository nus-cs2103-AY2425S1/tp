package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.student.Days;
import seedu.address.model.student.Student;

/**
 * Lists all student with lesson scheduled for today in the Result Displau box.
 */
public class RemindCommand extends Command {

    public static final String COMMAND_WORD = "remind";
    public static final String COMMAND_WORD_RANDOM_CASE = "ReMiNd";

    public static final Days TODAY = getToday();

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Student> reminder = model.getScheduledStudents(TODAY);

        return new CommandResult(Messages.getReminderMessage(TODAY, reminder));
    }


    private static Days getToday() {
        LocalDate currentDay = LocalDate.now();
        DayOfWeek dayOfWeek = currentDay.getDayOfWeek();

        return Days.valueOf(dayOfWeek.toString());
    }

}
