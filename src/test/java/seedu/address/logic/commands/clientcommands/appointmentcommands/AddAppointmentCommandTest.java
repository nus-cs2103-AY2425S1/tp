package seedu.address.logic.commands.clientcommands.appointmentcommands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddAppointmentCommandTest {

    private static final String VALID_DATE = "20-12-24";
    private static final String VALID_FROM = "0800";
    private static final String VALID_TO = "1000";

    private final Appointment validAppointment = new Appointment(
            new Date(VALID_DATE),
            new From(VALID_FROM),
            new To(VALID_TO)
    );

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        // Test null index
        assertThrows(NullPointerException.class, () -> new AddAppointmentCommand(null, validAppointment));
    }

    @Test
    public void constructor_nullAppointment_throwsNullPointerException() {
        // Test null appointment
        assertThrows(NullPointerException.class, () -> new AddAppointmentCommand(ALICE.getName(), null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // Arrange
        ModelStubWithPerson modelStub = new ModelStubWithPerson(new PersonBuilder().buildBuyer());
        Name invalidName = ALICE.getName();

        AddAppointmentCommand command = new AddAppointmentCommand(invalidName, validAppointment);

        // Act & Assert
        assertThrows(CommandException.class, () -> command.execute(modelStub),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validSellerIndex_addAppointmentSuccess() throws Exception {
        // Arrange
        Person personToEdit = ALICE;
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personToEdit);

        AddAppointmentCommand command = new AddAppointmentCommand(ALICE.getName(), validAppointment);

        // Act
        CommandResult result = command.execute(modelStub);

        // Assert
        Person editedPerson = new PersonBuilder(personToEdit).withAppointment(VALID_DATE, VALID_FROM, VALID_TO)
                .buildBuyer();
        assertEquals(String.format(AddAppointmentCommand.MESSAGE_ADD_APPOINTMENT_SUCCESS,
                        editedPerson.getName(), validAppointment),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_validBuyerIndex_addAppointmentSuccess() throws Exception {
        // Arrange
        Person personToEdit = DANIEL;
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personToEdit);

        AddAppointmentCommand command = new AddAppointmentCommand(DANIEL.getName(), validAppointment);

        // Act
        CommandResult result = command.execute(modelStub);

        // Assert
        Person editedPerson = new PersonBuilder(personToEdit).withAppointment(VALID_DATE, VALID_FROM, VALID_TO)
                .buildBuyer();
        assertEquals(String.format(AddAppointmentCommand.MESSAGE_ADD_APPOINTMENT_SUCCESS,
                        editedPerson.getName(), validAppointment),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_validIndex_updatesPersonWithAppointment() throws Exception {
        // Arrange
        Person personToEdit = ALICE;
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personToEdit);

        AddAppointmentCommand command = new AddAppointmentCommand(ALICE.getName(), validAppointment);

        // Act
        command.execute(modelStub);

        // Assert
        Person editedPerson = new PersonBuilder(personToEdit).withAppointment(VALID_DATE, VALID_FROM, VALID_TO)
                .buildBuyer();
        assertEquals(editedPerson.getAppointment(), validAppointment);
    }
    @Test
    public void equals() {
        AddAppointmentCommand firstAddAppointmentCommand =
                new AddAppointmentCommand(ALICE.getName(), validAppointment);
        AddAppointmentCommand secondAddAppointmentCommand =
                new AddAppointmentCommand(BENSON.getName(), validAppointment);

        // same object -> returns true
        assertTrue(firstAddAppointmentCommand.equals(firstAddAppointmentCommand));

        // same values -> returns true
        AddAppointmentCommand firstAddAppointmentCommandCopy =
                new AddAppointmentCommand(ALICE.getName(), validAppointment);
        assertTrue(firstAddAppointmentCommand.equals(firstAddAppointmentCommandCopy));

        // different types -> returns false
        assertFalse(firstAddAppointmentCommand.equals(1));

        // null -> returns false
        assertFalse(firstAddAppointmentCommand.equals(null));

        // different person -> returns false
        assertFalse(firstAddAppointmentCommand.equals(secondAddAppointmentCommand));
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final List<Person> persons = new ArrayList<>();

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.persons.add(person);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableList(persons);
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            int index = persons.indexOf(target);
            if (index == -1) {
                throw new AssertionError("Target person not found in list.");
            }
            persons.set(index, editedPerson);
        }

        @Override
        public boolean hasPersonOfName(Name name) {
            return this.persons.stream()
                    .anyMatch(person -> person.getName().equals(name));
        }

        @Override
        public Person getPersonByName(Name name) {
            return this.persons.stream()
                    .filter(person -> person.getName().equals(name))
                    .findFirst().orElse(null);
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            // Stub that doesn't filter persons but is needed to pass the method call
        }
    }

}
