package tuteez.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static tuteez.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tuteez.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tuteez.model.AddressBook;
import tuteez.model.Model;
import tuteez.model.ModelManager;
import tuteez.model.UserPrefs;
import tuteez.model.person.Person;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_clearLastViewedPerson_removesLastViewedPerson() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personOnDisplay = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        model.updateLastViewedPerson(personOnDisplay);
        expectedModel.updateLastViewedPerson(personOnDisplay);

        ClearCommand clearCommand = new ClearCommand();
        clearCommand.execute(model);

        assertFalse(model.getLastViewedPerson().get().isPresent(),
                "Expected last viewed person to be removed after clear is executed");
    }

}
