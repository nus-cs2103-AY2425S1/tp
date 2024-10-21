package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PersonWithCriteriaPredicate;

import static java.util.Objects.requireNonNull;

public class FilterCommand<T> extends Command{

    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons whose appointment dates "
            + "or age group are within the specified range (only integers) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " 70-79";

    private final PersonWithCriteriaPredicate<T> criteria;
    /**
     * @param criteria on the basis of which list is to be filtered
     */
    public FilterCommand(PersonWithCriteriaPredicate<T> criteria) {
        this.criteria = criteria;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(criteria);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
