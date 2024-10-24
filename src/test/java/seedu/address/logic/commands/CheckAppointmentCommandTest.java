package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CheckAppointmentCommand.MESSAGE_NO_APPOINTMENT_FOUND;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Id;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class CheckAppointmentCommandTest {


    private final LocalDateTime appointmentTime1 = LocalDateTime.of(2024, 12, 31, 12, 0);
    private final LocalDateTime appointmentTime2 = LocalDateTime.of(2024, 12, 31, 13, 0);
    private final LocalDate appointmentDate = LocalDate.of(2024, 12, 31);
    private final String appointmentRemark = "Follow-up check";

    @Test
    public void execute_validDoctorWithAppointment_returnsAppointments() throws Exception {
        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();
        modelStub.clearList();

        Person validPatient = new PersonBuilder().buildPatient();
        Person validDoctor = new PersonBuilder().buildDoctor();
        modelStub.addPerson(validDoctor);
        modelStub.addPerson(validPatient);

        LocalDate appointmentDate = LocalDate.of(2024, 12, 31);
        validPatient.addAppointment(appointmentTime1, validPatient.getId(), validDoctor.getId(), appointmentRemark);

        CheckAppointmentCommand command = new CheckAppointmentCommand(validDoctor.getId(), appointmentDate);
        CommandResult result = command.execute(modelStub);

        String expectedMessage = String.format(MESSAGE_APPOINTMENT_FOUND, doctor.getName(),
                validDoctor.getStringAppointmentsForDay(appointmentDate));

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_doctorWithNoAppointments_throwsCommandException() {
        ModelStubWithDoctorAndAppointments modelStub = new ModelStubWithDoctorAndAppointments();

        // Create a doctor without appointments
        Person doctor = new PersonBuilder().withName("Dr. John Doe").buildDoctor();
        modelStub.addDoctor(doctor);

        CheckAppointmentCommand command = new CheckAppointmentCommand(doctor.getId(), LocalDate.of(2024, 12, 31));

        CommandException thrown = org.junit.jupiter.api.Assertions.assertThrows(CommandException.class, () -> {
            command.execute(modelStub);
        });

        assertEquals(String.format(MESSAGE_NO_APPOINTMENT_FOUND, doctor.getName()), thrown.getMessage());
    }



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
        public ObservableList<Person> getFilteredPersonList() {
            return null;
        }

        @Override
        public ObservableList<Person> getFilteredPersonById(int id) {
            return null;
        }

        @Override
        public Person getFilteredPersonById(ObservableList<Person> allPersons, int id) {
            return null; // TODO?
        }

        @Override
        public Person getFilteredPatientById(ObservableList<Person> allPersons, int id) {
            return null;
        }

        @Override
        public Person getFilteredDoctorById(ObservableList<Person> allPersons, int id) {
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
        }
    }
    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithAppointment extends CheckAppointmentCommandTest.ModelStub {
        private final Person patient;
        private final Person doctor;

        ModelStubWithAppointment(Person patient, Person doctor) {
            requireNonNull(patient);
            requireNonNull(doctor);
            this.patient = patient;
            this.doctor = doctor;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return person.isSamePerson(patient) || person.isSamePerson(doctor);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableArrayList(patient, doctor);
        }

        @Override
        public Person getFilteredPatientById(ObservableList<Person> allPersons, int id) {
            return patient.getId() == (id) ? patient : null;
        }

        @Override
        public Person getFilteredDoctorById(ObservableList<Person> allPersons, int id) {
            return doctor.getId() == (id) ? doctor : null;
        }
    }

    /**
     * A Model stub that always accept the appointment being added.
     */
    public class ModelStubAcceptingAppointmentAdded extends CheckAppointmentCommandTest.ModelStub {

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

        public void clearList() {
            personList.clear();
        }

        @Override
        public Person getFilteredPatientById(ObservableList<Person> allPersons, int id) {
            // Search for a patient with the specified ID
            for (Person person : allPersons) {
                if (person.getId() == (id) && person instanceof Person) {
                    return person;
                }
            }
            return null;
        }

        @Override
        public Person getFilteredDoctorById(ObservableList<Person> allPersons, int id) {
            // Search for a doctor with the specified ID
            for (Person person : allPersons) {
                if (person.getId() == (id) && person instanceof Person) {
                    return person;
                }
            }
            return null;
        }
        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableArrayList(personList);
        }
        public void addPersonToList(Person person) {
            personList.add(person);
        }
    }

}

