package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PriorityHighToLowComparator;

public class SortByPriorityCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Persons list has been sorted from s to s";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateSortedPersonList(new PriorityHighToLowComparator());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
