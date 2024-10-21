package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person unsortedPerson = new PersonBuilder()
                .withName("Damith Rajapakse")
                .withEmail("damith@example.com")
                .withPhone("11111111")
                .withAddress("Kent Ridge")
                .build();

        Person unsortedPerson2 = new PersonBuilder()
                .withName("Aarush Kesar")
                .withEmail("aarush@example.com")
                .withPhone("22222222")
                .withAddress("Science Drive")
                .build();
        model.addPerson(unsortedPerson);
        model.addPerson(unsortedPerson2);
        expectedModel.addPerson(unsortedPerson);
        expectedModel.addPerson(unsortedPerson2);
        expectedModel.sortPersonList(Comparator.comparing(person -> person.getName().toString().toUpperCase()));
    }

    @Test
    public void execute_sortContactsByName_showsSortedList() {
        SortCommand sortCommand = new SortCommand();
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }
}
