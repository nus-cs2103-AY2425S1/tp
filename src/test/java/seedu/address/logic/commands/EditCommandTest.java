package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Person targetPerson = TypicalPersons.KEANU;

    @Test
    public void execute_allFieldsSpecified_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(targetPerson.getNric(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(targetPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        Person editedPerson = new PersonBuilder().withName("Keanu Reeves Tan").withNric("S9712385J").withSex("M")
                .withBirthdate("1997-11-30").withPhone("86526969")
                .withEmail("keanureeves@example.com").withAddress("Blk 512 Ang Mo Kio Ave 2").withBloodType("O+")
                .withNokName("Mila Kunis").withNokPhone("84126990").withAllergy("peanuts, cake").withHealthRisk("LOW")
                .withHealthRecord("diabetes").withNote("Patient has previous gunshot wound to chest").build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName("Keanu Reeves Tan")
                .withNric("S9712385J").withAllergy("peanuts, cake")
                .build();
        EditCommand editCommand = new EditCommand(targetPerson.getNric(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(targetPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePerson_failure() {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(targetPerson).build();
        EditCommand editCommand = new EditCommand(TypicalPersons.ALICE.getNric(), descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }


    @Test
    public void execute_invalidPerson_failure() {
        Nric invalidNric = new Nric("T0895432A");
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(invalidNric, descriptor);

        assertCommandFailure(editCommand, model, String.format(Messages.MESSAGE_INVALID_PERSON_NRIC, invalidNric));
    }
    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(DESC_AMY.getNric().get(), DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(DESC_AMY.getNric().get(), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(DESC_BOB.getNric().get(), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(DESC_AMY.getNric().get(), DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(targetPerson.getNric(), editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{NRIC=" + targetPerson.getNric()
                + ", editPersonDescriptor=" + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
