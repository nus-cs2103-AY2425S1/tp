package spleetwaise.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.UserPrefs;
import spleetwaise.address.testutil.TypicalIndexes;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.model.CommonModel;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private AddressBookModel model;
    private AddressBookModel expectedModel;

    @BeforeEach
    public void setUp() {
        model = new AddressBookModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        expectedModel = new AddressBookModelManager(model.getAddressBook(), new UserPrefs());
        CommonModel.initialise(model, null);
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandTestUtil.assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        CommandTestUtil.showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);
        CommandTestUtil.assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
