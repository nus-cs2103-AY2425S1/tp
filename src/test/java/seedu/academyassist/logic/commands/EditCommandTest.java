package seedu.academyassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.academyassist.logic.Messages.MESSAGE_DUPLICATE_IC;
import static seedu.academyassist.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.academyassist.logic.commands.CommandTestUtil.showPersonWithStudentId;
import static seedu.academyassist.testutil.TypicalPersons.getTypicalAcademyAssist;
import static seedu.academyassist.testutil.TypicalStudentIds.STUDENT_ID_FIRST_PERSON;
import static seedu.academyassist.testutil.TypicalStudentIds.STUDENT_ID_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.academyassist.logic.Messages;
import seedu.academyassist.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.academyassist.model.AcademyAssist;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.ModelManager;
import seedu.academyassist.model.UserPrefs;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.testutil.EditPersonDescriptorBuilder;
import seedu.academyassist.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAcademyAssist(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder("S10001").build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(STUDENT_ID_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                model.getPersonWithStudentId(STUDENT_ID_FIRST_PERSON).getName(), STUDENT_ID_FIRST_PERSON,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AcademyAssist(model.getAcademyAssist()), new UserPrefs());
        expectedModel.setPerson(model.getPersonWithStudentId(STUDENT_ID_FIRST_PERSON), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Person secondPerson = model.getPersonWithStudentId(STUDENT_ID_SECOND_PERSON);

        PersonBuilder personInList = new PersonBuilder(secondPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        EditCommand editCommand = new EditCommand(secondPerson.getStudentId(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
            secondPerson.getName(), secondPerson.getStudentId(), Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AcademyAssist(model.getAcademyAssist()), new UserPrefs());
        expectedModel.setPerson(secondPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(STUDENT_ID_FIRST_PERSON, new EditPersonDescriptor());
        Person editedPerson = model.getPersonWithStudentId(STUDENT_ID_FIRST_PERSON);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson.getName(),
                editedPerson.getStudentId(), Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AcademyAssist(model.getAcademyAssist()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonWithStudentId(model, STUDENT_ID_SECOND_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(0);
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(STUDENT_ID_SECOND_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, personInFilteredList.getName(),
                STUDENT_ID_SECOND_PERSON, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AcademyAssist(model.getAcademyAssist()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateIcUnfilteredList_failure() {
        Person firstPerson = model.getPersonWithStudentId(STUDENT_ID_FIRST_PERSON);
        Person secondPerson = model.getPersonWithStudentId(STUDENT_ID_SECOND_PERSON);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(secondPerson).build();
        descriptor.setIc(firstPerson.getIc());
        EditCommand editCommand = new EditCommand(STUDENT_ID_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_IC);
    }

    @Test
    public void execute_duplicateIcFilteredList_failure() {
        showPersonWithStudentId(model, STUDENT_ID_FIRST_PERSON);

        Person firstPerson = model.getPersonWithStudentId(STUDENT_ID_FIRST_PERSON);
        Person secondPerson = model.getPersonWithStudentId(STUDENT_ID_SECOND_PERSON);

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        descriptor.setIc(secondPerson.getIc());
        // edit person in filtered list into an IC duplicate in academy assist
        EditCommand editCommand = new EditCommand(STUDENT_ID_FIRST_PERSON, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_IC);
    }

    @Test
    public void execute_nonExistenceIdUnfilteredList_failure() {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(new StudentId("S99999"), descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_NO_STUDENT_FOUND);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(new StudentId("S10007"), DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(new StudentId("S10007"), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different id -> returns false
        assertFalse(standardCommand.equals(new EditCommand(new StudentId("S10003"), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(new StudentId("S10007"), DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(STUDENT_ID_FIRST_PERSON, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{studentId=" + STUDENT_ID_FIRST_PERSON
                + ", editPersonDescriptor=" + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
