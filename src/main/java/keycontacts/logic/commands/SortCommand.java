package keycontacts.logic.commands;

import static java.util.Objects.requireNonNull;
import static keycontacts.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static keycontacts.logic.parser.CliSyntax.PREFIX_GRADE_LEVEL;
import static keycontacts.logic.parser.CliSyntax.PREFIX_GROUP;
import static keycontacts.logic.parser.CliSyntax.PREFIX_NAME;
import static keycontacts.logic.parser.CliSyntax.PREFIX_PHONE;

import keycontacts.model.Model;
import keycontacts.model.student.StudentComparator;

/**
 * Sorts all students in the student directory by the specified field in specified order.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted students by ";

    public static final String MESSAGE_SORT_CLEAR = "Cleared sorting conditions!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the displayed students by the specified field(s) in specified order. "
            + "Use 'sort clear' to clear existing sorting conditions\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "ASC/DESC] "
            + "[" + PREFIX_PHONE + "ASC/DESC] "
            + "[" + PREFIX_ADDRESS + "ASC/DESC] "
            + "[" + PREFIX_GRADE_LEVEL + "ASC/DESC] "
            + "[" + PREFIX_GROUP + "ASC/DESC]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_GRADE_LEVEL + "ASC " + PREFIX_NAME + "DESC";

    private final StudentComparator comparator;

    public SortCommand(StudentComparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortStudentList(this.comparator);

        if (this.comparator == null) {
            return new CommandResult(MESSAGE_SORT_CLEAR);
        }
        return new CommandResult(MESSAGE_SUCCESS + comparator.getSortDescription());
    }

    public StudentComparator getComparator() {
        return comparator;
    }
}
