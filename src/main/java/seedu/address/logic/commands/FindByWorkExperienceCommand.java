package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.WorkExperienceContainsKeywordsPredicate;


/**
 * Finds and lists all persons in the address book whose work experience the argument keyword.
 * Keyword matching is case-insensitive.
 */
public class FindByWorkExperienceCommand extends Command {

    public static final String COMMAND_WORD = "findw"; // This will be the command keyword for find by work experience

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose work experience contains "
            + "the specified role, company, and year, and displays them as a list with index numbers.\n"
            + "Parameters: ROLE, COMPANY, YEAR\n"
            + "Example: " + COMMAND_WORD + " w/Intern, Google, 2024";

    private final WorkExperienceContainsKeywordsPredicate predicate;

    public FindByWorkExperienceCommand(WorkExperienceContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindByWorkExperienceCommand // instanceof handles nulls
                && predicate.equals(((FindByWorkExperienceCommand) other).predicate)); // state check
    }
}
