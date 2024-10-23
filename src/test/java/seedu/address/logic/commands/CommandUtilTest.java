package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_ALL;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class CommandUtilTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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
}
