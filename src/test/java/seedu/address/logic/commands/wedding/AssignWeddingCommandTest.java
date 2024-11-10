package seedu.address.logic.commands.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWeddings.AMY_WEDDING;
import static seedu.address.testutil.TypicalWeddings.BOB_WEDDING;
import static seedu.address.testutil.TypicalWeddings.CLIVE_WEDDING;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Vendor;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

public class AssignWeddingCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void assignWedding_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Map<Wedding, String> weddingsToAdd = new HashMap<>() {
            { put(CLIVE_WEDDING, "g"); }
        };
        AssignWeddingCommand assignWeddingCommand = new AssignWeddingCommand(
                INDEX_FIRST, weddingsToAdd, false);

        String expectedMessage = String.format(Messages.MESSAGE_ASSIGN_WEDDING_SUCCESS, "Clive's Wedding",
                personToEdit.getName().toString());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Wedding> updatedWeddings = new HashSet<>(personToEdit.getWeddings());
        updatedWeddings.addAll(weddingsToAdd.keySet());
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
        HashMap<Wedding, String> weddingsToAdd = new HashMap<>() {
            { put(unseenWedding, "g"); }
        };
        AssignWeddingCommand assignWeddingCommand = new AssignWeddingCommand(
                INDEX_FIRST, weddingsToAdd, false);
        String expectedMessage = Messages.MESSAGE_WEDDING_NOT_FOUND + "\n"
                + Messages.MESSAGE_FORCE_ASSIGN_WEDDING_TO_CONTACT;
        CommandTestUtil.assertCommandFailure(assignWeddingCommand, model, expectedMessage);
    }

    @Test
    public void sameCommandTest_success() {
        HashMap<Wedding, String> weddingsToRemove = new HashMap<>() {
            { put(BOB_WEDDING, "g"); }
        };
        AssignWeddingCommand command1 = new AssignWeddingCommand(INDEX_FIRST, weddingsToRemove, false);
        assertEquals(command1, command1);
    }

    @Test
    public void differentCommandType_fail() {
        HashMap<Wedding, String> weddingsToRemove = new HashMap<>();
        AssignWeddingCommand command1 = new AssignWeddingCommand(INDEX_FIRST, weddingsToRemove, false);
        assertNotEquals(null, command1);
    }

    @Test
    public void differentIndex_fail() {
        HashMap<Wedding, String> weddingsToRemove = new HashMap<>();
        AssignWeddingCommand command1 = new AssignWeddingCommand(INDEX_FIRST, weddingsToRemove, false);
        AssignWeddingCommand command2 = new AssignWeddingCommand(INDEX_SECOND, weddingsToRemove, false);
        assertNotEquals(command1, command2);
    }

    @Test
    public void differentWeddings_fail() {
        HashMap<Wedding, String> weddingsToRemove1 = new HashMap<>();
        HashMap<Wedding, String> weddingsToRemove2 = new HashMap<>() {
            { put(AMY_WEDDING, "g"); }
        };
        AssignWeddingCommand command1 = new AssignWeddingCommand(INDEX_FIRST, weddingsToRemove1, false);
        AssignWeddingCommand command2 = new AssignWeddingCommand(INDEX_SECOND, weddingsToRemove2, false);
        assertNotEquals(command1, command2);
    }

    @Test
    public void forceAssignWedding_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Wedding unseenWedding1 = new Wedding(new WeddingName("UNKNOWN WEDDING1"));
        Wedding unseenWedding2 = new Wedding(new WeddingName("UNKNOWN WEDDING2"));
        Wedding unseenWedding3 = new Wedding(new WeddingName("UNKNOWN WEDDING3"));
        HashMap<Wedding, String> weddingsToAdd = new HashMap<>() {
            { put(unseenWedding1, "g"); }
            { put(unseenWedding2, "p1"); }
            { put(unseenWedding3, "p2"); }
        };
        AssignWeddingCommand assignWeddingCommand = new AssignWeddingCommand(
                INDEX_FIRST, weddingsToAdd, true);
        String addedWeddings = weddingsToAdd.keySet().stream()
                .map(wedding -> wedding.toString().replaceAll("[\\[\\]]", ""))
                .collect(Collectors.joining(", "));
        String expectedMessage = String.format(
                Messages.MESSAGE_ASSIGN_WEDDING_SUCCESS,
                addedWeddings,
                personToEdit.getName().toString());
        CommandTestUtil.assertCommandSuccess(assignWeddingCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Map<Wedding, String> weddingsToAdd = new HashMap<>() {
            { put(CLIVE_WEDDING, "g"); }
        };
        AssignWeddingCommand assignWeddingCommand = new AssignWeddingCommand(
                Index.oneBasedNoConstraints(model.getFilteredPersonList().size() + 1),
                weddingsToAdd, false);
        assertThrows(CommandException.class, String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                1, model.getFilteredPersonList().size() + 1), () -> assignWeddingCommand.execute(model));
    }

    @Test
    public void execute_personAlreadyAssignedToWedding_throwsCommandException() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Map<Wedding, String> weddingsToAdd = new HashMap<>() {
            { put(AMY_WEDDING, "g"); }
        };

        // Manually assign wedding to person
        model.setPerson(personToEdit, new Person(personToEdit.getName(),
                personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getAddress(),
                personToEdit.getTags(), Set.of(AMY_WEDDING), personToEdit.getTasks()));

        AssignWeddingCommand assignWeddingCommand = new AssignWeddingCommand(INDEX_FIRST, weddingsToAdd, false);
        assertThrows(CommandException.class,
                String.format(Messages.MESSAGE_WEDDING_ALREADY_ASSIGNED,
                        personToEdit.getName()), () -> assignWeddingCommand.execute(model));
    }

    @Test
    public void execute_assignWeddingToVendor_success() throws CommandException {
        //get first index and make that person vendor
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Vendor vendor = new Vendor(personToEdit);
        model.setPerson(personToEdit, vendor);

        Map<Wedding, String> weddingsToAdd = new HashMap<>() {
            { put(BOB_WEDDING, "p1"); }
        };
        AssignWeddingCommand assignWeddingCommand = new AssignWeddingCommand(INDEX_FIRST, weddingsToAdd, true);
        CommandResult result = assignWeddingCommand.execute(model);

        String expectedMessage = String.format(Messages.MESSAGE_ASSIGN_WEDDING_SUCCESS,
                "Bob's Wedding", vendor.getName());
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertTrue(model.getWedding(BOB_WEDDING).hasPerson(vendor));
    }

}
