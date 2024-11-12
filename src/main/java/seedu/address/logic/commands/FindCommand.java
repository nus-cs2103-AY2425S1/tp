package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.student.predicate.ContainsKeywordsPredicate;
import seedu.address.ui.Ui.UiState;

/**
 * Finds and lists all students in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students based on either name, subject(s) or"
            + " level, using the specified keywords (case-insensitive) and "
            + "displays them as a list with index numbers.\n"
            + "Parameters (choose only one): " + PREFIX_NAME + "NAME [NAME]... or "
            + PREFIX_LEVEL + "LEVEL or "
            + PREFIX_SUBJECT + "SUBJECT [SUBJECT]..." + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Alex David or "
            + COMMAND_WORD + " " + PREFIX_LEVEL + "S2 NA or "
            + COMMAND_WORD + " " + PREFIX_SUBJECT + "MATH";

    private final ContainsKeywordsPredicate predicate;

    public FindCommand(ContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredStudentList().size()),
                UiState.DETAILS);
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
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
