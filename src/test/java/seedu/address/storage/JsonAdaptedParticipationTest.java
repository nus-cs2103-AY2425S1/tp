package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedParticipation.MISSING_ADDRESSBOOK_OBJECT_MESSAGE_FORMAT;
import static seedu.address.storage.JsonAdaptedParticipation.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.MATH;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

public class JsonAdaptedParticipationTest {

    @Test
    public void toModelType_validParticipationDetails_returnsParticipation() throws Exception {
        Attendance attendance = new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT));
        Participation participation = new Participation(BENSON, MATH, Collections.singletonList(attendance));
        JsonAdaptedParticipation jsonAdaptedParticipation = new JsonAdaptedParticipation(participation);
        AddressBookStubWithPersonAndTutorial model = new AddressBookStubWithPersonAndTutorial(BENSON, MATH);
        assertEquals(jsonAdaptedParticipation.toModelType(model), participation);
    }

    @Test
    public void toModelType_missingPerson_throwsIllegalValueException() {
        Attendance attendance = new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT));
        Participation participation = new Participation(BENSON, MATH, Collections.singletonList(attendance));
        JsonAdaptedParticipation jsonAdaptedParticipation = new JsonAdaptedParticipation(participation);
        AddressBookStubWithPersonAndTutorial model = new AddressBookStubWithPersonAndTutorial(null, MATH);
        String expectedMessage = String.format(
                MISSING_ADDRESSBOOK_OBJECT_MESSAGE_FORMAT, Person.class.getSimpleName());

        // Using an Anonymous Class to fit the assertThrows method descriptor
        Executable toModelTypeMethod = () -> jsonAdaptedParticipation.toModelType(model);
        assertThrows(IllegalValueException.class, expectedMessage, toModelTypeMethod);
    }

    @Test
    public void toModelType_missingTutorial_throwsIllegalValueException() {
        Attendance attendance = new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT));
        Participation participation = new Participation(BENSON, MATH, Collections.singletonList(attendance));
        JsonAdaptedParticipation jsonAdaptedParticipation = new JsonAdaptedParticipation(participation);
        AddressBookStubWithPersonAndTutorial model = new AddressBookStubWithPersonAndTutorial(BENSON, null);
        String expectedMessage = String.format(
                MISSING_ADDRESSBOOK_OBJECT_MESSAGE_FORMAT, Tutorial.class.getSimpleName());

        // Using an Anonymous Class to fit the assertThrows method descriptor
        Executable toModelTypeMethod = () -> jsonAdaptedParticipation.toModelType(model);
        assertThrows(IllegalValueException.class, expectedMessage, toModelTypeMethod);
    }

    @Test
    public void toModelType_nullPersonName_throwsIllegalValueException() {
        JsonAdaptedParticipation jsonAdaptedParticipation =
                new JsonAdaptedParticipation(null, BENSON.getPhoneValue(), "Math", new ArrayList<>());
        AddressBookStubWithPersonAndTutorial model = new AddressBookStubWithPersonAndTutorial(BENSON, MATH);
        String expectedMessage = String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName());

        // Using an Anonymous Class to fit the assertThrows method descriptor
        Executable toModelTypeMethod = () -> jsonAdaptedParticipation.toModelType(model);
        assertThrows(IllegalValueException.class, expectedMessage, toModelTypeMethod);
    }

    @Test
    public void toModelType_nullPersonPhone_throwsIllegalValueException() {
        JsonAdaptedParticipation jsonAdaptedParticipation =
                new JsonAdaptedParticipation(BENSON.getName().fullName, null, "Math", new ArrayList<>());
        AddressBookStubWithPersonAndTutorial model = new AddressBookStubWithPersonAndTutorial(BENSON, MATH);
        String expectedMessage = String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName());

        // Using an Anonymous Class to fit the assertThrows method descriptor
        Executable toModelTypeMethod = () -> jsonAdaptedParticipation.toModelType(model);
        assertThrows(IllegalValueException.class, expectedMessage, toModelTypeMethod);
    }

    @Test
    public void toModelType_nullTutorial_throwsIllegalValueException() {
        JsonAdaptedParticipation jsonAdaptedParticipation =
                new JsonAdaptedParticipation(BENSON.getName().fullName, BENSON.getPhoneValue(),
                        null, new ArrayList<>());
        AddressBookStubWithPersonAndTutorial model = new AddressBookStubWithPersonAndTutorial(BENSON, MATH);
        String expectedMessage = String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Tutorial.class.getSimpleName());

        // Using an Anonymous Class to fit the assertThrows method descriptor
        Executable toModelTypeMethod = () -> jsonAdaptedParticipation.toModelType(model);
        assertThrows(IllegalValueException.class, expectedMessage, toModelTypeMethod);
    }

    // TODO fix this portion.
    // Not exactly correct to use it like this, since AddressBook is not an Interface
    private class AddressBookStubWithPersonAndTutorial extends AddressBook {
        private final Person person;
        private final Tutorial tutorial;

        AddressBookStubWithPersonAndTutorial(Person person, Tutorial tutorial) {
            this.person = person;
            this.tutorial = tutorial;
        }

        @Override
        public ObservableList<Person> getPersonList() {
            if (person == null) {
                return FXCollections.observableList(Collections.EMPTY_LIST);
            }
            return FXCollections.observableList(Collections.singletonList(person));
        }
        @Override
        public ObservableList<Tutorial> getTutorialList() {
            if (tutorial == null) {
                return FXCollections.observableList(Collections.EMPTY_LIST);
            }
            return FXCollections.observableList(Collections.singletonList(tutorial));
        }
    }
}
