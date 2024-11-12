package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_ALL;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getShuffledTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class CommandUtilTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model shuffledModel = new ModelManager(getShuffledTypicalAddressBook(), new UserPrefs());

    @Test
    public void filterPersonsByIndex_shouldFilterAll_success() {
        List<Person> currDisplayedList = this.model.getFilteredPersonList();
        try {
            List<Person> filteredList = CommandUtil.filterPersonsByIndex(currDisplayedList,
                    INDEX_ALL);
            assertEquals(currDisplayedList, filteredList);
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void filterPersonsByIndex_shouldFilterByIndex_success() {
        List<Person> currDisplayedList = this.model.getFilteredPersonList();
        List<Person> expectedList = currDisplayedList.subList(0, 1);
        try {
            List<Person> filteredList = CommandUtil.filterPersonsByIndex(currDisplayedList,
                    INDEX_FIRST_PERSON);
            assertEquals(expectedList, filteredList);
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getComparatorByCurrentIndex_shouldSortByOriginalIndex_success() {
        List<Person> originalList = this.model.getFilteredPersonList();
        List<Person> shuffledList = new ArrayList<>(this.shuffledModel.getFilteredPersonList());
        Comparator<Person> comparator = CommandUtil.getComparatorByCurrentIndex(originalList);

        shuffledList.sort(comparator);

        assertEquals(originalList, shuffledList, "Sorted order should match the original list order.");
    }

    @Test
    public void getComparatorByCurrentIndex_unaffectedByOriginalChanges_success() {
        List<Person> originalList = new ArrayList<>(this.model.getFilteredPersonList());
        List<Person> originalListCopy = new ArrayList<>(originalList);
        List<Person> shuffledList = new ArrayList<>(this.shuffledModel.getFilteredPersonList());
        Comparator<Person> comparator = CommandUtil.getComparatorByCurrentIndex(originalList);

        // changing order of originalList
        Collections.shuffle(originalList);
        shuffledList.sort(comparator);

        assertEquals(originalListCopy, shuffledList,
                "Sorted order should still match the original list order after changing original list.");
    }
}
