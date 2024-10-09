package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagName;

public class TagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validTagsUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<Tag> tagsToAdd = Arrays.asList(new Tag(new TagName("colleague")));

        // Ensure the model has the tag before adding it to the person
        model.addTag(new Tag(new TagName("colleague")));

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, tagsToAdd);

        String expectedMessage = String.format(TagCommand.MESSAGE_ADD_TAG_SUCCESS,
                "colleague", personToEdit.getName().toString());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        updatedTags.addAll(tagsToAdd);
        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                updatedTags);
        expectedModel.setPerson(personToEdit, editedPerson);

        CommandTestUtil.assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_validMultipleTagsUnfilteredList_success() {
        Person personWithTags = new Person(
                new seedu.address.model.person.Name("Test Person"),
                new seedu.address.model.person.Phone("99999999"),
                new seedu.address.model.person.Email("test@example.com"),
                new seedu.address.model.person.Address("123, Test Street"),
                new HashSet<>(Arrays.asList(new Tag(new TagName("family"))))
        );
        model.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personWithTags);
        model.addTag(new Tag(new TagName("colleague")));
        model.addTag(new Tag(new TagName("gym")));
        List<Tag> tagsToAdd = Arrays.asList(new Tag(new TagName("colleague")), new Tag(new TagName("gym")));
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, tagsToAdd);
        String expectedMessage = String.format(TagCommand.MESSAGE_ADD_TAG_SUCCESS,
                "colleague, gym", personWithTags.getName().toString());

        // Create the expected model with the updated tags
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Tag> updatedTags = new HashSet<>(personWithTags.getTags());
        updatedTags.addAll(tagsToAdd);
        Person editedPerson = new Person(
                personWithTags.getName(),
                personWithTags.getPhone(),
                personWithTags.getEmail(),
                personWithTags.getAddress(),
                updatedTags);
        expectedModel.setPerson(personWithTags, editedPerson);

        CommandTestUtil.assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Tag> tagsToAdd = Arrays.asList(new Tag(new TagName("colleague")));

        TagCommand tagCommand = new TagCommand(outOfBoundIndex, tagsToAdd);

        String expectedMessage = TagCommand.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

        CommandTestUtil.assertCommandFailure(tagCommand, model, expectedMessage);
    }

    @Test
    public void execute_nonExistentTag_failure() {
        List<Tag> tagsToAdd = Arrays.asList(new Tag(new TagName("nonExistentTag")));
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, tagsToAdd);
        String expectedMessage = TagCommand.MESSAGE_TAG_NOT_FOUND;

        CommandTestUtil.assertCommandFailure(tagCommand, model, expectedMessage);
    }
}
