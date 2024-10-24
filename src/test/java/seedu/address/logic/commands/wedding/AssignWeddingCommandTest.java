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

public class AssignWeddingCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void assignWedding_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        HashSet<Wedding> weddingsToAdd = new HashSet<>(Arrays.asList(AMY_WEDDING));

        AssignWeddingCommand assignWeddingCommand = new AssignWeddingCommand(
                INDEX_FIRST, weddingsToAdd);

        String expectedMessage = String.format(Messages.MESSAGE_ADD_WEDDING_SUCCESS, "Amy's Wedding",
                personToEdit.getName().toString());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Wedding> updatedWeddings = new HashSet<>(personToEdit.getWeddings());
        updatedWeddings.addAll(weddingsToAdd);
        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                updatedWeddings,
                personToEdit.getTasks());
        expectedModel.setPerson(personToEdit, editedPerson);

        CommandTestUtil.assertCommandSuccess(assignWeddingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void assignWeddingZeroWeddings_fail() {
        Wedding unseenWedding = new Wedding(new WeddingName("UNKNOWN WEDDING"));
        HashSet<Wedding> weddingsToAdd = new HashSet<>(Arrays.asList(unseenWedding));
        AssignWeddingCommand assignWeddingCommand = new AssignWeddingCommand(
                INDEX_FIRST, weddingsToAdd);
        String expectedMessage = Messages.MESSAGE_WEDDING_NOT_FOUND + "\n"
                + Messages.MESSAGE_FORCE_ASSIGN_WEDDING_TO_CONTACT;
        CommandTestUtil.assertCommandFailure(assignWeddingCommand, model, expectedMessage);
    }

    @Test
    public void sameCommandTest_success() {
        HashSet<Wedding> weddingsToRemove = new HashSet<>(Arrays.asList(BOB_WEDDING));
        AssignWeddingCommand command1 = new AssignWeddingCommand(INDEX_FIRST, weddingsToRemove);
        assertTrue(command1.equals(command1));
    }

    @Test
    public void differentCommandType_fail() {
        HashSet<Wedding> weddingsToRemove = new HashSet<>();
        AssignWeddingCommand command1 = new AssignWeddingCommand(INDEX_FIRST, weddingsToRemove);
        assertFalse(command1.equals(null));
    }

    @Test
    public void differentIndex_fail() {
        HashSet<Wedding> weddingsToRemove = new HashSet<>();
        AssignWeddingCommand command1 = new AssignWeddingCommand(INDEX_FIRST, weddingsToRemove);
        AssignWeddingCommand command2 = new AssignWeddingCommand(INDEX_SECOND, weddingsToRemove);
        assertFalse(command1.equals(command2));
    }

    @Test
    public void differentWeddings_fail() {
        HashSet<Wedding> weddingsToRemove1 = new HashSet<>();
        HashSet<Wedding> weddingsToRemove2 = new HashSet<>(Arrays.asList(AMY_WEDDING));
        AssignWeddingCommand command1 = new AssignWeddingCommand(INDEX_FIRST, weddingsToRemove1);
        AssignWeddingCommand command2 = new AssignWeddingCommand(INDEX_SECOND, weddingsToRemove2);
        assertFalse(command1.equals(command2));
    }

    @Test
    public void forceAssignWedding_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Wedding unseenWedding = new Wedding(new WeddingName("UNKNOWN WEDDING"));
        HashSet<Wedding> weddingsToAdd = new HashSet<>(Arrays.asList(unseenWedding));
        AssignWeddingCommand assignWeddingCommand = new AssignWeddingCommand(
                INDEX_FIRST, weddingsToAdd, true);
        String expectedMessage = String.format(
                Messages.MESSAGE_ADD_WEDDING_SUCCESS,
                unseenWedding.getWeddingName().toString(),
                personToEdit.getName().toString());
        CommandTestUtil.assertCommandSuccess(assignWeddingCommand, model, expectedMessage, model);

    }
}
