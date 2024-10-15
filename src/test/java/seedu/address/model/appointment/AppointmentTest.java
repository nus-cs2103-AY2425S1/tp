package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppointmentTest {
    private Appointment appointment1;
    private Appointment appointment2;
    private Appointment appointment3;
    private LocalDateTime dateTime1;
    private LocalDateTime dateTime2;

    @BeforeEach
    void setUp() {
        dateTime1 = LocalDateTime.of(2024, 1, 1, 10, 0);
        dateTime2 = LocalDateTime.of(2024, 1, 1, 11, 0);

        appointment1 = new Appointment(new AppointmentType("Checkup"), dateTime1, 1,
                new Sickness("Cold"), new Medicine("Aspirin"));
        appointment2 = new Appointment(new AppointmentType("Checkup"), dateTime1, 1,
                new Sickness("Cold"), new Medicine("Aspirin"));
        appointment3 = new Appointment(new AppointmentType("Followup"), dateTime2, 2,
                new Sickness("Flu"), new Medicine("Tamiflu"));
        System.out.println(appointment1.toString());
        System.out.println(appointment2.toString());
        System.out.println(appointment3.toString());
    }

    @Test
    public void testGetAppointmentType() {
        assertEquals(new AppointmentType("Checkup"), appointment1.getAppointmentType());
        assertEquals(new AppointmentType("Checkup"), appointment2.getAppointmentType());
        assertEquals(new AppointmentType("Followup"), appointment3.getAppointmentType());
    }


    @Test
    public void testGetPersonId() {
        assertEquals(1, appointment1.getPersonId());
        assertEquals(1, appointment2.getPersonId());
        assertEquals(2, appointment3.getPersonId());
    }


    @Test
    public void testGetSickness() {
        assertEquals(new Sickness("Cold"), appointment1.getSickness());
        assertEquals(new Sickness("Cold"), appointment2.getSickness());
        assertEquals(new Sickness("Flu"), appointment3.getSickness());
    }


    @Test
    public void testGetAppointmentDateTime() {
        assertEquals(dateTime1, appointment1.getAppointmentDateTime());
        assertEquals(dateTime1, appointment2.getAppointmentDateTime());
        assertEquals(dateTime2, appointment3.getAppointmentDateTime());
    }

    @Test
    public void testGetMedicine() {
        assertEquals(new Medicine("aspirin"), appointment1.getMedicine());
        assertEquals(new Medicine("aspirin"), appointment2.getMedicine());
        assertEquals(new Medicine("tamiflu"), appointment3.getMedicine());
    }

    @Test
    public void testIsSameAppointment() {
        assertTrue(appointment1.isSameAppointment(appointment2));
        assertTrue(appointment1.isSameAppointment(appointment1));
        assertFalse(appointment1.isSameAppointment(appointment3));
    }

    @Test
    public void testEquals() {
        assertEquals(appointment1, appointment1);
        assertEquals(appointment1, appointment2);
        assertNotEquals(appointment1, appointment3);
    }

    @Test
    public void testHashCode() {
        assertEquals(appointment1.hashCode(), appointment2.hashCode());
        assertEquals(appointment2.hashCode(), appointment2.hashCode());
        assertNotEquals(appointment1.hashCode(), appointment3.hashCode());
    }

    @Test
    public void testToString() {
        String appointment1String = "seedu.address.model.appointment.Appointment"
                + "{appointmentType=Checkup, appointmentDateTime=2024-01-01T10:00,"
                + " personId=1, medicine=aspirin, sickness=Cold}";

        String appointment3String = "seedu.address.model.appointment.Appointment{appointmentType=Followup,"
                + " appointmentDateTime=2024-01-01T11:00, personId=2, medicine=tamiflu, sickness=Flu}";

        assertEquals(appointment1String, appointment1.toString());
        assertEquals(appointment3String, appointment3.toString());
    }
}
