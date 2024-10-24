package keycontacts.logic.commands;

import static java.util.Objects.requireNonNull;
import static keycontacts.logic.parser.CliSyntax.PREFIX_GRADE_LEVEL;
import static keycontacts.logic.parser.CliSyntax.PREFIX_NAME;

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
            + ": Sorts all students in the student directory by the specified field in specified order.\n"
            + "Parameters: Name, Address, Phone Number, Grade Level\n"
            + "Command: " + COMMAND_WORD + " [prefix/[ASC or DESC]]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_GRADE_LEVEL + "/ASC " + PREFIX_NAME + "/DESC";

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
        System.out.println(comparator);
        return new CommandResult(MESSAGE_SUCCESS + comparator.getSortDescription());
    }

    public StudentComparator getComparator() {
        return comparator;
    }
}
