package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Seeds dummy data into the SocialBook.
 */
public class SeedCommand extends Command {

    public static final String COMMAND_WORD = "seed";
    public static final String MESSAGE_SUCCESS = "Seeded sample data";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Not implemented yet";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
