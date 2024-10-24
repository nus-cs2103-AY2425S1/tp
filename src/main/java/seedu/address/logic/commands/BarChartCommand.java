package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import seedu.address.model.Model;
import seedu.address.model.person.MonthPaid;
import seedu.address.ui.BarChartWindow;


/**
 * Displays a Bar Chart of the number of students in each class.
 */
public class BarChartCommand extends Command {
    public static final String COMMAND_WORD = "bar";
    public static final String MESSAGE_SUCCESS = "Displayed Bar Chart of number of students who paid in each month";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Map<String, Integer> distributionOfMonthsPaid = new HashMap<>();
        model.getFilteredPersonList().forEach(person -> {
            assert person != null : "person should not be null";
            Set<MonthPaid> monthsPaid = person.getMonthsPaid();
            assert monthsPaid != null : "monthsPaid set should not be null";
            monthsPaid.forEach(monthPaid -> {
                assert monthPaid != null : "monthPaid should not be null";
                String month = monthPaid.monthPaidValue;
                distributionOfMonthsPaid.put(month, distributionOfMonthsPaid.getOrDefault(month, 0) + 1);
            });
        });
        assert distributionOfMonthsPaid != null : "distributionOfMonthsPaid map should not be null";
        BarChartWindow.setData(distributionOfMonthsPaid);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true, false, false);
    }
}

