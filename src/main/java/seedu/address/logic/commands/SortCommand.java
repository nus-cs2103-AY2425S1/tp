package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts the list of persons in the address book based on a specified parameter.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list of persons by the specified parameter. "
            + "Parameters: name/subject/classes\n"
            + "Example: " + COMMAND_WORD + " name";

    private final Comparator<? extends Person> comparator;

    public SortCommand(Comparator<? extends Person> comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult executeCommand(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> sortedList = new ArrayList<>(model.getFilteredPersonList());
        sortedList.sort((Comparator<? super Person>) comparator);
        model.setFilteredPersonList(sortedList);
        return new CommandResult("List sorted successfully.");
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
