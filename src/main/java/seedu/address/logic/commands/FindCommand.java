package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain any of "
            + "the specified tags and keywords (case-insensitive)\n"
            + "Parameters: /TAG KEYWORD [/MORE_TAGS MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " /name John";

    public static final String MESSAGE_SUCCESS = "Found %d matching entries!";

    public static final String MESSAGE_NO_ACTION = "No possible entries in EduConnect to find!";

    private final PersonContainsKeywordsPredicate predicate;

    // TODO : Add functionality so that we can group multiple tags at once
    public FindCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult executeCommand(Model model) {
        requireNonNull(model);
        assert model != null : "Model should not be null";
        model.updateFilteredPersonList(predicate);

        List<Person> remainingPersons = model.getFilteredPersonList();
        int newSize = remainingPersons.size();

        if (newSize == 0) {
            model.updateFilteredPersonList(x -> true);
            return new CommandResult(MESSAGE_NO_ACTION);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, newSize));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
