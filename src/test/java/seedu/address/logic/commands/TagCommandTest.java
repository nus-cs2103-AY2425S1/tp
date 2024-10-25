package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TagPersonDescriptorBuilder;

public class TagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addValidTag_success() throws Exception {
        Person personToTag = model.getFilteredPersonList().get(0); // Assuming Alice is the first person
        Name name = new Name("Alice Pauline");

        // Define the new tag to add
        String newTag = "friend";

        // Create the tag command descriptor with one tag
        TagCommand.TagPersonDescriptor descriptor = new TagPersonDescriptorBuilder().withTag(newTag).build();

        // Create the tag command
        TagCommand tagCommand = new TagCommand(name, descriptor);

        // Create the expected person with the new tag
        Person expectedPerson = new PersonBuilder(personToTag).withTag(newTag).build();

        // Setup the expected model
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToTag, expectedPerson);

        // Execute the command and assert success
        CommandResult result = tagCommand.execute(model);

        assertEquals(String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(expectedPerson)),
                result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_tagAlreadyExists_throwsCommandException() {
        Person personToTag = model.getFilteredPersonList().get(0);

        Name name = new Name(VALID_NAME_AMY);
        TagCommand.TagPersonDescriptor descriptor = new TagPersonDescriptorBuilder().withTag(VALID_TAG_FRIEND).build();

        TagCommand tagCommand = new TagCommand(name, descriptor);

        assertThrows(CommandException.class, () -> tagCommand.execute(model),
                String.format(TagCommand.MESSAGE_DUPLICATE_TAG, personToTag.getName()));
    }

    @Test
    public void equals() {
        TagCommand tagCommand1 = new TagCommand(new Name(VALID_NAME_AMY),
                new TagPersonDescriptorBuilder().withTag(VALID_TAG_FRIEND).build());
        TagCommand tagCommand2 = new TagCommand(new Name(VALID_NAME_AMY),
                new TagPersonDescriptorBuilder().withTag(VALID_TAG_FRIEND).build());

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
