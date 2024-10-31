package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_FOUND_MAJOR;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.Messages.MESSAGE_PERSON_FOUND_MAJOR;

import seedu.address.model.Model;
import seedu.address.model.person.MajorContainsKeywordsPredicate;

/**
 * Finds and lists all persons in the address book whose major matches the specified keyword.
 * Keyword matching is case-insensitive.
 */
public class FindByMajorCommand extends Command {

    public static final String COMMAND_WORD = "findm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose major or course contains "
            + "the specified keyword (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " Computer Science";

    private final MajorContainsKeywordsPredicate predicate;

    public FindByMajorCommand(MajorContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Add this predicate to the current list of active filters
        model.addPredicateToFilteredPersonList(predicate);

        int count = model.getFilteredPersonList().size();
        String major = predicate.getKeyword();
        String message;
        if (count == 0) {
            message = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        } else if (count == 1) {
            message = String.format(MESSAGE_PERSON_FOUND_MAJOR, major);
        } else {
            message = String.format(MESSAGE_PERSONS_FOUND_MAJOR, count, major);
        }
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof FindByMajorCommand
                && predicate.equals(((FindByMajorCommand) other).predicate));
    }

    @Override
    public String toString() {
        return FindByMajorCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
    }
}
