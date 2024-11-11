package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DeleteAppointmentCommandTest {

    private static final int VALID_PATIENT_ID = 1234;
    private static final int VALID_DOCTOR_ID = 5678;
    private static final LocalDateTime VALID_APPOINTMENT_TIME = LocalDateTime.of(2024, 12, 31, 15, 23);
    private static final String VALID_REMARK = "first appointment";
    private static final int NON_EXISTENT_PATIENT_ID = 9999;
    private static final int NON_EXISTENT_DOCTOR_ID = 8888;

    @Test
    public void execute_validInputs_success() throws Exception {
        ModelStubWithAppointment modelStub = new ModelStubWithAppointment();
        Person validPatient = new PersonBuilder().buildPatientWithChosenIdAndAppointment(VALID_PATIENT_ID,
                VALID_DOCTOR_ID, VALID_APPOINTMENT_TIME, VALID_REMARK);
        Person validDoctor = new PersonBuilder().buildDoctorWithChosenIdAndAppointment(VALID_PATIENT_ID,
                VALID_DOCTOR_ID, VALID_APPOINTMENT_TIME, VALID_REMARK);

        modelStub.addPersonToList(validPatient);
        modelStub.addPersonToList(validDoctor);
        System.out.println(modelStub.personList);
        DeleteAppointmentCommand command = new DeleteAppointmentCommand(VALID_APPOINTMENT_TIME,
                VALID_PATIENT_ID, VALID_DOCTOR_ID);
        CommandResult result = command.execute(modelStub);

        assertEquals(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    public void execute_nullAppointmentTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new DeleteAppointmentCommand(null, VALID_PATIENT_ID, VALID_DOCTOR_ID));
    }

    @Test
    public void execute_nonExistentPatientId_throwsCommandException() {
        ModelStubWithAppointment modelStub = new ModelStubWithAppointment();
        Person validDoctor = new PersonBuilder().buildDoctorWithChosenIdAndAppointment(VALID_PATIENT_ID,
                VALID_DOCTOR_ID, VALID_APPOINTMENT_TIME, VALID_REMARK);
        modelStub.addPersonToList(validDoctor);

        DeleteAppointmentCommand command = new DeleteAppointmentCommand(VALID_APPOINTMENT_TIME,
                NON_EXISTENT_PATIENT_ID, VALID_DOCTOR_ID);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX, () ->
                command.execute(modelStub));
    }

    @Test
    public void execute_nonExistentDoctorId_throwsCommandException() {
        ModelStubWithAppointment modelStub = new ModelStubWithAppointment();
        Person validPatient = new PersonBuilder().buildPatientWithChosenIdAndAppointment(VALID_PATIENT_ID,
                VALID_DOCTOR_ID, VALID_APPOINTMENT_TIME, VALID_REMARK);
        modelStub.addPersonToList(validPatient);
        DeleteAppointmentCommand command = new DeleteAppointmentCommand(VALID_APPOINTMENT_TIME,
                VALID_PATIENT_ID, NON_EXISTENT_DOCTOR_ID);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX, () ->
                command.execute(modelStub));
    }

    // Model Stub to simulate model behavior
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getPersonRole(Person person) {
            return "PATIENT";
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ObservableList<Person> getAllPersons() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonById(int id) {
            return null;
        }

        @Override
        public Person getFilteredPersonById(ObservableList<Person> allPersons, int id) {
            return null;
        }

        @Override
        public Person getFilteredPatientById(ObservableList<Person> allPersons, int id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getFilteredDoctorById(ObservableList<Person> allPersons, int id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    private class ModelStubWithAppointment extends DeleteAppointmentCommandTest.ModelStub {
        private final ArrayList<Person> personList = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personList.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personList.add(person);
        }

        @Override
        public Person getFilteredPatientById(ObservableList<Person> allPersons, int id) {
            Person patient = null;
            for (Person person : allPersons) {
                if (person.getId() == id) {
                    patient = person;
                    break;
                }
            }
            return patient;
        }

        @Override
        public Person getFilteredDoctorById(ObservableList<Person> allPersons, int id) {
            Person doctor = null;
            for (Person person : allPersons) {
                if (person.getId() == id) {
                    doctor = person;
                    break;
                }
            }
            return doctor;
        }
        @Override
        public ObservableList<Person> getAllPersons() {
            return FXCollections.observableArrayList(personList);
        }
        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableArrayList(personList);
        }
        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
        }
        public void addPersonToList(Person person) {
            personList.add(person);
        }

    }
}
