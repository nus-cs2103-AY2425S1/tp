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
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Property;

public class AddPropertyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Listings());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Property property = new Property("Pasir Ris");

        Person personToAddProperty = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(INDEX_FIRST_PERSON, property);

        Person personWithAddedProperty = new Person(personToAddProperty.getName(),
                personToAddProperty.getPhone(), personToAddProperty.getEmail(), personToAddProperty.getTags(),
                personToAddProperty.getAppointment(), property);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new Listings());
        expectedModel.setPerson(personToAddProperty, personWithAddedProperty);
        String expectedMessage = String.format(AddPropertyCommand.MESSAGE_ADD_PROPERTY_SUCCESS,
                property, Messages.format(personWithAddedProperty));

        assertCommandSuccess(addPropertyCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(outOfBoundIndex, new Property("DO_NOT_EXIST"));

        assertCommandFailure(addPropertyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Property property = new Property("Pasir Ris");

        Person personToAddProperty = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(INDEX_FIRST_PERSON, property);

        Person personWithAddedProperty = new Person(personToAddProperty.getName(),
                personToAddProperty.getPhone(), personToAddProperty.getEmail(), personToAddProperty.getTags(),
                personToAddProperty.getAppointment(), property);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new Listings());
        expectedModel.setPerson(personToAddProperty, personWithAddedProperty);
        String expectedMessage = String.format(AddPropertyCommand.MESSAGE_ADD_PROPERTY_SUCCESS,
                property, Messages.format(personWithAddedProperty));

        assertCommandSuccess(addPropertyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(INDEX_SECOND_PERSON, new Property(""));

        assertCommandFailure(addPropertyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        Property firstProperty = new Property("Pasir Ris");
        Property secondProperty = new Property("Tampines");
        AddPropertyCommand addPropertyFirstCommand = new AddPropertyCommand(INDEX_FIRST_PERSON, firstProperty);
        AddPropertyCommand addPropertyDifferentPersonCommand = new AddPropertyCommand(INDEX_SECOND_PERSON,
                firstProperty);
        AddPropertyCommand addPropertyDifferentPropertyCommand = new AddPropertyCommand(INDEX_FIRST_PERSON,
                secondProperty);


        // same object -> returns true
        assertTrue(addPropertyFirstCommand.equals(addPropertyFirstCommand));

        // same values -> returns true
        AddPropertyCommand addPropertyFirstCommandCopy = new AddPropertyCommand(INDEX_FIRST_PERSON, firstProperty);
        assertTrue(addPropertyFirstCommand.equals(addPropertyFirstCommandCopy));

        // different types -> returns false
        assertFalse(addPropertyFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addPropertyFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(addPropertyFirstCommandCopy.equals(addPropertyDifferentPersonCommand));

        // different property -> returns false
        assertFalse(addPropertyFirstCommand.equals(addPropertyDifferentPropertyCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Property property = new Property("Pasir Ris");
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(targetIndex, property);
        String expected = AddPropertyCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex
                + ", property=" + property + "}";
        assertEquals(expected, addPropertyCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
