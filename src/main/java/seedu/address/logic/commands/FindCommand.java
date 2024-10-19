package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.student.Student;
import seedu.address.model.student.predicates.AttributeContainsKeywordsPredicate;

import java.util.List;
import java.util.function.Predicate;

/**
 * Finds and lists all students in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_WORD_RANDOM_CASE = "FiNd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students whose:"
            + "1. names contain any of the specified KEYWORDS (case-insensitive)"
            + "Parameters: [n/KEYWORD [MORE_KEYWORDS]]...\n"
            + "2. schedules contain any of the specified KEYWORDS (case-insensitive)"
            + "Parameters: [d/KEYWORD [MORE_KEYWORDS]]...\n"
            + "and displays them as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD + " n/alice bob charlie" + " d/monday tuesday";

    public static final String MESSAGE_NO_PARAMETERS = "At least one field to find must be provided.";
    public static final String MESSAGE_NO_NAME_KEYWORDS = "At least one name keyword to find must be provided.";
    public static final String MESSAGE_NO_SCHEDULE_KEYWORDS = "At least one schedule keyword to find must be provided.";
    private final List<AttributeContainsKeywordsPredicate<?>> predicates;

    public FindCommand(List<AttributeContainsKeywordsPredicate<?>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(predicates);
        return new CommandResult(
                String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredStudentList().size()));
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
