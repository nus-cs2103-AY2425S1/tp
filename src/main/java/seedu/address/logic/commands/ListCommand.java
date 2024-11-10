package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.WHITESPACE;
import static seedu.address.logic.Messages.styleCommand;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import seedu.address.model.Model;

/**
 * Lists all contacts in the address book to the user.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all contacts";
    public static final String MESSAGE_FUNCTION = "List is to show all contacts, as opposed to find command.";
    public static final String MESSAGE_COMMAND_FORMAT = styleCommand(COMMAND_WORD);
    public static final String MESSAGE_COMMAND_FORMAT_ALT_VERSION_ONE =
            styleCommand(COMMAND_WORD + WHITESPACE + "all");
    public static final String MESSAGE_COMMAND_FORMAT_ALT_VERSION_TWO =
            styleCommand(COMMAND_WORD + WHITESPACE + "contacts");
    public static final String MESSAGE_COMMAND_FORMAT_ALT_VERSION_THREE =
            styleCommand(COMMAND_WORD + WHITESPACE + "all contacts");

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
