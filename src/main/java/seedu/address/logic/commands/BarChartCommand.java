package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import seedu.address.model.Model;
import seedu.address.model.person.MonthPaid;
import seedu.address.ui.BarChartWindow;


/**
 * Displays a Pie Chart of the number of students in each class.
 */
public class BarChartCommand extends Command {
    public static final String COMMAND_WORD = "bar";
    public static final String MESSAGE_SUCCESS = "Displayed Bar Chart of number of students who paid in each month";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Map<String, Integer> distributionOfMonthsPaid = new HashMap<>();
        model.getFilteredPersonList().forEach(person -> {
            Set<MonthPaid> monthsPaid = person.getMonthsPaid();
            monthsPaid.forEach(monthPaid -> {
                String month = monthPaid.value;
                distributionOfMonthsPaid.put(month, distributionOfMonthsPaid.getOrDefault(month, 0) + 1);
            });
        });
        assert distributionOfMonthsPaid != null : "numOfStudentsInEachClass map should not be null";
        BarChartWindow.setData(distributionOfMonthsPaid);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true, false);
    }
}

