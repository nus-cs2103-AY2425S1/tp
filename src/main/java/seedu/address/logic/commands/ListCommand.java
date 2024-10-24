// seedu/address/logic/commands/ListCommand.java

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;
import seedu.address.model.person.PersonIsVipPredicate;

/**
 * Lists all persons or VIP persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all persons.";
    public static final String MESSAGE_SUCCESS_VIP = "Listed all VIP persons.";

    private final boolean isVipFilter;

    /**
     * Creates a ListCommand with the option to filter only VIP persons.
     * @param isVipFilter If true, only VIP persons will be listed.
     */
    public ListCommand(boolean isVipFilter) {
        this.isVipFilter = isVipFilter;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (isVipFilter) {
            model.updateFilteredPersonList(new PersonIsVipPredicate());
            return new CommandResult(MESSAGE_SUCCESS_VIP);
        } else {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}

