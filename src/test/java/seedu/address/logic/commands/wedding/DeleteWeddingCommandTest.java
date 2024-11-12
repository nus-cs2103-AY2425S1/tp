package seedu.address.logic.commands.wedding;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWeddings.AMY_WEDDING;
import static seedu.address.testutil.TypicalWeddings.BOB_WEDDING;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

public class DeleteWeddingCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validDeleteWeddingCommand() {
        Wedding weddingToDelete = model.getFilteredWeddingList().get(2);
        DeleteWeddingCommand deleteWeddingCommand = new DeleteWeddingCommand(weddingToDelete, true);

        String expectedMessage = String.format(Messages.MESSAGE_DELETE_WEDDING_SUCCESS,
                Messages.format(weddingToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteWedding(weddingToDelete);
        ObservableList<Person> peopleInModel = expectedModel.getFilteredPersonList();
        for (Person person : expectedModel.getFilteredPersonList()) {
            HashSet<Wedding> personWeddings = new HashSet<>(person.getWeddings());
            if (personWeddings.contains(weddingToDelete)) {
                personWeddings.remove(weddingToDelete);
                Person newPerson = new Person(
                        person.getName(),
                        person.getPhone(),
                        person.getEmail(),
                        person.getAddress(),
                        person.getTags(),
                        personWeddings,
                        person.getTasks()
                );
                expectedModel.setPerson(person, newPerson);
                expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            }
        }

        assertCommandSuccess(deleteWeddingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNotFoundDeleteWeddingCommand() {
        Wedding weddingToDelete = model.getFilteredWeddingList().get(0);

        String expectedMessage = String.format(Messages.MESSAGE_DELETE_WEDDING_FAILURE_NOT_FOUND,
                Messages.format(weddingToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Wedding modelWedding = expectedModel.getWedding(weddingToDelete);
        expectedModel.deleteWedding(modelWedding);

        DeleteWeddingCommand expectedDeleteWeddingCommand = new DeleteWeddingCommand(weddingToDelete);

        assertCommandFailure(expectedDeleteWeddingCommand, expectedModel, expectedMessage);
    }

    @Test
    public void equals() {
        DeleteWeddingCommand deleteFloristWeddingCommand = new DeleteWeddingCommand(AMY_WEDDING);
        DeleteWeddingCommand deletePhotographerWeddingCommandCopy = new DeleteWeddingCommand(BOB_WEDDING);

        // same object -> returns true
        assertTrue(deleteFloristWeddingCommand.equals(deleteFloristWeddingCommand));

        // same values -> returns true
        DeleteWeddingCommand deleteFloristWeddingCommandCopy = new DeleteWeddingCommand(AMY_WEDDING);
        assertTrue(deleteFloristWeddingCommand.equals(deleteFloristWeddingCommandCopy));

        // different types -> return false
        assertFalse(deleteFloristWeddingCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFloristWeddingCommand.equals(null));

        // different Wedding -> returns false
        assertFalse(deleteFloristWeddingCommand.equals(deletePhotographerWeddingCommandCopy));
    }
}
