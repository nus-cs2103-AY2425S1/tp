package hallpointer.address.logic.commands;

import static hallpointer.address.model.Model.PREDICATE_SHOW_ALL_MEMBERS;
import static java.util.Objects.requireNonNull;

import hallpointer.address.model.Model;

/**
 * Lists all members in the CCA system to the user.
 */
public class ListMembersCommand extends Command {

    public static final String COMMAND_WORD = "list_members";

    public static final String MESSAGE_SUCCESS = "Listed all members";
    public static final String MESSAGE_NO_MEMBERS = "Error: No members found.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (model.getFilteredMemberList().isEmpty()) {
            return new CommandResult(MESSAGE_NO_MEMBERS);
        }

        model.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
