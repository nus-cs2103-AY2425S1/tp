package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.Grade;
import seedu.address.model.person.GradeList;
import seedu.address.model.person.Person;

/**
 * Aggregation functionalities on grade based on operation and current filtered person list.
 */
public class AggGradeCommand extends Command {
    public static final String COMMAND_WORD = "aggGrade";
    public static final Map<String, Operation> OPERATION_TRANSLATE = Collections.unmodifiableMap(
            Map.of("median", Operation.MEDIAN, "mean", Operation.MEAN));
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Performs aggregation operationson the displayed "
            + "person list.\n"
            + "Parameters: OPERATION " + PREFIX_NAME + "EXAM_NAME\n"
            + "Operations can be: " + String.join(", ", OPERATION_TRANSLATE.keySet()) + "\n"
            + "example:\n" + "  aggGrade median\n" + "  aggGrade median n/midterm";

    public static final String MESSAGE_OPERATION_CONSTRAINTS = "Invalid operations passed. The available operations "
            + "are: \n" + String.join(", ", OPERATION_TRANSLATE.keySet());

    /**
     * Operations that can be done with the aggGrade command.
     */
    public enum Operation {
        MEDIAN,
        MEAN
    }

    private final Operation operation;
    private final String examName;

    /**
     * Constructs a {@code AggGradeCommand} to perform aggregate functions on grade.
     *
     * @param operation The respective operation to be performed.
     * @param examName The (optional) exam name to filter exams.
     */
    public AggGradeCommand(Operation operation, String examName) {
        requireNonNull(operation);

        this.operation = operation;
        this.examName = examName;
    }

    private CommandResult executeMedian(Model model, SmartList filteredList) {
        requireNonNull(model);
        requireNonNull(filteredList);

        return new CommandResult(String.format("%.2f%%", filteredList.getMedian()));
    }

    private CommandResult executeMean(Model model, SmartList filteredList) {
        requireNonNull(model);
        requireNonNull(filteredList);

        return new CommandResult(String.format("%.2f%%", filteredList.getMean()));
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        SmartList filteredList =
                SmartList.createFromGradeLists(
                        model.getFilteredPersonList().stream().map(Person::getGradeList).toList());

        if (examName != null) {
            Predicate<Grade> match = (grade) -> grade.getTestName().equalsIgnoreCase(this.examName);

            filteredList =
                    SmartList.createFromGrades(model.getFilteredPersonList().stream().map(Person::getGradeList)
                                                       .flatMap(gradeList -> gradeList.filter(match).getMap().values()
                                                               .stream())
                                                       .toList());
        }

        switch (this.operation) {
        case MEDIAN:
            return executeMedian(model, filteredList);
        case MEAN:
            return executeMean(model, filteredList);
        default:
            throw new IllegalStateException();
        }
    }

    private static class SmartList extends ArrayList<Float> {

        private SmartList(List<Float> convertedList) {
            super(convertedList);
        }

        public static SmartList createFromGrades(List<Grade> grades) {
            return new SmartList(grades.stream().map(Grade::getScore).toList());
        }

        public static SmartList createFromGradeLists(List<GradeList> gradeLists) {
            return new SmartList(
                    gradeLists.stream().map(gradeList -> gradeList.getMap().values().stream()
                            .reduce(0F, (
                                            total, grade) -> grade.getScore() *
                                            grade.getWeightage() / 100,
                                    Float::sum)).toList());
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

        public float getMean() {
            List<Float> list = new ArrayList<>(this);
            return list.stream().reduce(0F, Float::sum, Float::sum) / list.size();
        }
    }
}
