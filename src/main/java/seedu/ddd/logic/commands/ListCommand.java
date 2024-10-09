package seedu.ddd.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;

import seedu.ddd.model.Model;
import seedu.ddd.model.person.Contact;

// TODO change to take in vargs (take in /t) also

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all contacts.\n"
            + "If tags are specified, all contacts that match any of "
            + "the specified tags (case-insensitive) will be displayed as a list with index numbers.\n"
            + "Parameters: " + PREFIX_TAG + " TAG...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + " catering " + PREFIX_TAG + " photography";

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
