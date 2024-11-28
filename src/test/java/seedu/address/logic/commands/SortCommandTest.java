package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_sortByNameAscending_success() {
        SortCommand sortCommand = new SortCommand("Name", "L");
        Comparator<Person> comparator = Comparator.comparing((Person person) -> person.getName().getLowerCaseName());
        expectedModel.sortPersonListWithComparator(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_SUCCESS, "Name", "L");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByNameDescending_success() {
        SortCommand sortCommand = new SortCommand("Name", "H");
        Comparator<Person> comparator = Comparator.comparing((Person person) ->
                person.getName().getLowerCaseName()).reversed();
        expectedModel.sortPersonListWithComparator(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_SUCCESS, "Name", "H");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByNumPropAscending_success() {
        SortCommand sortCommand = new SortCommand("NumProp", "L");
        Comparator<Person> comparator = Comparator.comparingInt(Person::getTotalNumProps);
        expectedModel.sortPersonListWithComparator(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_SUCCESS, "NumProp", "L");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByNumPropDescending_success() {
        SortCommand sortCommand = new SortCommand("NumProp", "H");
        Comparator<Person> comparator = Comparator.comparingInt(Person::getTotalNumProps).reversed();
        expectedModel.sortPersonListWithComparator(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_SUCCESS, "NumProp", "H");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidField_throwsCommandException() {
        SortCommand sortCommand = new SortCommand("InvalidField", "L");
        assertThrows(CommandException.class, () -> sortCommand.execute(model));
    }

    @Test
    public void execute_invalidOrder_throwsCommandException() {
        SortCommand sortCommand = new SortCommand("Name", "InvalidOrder");
        assertThrows(CommandException.class, () -> sortCommand.execute(model));
    }

    @Test
    public void equals() {
        SortCommand sortByNameAsc = new SortCommand("Name", "L");
        SortCommand sortByNameDesc = new SortCommand("Name", "H");
        SortCommand sortByNumPropAsc = new SortCommand("NumProp", "L");

        // same object -> returns true
        assertEquals(sortByNameAsc, sortByNameAsc);

        // same values -> returns true
        SortCommand sortByNameAscCopy = new SortCommand("Name", "L");
        assertEquals(sortByNameAsc, sortByNameAscCopy);

        // different types -> returns false
        assertNotEquals(sortByNameAsc, 1);

        // null -> returns false
        assertNotEquals(sortByNameAsc, null);

        // different field and order -> returns false
        assertNotEquals(sortByNameAsc, sortByNameDesc);
        assertNotEquals(sortByNameAsc, sortByNumPropAsc);
    }
}
