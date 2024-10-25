package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.role.Role;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonWithRoleDescriptorBuilder;

public class TagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addValidTag_success() throws Exception {
        Person personToTag = model.getFilteredPersonList().get(0); // Assuming Alice is the first person
        Name name = new Name("Alice Pauline");

        // Create a set of tags that includes the existing tags and the new role
        Set<Role> existingTags = personToTag.getRole();
        Set<String> newTags = new HashSet<>();

        // Add existing tags
        for (Role tag : existingTags) {
            newTags.add(tag.roleName);
        }
        newTags.add("friend"); // Add the new role

        // Create the role command descriptor
        RoleCommand.PersonWithRoleDescriptor descriptor = new PersonWithRoleDescriptorBuilder().withRole("friend").build();

        RoleCommand tagCommand = new RoleCommand(name, descriptor);

        // Create the expected person with the new tags as strings
        Person expectedPerson = new PersonBuilder(personToTag).withRoles(newTags.toArray(new String[0])).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToTag, expectedPerson);

        CommandResult result = tagCommand.execute(model);

        assertEquals(String.format(RoleCommand.MESSAGE_ADD_PERSON_ROLE_SUCCESS, Messages.format(expectedPerson)),
                result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void equals() {
        RoleCommand tagCommand1 = new RoleCommand(new Name(VALID_NAME_AMY),
                new PersonWithRoleDescriptorBuilder().withRole(VALID_TAG_FRIEND).build());
        RoleCommand tagCommand2 = new RoleCommand(new Name(VALID_NAME_AMY),
                new PersonWithRoleDescriptorBuilder().withRole(VALID_TAG_FRIEND).build());

        // same object -> returns true
        assertEquals(tagCommand1, tagCommand1);

        // same values -> returns true
        assertEquals(tagCommand1, tagCommand2);

        // different types -> returns false
        assertEquals(false, tagCommand1.equals(new Object()));

        // null -> returns false
        assertEquals(false, tagCommand1.equals(null));
    }

}
