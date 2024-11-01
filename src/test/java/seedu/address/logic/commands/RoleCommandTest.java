package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonWithRoleDescriptorBuilder;

public class RoleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addValidRole_success() throws Exception {
        Index indexFirstPerson = INDEX_FIRST_PERSON;

        Person personToAssign = model.getFilteredPersonList().get(0); // Assuming Alice is the first person

        // Define the new tag to add
        String newRole = "friend";

        // Create the tag command descriptor with one tag
        RoleCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder().withRole(newRole)
                .build();

        RoleCommand roleCommand = new RoleCommand(indexFirstPerson, null, descriptor, null);

        // Create the expected person with the new tags as strings
        Person expectedPerson = new PersonBuilder(personToAssign).withRole(newRole).build();

        // Setup the expected model
        Model expectedModel = new ModelManager(getTypicalAddressBook(), model.getUserPrefs());
        expectedModel.setPerson(personToAssign, expectedPerson);

        // Execute the command and assert success
        CommandResult result = roleCommand.execute(model);
        assertEquals(String.format(RoleCommand.MESSAGE_ASSIGN_PERSON_ROLE_SUCCESS, Messages.format(expectedPerson)),
                result.getFeedbackToUser());
        assertTrue(expectedModel.equals(model));

    }

    @Test
    public void execute_roleAlreadyExists_throwsCommandException() {
        Index indexFirstPerson = INDEX_FIRST_PERSON;

        // assuming first person is Alice with role "florist"
        Person personToAssign = model.getFilteredPersonList().get(0);

        RoleCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder()
                .withRole("florist").build();

        RoleCommand tagCommand = new RoleCommand(indexFirstPerson, null, descriptor, null);

        assertThrows(CommandException.class, () -> tagCommand.execute(model),
                String.format(RoleCommand.MESSAGE_DUPLICATE_ROLE, personToAssign.getName()));
    }

    @Test
    public void equals() {
        RoleCommand roleCommand1 = new RoleCommand(INDEX_FIRST_PERSON, null,
                new PersonWithRoleDescriptorBuilder().withRole(VALID_TAG_FRIEND).build(), null);
        RoleCommand roleCommand2 = new RoleCommand(INDEX_FIRST_PERSON, null,
                new PersonWithRoleDescriptorBuilder().withRole(VALID_TAG_FRIEND).build(), null);

        // same object -> returns true
        assertEquals(roleCommand1, roleCommand1);

        // same values -> returns true
        assertEquals(roleCommand1, roleCommand2);

        // different types -> returns false
        assertEquals(false, roleCommand1.equals(new Object()));

        // null -> returns false
        assertEquals(false, roleCommand1.equals(null));
    }

}
