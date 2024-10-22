package seedu.address.logic.commands.consultation;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONSULTATIONS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;

/**
 * Lists all consultations in the address book to the user.
 */
public class ListConsultsCommand extends Command {

    public static final String COMMAND_WORD = "listconsults";
    public static final CommandType COMMAND_TYPE = CommandType.LISTCONSULT;
    public static final String MESSAGE_SUCCESS = "Listed all consultations";

    @Override
    public CommandType getCommandType() {
        return COMMAND_TYPE;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredConsultationList(PREDICATE_SHOW_ALL_CONSULTATIONS);
        return new CommandResult(MESSAGE_SUCCESS, COMMAND_TYPE);
    }
}
