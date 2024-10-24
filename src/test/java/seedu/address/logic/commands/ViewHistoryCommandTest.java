package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.ViewHistoryCommand.MESSAGE_NO_HISTORY_FOUND;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
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
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ViewHistoryCommandTest {

    private final LocalDateTime appointmentTime = LocalDateTime.of(2024, 12, 31, 12, 0);
    private final String appointmentRemark = "Follow-up check";

    //    @Test
    //    public void execute_viewHistoryWithTime_success() throws Exception {
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
    //        validPatient.addAppointment(appointmentTime, validPatient.getId(), validDoctor.getId(), appointmentRemark);
    //
    //        // Execute the ViewHistoryCommand
    //        ViewHistoryCommand viewHistoryCommand = new ViewHistoryCommand(validPatient.getId(), appointmentTime);
    //        CommandResult commandResult = viewHistoryCommand.execute(modelStub);
    //
    //        // Validate that the appointment was retrieved correctly
    //        String expectedMessage = String.format("DateTime: %s Appointment: %s (patient id) with %s (doctor id). "
    //                        + "Remarks: %s", appointmentTime.format(formatter), validPatient.getId(),
    //                        validDoctor.getId(), appointmentRemark);
    //        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    //    } TODO

    //    @Test
    //    public void execute_viewHistoryWithoutTime_success() throws Exception {
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
    //        // Add multiple appointments to the patient
    //        LocalDateTime firstAppointmentTime = LocalDateTime.of(2024, 12, 31, 12, 0);
    //        LocalDateTime secondAppointmentTime = LocalDateTime.of(2024, 11, 30, 15, 0);
    //
    //        validPatient.addAppointment(firstAppointmentTime, validPatient.getId(),
    //                validDoctor.getId(), "First appointment");
    //        validPatient.addAppointment(secondAppointmentTime, validPatient.getId(),
    //                validDoctor.getId(), "Second appointment");
    //
    //        // Execute the ViewHistoryCommand without specifying a time
    //        ViewHistoryCommand viewHistoryCommand = new ViewHistoryCommand(validPatient.getId());
    //        CommandResult commandResult = viewHistoryCommand.execute(modelStub);
    //
    //        // Validate that both appointments are retrieved correctly
    //        String expectedMessage = String.format("DateTime: %s Appointment: %s (patient id) "
    //                        + "with %s (doctor id). Remarks: First appointment\n"
    //                        + "DateTime: %s Appointment: %s (patient id) "
    //                        + "with %s (doctor id). Remarks: Second appointment\n",
    //                firstAppointmentTime.format(formatter), validPatient.getId(), validDoctor.getId(),
    //                secondAppointmentTime.format(formatter), validPatient.getId(), validDoctor.getId());
    //
    //        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    //    } TODO

    //    @Test
    //    public void execute_noHistoryFound_throwsCommandException() {
    //        // Create a ModelStub that contains a patient without any medical history
    //        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();
    //        Person validPatient = new PersonBuilder().buildPatient();
    //        modelStub.addPersonToList(validPatient);
    //
    //        // Execute the ViewHistoryCommand for a patient with no history
    //        ViewHistoryCommand viewHistoryCommand = new ViewHistoryCommand(validPatient.getId());
    //
    //        // Expect CommandException with the no history found message
    //        assertThrows(CommandException.class, MESSAGE_NO_HISTORY_FOUND, () ->
    //                viewHistoryCommand.execute(modelStub));
    //    } TODO


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
            return null; // TODO
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
    private class ModelStubWithAppointment extends ViewHistoryCommandTest.ModelStub {
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

        //        @Override
        //        public Person getFilteredPatientById(ObservableList<Person> allPersons, Id id) {
        //            return patient.getId().equals(id) ? patient : null;
        //        }
        //
        //        @Override
        //        public Person getFilteredDoctorById(ObservableList<Person> allPersons, Id id) {
        //            return doctor.getId().equals(id) ? doctor : null;
        //        }
    }

    /**
     * A Model stub that always accept the appointment being added.
     */
    public class ModelStubAcceptingAppointmentAdded extends ViewHistoryCommandTest.ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        private final ArrayList<Person> personList = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
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
