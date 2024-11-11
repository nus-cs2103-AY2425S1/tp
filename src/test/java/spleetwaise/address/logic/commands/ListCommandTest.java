package spleetwaise.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.testutil.TypicalIndexes;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.model.CommonModelManager;

/**
 * Contains integration tests (interaction with the CommonModel) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private AddressBookModel model;
    private AddressBookModel expectedModel;

    @BeforeEach
    public void setUp() {
        model = new AddressBookModelManager(TypicalPersons.getTypicalAddressBook());
        expectedModel = new AddressBookModelManager(model.getAddressBook());
        CommonModelManager.initialise(model, null);
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
