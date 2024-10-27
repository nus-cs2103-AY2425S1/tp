package seedu.address.logic.commands.searchmode;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventManager;
import seedu.address.model.person.Person;

import java.util.function.Predicate;

public class SearchModeSearchCommand extends Command {
    private final Predicate<Person> predicate;
    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    public static final String MESSAGE_SUCCESS = "Added all Persons who fit search parameter";


    public SearchModeSearchCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, EventManager eventManager) {
        // combine the new predicates with the old ones
        Predicate<Person> currPredicate = model.getLastPredicate();
        Predicate<Person> newPredicate = currPredicate.or(predicate);
        model.updateFilteredPersonList(newPredicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
