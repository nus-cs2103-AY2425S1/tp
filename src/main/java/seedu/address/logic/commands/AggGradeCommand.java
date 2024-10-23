package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import seedu.address.model.Model;
import seedu.address.model.person.Grade;
import seedu.address.model.person.GradeList;
import seedu.address.model.person.Person;

public class AggGradeCommand extends Command {
    public static final String COMMAND_WORD = "aggGrade";
    public static final Map<String, Operation> OPERATION_TRANSLATE = Collections.unmodifiableMap(
            Map.of("median", Operation.MEDIAN));


    public enum Operation {
        MEDIAN
    }

    private Operation operation;

    public AggGradeCommand(Operation operation) {
        requireNonNull(operation);
        this.operation = operation;
    }

    private CommandResult executeMedian(Model model, SmartList filteredList) {
        requireNonNull(model);
        requireNonNull(filteredList);

        return new CommandResult(String.format("%.2f%%", filteredList.getMedian()));
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        SmartList filteredList =
                new SmartList(model.getFilteredPersonList().stream().map(Person::getGradeList).toList());

        switch (this.operation) {
        case MEDIAN:
            return executeMedian(model, filteredList);
        }
        throw new IllegalStateException();
    }

    private static class SmartList extends ArrayList<Float> {

        public SmartList(List<GradeList> gradeListList) {
            super(
                    gradeListList.stream().map(gradeList -> gradeList.getMap().values().stream()
                            .<Float>reduce(0F,
                                           (total, grade) -> grade.getScore() * grade.getWeightage() / 100,
                                           (a, b) -> a + b)).toList());
        }

        public float getMedian() {
            List<Float> list = new ArrayList<>(this);
            int mid = list.size() / 2;

            if (list.isEmpty()) {
                return 0;
            }

            list.sort(Comparator.naturalOrder());

            if (list.size() % 2 == 0) {
                return (list.get(mid - 1) + list.get(mid)) / 2;
            } else {
                return list.get(mid);
            }
        }
    }
}
