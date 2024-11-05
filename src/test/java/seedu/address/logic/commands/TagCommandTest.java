package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
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
        Set<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(new Tag("newTag"));

        TagCommand addTagCommand = new TagCommand(INDEX_FIRST_PERSON, tagsToAdd);

        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        updatedTags.addAll(tagsToAdd);

        String[] tagNames = updatedTags.stream().map(tag -> tag.tagName).toArray(String[]::new);

        Person updatedPerson = new StudentBuilder((Student) personToEdit)
                .withTags(tagNames).build();
        String expectedMessage = String.format(TagCommand.MESSAGE_ADD_TAG_SUCCESS, updatedPerson);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, updatedPerson);


        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addNewTagCompany_success() {
        Set<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(new Tag("newTag"));

        TagCommand addTagCommand = new TagCommand(INDEX_FOURTH_PERSON, tagsToAdd);

        Person personToEdit = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());

        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        updatedTags.addAll(tagsToAdd);

        String[] tagNames = updatedTags.stream().map(tag -> tag.tagName).toArray(String[]::new);

        Person updatedPerson = new CompanyBuilder((Company) personToEdit)
                .withTags(tagNames).build();
        String expectedMessage = String.format(TagCommand.MESSAGE_ADD_TAG_SUCCESS, updatedPerson);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, updatedPerson);


        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addDuplicateTag_failure() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Set<Tag> existingTags = personToEdit.getTags();

        Set<Tag> tagsToAdd = new HashSet<>(existingTags);

        String duplicateTagName = existingTags.iterator().next().tagName;

        TagCommand addTagCommand = new TagCommand(INDEX_FIRST_PERSON, tagsToAdd);

        String expectedMessage = String.format(TagCommand.MESSAGE_DUPLICATE_TAG, duplicateTagName);

        assertCommandFailure(addTagCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Set<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(new Tag("newTag"));

        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        TagCommand addTagCommand = new TagCommand(invalidIndex, tagsToAdd);

        assertCommandFailure(addTagCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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
        assertEquals(command1, command2);

        // different tags -> returns false
        assertNotEquals(command1, command3);

        // different index -> returns false
        assertNotEquals(command1, command4);

        // null -> returns false
        assertNotEquals(null, command1);

        // different type -> returns false
        assertFalse(command1.equals("friend"));
    }
}
