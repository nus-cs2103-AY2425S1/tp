package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;

/**
 * Sorts the list of persons in the address book based on a specified parameter.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons by the specified parameter.\n"
            + "Parameters: name / subject / class / attendance\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_SUCCESS = "List sorted successfully.";
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final Comparator<? super Person> comparator;

    /**
     * Creates a SortCommand to sort the list of persons based on the specified {@code comparator}.
     */
    public SortCommand(Comparator<? super Person> comparator) {
        requireNonNull(comparator);
        this.comparator = comparator;
    }

    @Override
    public CommandResult executeCommand(Model model) throws CommandException {
        requireNonNull(model);
        assert model.getFilteredPersonList() != null : "Filtered person list should not be null";
        logger.info("Starting to execute sort command.");

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> sortedList = new ArrayList<>(model.getFilteredPersonList());
        sortedList.sort(comparator);
        model.setFilteredPersonList(sortedList);

        logger.info("List sorted successfully.");
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SortCommand)) {
            return false;
        }
        return true;
    }

}
