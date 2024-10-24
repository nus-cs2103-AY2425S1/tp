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
    private static final int filterAll = -1;

    /**
     * Filters the list of persons by the indexes provided.
     * @param currDisplayedList list of persons currently displayed
     * @param index index to filter by
     * @return List of persons filtered by the index provided
     * @throws CommandException when the index is invalid
     */
    public static List<Person> filterPersonsByIndex(
            List<Person> currDisplayedList, Index index) throws CommandException {

        List<Person> personList = new ArrayList<>();
        if (index.getZeroBased() >= currDisplayedList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } else if (index.getZeroBased() == CommandUtil.filterAll) {
            return new ArrayList<>(currDisplayedList);
        } else {
            personList.add(currDisplayedList.get(index.getZeroBased()));
        }
        return personList;
    }
}
