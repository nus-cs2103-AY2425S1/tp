package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_FOUND_WORKEXP;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.Messages.MESSAGE_PERSON_FOUND_WORKEXP;

import seedu.address.model.Model;
import seedu.address.model.person.WorkExperienceContainsKeywordsPredicate;

/**
 * Finds and lists all persons in the address book whose work experience matches the specified criteria.
 * Keyword matching is case-insensitive.
 */
public class FindByWorkExperienceCommand extends Command {

    public static final String COMMAND_WORD = "findw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose work experience contains "
            + "the specified role, company, or year (case-insensitive) and "
            + "displays them as a list with index numbers.\n"
            + "Parameters: ROLE COMPANY YEAR\n"
            + "Example: " + COMMAND_WORD + " w/Intern,Google,2024";

    private final WorkExperienceContainsKeywordsPredicate predicate;

    public FindByWorkExperienceCommand(WorkExperienceContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Update the filtered list based on the predicate
        model.updateFilteredPersonList(predicate);
        int count = model.getFilteredPersonList().size();

        // Get the fully formatted work experience string
        String workExp = predicate.getFormattedWorkExperience();

        String message;
        if (count == 0) {
            message = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        } else if (count == 1) {
            message = String.format(MESSAGE_PERSON_FOUND_WORKEXP, workExp);
        } else {
            message = String.format(MESSAGE_PERSONS_FOUND_WORKEXP, count, workExp);
        }
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindByWorkExperienceCommand
                && predicate.equals(((FindByWorkExperienceCommand) other).predicate));
    }
}
