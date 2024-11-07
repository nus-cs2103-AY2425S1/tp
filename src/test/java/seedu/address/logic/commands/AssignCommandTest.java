package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.AssignCommand.MESSAGE_ASSIGN_PERSON_TO_WEDDING_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WEDDING;
import static seedu.address.testutil.TypicalPersons.AMY_WEDDING;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.ELLE_WEDDING;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameMatchesKeywordPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonWithRoleDescriptorBuilder;
import seedu.address.testutil.WeddingBuilder;

public class AssignCommandTest {

    @Test
    public void execute_assignValidRole_success() throws Exception {
        // Prepare model with typical persons
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Person personToAssign = model.getFilteredPersonList().get(0); // Assuming Alice is the first person

        // Define the new role to add
        String newRole = "vendor";

        // Create the role command descriptor with one role
        AssignCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder().withRole(newRole)
                .build();

        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, null, descriptor, null);

        // Create the expected person with the new role
        Person expectedPerson = new PersonBuilder(personToAssign).withRole(newRole).build();

        // Setup the expected model
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToAssign, expectedPerson);

        // Execute the command and assert success
        CommandResult result = assignCommand.execute(model);
        assertEquals(String.format(AssignCommand.MESSAGE_ASSIGN_PERSON_ROLE_SUCCESS, Messages.format(expectedPerson)),
                result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_assignValidRoleByName_success() throws Exception {
        // Prepare model with typical persons
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Use a name that matches a single person
        String nameKeyword = "Alice Pauline";
        NameMatchesKeywordPredicate predicate = new NameMatchesKeywordPredicate(Collections.singletonList(nameKeyword));

        // Get the person matching the keyword
        Person personToAssign = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().fullName.equals(nameKeyword))
                .findFirst().get();

        // Define the new role to add
        String newRole = "vendor";

        // Create the role command descriptor with one role
        AssignCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder().withRole(newRole)
                .build();

        AssignCommand assignCommand = new AssignCommand(null, predicate, descriptor, null);

        // Create the expected person with the new role
        Person expectedPerson = new PersonBuilder(personToAssign).withRole(newRole).build();

        // Setup the expected model
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToAssign, expectedPerson);

        // Execute the command and assert success
        CommandResult result = assignCommand.execute(model);
        assertEquals(String.format(AssignCommand.MESSAGE_ASSIGN_PERSON_ROLE_SUCCESS, Messages.format(expectedPerson)),
                result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_assignByKeywordMultiplePersonsFound_throwsCommandException() {
        // Prepare model with typical persons
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Use a name that matches multiple persons (assuming "Meier" matches multiple)
        String nameKeyword = "Meier";
        NameMatchesKeywordPredicate predicate = new NameMatchesKeywordPredicate(Collections.singletonList(nameKeyword));

        // Define the new role to add
        String newRole = "vendor";

        // Create the role command descriptor with one role
        AssignCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder().withRole(newRole)
                .build();

        AssignCommand assignCommand = new AssignCommand(null, predicate, descriptor, null);

        // Execute the command and expect exception
        assertThrows(CommandException.class, () -> assignCommand.execute(model),
                AssignCommand.MESSAGE_DUPLICATE_HANDLING);
    }

    @Test
    public void execute_assignByKeywordNoPersonFound_throwsCommandException() {
        // Prepare model with typical persons
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Use a name that matches no one
        String nameKeyword = "Non Existent Person";
        NameMatchesKeywordPredicate predicate = new NameMatchesKeywordPredicate(Collections.singletonList(nameKeyword));

        // Define the new role to add
        String newRole = "vendor";

        // Create the role command descriptor with one role
        AssignCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder().withRole(newRole)
                .build();

        AssignCommand assignCommand = new AssignCommand(null, predicate, descriptor, null);

        // Execute the command and expect exception
        assertThrows(CommandException.class, () -> assignCommand.execute(model),
                AssignCommand.MESSAGE_ASSIGN_EMPTY_PERSON_LIST_ERROR);
    }

    @Test
    public void execute_assignWeddingOnly_success() throws Exception {
        // Prepare model with a copy of AMY_WEDDING and BENSON
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Wedding amyWeddingCopy = new WeddingBuilder(AMY_WEDDING).build();
        amyWeddingCopy.setClient(new PersonBuilder(BENSON).build());
        model.addWedding(amyWeddingCopy);

        Index indexFirstPerson = INDEX_FIRST_PERSON;

        Person personToAssign = model.getFilteredPersonList().get(0); // Assuming Alice is the first person
        Set<Index> weddingIndices = Set.of(INDEX_FIRST_WEDDING);
        Wedding assignedWedding = model.getFilteredWeddingList().get(0);

        AssignCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder().build();
        descriptor.setRole(null);
        AssignCommand assignCommand = new AssignCommand(indexFirstPerson, null,
                descriptor, weddingIndices);

        Person expectedPerson = new PersonBuilder(personToAssign).build();
        expectedPerson.setWeddingJobs(Set.of(assignedWedding));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
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
        // Prepare model with copies of AMY_WEDDING and ELLE_WEDDING
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Wedding amyWeddingCopy = new WeddingBuilder(AMY_WEDDING).build();
        amyWeddingCopy.setClient(new PersonBuilder(BENSON).build());
        model.addWedding(amyWeddingCopy);

        Wedding elleWeddingCopy = new WeddingBuilder(ELLE_WEDDING).build();
        elleWeddingCopy.setClient(new PersonBuilder(FIONA).build());
        model.addWedding(elleWeddingCopy);

        Person personToAssign = model.getFilteredPersonList().get(0); // Assume Alice is the first person
        Set<Index> weddingIndices = Set.of(INDEX_FIRST_WEDDING, Index.fromOneBased(2));

        AssignCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder().build();
        descriptor.setRole(null);
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, null,
                descriptor, weddingIndices);

        // Create expected person with assigned weddings
        Person expectedPerson = new PersonBuilder(personToAssign).build();
        expectedPerson.setWeddingJobs(Set.of(amyWeddingCopy, elleWeddingCopy));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
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
    public void execute_duplicateWeddingAssignment_throwsCommandException() throws Exception {
        // Prepare model with a copy of AMY_WEDDING and BENSON
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Wedding amyWeddingCopy = new WeddingBuilder(AMY_WEDDING).build();
        amyWeddingCopy.setClient(new PersonBuilder(BENSON).build());
        model.addWedding(amyWeddingCopy);

        // Assign person to wedding
        Set<Index> weddingIndices = Set.of(INDEX_FIRST_WEDDING);
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, null,
                new PersonWithRoleDescriptorBuilder().build(), weddingIndices);

        assignCommand.execute(model);

        // Try to assign the same wedding again
        AssignCommand duplicateAssignCommand = new AssignCommand(INDEX_FIRST_PERSON, null,
                new PersonWithRoleDescriptorBuilder().build(), weddingIndices);

        assertThrows(CommandException.class, () -> duplicateAssignCommand.execute(model),
                AssignCommand.MESSAGE_DUPLICATE_WEDDING);
    }

    @Test
    public void execute_assignPersonAsOwnWeddingJob_throwsCommandException() throws CommandException {
        // Prepare model with a person who is the client of a wedding
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Get BENSON from the model
        Person personToAssign = model.getFilteredPersonList().stream()
                .filter(p -> p.isSamePerson(BENSON))
                .findFirst()
                .get();

        // Build amyWeddingCopy and set client to personToAssign
        Wedding amyWeddingCopy = new WeddingBuilder(AMY_WEDDING).build();
        amyWeddingCopy.setClient(personToAssign);
        model.addWedding(amyWeddingCopy);

        // Update personToAssign to have ownWedding as amyWeddingCopy
        Person updatedPersonToAssign = new PersonBuilder(personToAssign).withOwnWedding(amyWeddingCopy).build();
        model.setPerson(personToAssign, updatedPersonToAssign);

        // Assign person to their own wedding
        Set<Index> weddingIndices = Set.of(INDEX_FIRST_WEDDING);

        // Get index of updatedPersonToAssign in the model
        int index = model.getFilteredPersonList().indexOf(updatedPersonToAssign);
        Index personIndex = Index.fromZeroBased(index);

        AssignCommand assignCommand = new AssignCommand(personIndex, null,
                new PersonWithRoleDescriptorBuilder().build(), weddingIndices);

        assertThrows(CommandException.class, () -> assignCommand.execute(model),
                AssignCommand.MESSAGE_CLIENT_ASSIGN_ERROR);
    }

    @Test
    public void execute_emptyWeddingList_throwsCommandException() throws Exception {
        // Prepare model with no weddings
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Try to assign a wedding when wedding list is empty
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, null,
                new PersonWithRoleDescriptorBuilder().build(), Set.of(Index.fromOneBased(1)));

        assertThrows(CommandException.class, () -> assignCommand.execute(model),
                AssignCommand.MESSAGE_ASSIGN_EMPTY_WEDDING_LIST_ERROR);
    }

    @Test
    public void execute_emptyPersonList_throwsCommandException() throws Exception {
        // Prepare model with empty person list
        Model model = new ModelManager();

        // Try to assign a role to a person when person list is empty
        AssignCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder()
                .withRole("vendor").build();

        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, null, descriptor, null);

        assertThrows(CommandException.class, () -> assignCommand.execute(model),
                AssignCommand.MESSAGE_ASSIGN_EMPTY_PERSON_LIST_ERROR);
    }

    @Test
    public void execute_assignRoleAndWedding_success() throws Exception {
        // Prepare model with copies of AMY_WEDDING and BENSON
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Wedding amyWeddingCopy = new WeddingBuilder(AMY_WEDDING).build();
        amyWeddingCopy.setClient(new PersonBuilder(BENSON).build());
        model.addWedding(amyWeddingCopy);

        Index indexFirstPerson = INDEX_FIRST_PERSON;

        Person personToAssign = model.getFilteredPersonList().get(0); // Assuming Alice is the first person
        Set<Index> weddingIndices = Set.of(INDEX_FIRST_WEDDING);
        Wedding assignedWedding = model.getFilteredWeddingList().get(0);

        String newRole = "vendor";
        AssignCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder().withRole(newRole)
                .build();

        AssignCommand assignCommand = new AssignCommand(indexFirstPerson, null, descriptor, weddingIndices);

        Person expectedPerson = new PersonBuilder(personToAssign).withRole(newRole).build();
        expectedPerson.setWeddingJobs(Set.of(assignedWedding));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
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
    public void execute_nullDescriptor_throwsNullPointerException() {
        Index indexFirstPerson = INDEX_FIRST_PERSON;
        assertThrows(NullPointerException.class, () -> new AssignCommand(indexFirstPerson, null,
                null, null));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AssignCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder()
                .withRole("vendor").build();
        AssignCommand assignCommand = new AssignCommand(invalidIndex, null, descriptor, null);

        assertThrows(CommandException.class, () -> assignCommand.execute(model),
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, model.getFilteredPersonList().size()));
    }

    @Test
    public void execute_invalidWeddingIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Wedding amyWeddingCopy = new WeddingBuilder(AMY_WEDDING).build();
        model.addWedding(amyWeddingCopy);
        Index invalidWeddingIndex = Index.fromOneBased(model.getFilteredWeddingList().size() + 1);
        Set<Index> weddingIndices = Set.of(invalidWeddingIndex);

        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_PERSON, null,
                new PersonWithRoleDescriptorBuilder().build(), weddingIndices);

        assertThrows(CommandException.class, () -> assignCommand.execute(model),
                String.format(Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX, model.getFilteredWeddingList().size()));
    }

    @Test
    public void execute_roleAlreadyExists_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index indexFirstPerson = INDEX_FIRST_PERSON;

        // assuming first person is Alice with role "florist"
        Person personToAssign = model.getFilteredPersonList().get(0);
        Person updatedPerson = new PersonBuilder(personToAssign).withRole("florist").build();
        model.setPerson(personToAssign, updatedPerson);

        AssignCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder()
                .withRole("florist").build();

        AssignCommand tagCommand = new AssignCommand(indexFirstPerson, null, descriptor, null);

        assertThrows(CommandException.class, () -> tagCommand.execute(model),
                String.format(AssignCommand.MESSAGE_DUPLICATE_ROLE, updatedPerson.getName()));
    }

    @Test
    public void equals() {
        AssignCommand.PersonWithRoleDescriptor descriptor1 = new PersonWithRoleDescriptorBuilder()
                .withRole(VALID_TAG_FRIEND).build();
        AssignCommand.PersonWithRoleDescriptor descriptor2 = new PersonWithRoleDescriptorBuilder()
                .withRole("anotherRole").build();

        AssignCommand assignCommand1 = new AssignCommand(INDEX_FIRST_PERSON, null,
                descriptor1, Set.of(INDEX_FIRST_WEDDING));

        AssignCommand assignCommand2 = new AssignCommand(INDEX_FIRST_PERSON, null,
                descriptor1, Set.of(INDEX_FIRST_WEDDING));

        AssignCommand assignCommand3 = new AssignCommand(INDEX_FIRST_PERSON, null,
                descriptor2, Set.of(INDEX_FIRST_WEDDING));

        AssignCommand assignCommand4 = new AssignCommand(INDEX_FIRST_PERSON, null,
                descriptor1, Set.of(Index.fromOneBased(2)));

        // same object -> returns true
        assertEquals(assignCommand1, assignCommand1);

        // same values -> returns true
        assertEquals(assignCommand1, assignCommand2);

        // different descriptors -> returns false
        assertNotEquals(assignCommand1, assignCommand3);

        // different wedding indices -> returns false
        assertNotEquals(assignCommand1, assignCommand4);

        // different types -> returns false
        assertFalse(assignCommand1.equals(new Object()));

        // null -> returns false
        assertNotEquals(assignCommand1, null);
    }

    @Test
    public void equals_withPredicate() {
        NameMatchesKeywordPredicate predicate1 = new NameMatchesKeywordPredicate(Collections.singletonList("Alice"));
        NameMatchesKeywordPredicate predicate2 = new NameMatchesKeywordPredicate(Collections.singletonList("Bob"));

        AssignCommand.PersonWithRoleDescriptor descriptor1 = new PersonWithRoleDescriptorBuilder().withRole("florist")
                .build();
        AssignCommand.PersonWithRoleDescriptor descriptor2 = new PersonWithRoleDescriptorBuilder().withRole("vendor")
                .build();

        AssignCommand assignCommand1 = new AssignCommand(null, predicate1, descriptor1, null);
        AssignCommand assignCommand2 = new AssignCommand(null, predicate1, descriptor1, null);
        AssignCommand assignCommand3 = new AssignCommand(null, predicate2, descriptor1, null);
        AssignCommand assignCommand4 = new AssignCommand(null, predicate1, descriptor2, null);

        // same object -> returns true
        assertEquals(assignCommand1, assignCommand1);

        // same values -> returns true
        assertEquals(assignCommand1, assignCommand2);

        // different predicates -> returns false
        assertNotEquals(assignCommand1, assignCommand3);

        // different descriptors -> returns false
        assertNotEquals(assignCommand1, assignCommand4);

        // different types -> returns false
        assertFalse(assignCommand1.equals(new Object()));

        // null -> returns false
        assertNotEquals(assignCommand1, null);
    }

    // Helper method for clearer assertions
    private void assertNotEquals(AssignCommand expected, AssignCommand actual) {
        assertFalse(expected.equals(actual));
    }
}
