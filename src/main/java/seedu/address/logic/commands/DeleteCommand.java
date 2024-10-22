package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.DEFAULT_DELIMITER;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Deletes a student identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final CommandType COMMAND_TYPE = CommandType.DELETESTUDENT;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the index number used in the displayed student list.\n"
            + "Parameters: INDEX (must be a positive integer) [;INDEX...]\n"
            + "Example: " + COMMAND_WORD + " 1" + DEFAULT_DELIMITER + "2";

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student(s):\n%1$s";

    private final Set<Index> targetIndices;

    public DeleteCommand(Set<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    /**
     * Returns Command Type DELETESTUDENT
     *
     * @return Command Type DELETESTUDENT
     */
    @Override
    public CommandType getCommandType() {
        return COMMAND_TYPE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        boolean throwException = false;
        ArrayList<Index> outOfBounds = new ArrayList<>();

        for (Index item: targetIndices) {
            if (item.getZeroBased() >= lastShownList.size()) {
                throwException = true;
                outOfBounds.add(item);
            }
        }

        if (throwException) {
            String formattedOutOfBoundIndices = outOfBounds.stream()
                    .map(index -> String.valueOf(index.getOneBased()))
                    .collect(Collectors.joining(", "));
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN, formattedOutOfBoundIndices));
        }

        List<Student> deletedPeople = targetIndices.stream()
                .map(targetIndex -> lastShownList.get(targetIndex.getZeroBased()))
                .toList();


        deletedPeople.forEach(model::deleteStudent);

        String formattedDeletedPeople = deletedPeople.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n"));


        return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS, formattedDeletedPeople),
                COMMAND_TYPE);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndices.equals(otherDeleteCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .toString();
    }
}
