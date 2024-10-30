package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE_P;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;

public class UniqueAppointmentListTest {
    private UniqueAppointmentList uniqueAppointmentList;
    private Appointment appointment1;
    private Appointment appointment2;

    @BeforeEach
    void setUp() {
        uniqueAppointmentList = new UniqueAppointmentList();
        appointment1 = new Appointment(new AppointmentType("Checkup"), LocalDateTime.of(2024, 1, 1, 10, 0),
                ALICE_P, new Sickness("Cold"), new Medicine("Aspirin"));
        appointment2 = new Appointment(new AppointmentType("Followup"), LocalDateTime.of(2024, 1, 2, 11, 0),
                BOB, new Sickness("Flu"), new Medicine("Tamiflu"));
    }

    @Test
    public void contains_existingAndNonExistingAppointment_returnsCorrect() {
        assertFalse(uniqueAppointmentList.containsAppointment(appointment1));
        uniqueAppointmentList.addAppointment(appointment1);
        assertTrue(uniqueAppointmentList.containsAppointment(appointment1));
    }

    @Test
    public void add_newAppointment_addsSuccessfully() {
        uniqueAppointmentList.addAppointment(appointment1);
        assertTrue(uniqueAppointmentList.containsAppointment(appointment1));
        assertThrows(DuplicateAppointmentException.class, () -> uniqueAppointmentList.addAppointment(appointment1));
    }

    @Test
    public void setAppointment_existingAppointment_updatesSuccessfully() {
        uniqueAppointmentList.addAppointment(appointment1);
        Appointment editedAppointment = new Appointment(new AppointmentType("Followup"),
                LocalDateTime.of(2024, 1, 1, 10, 0), ALICE_P,
                new Sickness("Fever"), new Medicine("Paracetamol"));
        uniqueAppointmentList.setAppointment(appointment1, editedAppointment);
        assertTrue(uniqueAppointmentList.containsAppointment(editedAppointment));
        assertFalse(uniqueAppointmentList.containsAppointment(appointment1));
    }

    @Test
    public void setAppointment_nonExistingAppointment_throwsAppointmentNotFoundException() {
        assertThrows(AppointmentNotFoundException.class, () -> {
            uniqueAppointmentList.setAppointment(appointment1, appointment2);
        });
    }

    @Test
    public void remove_existingAppointment_removesSuccessfully() {
        uniqueAppointmentList.addAppointment(appointment1);
        uniqueAppointmentList.removeAppointment(appointment1);
        assertFalse(uniqueAppointmentList.containsAppointment(appointment1));
    }

    @Test
    public void setAppointments_validList_replacesExistingList() {
        uniqueAppointmentList.setAppointments(Arrays.asList(appointment1, appointment2));
        assertTrue(uniqueAppointmentList.containsAppointment(appointment1));
        assertTrue(uniqueAppointmentList.containsAppointment(appointment2));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        uniqueAppointmentList.addAppointment(appointment1);
        uniqueAppointmentList.addAppointment(appointment2);
        List<Appointment> unmodifiableList = uniqueAppointmentList.asUnmodifiableObservableList();
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.remove(0));
    }

    @Test
    public void equals_differentAndSameAppointments_returnsCorrectEquality() {
        UniqueAppointmentList list1 = new UniqueAppointmentList();
        UniqueAppointmentList list2 = new UniqueAppointmentList();
        list1.addAppointment(appointment1);
        list2.addAppointment(appointment1);
        assertEquals(list1, list2);
        list2.addAppointment(appointment2);
        assertNotEquals(list1, list2);
    }

    @Test
    public void hashCode_differentAndSameAppointments_returnsCorrectHashCodeEquality() {
        UniqueAppointmentList list1 = new UniqueAppointmentList();
        UniqueAppointmentList list2 = new UniqueAppointmentList();
        list1.addAppointment(appointment1);
        list2.addAppointment(appointment1);
        assertEquals(list1.hashCode(), list2.hashCode());
        list2.addAppointment(appointment2);
        assertNotEquals(list1.hashCode(), list2.hashCode());
    }

    @Test
    public void toString_multipleAppointments_returnsFormattedString() {
        uniqueAppointmentList.addAppointment(appointment1);
        uniqueAppointmentList.addAppointment(appointment2);
        String expectedString = "[" + appointment1.toString() + ", " + appointment2.toString() + "]";
        assertEquals(expectedString, uniqueAppointmentList.toString());
    }

}
