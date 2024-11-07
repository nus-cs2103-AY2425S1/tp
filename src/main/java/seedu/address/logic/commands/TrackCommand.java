package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.CategoryContainsKeywordPredicate;
import seedu.address.model.person.Person;

import java.util.List;

/**
 * Tracks and lists all persons in the address book who are in the specified category to the user.
 */
public class TrackCommand extends Command {

    public static final String COMMAND_WORD = "track";
    public static final String MESSAGE_INVALID_INPUT_ERROR = COMMAND_WORD
            + " command accepts only 1 predefined category (student or company).";
    public static final String MESSAGE_NO_CONTACTS_TO_TRACK = "There are no contacts to track.\n"
            + "Enter the command help to start adding student or company contacts!";

    public static final String MESSAGE_SUCCESS = "Listed all persons under category: %1$s\n"
            + "%2$s persons listed!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tracks and lists all contacts who are in the category of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: CATEGORY (must be one of the predefined categories (student, company))\n"
            + "Example: " + COMMAND_WORD + " student";

    private final CategoryContainsKeywordPredicate categoryPredicate;

    private final String category;

    /**
     * @param categoryPredicate the category to track from the list of predefined categories
     */
    public TrackCommand(CategoryContainsKeywordPredicate categoryPredicate) {
        requireAllNonNull(categoryPredicate);
        this.categoryPredicate = categoryPredicate;
        this.category = categoryPredicate.toString();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException{
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Checks if there are contacts in the list to add the tags to
        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_NO_CONTACTS_TO_TRACK);
        }
        model.updateFilteredPersonList(categoryPredicate);
        int size = model.getFilteredPersonList().size();
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.category, size));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TrackCommand t)) {
            return false;
        }

        return categoryPredicate.equals(t.categoryPredicate);
    }

}
