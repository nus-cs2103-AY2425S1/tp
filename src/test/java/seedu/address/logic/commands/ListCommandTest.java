package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new ArrayList<>(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new ArrayList<>(), new UserPrefs());
    }

    @Test
    public void execute_list_showsSameList() {
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_CURRENT_PERSONS);
        assertCommandSuccess(ListCommand.ofCurrent(), model, ListCommand.MESSAGE_SUCCESS_CURRENT, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_CURRENT_PERSONS);
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(ListCommand.ofCurrent(), model, ListCommand.MESSAGE_SUCCESS_CURRENT, expectedModel);
    }

    @Test
    public void execute_listArchive_showsSameList() {
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ARCHIVED_PERSONS);
        assertCommandSuccess(ListCommand.ofArchive(), model, ListCommand.MESSAGE_SUCCESS_ARCHIVE, expectedModel);
    }

    @Test
    public void execute_listArchiveIsFiltered_showsEverything() {
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ARCHIVED_PERSONS);
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(ListCommand.ofArchive(), model, ListCommand.MESSAGE_SUCCESS_ARCHIVE, expectedModel);
    }

    @Test
    public void execute_listAll_showsSameList() {
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        assertCommandSuccess(ListCommand.ofAll(), model, ListCommand.MESSAGE_SUCCESS_ALL, expectedModel);
    }

    @Test
    public void execute_listAllIsFiltered_showsEverything() {
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(ListCommand.ofAll(), model, ListCommand.MESSAGE_SUCCESS_ALL, expectedModel);
    }

    @Test
    public void equals() {
        Object object = ListCommand.ofCurrent();

        assertEquals(object, object);
        assertEquals(ListCommand.ofCurrent(), ListCommand.ofCurrent());
        assertEquals(ListCommand.ofAll(), ListCommand.ofAll());
        assertEquals(ListCommand.ofArchive(), ListCommand.ofArchive());

        assertNotEquals(ListCommand.ofCurrent(), null);
        assertNotEquals(ListCommand.ofCurrent(), 0);
        assertNotEquals(ListCommand.ofCurrent(), ListCommand.ofAll());
        assertNotEquals(ListCommand.ofAll(), ListCommand.ofArchive());
    }
}
