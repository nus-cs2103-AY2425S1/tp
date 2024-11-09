package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonIsStarredPredicate;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists relevant contacts\n"
            + "Use 'list' only to view all contacts or 'list *' to list starred contacts\n"
            + "Avoid adding extra parameters or text after 'list' or 'list *'"
            + "Parameters: [*] (to list starred contacts)\n"
            + "Examples: \n"
            + "- " + COMMAND_WORD + "\n"
            + "- " + COMMAND_WORD + " *";

    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_EMPTY_LIST = "The list is currently empty.";
    public static final String MESSAGE_EMPTY_STARRED_LIST = "No contacts starred";
    public static final String MESSAGE_STARRED_LIST = "Starred contacts listed";
    public final PersonIsStarredPredicate predicate;

    /**
     * Lists all contacts as per usual.
     */
    public ListCommand() {
        this.predicate = null;
    }

    /**
     * Lists all starred contacts.
     */
    public ListCommand(PersonIsStarredPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (predicate == null) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.sortByName();
            List<Person> lastShownList = model.getFilteredPersonList();
            if (lastShownList.isEmpty()) {
                return new CommandResult(MESSAGE_EMPTY_LIST);
            }
        } else {
            model.updateFilteredPersonList(predicate);
            model.sortByName();
            List<Person> lastShownList = model.getFilteredPersonList();
            if (lastShownList.isEmpty()) {
                return new CommandResult(MESSAGE_EMPTY_STARRED_LIST);
            }
            return new CommandResult(MESSAGE_STARRED_LIST);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
