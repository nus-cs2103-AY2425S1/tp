package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSONS_DISPLAYED_INDEX;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.SelectPredicate;

/**
 * Selects the persons in the address book by the index numbers of the last shown list
 * Strictly only one space between the numbers
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Selects the persons based on the indexes "
            + "Parameters: INDEX [MORE_INDEXES]...\n"
            + "Example: " + COMMAND_WORD + " 1 2 7";

    private List<Person> persons = new ArrayList<>();

    private final List<Index> indexes;

    private SelectPredicate selectPredicate;

    public SelectCommand(List<Index> indexes) {
        this.indexes = indexes;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Index> invalidIndexes = new ArrayList<>();

        for (Index index : this.indexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                invalidIndexes.add(index);
            } else {
                persons.add(lastShownList.get(index.getZeroBased()));
            }
        }
        // If there are invalid indexes, throw a CommandException with details
        if (!invalidIndexes.isEmpty()) {
            String invalidIndexesMessage = formatIndexes(invalidIndexes);

            throw new CommandException(
                    String.format(MESSAGE_INVALID_PERSONS_DISPLAYED_INDEX, invalidIndexesMessage));
        }

        String selectedPersons = formatSelectedPersons(persons);
        selectPredicate = new SelectPredicate(persons);
        model.updateFilteredPersonList(selectPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_SELECT_PERSON_SUCCESS, selectedPersons));
    }

    /**
     * Formats a list of indexes into a comma-separated string for display.
     *
     * @param indexes The list of indexes to be formatted.
     * @return A comma-separated string of index numbers, or an empty string if the list is empty.
     */
    private static String formatIndexes(List<Index> indexes) {
        return indexes.stream()
                .map(Index::getOneBased)
                .map(String::valueOf)
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("");
    }

    /**
     * Formats a list of selected persons' names into a comma-separated string for display.
     *
     * @param persons The list of selected persons.
     * @return A comma-separated string of selected persons' names, or "none" if the list is empty.
     */
    private static String formatSelectedPersons(List<Person> persons) {
        return persons.stream()
                .map(person -> person.getName().toString()) // Convert Name object to String
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("none"); // Fallback if no persons are selected
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SelectCommand)) {
            return false;
        }

        SelectCommand otherSelectCommand = (SelectCommand) other;
        return indexes.equals(otherSelectCommand.indexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("indexes", indexes)
                .toString();
    }
}
