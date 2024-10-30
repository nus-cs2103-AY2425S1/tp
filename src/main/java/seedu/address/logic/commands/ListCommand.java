package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST_ARCHIVE;

import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all current persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists current/archived/all persons in the address book as a list with index numbers.\n"
            + "Should not be used with both " + PREFIX_LIST_ARCHIVE + " and " + PREFIX_LIST_ALL + " concurrently.\n"
            + PREFIX_LIST_ARCHIVE + " and " + PREFIX_LIST_ALL + " should not have a parameter value.\n"
            + "Parameters: "
            + "[" + PREFIX_LIST_ARCHIVE + "] "
            + "[" + PREFIX_LIST_ALL + "]\n"
            + "Examples: " + COMMAND_WORD + ", " + COMMAND_WORD + " archive/, " + COMMAND_WORD + " all/";

    public static final String MESSAGE_SUCCESS_ALL = "Listed all persons";
    public static final String MESSAGE_SUCCESS_CURRENT = "Listed all current persons";
    public static final String MESSAGE_SUCCESS_ARCHIVE = "Listed all archived persons";

    private final Predicate<Person> predicate;
    private final String successMessage;

    private ListCommand(Predicate<Person> predicate, String successMessage) {
        this.predicate = predicate;
        this.successMessage = successMessage;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(this.predicate);
        return new CommandResult(this.successMessage);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // handles null
        if (!(other instanceof ListCommand otherListCommand)) {
            return false;
        }

        return this.predicate.equals(otherListCommand.predicate)
                && this.successMessage.equals(otherListCommand.successMessage);
    }

    public static ListCommand ofCurrent() {
        return new ListCommand(Model.PREDICATE_SHOW_CURRENT_PERSONS, MESSAGE_SUCCESS_CURRENT);
    }

    public static ListCommand ofAll() {
        return new ListCommand(Model.PREDICATE_SHOW_ALL_PERSONS, MESSAGE_SUCCESS_ALL);
    }

    public static ListCommand ofArchive() {
        return new ListCommand(Model.PREDICATE_SHOW_ARCHIVED_PERSONS, MESSAGE_SUCCESS_ARCHIVE);
    }
}
