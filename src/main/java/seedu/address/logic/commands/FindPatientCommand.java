package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class FindPatientCommand extends Command{
    public static final String COMMAND_WORD = "find-patient";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("Command not implemented yet");
    }
}
