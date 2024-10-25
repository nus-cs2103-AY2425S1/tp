package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
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
            Map.of("median", Operation.MEDIAN, "mean", Operation.MEAN, "max", Operation.MAX,
                   "min", Operation.MIN, "stddev", Operation.STDDEV, "var", Operation.VAR));
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Performs aggregation operation the displayed "
            + "person list.\n"
            + "Parameters: OPERATION " + PREFIX_NAME + "EXAM_NAME\n"
            + "Operations can be: " + String.join(", ", OPERATION_TRANSLATE.keySet()) + "\n"
            + "example:\n" + "  aggGrade median\n" + "  aggGrade median n/midterm";

    public static final String MESSAGE_OPERATION_CONSTRAINTS = "Invalid operations passed. The available operations "
            + "are: \n" + String.join(", ", OPERATION_TRANSLATE.keySet());
    public static final String MESSAGE_EMPTY_LIST = "No exam matches your criteria.";

    public static final String MESSAGE_AGGREGATE_RESULT = "Result of the aggregation operation: %.2f%%";

    /**
     * Operations that can be done with the aggGrade command.
     */
    public enum Operation {
        MEDIAN,
        MEAN,
        MAX,
        MIN,
        STDDEV,
        VAR
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

    @Override
    public CommandResult execute(Model model) throws CommandException {
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

        if (filteredList.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        float result = filteredList.execute(operation);

        return new CommandResult(String.format(MESSAGE_AGGREGATE_RESULT, result));
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
                                            total, grade) -> grade.getScore() * grade.getWeightage() / 100,
                                    Float::sum)).toList());
        }

        public float getMedian() {
            List<Float> list = new ArrayList<>(this);
            int mid = list.size() / 2;

            if (list.isEmpty()) {
                throw new IllegalStateException();
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

            if (list.isEmpty()) {
                throw new IllegalStateException();
            }

            return list.stream().reduce(0F, Float::sum, Float::sum) / list.size();
        }

        public float getMax() {
            List<Float> list = new ArrayList<>(this);

            if (list.isEmpty()) {
            }

            list.sort(Comparator.reverseOrder());
            return list.get(0);
        }

        public float getMin() {
            List<Float> list = new ArrayList<>(this);

            if (list.isEmpty()) {
                throw new IllegalStateException();
            }

            list.sort(Comparator.naturalOrder());
            return list.get(0);
        }

        public float getVar() {
            float mean = this.getMean();

            float result = super.stream().map(grade -> (grade - mean) * (grade - mean)).reduce(0F, Float::sum);
            result /= this.size();

            return result;
        }

        public float getStddev() {
            return (float) Math.sqrt(this.getVar());
        }

        public float execute(Operation operation) {
            switch (operation) {
            case MEDIAN:
                return this.getMedian();
            case MEAN:
                return this.getMean();
            case MAX:
                return this.getMax();
            case MIN:
                return this.getMin();
            case STDDEV:
                return this.getStddev();
            case VAR:
                return this.getVar();
            default:
                throw new IllegalStateException();
            }
        }
    }
}
