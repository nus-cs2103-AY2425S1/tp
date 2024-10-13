package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODEL;

public class AddCarCommand extends Command {

    public static final String COMMAND_WORD = "add-car";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a new car to the client of the index provided, with the details provided by the user.\n"
            + "User must not currently have a car.\n"
            + "The index must be a positive integer. \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_VRN + "SGX 1234 B "
            + PREFIX_VIN + "KMHGH4JH3EU073801 "
            + PREFIX_MAKE + "Toyota "
            + PREFIX_MODEL + "Corolla ";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("This is for testing purposes." + MESSAGE_USAGE);
    }
}
