package seedu.ddd.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;

import seedu.ddd.model.Model;
import seedu.ddd.model.contact.common.Contact;


/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all contacts";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all contacts.\n";

    public static final String TAG_MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_TAG
            + " : All contacts that match any of "
            + "the specified tags (case-insensitive) will be displayed as a list with index numbers.\n"
            + "Parameters: " + PREFIX_TAG + " TAG...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + " catering " + PREFIX_TAG + " photography";
    public static final String NAME_MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_NAME + " : All contacts with the "
            + "specified name (case-insensitive) will be displayed as a list with index numbers. \n"
            + "Parameters: " + PREFIX_NAME + "NAME...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + " jane ";


    private final Predicate<Contact> predicate;
    public ListCommand(Predicate<Contact> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredContactList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
