package seedu.address.logic.commands.findcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.student.StudentMatchesQueryPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindStudentCommand extends Command {

    public static final String COMMAND_WORD = "find_s";
    public static final String COMMAND_WORD_ALIAS = "fs";
    public static final int LIST_STUDENT_MARKER = 0;

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Finds all students whose fields contain any of "
        + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
        + "Fields searched: Student Number, Name, Email, Group and Tags\n"
        + "Parameters: KEYWORD \n"
        + "Example: " + COMMAND_WORD + "/" + COMMAND_WORD_ALIAS + " alice";
    public static final String NO_STUDENTS_FOUND = "No students found";

    private final StudentMatchesQueryPredicate predicate;

    public FindStudentCommand(StudentMatchesQueryPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        model.setStateStudents();
        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(NO_STUDENTS_FOUND, LIST_STUDENT_MARKER);
        }
        return new CommandResult(
            String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
            LIST_STUDENT_MARKER);
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        return versionHistory;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindStudentCommand)) {
            return false;
        }

        FindStudentCommand otherFindStudentCommand = (FindStudentCommand) other;
        return predicate.equals(otherFindStudentCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("predicate", predicate)
            .toString();
    }
}
