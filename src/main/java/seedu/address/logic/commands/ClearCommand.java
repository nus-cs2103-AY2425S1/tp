package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.DefaultCommandResult;
import seedu.address.model.ClinicConnectSystem;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "ClinicConnect system has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setClinicConnectSystem(new ClinicConnectSystem());
        return new DefaultCommandResult(MESSAGE_SUCCESS);
    }
}
