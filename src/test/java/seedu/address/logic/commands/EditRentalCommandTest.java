package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.EDIT_RENTAL_DESCRIPTOR_ONE;
import static seedu.address.logic.commands.CommandTestUtil.EDIT_RENTAL_DESCRIPTOR_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTHLY_RENT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RENTAL;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RENTAL;
import static seedu.address.testutil.TypicalPersons.ALICE_WITH_RENTAL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithRental;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditRentalCommand.EditRentalDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.rentalinformation.RentalInformation;
import seedu.address.testutil.EditRentalDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RentalInformationBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code EditRentalCommand}.
 */
public class EditRentalCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithRental(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        RentalInformation editedRentalInformation = new RentalInformationBuilder().build();
        EditRentalDescriptor descriptor = new EditRentalDescriptorBuilder(editedRentalInformation).build();
        EditRentalCommand editRentalCommand = new EditRentalCommand(INDEX_FIRST_PERSON, INDEX_FIRST_RENTAL, descriptor);

        String expectedMessage = String.format(EditRentalCommand.MESSAGE_EDIT_RENTAL_SUCCESS,
                Messages.formatRentalInformation(editedRentalInformation));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Client editedClient = new PersonBuilder(ALICE_WITH_RENTAL).withRentalInformation(editedRentalInformation)
                .build();
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedClient);

        assertCommandSuccess(editRentalCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Client lastClient = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());
        RentalInformation firstRentalInformationOfLastClient = lastClient.getRentalInformation().get(0);

        PersonBuilder personInList = new PersonBuilder(lastClient);
        RentalInformation editedRentalInformation = new RentalInformationBuilder(firstRentalInformationOfLastClient)
                .withAddress(VALID_ADDRESS_ONE).withMonthlyRent(VALID_MONTHLY_RENT_ONE).build();
        Client editedClient = personInList
                .withRentalInformation(editedRentalInformation, lastClient.getRentalInformation().get(1))
                .build();

        EditRentalDescriptor descriptor = new EditRentalDescriptorBuilder().withAddress(VALID_ADDRESS_ONE)
                .withMonthlyRent(VALID_MONTHLY_RENT_ONE).build();
        EditRentalCommand editRentalCommand = new EditRentalCommand(indexLastPerson, INDEX_FIRST_RENTAL, descriptor);

        String expectedMessage = String.format(EditRentalCommand.MESSAGE_EDIT_RENTAL_SUCCESS,
                Messages.formatRentalInformation(editedRentalInformation));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastClient, editedClient);

        assertCommandSuccess(editRentalCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditRentalCommand editRentalCommand = new EditRentalCommand(INDEX_FIRST_PERSON, INDEX_FIRST_RENTAL,
                new EditRentalDescriptor());
        Client editedClient = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RentalInformation editedRentalInformation = editedClient.getRentalInformation().get(
                INDEX_FIRST_RENTAL.getZeroBased());

        String expectedMessage = String.format(EditRentalCommand.MESSAGE_EDIT_RENTAL_SUCCESS,
                Messages.formatRentalInformation(editedRentalInformation));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editRentalCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Client clientInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RentalInformation firstRentalInformationOfClientInFilteredList = clientInFilteredList.getRentalInformation()
                .get(0);

        RentalInformation editeRentalInformation =
                new RentalInformationBuilder(firstRentalInformationOfClientInFilteredList)
                        .withAddress(VALID_ADDRESS_ONE).build();
        Client editedClient = new PersonBuilder(clientInFilteredList).withRentalInformation(editeRentalInformation)
                .build();
        EditRentalCommand editRentalCommand = new EditRentalCommand(INDEX_FIRST_PERSON, INDEX_FIRST_RENTAL,
                new EditRentalDescriptorBuilder().withAddress(VALID_ADDRESS_ONE).build());

        String expectedMessage = String.format(EditRentalCommand.MESSAGE_EDIT_RENTAL_SUCCESS,
                Messages.formatRentalInformation(editeRentalInformation));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedClient);

        assertCommandSuccess(editRentalCommand, model, expectedMessage, expectedModel);
    }

    //TODO: execute_duplicatePersonUnfilteredList_failure() and execute_duplicatePersonFilteredList_failure()

    @Test
    public void execute_invalidRentalIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditRentalDescriptor descriptor = new EditRentalDescriptorBuilder().withAddress(VALID_ADDRESS_ONE).build();
        EditRentalCommand editRentalCommand = new EditRentalCommand(INDEX_FIRST_PERSON, outOfBoundIndex, descriptor);

        assertCommandFailure(editRentalCommand, model, Messages.MESSAGE_INVALID_RENTAL_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidRentalIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditRentalCommand editRentalCommand = new EditRentalCommand(INDEX_FIRST_PERSON, outOfBoundIndex,
                new EditRentalDescriptorBuilder().withAddress(VALID_ADDRESS_ONE).build());

        assertCommandFailure(editRentalCommand, model, Messages.MESSAGE_INVALID_RENTAL_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditRentalCommand standardCommand =
                new EditRentalCommand(INDEX_FIRST_PERSON, INDEX_FIRST_RENTAL, EDIT_RENTAL_DESCRIPTOR_ONE);

        // same values -> returns true
        EditRentalDescriptor copyDescriptor = new EditRentalDescriptor(EDIT_RENTAL_DESCRIPTOR_ONE);
        EditRentalCommand commandWithSameValues =
                new EditRentalCommand(INDEX_FIRST_PERSON, INDEX_FIRST_RENTAL, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditRentalCommand(INDEX_FIRST_PERSON, INDEX_SECOND_RENTAL,
                EDIT_RENTAL_DESCRIPTOR_ONE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditRentalCommand(INDEX_FIRST_PERSON, INDEX_FIRST_RENTAL,
                EDIT_RENTAL_DESCRIPTOR_TWO)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditRentalDescriptor editRentalDescriptor = new EditRentalDescriptor();
        EditRentalCommand editRentalCommand = new EditRentalCommand(index, index, editRentalDescriptor);
        String expected = EditRentalCommand.class.getCanonicalName() + "{client index=" + index + ", rental index="
                + index + ", editRentalDescriptor=" + editRentalDescriptor + "}";
        assertEquals(expected, editRentalCommand.toString());
    }
}
