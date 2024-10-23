package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sortByNameAscending_success() throws CommandException {
        Comparator<Person> comparator = Comparator.comparing(person -> person.getName().fullName);
        SortCommand sortCommand = new SortCommand(comparator);
        String expectedMessage = "List sorted successfully.";

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        List<Person> sortedList = new ArrayList<>(expectedModel.getFilteredPersonList());
        sortedList.sort(comparator);
        expectedModel.setFilteredPersonList(sortedList);

        CommandResult result = sortCommand.executeCommand(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_sortByNameDescending_success() throws CommandException {
        Comparator<Person> comparator = (p1, p2) -> p2.getName().fullName.compareToIgnoreCase(p1.getName().fullName);
        SortCommand sortCommand = new SortCommand(comparator);
        String expectedMessage = "List sorted successfully.";

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        List<Person> sortedList = new ArrayList<>(expectedModel.getFilteredPersonList());
        sortedList.sort(comparator);
        expectedModel.setFilteredPersonList(sortedList);

        CommandResult result = sortCommand.executeCommand(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }
}
