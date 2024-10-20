package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class PatientTest {

    private Patient testPatient1;
    private Patient testPatient2;

    @BeforeEach
    public void setUp() {
        // Clear the static list of patients before each test
        Patient.getPatients().clear();

        testPatient1 = new Patient(
                new Name("Alice"), "patient", new Phone("12345678"), new Email("alice@example.com"),
                new Address("123 Wonderland"), new Remark("Diabetic"), Set.of(new Tag("Chronic")));

        testPatient2 = new Patient(
                new Name("Bob"), "patient", new Phone("87654321"), new Email("bob@example.com"),
                new Address("456 Nowhere Street"), new Remark("Healthy"), Set.of(new Tag("General")));
    }

    @Test
    public void constructor_addsPatientToList() {
        ArrayList<Patient> patients = Patient.getPatients();
        assertEquals(2, patients.size());
        assertEquals(testPatient1, patients.get(0));
        assertEquals(testPatient2, patients.get(1));
    }

    @Test
    public void getPatientWithId_validId_returnsCorrectPatient() {
        Id testPatient1Id = testPatient1.getId();
        Patient foundPatient = Patient.getPatientWithId(testPatient1Id);
        assertNotNull(foundPatient);
        assertEquals(testPatient1, foundPatient);

        Id testPatient2Id = testPatient2.getId();
        Patient foundPatient2 = Patient.getPatientWithId(testPatient2Id);
        assertNotNull(foundPatient2);
        assertEquals(testPatient2, foundPatient2);
    }

    @Test
    public void getPatientWithId_validStringId_returnsCorrectPatient() {
        int testPatient1Id = testPatient1.getId().getIdValue();
        int testPatient2Id = testPatient2.getId().getIdValue();

        Patient foundPatient = Patient.getPatientWithId(Integer.toString(testPatient1Id));
        assertNotNull(foundPatient);
        assertEquals(testPatient1, foundPatient);

        Patient foundPatient2 = Patient.getPatientWithId(Integer.toString(testPatient2Id));
        assertNotNull(foundPatient2);
        assertEquals(testPatient2, foundPatient2);
    }

    @Test
    public void getPatientWithId_invalidId_returnsNull() {
        Patient foundPatient = Patient.getPatientWithId(new Id(Person.class));
        assertNull(foundPatient);
    }

    @Test
    public void getPatientWithId_invalidStringId_returnsNull() {
        Patient foundPatient = Patient.getPatientWithId("Invalid");
        assertNull(foundPatient);
    }

}
