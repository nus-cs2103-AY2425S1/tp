package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_HELP_PROMPT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
    public static final String MESSAGE_COMMAND_FORMAT = styleCommand(
            COMMAND_WORD + WHITESPACE + "[all]... [contacts]... [allcontacts]");
    public static final String NO_UNACCEPTABLE_WORDS = " There shouldn't be any words after "
            + "the list command word except for a few special cases. ";
    public static final String MESSAGE_WRONG_ARGUMENTS =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NO_UNACCEPTABLE_WORDS
                    + String.format(MESSAGE_HELP_PROMPT, HelpCommand.COMMAND_WORD
                    + " " + ListCommand.COMMAND_WORD));

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ListCommand otherListCommand)) {
            return false;
        }
        return true; // this sounds very strange
    }
}
