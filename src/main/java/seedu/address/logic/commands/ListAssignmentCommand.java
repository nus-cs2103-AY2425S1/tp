package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all assignments to the user.
 */
public class ListAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "listAsg";

    public static final String MESSAGE_SUCCESS = "All current assignments: ";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String assignmentList = model.listAssignments();
        return new CommandResult(MESSAGE_SUCCESS + "\n" + assignmentList);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof ListAssignmentCommand);
    }
}
