package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_ALICE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.getTypicalClinicConnectSystem;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.testutil.PatientBuilder;

public class ClinicConnectSystemTest {

    private final ClinicConnectSystem clinicConnectSystem = new ClinicConnectSystem();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), clinicConnectSystem.getPatientList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clinicConnectSystem.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyClinicConnectSystem_replacesData() {
        ClinicConnectSystem newData = getTypicalClinicConnectSystem();
        clinicConnectSystem.resetData(newData);
        assertEquals(newData, clinicConnectSystem);
    }

    @Test
    public void resetData_withDuplicatePatients_throwsDuplicatePatientException() {
        // Two patients with the same identity fields
        Patient editedAlice = new PatientBuilder(ALICE).withNric(VALID_NRIC_ALICE).build();
        List<Patient> newPatients = Arrays.asList(ALICE, editedAlice);
        ClinicConnectSystemStub newData = new ClinicConnectSystemStub(newPatients);

        assertThrows(DuplicatePatientException.class, () -> clinicConnectSystem.resetData(newData));
    }

    @Test
    public void hasPatient_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clinicConnectSystem.hasPatient(null));
    }

    @Test
    public void hasPatient_patientNotInClinicConnectSystem_returnsFalse() {
        assertFalse(clinicConnectSystem.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_patientInClinicConnectSystem_returnsTrue() {
        clinicConnectSystem.addPatient(ALICE);
        assertTrue(clinicConnectSystem.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_patientWithSameIdentityFieldsInClinicConnectSystem_returnsTrue() {
        clinicConnectSystem.addPatient(ALICE);
        Patient editedAlice = new PatientBuilder(ALICE).withNric(VALID_NRIC_ALICE).build();
        assertTrue(clinicConnectSystem.hasPatient(editedAlice));
    }

    @Test
    public void getPatientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> clinicConnectSystem.getPatientList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = ClinicConnectSystem.class.getCanonicalName() + "{patients=" + clinicConnectSystem.getPatientList() + "}";
        assertEquals(expected, clinicConnectSystem.toString());
    }

    /**
     * A stub ReadOnlyClinicConnectSystem whose patients list can violate interface constraints.
     */
    private static class ClinicConnectSystemStub implements ReadOnlyClinicConnectSystem {
        private final ObservableList<Patient> patients = FXCollections.observableArrayList();

        ClinicConnectSystemStub(Collection<Patient> patients) {
            this.patients.setAll(patients);
        }

        @Override
        public ObservableList<Patient> getPatientList() {
            return patients;
        }
    }

}
