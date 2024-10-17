package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PETS;

import seedu.address.model.Model;

public class ListPetCommand extends ListCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all pets";
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPetList(PREDICATE_SHOW_ALL_PETS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
