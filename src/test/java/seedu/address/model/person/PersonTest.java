package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_CHRIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSES_CHRIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSES_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_CHRIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_CHRIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CHRIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_CHRIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_CHRIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ATHLETE;
import static seedu.address.model.person.Student.STUDENT_TYPE;
import static seedu.address.model.person.Teacher.TEACHER_TYPE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.STUDENT_MICHAEL;
import static seedu.address.testutil.TypicalPersons.TEACHER_CHRIS;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.InvalidPersonTypeException;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

public class PersonTest {

    public class PersonStub extends Person {
        public PersonStub(Name name, Gender gender, Phone phone, Email email, Address address, Set<Tag> tags,
                          Set<Subject> subjects, Set<String> classes) {
            super(name, gender, phone, email, address, tags, subjects, classes);
        }

        @Override
        public Person withIncrementedAttendance() {
            return null;
        }

        @Override
        public Person withDecrementedAttendance() throws CommandException {
            return null;
        }

        @Override
        public Person withResetAttendance() {
            return null;
        }
    }
    private final PersonStub personStubAmy = new PersonStub(new Name(VALID_NAME_AMY),
        new Gender(VALID_GENDER_AMY), new Phone(VALID_PHONE_AMY), new Email(VALID_EMAIL_AMY),
        new Address(VALID_ADDRESS_AMY), new HashSet<>(), new HashSet<>(), new HashSet<>());

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {

        assertThrows(UnsupportedOperationException.class, () -> personStubAmy.getTags().remove(0));
    }

    @Test
    public void isSamePerson_throwsInvalidPersonTypeException() {
        assertThrows(InvalidPersonTypeException.class, () -> personStubAmy.isSamePerson(personStubAmy));

    }

    @Test
    public void equals() {
        // same values -> returns true
        PersonStub personStubAmyCopy = new PersonStub(new Name(VALID_NAME_AMY), new Gender(VALID_GENDER_AMY),
            new Phone(VALID_PHONE_AMY), new Email(VALID_EMAIL_AMY), new Address(VALID_ADDRESS_AMY),
            new HashSet<>(), new HashSet<>(), new HashSet<>());
        assertTrue(personStubAmy.equals(personStubAmyCopy));

        // same object -> returns true
        assertTrue(personStubAmy.equals(personStubAmy));

        // null -> returns false
        assertFalse(personStubAmy.equals(null));

        // different type -> returns false
        assertFalse(personStubAmy.equals(5));

        // different person -> returns false
        PersonStub personStubBob = new PersonStub(new Name(VALID_NAME_BOB), new Gender(VALID_GENDER_BOB),
            new Phone(VALID_PHONE_BOB), new Email(VALID_EMAIL_BOB), new Address(VALID_ADDRESS_BOB),
            new HashSet<>(), new HashSet<>(), new HashSet<>());
        assertFalse(personStubAmy.equals(personStubBob));

        // different name -> returns false
        PersonStub personStubEditedAmy = new PersonStub(new Name(VALID_NAME_MICHAEL), new Gender(VALID_GENDER_AMY),
            new Phone(VALID_PHONE_AMY), new Email(VALID_EMAIL_AMY), new Address(VALID_ADDRESS_AMY),
            new HashSet<>(), new HashSet<>(), new HashSet<>());
        assertFalse(personStubAmy.equals(personStubEditedAmy));

        // different gender -> returns false
        personStubEditedAmy = new PersonStub(new Name(VALID_NAME_AMY), new Gender(VALID_GENDER_BOB),
            new Phone(VALID_PHONE_AMY), new Email(VALID_EMAIL_AMY), new Address(VALID_ADDRESS_AMY),
            new HashSet<>(), new HashSet<>(), new HashSet<>());
        assertFalse(personStubAmy.equals(personStubEditedAmy));

        // different phone -> returns false
        personStubEditedAmy = new PersonStub(new Name(VALID_NAME_AMY), new Gender(VALID_GENDER_AMY),
            new Phone(VALID_PHONE_MICHAEL), new Email(VALID_EMAIL_AMY), new Address(VALID_ADDRESS_AMY),
            new HashSet<>(), new HashSet<>(), new HashSet<>());
        assertFalse(personStubAmy.equals(personStubEditedAmy));

        // different email -> returns false
        personStubEditedAmy = new PersonStub(new Name(VALID_NAME_AMY), new Gender(VALID_GENDER_AMY),
            new Phone(VALID_PHONE_AMY), new Email(VALID_EMAIL_MICHAEL), new Address(VALID_ADDRESS_AMY),
            new HashSet<>(), new HashSet<>(), new HashSet<>());
        assertFalse(personStubAmy.equals(personStubEditedAmy));

        // different address -> returns false
        personStubEditedAmy = new PersonStub(new Name(VALID_NAME_AMY), new Gender(VALID_GENDER_AMY),
            new Phone(VALID_PHONE_AMY), new Email(VALID_EMAIL_AMY), new Address(VALID_ADDRESS_MICHAEL),
            new HashSet<>(), new HashSet<>(), new HashSet<>());
        assertFalse(personStubAmy.equals(personStubEditedAmy));

        // different tags -> returns false
        Set<Tag> differentTags = new HashSet<>();
        differentTags.add(new Tag(VALID_TAG_ATHLETE));
        personStubEditedAmy = new PersonStub(new Name(VALID_NAME_AMY), new Gender(VALID_GENDER_AMY),
            new Phone(VALID_PHONE_AMY), new Email(VALID_EMAIL_AMY), new Address(VALID_ADDRESS_AMY),
            differentTags, new HashSet<>(), new HashSet<>());
        assertFalse(personStubAmy.equals(personStubEditedAmy));

        // different subjects -> returns false
        Set<Subject> differentSubjects = new HashSet<>();
        differentSubjects.add(new Subject(VALID_SUBJECT_BOB));
        personStubEditedAmy = new PersonStub(new Name(VALID_NAME_AMY), new Gender(VALID_GENDER_AMY),
            new Phone(VALID_PHONE_AMY), new Email(VALID_EMAIL_AMY), new Address(VALID_ADDRESS_AMY),
            new HashSet<>(), differentSubjects, new HashSet<>());
        assertFalse(personStubAmy.equals(personStubEditedAmy));

        // different classes -> returns false
        Set<String> differentClasses = new HashSet<>();
        differentClasses.add(VALID_CLASSES_BOB);
        personStubEditedAmy = new PersonStub(new Name(VALID_NAME_AMY), new Gender(VALID_GENDER_AMY),
            new Phone(VALID_PHONE_AMY), new Email(VALID_EMAIL_AMY), new Address(VALID_ADDRESS_AMY),
            new HashSet<>(), new HashSet<>(), differentClasses);
        assertFalse(personStubAmy.equals(personStubEditedAmy));
    }

    @Test
    public void toStringMethod() {
        String expectedTeacher = PersonStub.class.getCanonicalName() + "{name=" + VALID_NAME_AMY
            + ", gender=" + VALID_GENDER_AMY + ", phone=" + VALID_PHONE_AMY
            + ", email=" + VALID_EMAIL_AMY
            + ", address=" + VALID_ADDRESS_AMY + ", tags=[]"
            + ", subject=[]"
            + ", classes=[]" + "}";
        assertEquals(expectedTeacher, personStubAmy.toString());
    }

    @Test
    public void createPerson_createsPerson_success() {
        Person createdChris = Person.createPerson(TEACHER_TYPE, new Name(VALID_NAME_CHRIS),
            new Gender(VALID_GENDER_CHRIS), new Phone(VALID_PHONE_CHRIS), new Email(VALID_EMAIL_CHRIS),
            new Address(VALID_ADDRESS_CHRIS), SampleDataUtil.getTagSet("friends"),
            SampleDataUtil.getSubjectSet(VALID_SUBJECT_CHRIS),
            SampleDataUtil.getClassSet(VALID_CLASSES_CHRIS), null);
        assertEquals(TEACHER_CHRIS, createdChris);

        Person createdMichael = Person.createPerson(STUDENT_TYPE, new Name(VALID_NAME_MICHAEL),
            new Gender(VALID_GENDER_MICHAEL), new Phone(VALID_PHONE_MICHAEL), new Email(VALID_EMAIL_MICHAEL),
            new Address(VALID_ADDRESS_MICHAEL), SampleDataUtil.getTagSet(),
            SampleDataUtil.getSubjectSet(VALID_SUBJECT_MICHAEL),
            SampleDataUtil.getClassSet(VALID_CLASSES_MICHAEL), new DaysAttended(VALID_ATTENDANCE_MICHAEL));
        assertEquals(STUDENT_MICHAEL, createdMichael);

        assertThrows(InvalidPersonTypeException.class, () -> Person.createPerson("INVALID_TYPE",
            new Name(VALID_NAME_CHRIS),
            new Gender(VALID_GENDER_CHRIS), new Phone(VALID_PHONE_CHRIS), new Email(VALID_EMAIL_CHRIS),
            new Address(VALID_ADDRESS_CHRIS), SampleDataUtil.getTagSet("friends"),
            SampleDataUtil.getSubjectSet(VALID_SUBJECT_CHRIS),
            SampleDataUtil.getClassSet(VALID_CLASSES_CHRIS), null));
    }

    @Test
    public void getType_returnsType_throwsInvalidPersonTypeException() {
        assertThrows(InvalidPersonTypeException.class, () -> personStubAmy.getType());
    }

    @Test
    public void getDaysAttended_returnsNull() {
        assertEquals(null, personStubAmy.getDaysAttended());
    }
}
