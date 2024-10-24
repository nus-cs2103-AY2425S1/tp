package tuteez.logic.commands;

import static java.util.Objects.requireNonNull;
import static tuteez.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.logging.Logger;

import tuteez.commons.core.LogsCenter;
import tuteez.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    private static final Logger logger = LogsCenter.getLogger(ListCommand.class);


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Executing list command");
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        int totalPersons = model.getFilteredPersonList().size();
        String logMessage = String.format("List command completed. Displaying %d %s",
                totalPersons, totalPersons == 1 ? "person" : "persons");
        logger.info(logMessage);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
