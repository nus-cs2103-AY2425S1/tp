package tutorease.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.logic.commands.CommandTestUtil.DESC_AMY;
import static tutorease.address.logic.commands.CommandTestUtil.DESC_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tutorease.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutorease.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorease.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static tutorease.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tutorease.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static tutorease.address.testutil.TypicalLessons.getTypicalLessons;
import static tutorease.address.testutil.TypicalStudents.getTypicalTutorEase;

import org.junit.jupiter.api.Test;

import tutorease.address.commons.core.index.Index;
import tutorease.address.logic.Messages;
import tutorease.address.logic.commands.EditContactCommand.EditPersonDescriptor;
import tutorease.address.model.Model;
import tutorease.address.model.ModelManager;
import tutorease.address.model.TutorEase;
import tutorease.address.model.UserPrefs;
import tutorease.address.model.person.Person;
import tutorease.address.testutil.EditPersonDescriptorBuilder;
import tutorease.address.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditContactCommandTest {

    private Model model = new ModelManager(getTypicalTutorEase(), new UserPrefs(), getTypicalLessons());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new StudentBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditContactCommand editCommand = new EditContactCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new TutorEase(model.getTutorEase()), new UserPrefs(),
                getTypicalLessons());

        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        StudentBuilder personInList = new StudentBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditContactCommand editCommand = new EditContactCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new TutorEase(model.getTutorEase()), new UserPrefs(),
                getTypicalLessons());

        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditContactCommand editCommand = new EditContactCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new TutorEase(model.getTutorEase()), new UserPrefs(),
                getTypicalLessons());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new StudentBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditContactCommand editCommand = new EditContactCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new TutorEase(model.getTutorEase()), new UserPrefs(),
                getTypicalLessons());

        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditContactCommand editCommand = new EditContactCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditContactCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getTutorEase().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditContactCommand editCommand = new EditContactCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, EditContactCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditContactCommand editCommand = new EditContactCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTutorEase().getPersonList().size());

        EditContactCommand editCommand = new EditContactCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditContactCommand standardCommand = new EditContactCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditContactCommand commandWithSameValues = new EditContactCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditContactCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditContactCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditContactCommand editCommand = new EditContactCommand(index, editPersonDescriptor);
        String expected = EditContactCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
