package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TagCommand.MESSAGE_INVALID_INDEX_OR_STRING;
import static seedu.address.logic.commands.TagCommand.MESSAGE_NO_CONTACTS_TO_TAG;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.company.Company;
import seedu.address.model.person.student.Student;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CompanyBuilder;
import seedu.address.testutil.StudentBuilder;

/**
 * Contains unit tests for {@code TagCommand}.
 */
public class TagCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_addNewTagStudent_success() {
        Tag newTag = new Tag("newTag");
        Set<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(newTag);

        TagCommand addTagCommand = new TagCommand(INDEX_FIRST_PERSON, tagsToAdd);

        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        updatedTags.addAll(tagsToAdd);
        String[] tagNames = updatedTags.stream().map(tag -> tag.tagName).toArray(String[]::new);

        Person updatedPerson = new StudentBuilder((Student) personToEdit)
                .withTags(tagNames).build();

        String expectedMessage = String.format(TagCommand.MESSAGE_ADD_TAG_SUCCESS, newTag,
                Messages.format(updatedPerson));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, updatedPerson);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addNewTagCompany_success() {
        Tag newTag = new Tag("newTag");
        Set<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(newTag);

        TagCommand addTagCommand = new TagCommand(INDEX_FOURTH_PERSON, tagsToAdd);

        Person personToEdit = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());

        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        updatedTags.addAll(tagsToAdd);
        String[] tagNames = updatedTags.stream().map(tag -> tag.tagName).toArray(String[]::new);

        Person updatedPerson = new CompanyBuilder((Company) personToEdit)
                .withTags(tagNames).build();

        String expectedMessage = String.format(TagCommand.MESSAGE_ADD_TAG_SUCCESS, newTag,
                Messages.format(updatedPerson));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, updatedPerson);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addTagToAllContacts_success() {
        Tag commonTag1 = new Tag("commonTag1");
        Tag commonTag2 = new Tag("commonTag2");
        Set<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(commonTag1);
        tagsToAdd.add(commonTag2);

        TagCommand addTagCommand = new TagCommand(tagsToAdd);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        for (Person person : model.getFilteredPersonList()) {
            Set<Tag> updatedTags = new HashSet<>(person.getTags());
            updatedTags.addAll(tagsToAdd);
            String[] tagNames = updatedTags.stream().map(tag -> tag.tagName).toArray(String[]::new);
            Person updatedPerson = person instanceof Student
                    ? new StudentBuilder((Student) person).withTags(tagNames).build()
                    : new CompanyBuilder((Company) person).withTags(tagNames).build();
            expectedModel.setPerson(person, updatedPerson);
        }
        String addedTags = tagsToAdd.stream().map(Tag::toString).collect(Collectors.joining(", "));
        String expectedMessage = String.format(TagCommand.MESSAGE_ADD_TAG_TO_ALL_SUCCESS, addedTags);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);

        // Additional check to ensure both models have the same data
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_addTagToAllNoContacts_failure() {
        Model emptyModel = new ModelManager();
        Tag newTag = new Tag("commonTag");
        Set<Tag> tagsToAdd = Collections.singleton(newTag);

        TagCommand addTagCommand = new TagCommand(tagsToAdd);
        assertCommandFailure(addTagCommand, emptyModel, MESSAGE_NO_CONTACTS_TO_TAG);
    }

    @Test
    public void execute_addDuplicateTag_failure() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> existingTags = personToEdit.getTags();

        // Add duplicate tag
        Set<Tag> tagsToAdd = new HashSet<>(existingTags);
        Tag duplicateTag = existingTags.iterator().next();
        String duplicateTagName = duplicateTag.tagName;

        // Add duplicate tag with case-insensitivity
        Tag upperDuplicateTag = new Tag(duplicateTagName.toUpperCase());
        Set<Tag> upperTagsToAdd = Set.of(upperDuplicateTag);

        TagCommand addTagCommand = new TagCommand(INDEX_FIRST_PERSON, tagsToAdd);
        TagCommand upperAddTagCommand = new TagCommand(INDEX_FIRST_PERSON, upperTagsToAdd);

        String expectedMessage = String.format(TagCommand.MESSAGE_DUPLICATE_TAG, duplicateTag);
        String lowerExpectedMessage = String.format(TagCommand.MESSAGE_DUPLICATE_TAG, upperDuplicateTag);

        assertCommandFailure(addTagCommand, model, expectedMessage);
        assertCommandFailure(upperAddTagCommand, model, lowerExpectedMessage);
    }

    @Test
    public void execute_invalidPersonIndex_failure() throws CommandException {
        Set<Tag> tagsToAdd = Set.of(new Tag("newTag"));

        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        TagCommand addTagCommand = new TagCommand(invalidIndex, tagsToAdd);

        assertCommandFailure(addTagCommand, model, MESSAGE_INVALID_INDEX_OR_STRING);
    }

    @Test
    public void equals() {
        Set<Tag> tagsToAdd1 = new HashSet<>();
        tagsToAdd1.add(new Tag("friend"));

        Set<Tag> tagsToAdd2 = new HashSet<>();
        tagsToAdd2.add(new Tag("colleague"));

        TagCommand command1 = new TagCommand(INDEX_FIRST_PERSON, tagsToAdd1);
        TagCommand command2 = new TagCommand(INDEX_FIRST_PERSON, tagsToAdd1);
        TagCommand command3 = new TagCommand(INDEX_FIRST_PERSON, tagsToAdd2);
        TagCommand command4 = new TagCommand(INDEX_FOURTH_PERSON, tagsToAdd1);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // different objects, same fields -> returns true
        assertTrue(command1.equals(command2));

        // different tags -> returns false
        assertFalse(command1.equals(command3));

        // different index -> returns false
        assertFalse(command1.equals(command4));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different type -> returns false
        assertFalse(command1.equals("friend"));
    }
}
