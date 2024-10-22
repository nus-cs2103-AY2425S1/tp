package seedu.address.logic.commands.wedding;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWeddings.AMY_WEDDING;
import static seedu.address.testutil.TypicalWeddings.BOB_WEDDING;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

public class UnassignWeddingCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void unassignWedding_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        HashSet<Wedding> weddingsToRemove = new HashSet<>(Arrays.asList(new Wedding(new WeddingName("Wedding 2"))));

        UnassignWeddingCommand unassignWeddingCommand = new UnassignWeddingCommand(
                INDEX_SECOND, weddingsToRemove);

        String expectedMessage = String.format(Messages.MESSAGE_REMOVE_WEDDING_SUCCESS, "Wedding 2",
                personToEdit.getName().toString());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Wedding> updatedWeddings = new HashSet<>(personToEdit.getWeddings());
        updatedWeddings.removeAll(weddingsToRemove);
        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                updatedWeddings);
        expectedModel.setPerson(personToEdit, editedPerson);

        CommandTestUtil.assertCommandSuccess(unassignWeddingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void unassignWeddingZeroWeddings_fail() {
        HashSet<Wedding> weddingsToRemove = new HashSet<>(Arrays.asList(AMY_WEDDING));
        UnassignWeddingCommand unassignWeddingCommand = new UnassignWeddingCommand(
                INDEX_SECOND, weddingsToRemove);
        String expectedMessage = Messages.MESSAGE_WEDDING_NOT_FOUND_IN_CONTACT;
        CommandTestUtil.assertCommandFailure(unassignWeddingCommand, model, expectedMessage);
    }

    @Test
    public void unassignWeddingNoMatchingWedding_fail() {
        HashSet<Wedding> weddingsToRemove = new HashSet<>(Arrays.asList(BOB_WEDDING));
        UnassignWeddingCommand unassignWeddingCommand = new UnassignWeddingCommand(
                INDEX_FIRST, weddingsToRemove);
        String expectedMessage = Messages.MESSAGE_WEDDING_NOT_FOUND_IN_CONTACT;
        CommandTestUtil.assertCommandFailure(unassignWeddingCommand, model, expectedMessage);
    }

    @Test
    public void sameCommandTest_success() {
        HashSet<Wedding> weddingsToRemove = new HashSet<>(Arrays.asList(BOB_WEDDING));
        UnassignWeddingCommand command1 = new UnassignWeddingCommand(INDEX_FIRST, weddingsToRemove);
        assertTrue(command1.equals(command1));
    }

    @Test
    public void differentCommandType_fail() {
        HashSet<Wedding> weddingsToRemove = new HashSet<>();
        UnassignWeddingCommand command1 = new UnassignWeddingCommand(INDEX_FIRST, weddingsToRemove);
        assertFalse(command1.equals(null));
    }

    @Test
    public void differentIndex_fail() {
        HashSet<Wedding> weddingsToRemove = new HashSet<>();
        UnassignWeddingCommand command1 = new UnassignWeddingCommand(INDEX_FIRST, weddingsToRemove);
        UnassignWeddingCommand command2 = new UnassignWeddingCommand(INDEX_SECOND, weddingsToRemove);
        assertFalse(command1.equals(command2));
    }

    @Test
    public void differentWeddings_fail() {
        HashSet<Wedding> weddingsToRemove1 = new HashSet<>();
        HashSet<Wedding> weddingsToRemove2 = new HashSet<>(Arrays.asList(AMY_WEDDING));
        UnassignWeddingCommand command1 = new UnassignWeddingCommand(INDEX_FIRST, weddingsToRemove1);
        UnassignWeddingCommand command2 = new UnassignWeddingCommand(INDEX_SECOND, weddingsToRemove2);
        assertFalse(command1.equals(command2));
    }
}
