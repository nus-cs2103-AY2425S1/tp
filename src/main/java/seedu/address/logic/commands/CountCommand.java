package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Counts the number of persons in the address book.
 */
public class CountCommand extends Command {

    public static final String COMMAND_WORD = "count";
    public static final String MESSAGE_COUNT_PERSONS = "There are %d person(s) in your list.";

    @Override
    public CommandResult execute(Model model) {
        int personCount = model.getFilteredPersonList().size();
        return new CommandResult(String.format(MESSAGE_COUNT_PERSONS, personCount));
    }
}

