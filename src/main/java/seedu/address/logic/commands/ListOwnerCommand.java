package seedu.address.logic.commands;

import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_OWNERS;

public class ListOwnerCommand extends ListCommand{

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all owners";
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredOwnerList(PREDICATE_SHOW_ALL_OWNERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
