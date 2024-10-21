package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PersonWithCriteriaPredicate;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;

public class FilterCommand extends Command{

    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons whose appointment dates "
            + "or age group are within the specified range. \n"
            + "Parameters: "
            + PREFIX_AGE +  "AGE RANGE "
            + PREFIX_APPOINTMENT + "APPOINTMENT \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_AGE + " 70-79 "
            + PREFIX_APPOINTMENT + " 01/01/2024-01/01/2025";

    private final PersonWithCriteriaPredicate criteria;
    /**
     * @param criteria on the basis of which list is to be filtered
     */
    public FilterCommand(PersonWithCriteriaPredicate criteria) {
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
