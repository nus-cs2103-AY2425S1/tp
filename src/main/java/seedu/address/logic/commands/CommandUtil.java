package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * Contains utility methods used for command execution.
 */
public class CommandUtil {

    /**
     * Filters the list of persons by the indexes provided.
     * @param currDisplayedList list of persons currently displayed
     * @param indexList list of indexes to filter by
     * @param shouldFilterAll whether to filter all persons
     * @return List of persons filtered by the indexes provided
     * @throws CommandException when the index is invalid
     */
    public static List<Person> filterPersonsByIndex(
            List<Person> currDisplayedList, List<Index> indexList, boolean shouldFilterAll) throws CommandException {
        if (shouldFilterAll) {
            return currDisplayedList;
        } else {
            List<Person> personList = new ArrayList<>();
            for (Index index : indexList) {
                if (index.getZeroBased() >= currDisplayedList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
                personList.add(currDisplayedList.get(index.getZeroBased()));
            }
            return personList;
        }
    }
}
