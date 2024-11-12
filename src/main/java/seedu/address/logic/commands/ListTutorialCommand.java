package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all tutorials to the user.
 */
public class ListTutorialCommand extends Command {

    public static final String COMMAND_WORD = "listTut";

    public static final String MESSAGE_SUCCESS = "All current tutorials: ";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String tutorialList = model.listTutorials();
        return new CommandResult(MESSAGE_SUCCESS + "\n" + tutorialList);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof ListTutorialCommand);
    }
}
