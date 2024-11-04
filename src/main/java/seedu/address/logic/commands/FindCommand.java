package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.student.predicates.AttributeContainsKeywordsPredicate;

/**
 * Finds and lists all students in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_WORD_RANDOM_CASE = "FiNd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students whose:\n"
            + "1. name contain any of the specified KEYWORDS (case-insensitive)\n"
            + "Parameters: [n/KEYWORD [MORE_KEYWORDS...]] AND\n"
            + "2. schedule is on any of the specified DAY keywords (case-insensitive)\n"
            + "Parameters: [d/DAY [MORE_DAYS...]]\n"
            + "and displays them as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD + " n/alice bob charlie" + " d/monday tuesday";

    public static final String MESSAGE_NO_PARAMETERS =
            "At least one field to find must be provided.\n" + MESSAGE_USAGE;
    public static final String MESSAGE_NO_NAME_KEYWORDS_AFTER_PREFIX =
            "At least one name keyword to find must be provided.";
    public static final String MESSAGE_NO_SCHEDULE_KEYWORDS_AFTER_PREFIX =
            "At least one day keyword to find must be provided.";
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
        return Objects.equals(predicates, otherFindCommand.predicates);
    }

    @Override
    public String toString() {
        String str = new ToStringBuilder(this)
                .add("predicates", predicates)
                .toString();
        return str;

    }

    @Override
    public int hashCode() {
        return Objects.hash(predicates);
    }
}
