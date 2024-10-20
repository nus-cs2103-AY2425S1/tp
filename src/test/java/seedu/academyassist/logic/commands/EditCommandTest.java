package seedu.academyassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.academyassist.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.academyassist.logic.commands.CommandTestUtil.showPersonWithIc;
import static seedu.academyassist.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.academyassist.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.academyassist.testutil.TypicalPersons.getTypicalAcademyAssist;

import org.junit.jupiter.api.Test;

import seedu.academyassist.logic.Messages;
import seedu.academyassist.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.academyassist.model.AcademyAssist;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.ModelManager;
import seedu.academyassist.model.UserPrefs;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.IcMatchesPredicate;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.testutil.EditPersonDescriptorBuilder;
import seedu.academyassist.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAcademyAssist(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(new Ic("F2234567X"), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                model.getFilteredPersonList().get(0).getName(), "F2234567X", Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AcademyAssist(model.getAcademyAssist()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Ic lastPersonIc = new Ic("F1294667X");
        model.updateFilteredPersonList(new IcMatchesPredicate(lastPersonIc));
        Person lastPerson = model.getFilteredPersonList().get(0);

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(lastPersonIc, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
            lastPerson.getName(), lastPerson.getIc(), Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AcademyAssist(model.getAcademyAssist()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(new Ic("F1264567X"), new EditPersonDescriptor());
        model.updateFilteredPersonList(new IcMatchesPredicate(new Ic("F1264567X")));
        Person editedPerson = model.getFilteredPersonList().get(0);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson.getName(),
                "F1264567X", Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AcademyAssist(model.getAcademyAssist()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonWithIc(model, new Ic("F1264567X"));

        Person personInFilteredList = model.getFilteredPersonList().get(0);
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(new Ic("F1264567X"),
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, personInFilteredList.getName(),
                "F1264567X", Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AcademyAssist(model.getAcademyAssist()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Ic firstPersonIc = new Ic("F2234567X");
        Ic secondPersonIc = new Ic("F1264567X");
        model.updateFilteredPersonList(new IcMatchesPredicate(firstPersonIc));
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(secondPersonIc, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        Ic firstPersonIc = new Ic("F2234567X");
        showPersonWithIc(model, firstPersonIc);

        // edit person in filtered list into a duplicate in academy assist
        Person personInList = model.getAcademyAssist().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(firstPersonIc,
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_nonExistenceIdUnfilteredList_failure() {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(new Ic("S2222222G"), descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NRIC);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(new Ic("F1264567X"), DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(new Ic("F1264567X"), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different id -> returns false
        assertFalse(standardCommand.equals(new EditCommand(new Ic("S2222222G"), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(new Ic("F1264567X"), DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Ic nric = new Ic("F1264567X");
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(nric, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{NRIC=" + nric + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
