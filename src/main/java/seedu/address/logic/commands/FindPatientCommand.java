package seedu.address.logic.commands;

import seedu.address.model.Model;

public class FindPatientCommand extends Command{
    public static final String COMMAND_WORD = "find-patient";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Find Patient");
    }
}
