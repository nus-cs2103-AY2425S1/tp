package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_FOUND_UNIVERSITY;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.Messages.MESSAGE_PERSON_FOUND_UNIVERSITY;

import seedu.address.model.Model;
import seedu.address.model.person.UniversityContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose university contains the argument keyword.
 * Keyword matching is case-insensitive.
 */
public class FindByUniversityCommand extends Command {

    public static final String COMMAND_WORD = "findu";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose universities contain "
            + "the specified keyword (case-insensitive) and displays them as a list.\n"
            + "Parameters: u/KEYWORD\n"
            + "Example: " + COMMAND_WORD + " u/NUS";

    private final UniversityContainsKeywordsPredicate predicate;

    public FindByUniversityCommand(UniversityContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        int count = model.getFilteredPersonList().size();
        String university = predicate.getKeyword();
        university = university.toUpperCase();
        String message;
        if (count == 0) {
            message = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        } else if (count == 1) {
            message = String.format(MESSAGE_PERSON_FOUND_UNIVERSITY, university);
        } else {
            message = String.format(MESSAGE_PERSONS_FOUND_UNIVERSITY, count, university);
        }
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindByUniversityCommand // instanceof handles nulls
                && predicate.equals(((FindByUniversityCommand) other).predicate));
    }
    @Override
    public String toString() {
        return FindByUniversityCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
    }

}
