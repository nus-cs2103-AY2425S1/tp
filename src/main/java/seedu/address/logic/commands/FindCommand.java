package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.DEFAULT_DELIMITER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Finds and lists all students in address book whose name contains any of the
 * argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final CommandType COMMAND_TYPE = CommandType.FINDSTUDENT;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD PARAMETERS [;PARAMETERS...] [MORE_KEYWORDS_WITH_PARAMETERS]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "alice" + DEFAULT_DELIMITER + "bob"
            + PREFIX_COURSE + "CS2100" + DEFAULT_DELIMITER + "CS2040";

    /** Raw unmodified list of predicates for use in equality checking */
    private final List<? extends Predicate<? super Student>> predicates;
    /** Protected for usage in testcases */
    private final Predicate<Student> combinedPredicate;

    /**
     * Creates a FindCommand given a single predicate.
     *
     * @param predicate Predicate.
     */
    public FindCommand(Predicate<Student> predicate) {
        this.predicates = List.of(predicate);
        this.combinedPredicate = predicate;
    }

    /**
     * Creates a FindCommand given a list of predicates.
     *
     * @param predicates List of predicates.
     */
    public FindCommand(List<? extends Predicate<? super Student>> predicates) {
        this.predicates = predicates;
        this.combinedPredicate = combinePredicates(predicates);
    }

    public Predicate<Student> getPredicate() {
        return student -> combinedPredicate.test(student);
    }

    /**
     * Returns a predicate made by chaining together the given predicates.
     *
     * @param predicates List of predicates.
     * @return Combined predicate.
     */
    private Predicate<Student> combinePredicates(List<? extends Predicate<? super Student>> predicates) {
        return student -> predicates.stream().allMatch(p -> p.test(student));
    }

    /**
     * Returns Command Type FINDSTUDENT
     *
     * @return Command Type FINDSTUDENT
     */
    @Override
    public CommandType getCommandType() {
        return COMMAND_TYPE;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(combinedPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredStudentList().size()),
                COMMAND_TYPE);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicates.equals(otherFindCommand.predicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicates", predicates)
                .toString();
    }
}
