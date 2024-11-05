package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.AssignCommand.MESSAGE_ASSIGN_PERSON_TO_WEDDING_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WEDDING;
import static seedu.address.testutil.TypicalPersons.AMY_WEDDING;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.ELLE_WEDDING;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonWithRoleDescriptorBuilder;

public class AssignCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_assignValidRole_success() throws Exception {
        Index indexFirstPerson = INDEX_FIRST_PERSON;

        Person personToAssign = model.getFilteredPersonList().get(0); // Assuming Alice is the first person

        // Define the new role to add
        String newRole = "vendor";

        // Create the role command descriptor with one role
        AssignCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder().withRole(newRole)
                .build();

        AssignCommand assignCommand = new AssignCommand(indexFirstPerson, null, descriptor, null);

        // Create the expected person with the new role
        Person expectedPerson = new PersonBuilder(personToAssign).withRole(newRole).build();

        // Setup the expected model
        Model expectedModel = new ModelManager(getTypicalAddressBook(), model.getUserPrefs());
        expectedModel.setPerson(personToAssign, expectedPerson);

        // Execute the command and assert success
        CommandResult result = assignCommand.execute(model);
        assertEquals(String.format(AssignCommand.MESSAGE_ASSIGN_PERSON_ROLE_SUCCESS, Messages.format(expectedPerson)),
                result.getFeedbackToUser());
        assertTrue(expectedModel.equals(model));

    }


    @Test
    public void execute_assignWeddingOnly_success() throws Exception {
        AMY_WEDDING.setClient(BENSON);
        model.addWedding(AMY_WEDDING);
        Index indexFirstPerson = INDEX_FIRST_PERSON;

        Person personToAssign = model.getFilteredPersonList().get(0); // Assuming Alice is the first person
        Set<Index> weddingIndices = Set.of(INDEX_FIRST_WEDDING);
        Wedding assignedWedding = model.getFilteredWeddingList().get(0);

        AssignCommand assignCommand = new AssignCommand(indexFirstPerson, null,
                new PersonWithRoleDescriptorBuilder().build(), weddingIndices);

        Person expectedPerson = new PersonBuilder(personToAssign).build();
        expectedPerson.setWeddingJobs(Set.of(assignedWedding));
        Model expectedModel = new ModelManager(getTypicalAddressBook(), model.getUserPrefs());
        expectedModel.setPerson(personToAssign, expectedPerson);


        CommandResult result = assignCommand.execute(model);
        assertEquals(String.format(
                        MESSAGE_ASSIGN_PERSON_TO_WEDDING_SUCCESS,
                        Messages.format(expectedPerson),
                        Messages.format(expectedPerson.getWeddingJobs())
                ),
                result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }


    @Test
    public void execute_multipleWeddingsAssignment_success() throws Exception {
        // Setup - add two weddings
        AMY_WEDDING.setClient(BENSON);
        ELLE_WEDDING.setClient(FIONA);
        model.addWedding(AMY_WEDDING);
        Wedding anotherWedding = ELLE_WEDDING;
        model.addWedding(anotherWedding);

        Person personToAssign = model.getFilteredPersonList().get(0); // Assume Alice is the first person
        Set<Index> weddingIndices = Set.of(INDEX_FIRST_WEDDING, Index.fromOneBased(2));
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, null,
                new PersonWithRoleDescriptorBuilder().build(), weddingIndices);

        // Create expected person with assigned weddings
        Person expectedPerson = new PersonBuilder(personToAssign).build();
        expectedPerson.setWeddingJobs(Set.of(AMY_WEDDING, anotherWedding));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), model.getUserPrefs());
        expectedModel.setPerson(personToAssign, expectedPerson);

        CommandResult result = assignCommand.execute(model);
        assertEquals(String.format(
                        MESSAGE_ASSIGN_PERSON_TO_WEDDING_SUCCESS,
                        Messages.format(expectedPerson),
                        Messages.format(expectedPerson.getWeddingJobs())
                ),
                result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_assignRoleAndWedding_success() throws Exception {
        Index indexFirstPerson = INDEX_FIRST_PERSON;
        AMY_WEDDING.setClient(BENSON);
        model.addWedding(AMY_WEDDING);

        Person personToAssign = model.getFilteredPersonList().get(0); // Assuming Alice is the first person
        Set<Index> weddingIndices = Set.of(INDEX_FIRST_WEDDING);
        Wedding assignedWedding = model.getFilteredWeddingList().get(0);

        String newRole = "vendor";
        AssignCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder().withRole(newRole)
                .build();

        AssignCommand assignCommand = new AssignCommand(indexFirstPerson, null, descriptor, weddingIndices);

        Person expectedPerson = new PersonBuilder(personToAssign).withRole(newRole).build();
        expectedPerson.setWeddingJobs(Set.of(assignedWedding));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), model.getUserPrefs());
        expectedModel.setPerson(personToAssign, expectedPerson);

        CommandResult result = assignCommand.execute(model);
        assertEquals(String.format(
                        MESSAGE_ASSIGN_PERSON_TO_WEDDING_SUCCESS,
                        Messages.format(expectedPerson),
                        Messages.format(expectedPerson.getWeddingJobs())
                ),
                result.getFeedbackToUser());
        assertTrue(expectedModel.equals(model));
    }

    @Test
    public void execute_nullDescriptor_throwsNullPointerException() {
        Index indexFirstPerson = INDEX_FIRST_PERSON;
        assertThrows(NullPointerException.class, () -> new AssignCommand(indexFirstPerson, null,
                null, null));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AssignCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder()
                .withRole("vendor").build();
        AssignCommand assignCommand = new AssignCommand(invalidIndex, null, descriptor, null);

        assertThrows(CommandException.class, () -> assignCommand.execute(model),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidWeddingIndex_throwsCommandException() {
        model.addWedding(AMY_WEDDING);
        Index invalidWeddingIndex = Index.fromOneBased(model.getFilteredWeddingList().size() + 1);
        Set<Index> weddingIndices = Set.of(invalidWeddingIndex);

        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, null,
                new PersonWithRoleDescriptorBuilder().build(), weddingIndices);

        assertThrows(CommandException.class, () -> assignCommand.execute(model),
                Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_roleAlreadyExists_throwsCommandException() {
        Index indexFirstPerson = INDEX_FIRST_PERSON;

        // assuming first person is Alice with role "florist"
        Person personToAssign = model.getFilteredPersonList().get(0);

        AssignCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder()
                .withRole("florist").build();

        AssignCommand tagCommand = new AssignCommand(indexFirstPerson, null, descriptor, null);

        assertThrows(CommandException.class, () -> tagCommand.execute(model),
                String.format(AssignCommand.MESSAGE_DUPLICATE_ROLE, personToAssign.getName()));
    }

    @Test
    public void equals() {
        AssignCommand assignCommand1 = new AssignCommand(INDEX_FIRST_PERSON, null,
                new PersonWithRoleDescriptorBuilder().withRole(VALID_TAG_FRIEND).build(), Set.of(INDEX_FIRST_WEDDING));

        AssignCommand assignCommand2 = new AssignCommand(INDEX_FIRST_PERSON, null,
                new PersonWithRoleDescriptorBuilder().withRole(VALID_TAG_FRIEND).build(), Set.of(INDEX_FIRST_WEDDING));

        AssignCommand assignCommand3 = new AssignCommand(INDEX_FIRST_PERSON, null,
                new PersonWithRoleDescriptorBuilder().withRole("anotherRole").build(), Set.of(INDEX_FIRST_WEDDING));

        AssignCommand assignCommand4 = new AssignCommand(INDEX_FIRST_PERSON, null,
                new PersonWithRoleDescriptorBuilder().withRole(VALID_TAG_FRIEND).build(),
                Set.of(Index.fromOneBased(2)));

        // same object -> returns true
        assertEquals(assignCommand1, assignCommand1);

        // same values -> returns true
        assertEquals(assignCommand1, assignCommand2);

        // different descriptors -> returns false
        assertNotEquals(assignCommand1, assignCommand3);

        // different wedding indices -> returns false
        assertNotEquals(assignCommand1, assignCommand4);

        // different types -> returns false
        assertEquals(false, assignCommand1.equals(new Object()));

        // null -> returns false
        assertNotEquals(assignCommand1, null);
    }

    // Helper method for clearer assertions
    private void assertNotEquals(AssignCommand expected, AssignCommand actual) {
        assertFalse(expected.equals(actual));
    }


}
