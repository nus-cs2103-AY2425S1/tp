package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private Appointment appointment3;

    @BeforeEach
    void setUp() {
        uniqueAppointmentList = new UniqueAppointmentList();
        appointment1 = new Appointment(new AppointmentType("Checkup"), LocalDateTime.of(2024, 1, 1, 10, 0), 1,
                new Sickness("Cold"), new Medicine("Aspirin"));
        appointment2 = new Appointment(new AppointmentType("Followup"), LocalDateTime.of(2024, 1, 2, 11, 0), 2,
                new Sickness("Flu"), new Medicine("Tamiflu"));
        appointment3 = new Appointment(new AppointmentType("Consultation"), LocalDateTime.of(2024, 1, 3, 14, 0), 3,
                new Sickness("Headache"), new Medicine("Ibuprofen"));
    }

    @Test
    public void testContains() {
        assertFalse(uniqueAppointmentList.contains(appointment1));
        uniqueAppointmentList.add(appointment1);
        assertTrue(uniqueAppointmentList.contains(appointment1));
    }

    @Test
    public void testAdd() {
        uniqueAppointmentList.add(appointment1);
        assertTrue(uniqueAppointmentList.contains(appointment1));
        assertThrows(DuplicateAppointmentException.class, () -> uniqueAppointmentList.add(appointment1));
    }

    @Test
    public void testSetAppointment() {
        uniqueAppointmentList.add(appointment1);
        Appointment editedAppointment = new Appointment(new AppointmentType("Checkup"),
                LocalDateTime.of(2024, 1, 1, 10, 0), 1,
                new Sickness("Fever"), new Medicine("Paracetamol"));
        uniqueAppointmentList.setAppointment(appointment1, editedAppointment);
        assertTrue(uniqueAppointmentList.contains(editedAppointment));
        assertFalse(uniqueAppointmentList.contains(appointment1));
    }

    @Test
    public void testSetAppointmentNonExisting() {
        assertThrows(AppointmentNotFoundException.class, () -> {
            uniqueAppointmentList.setAppointment(appointment1, appointment2);
        });
    }

    @Test
    public void testRemove() {
        uniqueAppointmentList.add(appointment1);
        uniqueAppointmentList.remove(appointment1);
        assertFalse(uniqueAppointmentList.contains(appointment1));
    }

    @Test
    public void testSetAppointments() {
        uniqueAppointmentList.setAppointments(Arrays.asList(appointment1, appointment2));
        assertTrue(uniqueAppointmentList.contains(appointment1));
        assertTrue(uniqueAppointmentList.contains(appointment2));
    }

    @Test
    public void testAsUnmodifiableObservableList() {
        uniqueAppointmentList.add(appointment1);
        uniqueAppointmentList.add(appointment2);
        List<Appointment> unmodifiableList = uniqueAppointmentList.asUnmodifiableObservableList();
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.remove(0));
    }

    @Test
    public void testEquals() {
        UniqueAppointmentList list1 = new UniqueAppointmentList();
        UniqueAppointmentList list2 = new UniqueAppointmentList();
        list1.add(appointment1);
        list2.add(appointment1);
        assertEquals(list1, list2);
        list2.add(appointment2);
        assertNotEquals(list1, list2);
    }

    @Test
    public void testHashCode() {
        UniqueAppointmentList list1 = new UniqueAppointmentList();
        UniqueAppointmentList list2 = new UniqueAppointmentList();
        list1.add(appointment1);
        list2.add(appointment1);
        assertEquals(list1.hashCode(), list2.hashCode());
        list2.add(appointment2);
        assertNotEquals(list1.hashCode(), list2.hashCode());
    }

    @Test
    public void testToString() {
        uniqueAppointmentList.add(appointment1);
        uniqueAppointmentList.add(appointment2);
        String expectedString = "[" + appointment1.toString() + ", " + appointment2.toString() + "]";
        assertEquals(expectedString, uniqueAppointmentList.toString());
    }

}
