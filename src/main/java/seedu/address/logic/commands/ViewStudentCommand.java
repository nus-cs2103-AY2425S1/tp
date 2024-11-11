package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Expands the details of a student identified using it's displayed index from the address book.
 */
public class ViewStudentCommand extends Command {

    public static final String COMMAND_WORD = "view_student";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays details of the student identified by the index number used in the displayed student list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_STUDENT_SUCCESS = "Displayed Student: %1$s";

    private final Index targetIndex;

    /**
     * @param index index of the student to display the details of
     */
    public ViewStudentCommand(Index index) {
        requireAllNonNull(index);

        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToDisplay = lastShownList.get(targetIndex.getZeroBased());

        return new CommandResult(String.format(MESSAGE_VIEW_STUDENT_SUCCESS, targetIndex.getZeroBased() + 1),
                studentToDisplay, targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewStudentCommand)) {
            return false;
        }

        ViewStudentCommand e = (ViewStudentCommand) other;
        return targetIndex.equals(e.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
