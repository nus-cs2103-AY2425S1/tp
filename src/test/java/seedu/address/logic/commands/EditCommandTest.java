package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_TUTOR;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalEduContacts;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EduContacts;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalEduContacts(), new UserPrefs());

    @Test
    public void execute_editModuleOldModuleNotFound_throwsCommandException() {
        Person originalPerson = TypicalPersons.BENSON;
        Module oldModule = new Module("CS1111");
        Module newModule = new Module("CS1231S");

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().build();
        descriptor.setModuleChanges(oldModule, newModule);

        EditCommand editCommand = new EditCommand(originalPerson.getStudentId(), descriptor);

        String expectedMessage = EditCommand.MESSAGE_MODULE_NOT_FOUND;
        assertThrows(CommandException.class, () -> editCommand.execute(model), expectedMessage);
    }


    @Test
    public void execute_editModuleWithoutGrade_success() throws Exception {
        Person originalPerson = TypicalPersons.BENSON;
        Module oldModule = new Module("GEC1044");
        Module newModule = new Module("CS1231S");

        Person expectedPerson = new PersonBuilder()
                .withStudentId("19191919")
                .withName("Benson Meier")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com")
                .withPhone("98765432")
                .withCourse("Medicine")
                .withRole("Student")
                .addUngradedModule("CS1231S")
                .build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().build();
        descriptor.setModuleChanges(oldModule, newModule);

        EditCommand editCommand = new EditCommand(originalPerson.getStudentId(), descriptor);

        Model expectedModel = new ModelManager(model.getEduContacts(), new UserPrefs());
        expectedModel.setPerson(originalPerson, expectedPerson);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(expectedPerson));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);

        assertTrue(expectedPerson.getModules().stream().anyMatch(m -> m.value.equals("CS1231S")));
        assertFalse(expectedPerson.getModules().stream().anyMatch(m -> m.value.equals("GEC1044")));
    }

    @Test
    public void execute_editModuleWithGrade_success() throws Exception {
        Person originalPerson = TypicalPersons.ALICE;
        Module oldModule = new Module("MA1100");
        oldModule.setGrade(new Grade("A"));
        Module newModule = new Module("CS1010");

        Person expectedPerson = new PersonBuilder()
                .withStudentId("22223333")
                .withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111")
                .withEmail("alice@example.com")
                .withPhone("94351253")
                .withCourse("Math")
                .withRole("Student")
                .addGradedModule("CS1010", "A")
                .addUngradedModule("MA2202")
                .build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().build();
        descriptor.setModuleChanges(oldModule, newModule);

        EditCommand editCommand = new EditCommand(originalPerson.getStudentId(), descriptor);

        Model expectedModel = new ModelManager(model.getEduContacts(), new UserPrefs());
        expectedModel.setPerson(originalPerson, expectedPerson);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(expectedPerson));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editToDuplicateModule_failure() {
        Person editedPerson = model.getFilteredPersonList().get(0);
        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        descriptor.setOldModule(editedPerson.getModules().get(0));
        descriptor.setNewModule(editedPerson.getModules().get(1));
        StudentId studentId = editedPerson.getStudentId();

        EditCommand editCommand = new EditCommand(studentId, descriptor);

        Model expectedModel = new ModelManager(new EduContacts(model.getEduContacts()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MODULE);
    }


    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().withStudentId("22223333").build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        StudentId studentId = model.getFilteredPersonList().get(0).getStudentId();
        EditCommand editCommand = new EditCommand(studentId, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new EduContacts(model.getEduContacts()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Person lastPerson = model.getFilteredPersonList().get(model.getFilteredPersonList().size() - 1);
        StudentId lastPersonId = lastPerson.getStudentId();

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withRole(VALID_ROLE_TUTOR).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withRole(VALID_ROLE_TUTOR).build();
        EditCommand editCommand = new EditCommand(lastPersonId, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new EduContacts(model.getEduContacts()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        StudentId studentId = personInFilteredList.getStudentId();
        EditCommand editCommand = new EditCommand(studentId,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new EduContacts(model.getEduContacts()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidPersonIdUnfilteredList_failure() {
        StudentId invalidStudentId = new StudentId("99999999"); // id that doesn't exist in the EduContacts
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(invalidStudentId, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_STUDENTID);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of EduContacts
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        StudentId invalidStudentId = new StudentId("99999999"); // id that doesn't exist in EduContacts

        EditCommand editCommand = new EditCommand(invalidStudentId,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_STUDENTID);
    }

    @Test
    public void execute_personDisplayedEdited_success() throws CommandException {

        Person personToDisplay = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.setPersonToDisplay(personToDisplay);

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName("Edited Name")
                .withPhone("98765432")
                .withEmail("edited@example.com")
                .build();

        EditCommand editCommand = new EditCommand(personToDisplay.getStudentId(), descriptor);

        Person editedPerson = new PersonBuilder(personToDisplay)
                .withName("Edited Name")
                .withPhone("98765432")
                .withEmail("edited@example.com")
                .build();

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(model.getEduContacts(), new UserPrefs());
        expectedModel.setPerson(personToDisplay, editedPerson);
        expectedModel.setPersonToDisplay(editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        StudentId firstPersonId = model.getFilteredPersonList().get(0).getStudentId();
        StudentId secondPersonId = model.getFilteredPersonList().get(1).getStudentId();
        final EditCommand standardCommand = new EditCommand(firstPersonId, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(firstPersonId, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(secondPersonId, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(firstPersonId, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        StudentId studentId = model.getFilteredPersonList().get(0).getStudentId();
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(studentId, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{studentId=" + studentId + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
