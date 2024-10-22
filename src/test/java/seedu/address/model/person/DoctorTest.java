package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class DoctorTest {

    private Doctor testDoctor1;
    private Doctor testDoctor2;

    @BeforeEach
    public void setUp() {
        // Clear the static list of patients before each test
        Doctor.getDoctors().clear();

        testDoctor1 = new Doctor(
                new Name("Alice"), "doctor", new Phone("12345678"), new Email("alice@example.com"),
                new Address("123 Wonderland"), new Remark("Diabetic"), Set.of(new Tag("Chronic")));

        testDoctor2 = new Doctor(
                new Name("Bob"), "doctor", new Phone("87654321"), new Email("bob@example.com"),
                new Address("456 Nowhere Street"), new Remark("Healthy"), Set.of(new Tag("General")));
    }

    @Test
    public void constructor_addsDoctorToList() {
        ArrayList<Doctor> doctors = Doctor.getDoctors();
        assertEquals(2, doctors.size());
        assertEquals(testDoctor1, doctors.get(0));
        assertEquals(testDoctor2, doctors.get(1));
    }

    @Test
    public void getDoctorWithId_validId_returnsCorrectDoctor() {
        Id testDoctor1Id = testDoctor1.getId();
        Doctor foundDoctor = Doctor.getDoctorWithId(testDoctor1Id);
        assertNotNull(foundDoctor);
        assertEquals(testDoctor1, foundDoctor);

        Id testDoctor2Id = testDoctor2.getId();
        Doctor foundDoctor2 = Doctor.getDoctorWithId(testDoctor2Id);
        assertNotNull(foundDoctor2);
        assertEquals(testDoctor2, foundDoctor2);
    }

    @Test
    public void getDoctorWithId_validStringId_returnsCorrectDoctor() {
        int testDoctor1Id = testDoctor1.getId().getIdValue();
        int testDoctor2Id = testDoctor2.getId().getIdValue();

        Doctor foundDoctor = Doctor.getDoctorWithId(Integer.toString(testDoctor1Id));
        assertNotNull(foundDoctor);
        assertEquals(testDoctor1, foundDoctor);

        Doctor foundDoctor2 = Doctor.getDoctorWithId(Integer.toString(testDoctor2Id));
        assertNotNull(foundDoctor2);
        assertEquals(testDoctor2, foundDoctor2);
    }

    @Test
    public void getDoctorWithId_invalidId_returnsNull() {
        Doctor foundDoctor = Doctor.getDoctorWithId(new Id(Person.class));
        assertNull(foundDoctor);
    }

    @Test
    public void getDoctorWithId_invalidStringId_returnsNull() {
        Doctor foundDoctor = Doctor.getDoctorWithId("Invalid");
        assertNull(foundDoctor);
    }
}
