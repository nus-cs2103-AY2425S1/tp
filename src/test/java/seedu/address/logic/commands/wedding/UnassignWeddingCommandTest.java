package seedu.address.logic.commands.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWeddings.AMY_WEDDING;
import static seedu.address.testutil.TypicalWeddings.BOB_WEDDING;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Vendor;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;
import seedu.address.testutil.PersonBuilder;

public class UnassignWeddingCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void unassignWedding_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        HashSet<Wedding> weddingsToRemove = new HashSet<>(List.of(new Wedding(new WeddingName("Wedding 2"))));

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
                updatedWeddings,
                personToEdit.getTasks());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(unassignWeddingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void unassignWeddingZeroWeddings_fail() {
        HashSet<Wedding> weddingsToRemove = new HashSet<>(List.of(AMY_WEDDING));
        UnassignWeddingCommand unassignWeddingCommand = new UnassignWeddingCommand(
                INDEX_SECOND, weddingsToRemove);
        String expectedMessage = Messages.MESSAGE_WEDDING_NOT_FOUND_IN_CONTACT;
        assertCommandFailure(unassignWeddingCommand, model, expectedMessage);
    }

    @Test
    public void unassignWeddingNoMatchingWedding_fail() {
        HashSet<Wedding> weddingsToRemove = new HashSet<>(List.of(BOB_WEDDING));
        UnassignWeddingCommand unassignWeddingCommand = new UnassignWeddingCommand(
                INDEX_FIRST, weddingsToRemove);
        String expectedMessage = Messages.MESSAGE_WEDDING_NOT_FOUND_IN_CONTACT;
        assertCommandFailure(unassignWeddingCommand, model, expectedMessage);
    }

    @Test
    public void unassignWeddingIndexError_fail() {
        HashSet<Wedding> weddingsToRemove = new HashSet<>(List.of(BOB_WEDDING));
        UnassignWeddingCommand unassignWeddingCommand = new UnassignWeddingCommand(
                Index.fromOneBased(100), weddingsToRemove);
        assertCommandFailure(unassignWeddingCommand, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, 1, 7));
    }

    @Test
    public void sameCommandTest_success() {
        HashSet<Wedding> weddingsToRemove = new HashSet<>(List.of(BOB_WEDDING));
        UnassignWeddingCommand command1 = new UnassignWeddingCommand(INDEX_FIRST, weddingsToRemove);
        assertEquals(command1, command1);
    }

    @Test
    public void differentCommandType_fail() {
        HashSet<Wedding> weddingsToRemove = new HashSet<>();
        UnassignWeddingCommand command1 = new UnassignWeddingCommand(INDEX_FIRST, weddingsToRemove);
        assertNotEquals(null, command1);
    }

    @Test
    public void differentIndex_fail() {
        HashSet<Wedding> weddingsToRemove = new HashSet<>();
        UnassignWeddingCommand command1 = new UnassignWeddingCommand(INDEX_FIRST, weddingsToRemove);
        UnassignWeddingCommand command2 = new UnassignWeddingCommand(INDEX_SECOND, weddingsToRemove);
        assertNotEquals(command1, command2);
    }

    @Test
    public void differentWeddings_fail() {
        HashSet<Wedding> weddingsToRemove1 = new HashSet<>();
        HashSet<Wedding> weddingsToRemove2 = new HashSet<>(List.of(AMY_WEDDING));
        UnassignWeddingCommand command1 = new UnassignWeddingCommand(INDEX_FIRST, weddingsToRemove1);
        UnassignWeddingCommand command2 = new UnassignWeddingCommand(INDEX_SECOND, weddingsToRemove2);
        assertNotEquals(command1, command2);
    }

    @Test
    public void unassignWeddingVendor_success() {
        // Setup Vendor instance with weddings
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Person vendor = new Vendor(personToEdit);
        model.setPerson(personToEdit, vendor);

        HashSet<Wedding> weddingsToRemove = new HashSet<>(List.of(AMY_WEDDING));
        UnassignWeddingCommand unassignWeddingCommand = new UnassignWeddingCommand(INDEX_FIRST, weddingsToRemove);

        // Expect Vendor to have wedding removed and updated as Vendor, not Person
        String expectedMessage = String.format(Messages.MESSAGE_REMOVE_WEDDING_SUCCESS,
                "Amy's Wedding", vendor.getName().toString());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Wedding> updatedWeddings = new HashSet<>(vendor.getWeddings());
        updatedWeddings.removeAll(weddingsToRemove);
        Person editedVendor = new Vendor(
                vendor.getName(),
                vendor.getPhone(),
                vendor.getEmail(),
                vendor.getAddress(),
                vendor.getTags(),
                updatedWeddings,
                vendor.getTasks());
        expectedModel.setPerson(vendor, editedVendor);

        assertCommandSuccess(unassignWeddingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void unassignWedding_emptyPersonWeddings_fail() {
        Person firstPax = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Person personWithNoWeddings = new PersonBuilder().withName("Test").build();
        assertTrue(personWithNoWeddings.getWeddings().isEmpty());
        model.setPerson(firstPax, personWithNoWeddings);

        HashSet<Wedding> weddingsToRemove = new HashSet<>(List.of(AMY_WEDDING));
        UnassignWeddingCommand unassignWeddingCommand = new UnassignWeddingCommand(INDEX_FIRST, weddingsToRemove);

        assertCommandFailure(unassignWeddingCommand, model, Messages.MESSAGE_WEDDING_NOT_FOUND_IN_CONTACT);
    }

    @Test
    public void unassignWedding_emptyWeddingsToRemove_fail() {
        Person personWithWeddings = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        // Ensure person has weddings
        assertFalse(personWithWeddings.getWeddings().isEmpty());

        HashSet<Wedding> emptyWeddingsToRemove = new HashSet<>();
        UnassignWeddingCommand unassignWeddingCommand = new UnassignWeddingCommand(INDEX_FIRST, emptyWeddingsToRemove);

        assertCommandFailure(unassignWeddingCommand, model, Messages.MESSAGE_WEDDING_NOT_FOUND_IN_CONTACT);
    }
}
