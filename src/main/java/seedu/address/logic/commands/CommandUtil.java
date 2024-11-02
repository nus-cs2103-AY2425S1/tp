package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Comparator;
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
     * @param index index to filter by
     * @return List of persons filtered by the index provided
     * @throws CommandException when the index is invalid
     */
    public static List<Person> filterPersonsByIndex(
            List<Person> currDisplayedList, Index index) throws CommandException {
        assert currDisplayedList != null : "currDisplayedList should not be null";
        List<Person> personList = new ArrayList<>();
        if (index.isWildcard()) {
            return new ArrayList<>(currDisplayedList);
        } else if (index.getZeroBased() >= currDisplayedList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } else {
            personList.add(currDisplayedList.get(index.getZeroBased()));
        }
        return personList;
    }

    /**
     * Returns a {@code Comparator} that compares {@code Person} objects based on their current
     * display order in {@code currDisplayedList}. This allows for sorting by the position
     * in the current provided list.
     *
     * @param currDisplayedList the list of persons currently displayed
     * @return a comparator that orders persons by their index in the list
     * @throws AssertionError if a {@code Person} is not found in {@code currDisplayedList}
     */
    public static Comparator<Person> getComparatorByCurrentIndex(List<Person> currDisplayedList) {
        assert currDisplayedList != null : "currDisplayedList should not be null";
        ArrayList<Person> copyOfList = new ArrayList<>(currDisplayedList);
        return (p1, p2) -> {
            Integer index1 = copyOfList.stream().filter(p -> p.isSamePerson(p1))
                    .findFirst().map(copyOfList :: indexOf)
                    .orElseThrow(() -> new AssertionError("Person 1 not found in the list"));
            Integer index2 = copyOfList.stream().filter(p -> p.isSamePerson(p2))
                    .findFirst().map(copyOfList :: indexOf)
                    .orElseThrow(() -> new AssertionError("Person 2 not found in the list"));
            return Integer.compare(index1, index2);
        };
    }
}
