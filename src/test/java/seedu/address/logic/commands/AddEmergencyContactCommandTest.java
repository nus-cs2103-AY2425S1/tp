package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.*;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.AddCommandTest.ModelStubAcceptingPersonAdded;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relationship;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddEmergencyContactCommandTest {
    private EmergencyContact VALID_EMERGENCY_CONTACT = new EmergencyContact(
            new Name(VALID_ECNAME_AMY), new Phone(VALID_ECPHONE_AMY), new Relationship(VALID_ECRS_AMY));
    private EmergencyContact VALID_EMERGENCY_CONTACT_BOB = new EmergencyContact(
            new Name(VALID_ECNAME_BOB), new Phone(VALID_ECPHONE_BOB), new Relationship(VALID_ECRS_BOB));
    private Set<EmergencyContact> ALICE_EMERGENCY_CONTACTS = new LinkedHashSet<>(ALICE.getEmergencyContacts());
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Person createExpectedPerson() {
        Set<EmergencyContact> emergencyContacts = new LinkedHashSet<>(ALICE_EMERGENCY_CONTACTS);
        emergencyContacts.add(VALID_EMERGENCY_CONTACT);
     return new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getAddress(), emergencyContacts, ALICE.getDoctor(), ALICE.getTags());
    }

    @Test
    public void constructor_nullIndex_nullEmergencyContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEmergencyContactCommand(null,
                null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {

        CommandResult commandResult = new AddEmergencyContactCommand(INDEX_FIRST_PERSON,
                VALID_EMERGENCY_CONTACT).execute(model);

        Person expectedPerson = createExpectedPerson();

        assertEquals(String.format(AddEmergencyContactCommand.MESSAGE_SUCCESS,
                        Messages.format(expectedPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(expectedPerson, model.getFilteredPersonList().get(0));
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        AddEmergencyContactCommand addEmergencyContactCommand = new AddEmergencyContactCommand(outOfBoundIndex,
                VALID_EMERGENCY_CONTACT);

        assertCommandFailure(addEmergencyContactCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void createEditedPerson_test() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person actualPerson = AddEmergencyContactCommand.createEditedPerson(
                model.getFilteredPersonList().get(0), VALID_EMERGENCY_CONTACT);
        Person expectedPerson = createExpectedPerson();
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void duplicateEmergencyContact_test() {
        EmergencyContact invalidEmergencyContact =
                model.getFilteredPersonList().get(0).getEmergencyContact(Index.fromOneBased(1));
        AddEmergencyContactCommand addEmergencyContactCommand = new AddEmergencyContactCommand(INDEX_FIRST_PERSON,
                invalidEmergencyContact);
        assertCommandFailure(addEmergencyContactCommand, model, AddEmergencyContactCommand.MESSAGE_DUPLICATE_EMERGENCY_CONTACT);
    }

    @Test
    public void equals() {
        AddEmergencyContactCommand addAliceCommand =
                new AddEmergencyContactCommand(INDEX_FIRST_PERSON, VALID_EMERGENCY_CONTACT);
        AddEmergencyContactCommand addBobCommand = new AddEmergencyContactCommand(INDEX_FIRST_PERSON,
                VALID_EMERGENCY_CONTACT_BOB);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddEmergencyContactCommand addAliceCommandCopy = new AddEmergencyContactCommand(INDEX_FIRST_PERSON,
                VALID_EMERGENCY_CONTACT);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddEmergencyContactCommand addCommand = new AddEmergencyContactCommand(INDEX_FIRST_PERSON,
                VALID_EMERGENCY_CONTACT);
        String expected = AddEmergencyContactCommand.class.getCanonicalName() + "{toAdd="
                + VALID_EMERGENCY_CONTACT + "}";
        assertEquals(expected, addCommand.toString());
    }


}
