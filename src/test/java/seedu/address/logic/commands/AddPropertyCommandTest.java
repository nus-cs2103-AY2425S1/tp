package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Property;
import seedu.address.model.person.PropertyList;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddPropertyCommand.
 */
public class AddPropertyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addPropertyUnfilteredList_success() {
        Property newProperty = Property.of(
                "123 Main St", "Central", "Condo",
                85.0, 2, 2, 500000.0);

        AddPropertyCommand command = new AddPropertyCommand(
                INDEX_FIRST_PERSON, "123 Main St", "Central", "Condo",
                85.0, 2, 2, 500000.0);

        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PropertyList updatedPropertyList = PropertyList.addProperty(personToEdit.getPropertyList(), newProperty);
        Person editedPerson = new PersonBuilder(personToEdit).withPropertyList(updatedPropertyList).build();

        String expectedMessage = String.format(Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addPropertyFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Property newProperty = Property.of(
                "456 Orchard Rd", "Orchard", "Apartment",
                70.0, 1, 1, 400000.0);

        AddPropertyCommand command = new AddPropertyCommand(
                INDEX_FIRST_PERSON, "456 Orchard Rd", "Orchard", "Apartment",
                70.0, 1, 1, 400000.0);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PropertyList updatedPropertyList = PropertyList.addProperty(personInFilteredList.getPropertyList(),
                newProperty);
        Person editedPerson = new PersonBuilder(personInFilteredList).withPropertyList(updatedPropertyList).build();

        String expectedMessage = String.format(Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personInFilteredList, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddPropertyCommand command = new AddPropertyCommand(
                outOfBoundIndex, "123 Main St", "Central", "Condo",
                85.0, 2, 2, 500000.0);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddPropertyCommand command = new AddPropertyCommand(
                outOfBoundIndex, "123 Main St", "Central", "Condo",
                85.0, 2, 2, 500000.0);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AddPropertyCommand command1 = new AddPropertyCommand(
                INDEX_FIRST_PERSON, "123 Main St", "Central", "Condo",
                85.0, 2, 2, 500000.0);

        AddPropertyCommand command2 = new AddPropertyCommand(
                INDEX_SECOND_PERSON, "456 Orchard Rd", "Orchard", "Apartment",
                70.0, 1, 1, 400000.0);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        AddPropertyCommand command1Copy = new AddPropertyCommand(
                INDEX_FIRST_PERSON, "123 Main St", "Central", "Condo",
                85.0, 2, 2, 500000.0);
        assertTrue(command1.equals(command1Copy));

        // different types -> returns false
        assertFalse(command1.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different person -> returns false
        assertFalse(command1.equals(command2));
    }

    @Test
    public void toStringMethod() {
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(
                INDEX_FIRST_PERSON, "123 Main St", "Central", "Condo",
                85.0, 2, 2, 500000.0);
        String expected = AddPropertyCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_PERSON
                + ", address='123 Main St', location='Central', type='Condo',"
                + " area=85.0, bedrooms=2, bathrooms=2, price=500000.0}";
        assertEquals(expected, addPropertyCommand.toString());
    }
}
