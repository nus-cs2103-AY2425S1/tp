package seedu.address.logic.commands.listcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.ui.DisplayType;

/**
 * Lists all persons in the address book to the user.
 */
public class ListPersonCommand extends ListCommand {

    // Command word using the 'list' prefix + 'person'
    public static final String COMMAND_WORD = COMMAND_PREFIX + "person";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS, DisplayType.PERSON_LIST);
    }
}
