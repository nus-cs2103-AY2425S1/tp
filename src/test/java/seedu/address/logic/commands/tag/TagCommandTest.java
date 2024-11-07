package seedu.address.logic.commands.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.FLORIST;
import static seedu.address.testutil.TypicalTags.PHOTOGRAPHER;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagName;

public class TagCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validTagsUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        HashSet<Tag> tagsToAdd = new HashSet<>(List.of(new Tag(new TagName("colleague"))));

        // Ensure the model has the tag before adding it to the person
        model.addTag(new Tag(new TagName("colleague")));

        TagCommand tagCommand = new TagCommand(INDEX_FIRST, tagsToAdd);

        String expectedMessage = String.format(Messages.MESSAGE_ADD_TAG_SUCCESS,
                "colleague", personToEdit.getName().toString());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        updatedTags.addAll(tagsToAdd);
        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                updatedTags,
                personToEdit.getWeddings(),
                personToEdit.getTasks());
        expectedModel.setPerson(personToEdit, editedPerson);

        CommandTestUtil.assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validTagsUnfilteredListWithForce_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        HashSet<Tag> tagsToAdd = new HashSet<>(List.of(new Tag(new TagName("colleague"))));

        // Ensure the model has the tag before adding it to the person

        TagCommand tagCommand = new TagCommand(INDEX_FIRST, tagsToAdd, true);

        String expectedMessage = String.format(Messages.MESSAGE_ADD_TAG_SUCCESS,
                "colleague", personToEdit.getName().toString());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        updatedTags.addAll(tagsToAdd);
        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                updatedTags,
                personToEdit.getWeddings(),
                personToEdit.getTasks());
        expectedModel.setPerson(personToEdit, editedPerson);
        expectedModel.addTag(new Tag(new TagName("colleague")));

        CommandTestUtil.assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_validMultipleTagsUnfilteredList_success() {
        Person personWithTags = new Person(
                new Name("Test Person"),
                new Phone("99999999"),
                new Email("test@example.com"),
                new Address("123, Test Street"),
                new HashSet<>(List.of(new Tag(new TagName("family")))),
                new HashSet<>(),
                new HashSet<>()
        );
        model.addTag(new Tag(new TagName("family")));
        model.setPerson(model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased()), personWithTags);
        model.addTag(new Tag(new TagName("colleague")));
        model.addTag(new Tag(new TagName("gym")));
        HashSet<Tag> tagsToAdd = new HashSet<>(Arrays.asList(new Tag(new TagName("colleague")),
                new Tag(new TagName("gym"))));
        TagCommand tagCommand = new TagCommand(INDEX_FIRST, tagsToAdd);
        String expectedMessage = String.format(Messages.MESSAGE_ADD_TAG_SUCCESS,
                "gym, colleague", personWithTags.getName().toString());

        // Create the expected model with the updated tags
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Tag> updatedTags = new HashSet<>(personWithTags.getTags());
        updatedTags.addAll(tagsToAdd);
        Person editedPerson = new Person(
                personWithTags.getName(),
                personWithTags.getPhone(),
                personWithTags.getEmail(),
                personWithTags.getAddress(),
                updatedTags,
                personWithTags.getWeddings(),
                personWithTags.getTasks());
        expectedModel.setPerson(personWithTags, editedPerson);

        CommandTestUtil.assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_indexZero_failure() {
        Index zeroIndex = Index.oneBasedNoConstraints(0);
        HashSet<Tag> tagsToAdd = new HashSet<>(List.of(new Tag(new TagName("colleague"))));

        TagCommand tagCommand = new TagCommand(zeroIndex, tagsToAdd);

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                1, model.getFilteredPersonList().size());

        CommandTestUtil.assertCommandFailure(tagCommand, model, expectedMessage);
    }

    @Test
    public void execute_nonExistentTag_failure() {
        HashSet<Tag> tagsToAdd = new HashSet<>(List.of(new Tag(new TagName("nonExistentTag"))));
        TagCommand tagCommand = new TagCommand(INDEX_FIRST, tagsToAdd);
        String expectedMessage = Messages.MESSAGE_TAG_NOT_FOUND + '\n' + Messages.MESSAGE_FORCE_TAG_TO_CONTACT;

        CommandTestUtil.assertCommandFailure(tagCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        // Create some tags for testing
        Tag tag1 = FLORIST;
        Tag tag2 = PHOTOGRAPHER;

        // Create TagCommand instances
        TagCommand tagCommand1 = new TagCommand(Index.fromOneBased(1), new HashSet<>() {{
                add(tag1);
                add(tag2);
            }});

        TagCommand tagCommand2 = new TagCommand(Index.fromOneBased(1), new HashSet<>() {{
                add(tag1);
                add(tag2);
            }});

        TagCommand tagCommand3 = new TagCommand(Index.fromOneBased(2), new HashSet<>() {{
                add(tag1);
                add(tag2);
            }});

        TagCommand tagCommand4 = new TagCommand(Index.fromOneBased(1), new HashSet<>() {{
                add(tag1);
            }});

        // Same index and same tags -> should be equal
        assertEquals(tagCommand1, tagCommand2);

        // Different index -> should not be equal
        assertNotEquals(tagCommand1, tagCommand3);

        // Same index but different set of tags -> should not be equal
        assertNotEquals(tagCommand1, tagCommand4);

        // Comparing with itself -> should be equal
        assertEquals(tagCommand1, tagCommand1);
    }
}
