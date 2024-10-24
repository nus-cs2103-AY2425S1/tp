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
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Id;
import seedu.address.model.person.Patient;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class CheckAppointmentCommandTest {


//    private final LocalDateTime appointmentTime1 = LocalDateTime.of(2024, 12, 31, 12, 0);
//    private final LocalDateTime appointmentTime2 = LocalDateTime.of(2024, 12, 31, 13, 0);
//    private final LocalDate appointmentDate = LocalDate.of(2024, 12, 31);
//    private final String appointmentRemark = "Follow-up check";
//
//    @Test
//    public void checkAppointment_success() throws Exception {
//        // Create a ModelStub that can accept appointments
//        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        Person validPatient = new PersonBuilder().buildPatient();
//        Person validDoctor = new PersonBuilder().buildDoctor();
//
//        // Add doctor and patient to the model
//        modelStub.addPersonToList(validPatient);
//        modelStub.addPersonToList(validDoctor);
//
//        // Add an appointment to the patient
//        validDoctor.addAppointment(appointmentTime1, validPatient.getId(), validDoctor.getId(), appointmentRemark);
//
//        // Execute the ViewHistoryCommand
//        CheckAppointmentCommand checkAppointmentCommand =
//                new CheckAppointmentCommand(validDoctor.getId(), appointmentDate);
//        CommandResult commandResult = checkAppointmentCommand.execute(modelStub);
//
//        // Validate that the appointment was retrieved correctly
//        String expectedMessage = String.format("DateTime: %s Appointment: %s (patient id) with %s (doctor id). "
//                        + "Remarks: %s" + "\n", appointmentTime1.format(formatter), validPatient.getId(),
//                validDoctor.getId(), appointmentRemark);
//        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
//    }
//
//    @Test
//    public void execute_checkMultipleValidAppointment_success() throws Exception {
//        // Create a ModelStub that accepts appointments
//        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        Person validPatient = new PersonBuilder().buildPatient();
//        Person validDoctor = new PersonBuilder().buildDoctor();
//
//        // Add patient and doctor to model
//        modelStub.addPersonToList(validPatient);
//        modelStub.addPersonToList(validDoctor);
//
//        // Add an appointment to patient
//        validPatient.addAppointment(appointmentTime1, validPatient.getId(), validDoctor.getId(), appointmentRemark);
//        validPatient.addAppointment(appointmentTime2, validPatient.getId(), validDoctor.getId(), appointmentRemark);
//
//        // Create a CheckAppointmentCommand and execute it
//        CheckAppointmentCommand checkAppointmentCommand =
//                new CheckAppointmentCommand(validDoctor.getId(), appointmentDate);
//        CommandResult commandResult = checkAppointmentCommand.execute(modelStub);
//
//        // Expected output message
//        String expectedMessage = String.format("DateTime: %s Appointment: %s (patient id) "
//                        + "with %s (doctor id). Remarks: %s\n"
//                        + "DateTime: %s Appointment: %s (patient id) "
//                        + "with %s (doctor id). Remarks: %s\n",
//                appointmentTime1.format(formatter), validPatient.getId(), validDoctor.getId(), appointmentRemark,
//                appointmentTime2.format(formatter), validPatient.getId(), validDoctor.getId(), appointmentRemark);
//        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
//    }
//
//    @Test
//    public void execute_checkNonexistentAppointment_throwsCommandException() {
//        // Create a ModelStub with no appointments
//        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();
//        Person validDoctor = new PersonBuilder().buildDoctor();
//        Person validPatient = new PersonBuilder().buildPatient();
//
//        modelStub.addPersonToList(validPatient);
//        modelStub.addPersonToList(validDoctor);
//
//        // Create a CheckAppointmentCommand for a nonexistent appointment
//        CheckAppointmentCommand checkAppointmentCommand =
//                new CheckAppointmentCommand(validDoctor.getId(), appointmentDate);
//
//        // Verify that an exception is thrown when no appointment is found
//        assertThrows(CommandException.class, String.format(MESSAGE_NO_APPOINTMENT_FOUND, validDoctor.getName()), () ->
//                checkAppointmentCommand.execute(modelStub));
//    }
//
//    private class ModelStub implements Model {
//        @Override
//        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public ReadOnlyUserPrefs getUserPrefs() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public GuiSettings getGuiSettings() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void setGuiSettings(GuiSettings guiSettings) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public Path getAddressBookFilePath() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void setAddressBookFilePath(Path addressBookFilePath) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void addPerson(Person person) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void setAddressBook(ReadOnlyAddressBook newData) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public ReadOnlyAddressBook getAddressBook() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public boolean hasPerson(Person person) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void deletePerson(Person target) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void setPerson(Person target, Person editedPerson) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public ObservableList<Person> getFilteredPersonList() {
//            return null;
//        }
//
//        @Override
//        public ObservableList<Person> getFilteredPersonById(Id id) {
//            return null;
//        }
//
//        @Override
//        public Patient getFilteredPatientById(ObservableList<Person> allPersons, Id id) {
//            return null;
//        }
//
//        @Override
//        public Doctor getFilteredDoctorById(ObservableList<Person> allPersons, Id id) {
//            return null;
//        }
//
//        @Override
//        public void updateFilteredPersonList(Predicate<Person> predicate) {
//        }
//    }
//    /**
//     * A Model stub that contains a single person.
//     */
//    private class ModelStubWithAppointment extends CheckAppointmentCommandTest.ModelStub {
//        private final Patient patient;
//        private final Doctor doctor;
//
//        ModelStubWithAppointment(Patient patient, Doctor doctor) {
//            requireNonNull(patient);
//            requireNonNull(doctor);
//            this.patient = patient;
//            this.doctor = doctor;
//        }
//
//        @Override
//        public boolean hasPerson(Person person) {
//            requireNonNull(person);
//            return person.isSamePerson(patient) || person.isSamePerson(doctor);
//        }
//
//        @Override
//        public ObservableList<Person> getFilteredPersonList() {
//            return javafx.collections.FXCollections.observableArrayList(patient, doctor);
//        }
//
//        @Override
//        public Patient getFilteredPatientById(ObservableList<Person> allPersons, Id id) {
//            return patient.getId().equals(id) ? patient : null;
//        }
//
//        @Override
//        public Doctor getFilteredDoctorById(ObservableList<Person> allPersons, Id id) {
//            return doctor.getId().equals(id) ? doctor : null;
//        }
//    }
//
//    /**
//     * A Model stub that always accept the appointment being added.
//     */
//    public class ModelStubAcceptingAppointmentAdded extends CheckAppointmentCommandTest.ModelStub {
//        final ArrayList<Person> personsAdded = new ArrayList<>();
//
//        private final ArrayList<Person> personList = new ArrayList<>();
//
//        @Override
//        public boolean hasPerson(Person person) {
//            requireNonNull(person);
//            return personsAdded.stream().anyMatch(person::isSamePerson);
//        }
//
//        @Override
//        public void addPerson(Person person) {
//            requireNonNull(person);
//            personsAdded.add(person);
//        }
//
//        @Override
//        public Patient getFilteredPatientById(ObservableList<Person> allPersons, Id id) {
//            // Search for a patient with the specified ID
//            for (Person person : allPersons) {
//                if (person.getId().equals(id) && person instanceof Patient) {
//                    return (Patient) person;
//                }
//            }
//            return null;
//        }
//
//        @Override
//        public Doctor getFilteredDoctorById(ObservableList<Person> allPersons, Id id) {
//            // Search for a doctor with the specified ID
//            for (Person person : allPersons) {
//                if (person.getId().equals(id) && person instanceof Doctor) {
//                    return (Doctor) person;
//                }
//            }
//            return null;
//        }
//        @Override
//        public ObservableList<Person> getFilteredPersonList() {
//            return javafx.collections.FXCollections.observableArrayList(personList);
//        }
//        public void addPersonToList(Person person) {
//            personList.add(person);
//        }
//    }

}

