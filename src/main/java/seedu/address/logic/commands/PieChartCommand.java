package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;

import seedu.address.model.Model;
import seedu.address.ui.PieChartWindow;

/**
 * Displays a Pie Chart of the number of students in each class.
 */
public class PieChartCommand extends Command {
    public static final String COMMAND_WORD = "pie";
    public static final String MESSAGE_SUCCESS = "Displayed Pie Chart of number of students in each class";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Map<String, Integer> numOfStudentsInEachClass = new HashMap<>();
        model.getFilteredPersonList().forEach(person -> {
            assert person.getClassId() != null;
            String classId = person.getClassId().value;
            numOfStudentsInEachClass.put(classId, numOfStudentsInEachClass.getOrDefault(classId, 0) + 1);
        });
        assert numOfStudentsInEachClass != null : "numOfStudentsInEachClass map should not be null";
        PieChartWindow.setData(numOfStudentsInEachClass);
        return new CommandResult(MESSAGE_SUCCESS, false, true, false, false, false);
    }
}

