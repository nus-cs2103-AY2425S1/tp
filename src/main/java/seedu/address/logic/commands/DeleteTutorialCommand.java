package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tut.Tutorial;

/**
 * Deletes a tutorial class identified using it's tutorial id from the tutorial class list.
 */
public class DeleteTutorialCommand extends Command {


    public static final String COMMAND_WORD = "deleteTut";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tutorial identified by the tutorial ID used in the displayed tutorial list."
            + " Parameters: TUTORIAL ID\n"
            + "Example: " + COMMAND_WORD + " T1001";

    public static final String MESSAGE_DELETE_TUTORIAL_SUCCESS = "Deleted Tutorial: %1$s";
    public static final String MESSAGE_TUTORIAL_NOT_FOUND = "This tutorial class doesn't exist!";

    private final Tutorial tutorial;

    public DeleteTutorialCommand(Tutorial tutorial) {
        this.tutorial = tutorial;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTutorial(tutorial)) {
            throw new CommandException(MESSAGE_TUTORIAL_NOT_FOUND);
        }

        model.deleteTutorial(tutorial);

        return new CommandResult(String.format(MESSAGE_DELETE_TUTORIAL_SUCCESS, tutorial.getTutorialId()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTutorialCommand otherDeleteCommand)) {
            return false;
        }

        return tutorial.equals(otherDeleteCommand.tutorial);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tutorial", tutorial)
                .toString();
    }

}
