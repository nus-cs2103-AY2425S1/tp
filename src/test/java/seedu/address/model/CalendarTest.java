package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Appointment;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;



public class CalendarTest {

    private final Calendar calendar = new Calendar(new AddressBook());

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), calendar.getAppointments());
    }

    @Test
    public void hasAppointment_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> calendar.hasAppointment(null));
    }

    @Test
    public void hasAppointment_appointmentSlotTaken_returnsTrue() {
        calendar.addAppointment(ALICE);
        Person temp = new PersonBuilder(BOB).withAppointment("01 January 2024 13:40").build();
        assertTrue(calendar.hasAppointment(temp));
    }

    @Test
    public void hasAppointment_appointmentNotInCalendar_returnsFalse() {
        assertFalse(calendar.hasAppointment(ALICE));
    }

    @Test
    public void hasAppointment_appointmentInCalender_returnsTrue() {
        calendar.addAppointment(ALICE);
        assertTrue(calendar.hasAppointment(ALICE));
    }

    @Test
    public void hasAppointment_personWithDifferentIdentityFieldsSameAppointmentInCalender_returnsTrue() {
        calendar.addAppointment(BOB);
        Person editedAlice = new PersonBuilder(ALICE).withAppointment(VALID_APPOINTMENT_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(calendar.hasAppointment(editedAlice));
    }

    @Test
    public void isValidAppointmentUpdate() {
        calendar.addAppointment(new PersonBuilder(BOB).withAppointment(null).build());
        calendar.addAppointment(new PersonBuilder(ALICE).withAppointment("10/10/2024 10:30").build());
        calendar.addAppointment(new PersonBuilder(IDA).withAppointment("10/10/2024 10:15").build());
        calendar.addAppointment(new PersonBuilder((HOON)).withAppointment("10/10/2024 10:45").build());

        assertFalse(calendar.isValidAppointmentUpdate(new Appointment("10/10/2024 10:15"),
                                                        new Appointment("10/10/2024 10:16"))); // will clash

        assertFalse(calendar.isValidAppointmentUpdate(new Appointment(null),
                                                        new Appointment("10/10/2024 10:15"))); // taken

        assertTrue(calendar.isValidAppointmentUpdate(new Appointment(null), new Appointment(null)));

        assertTrue(calendar.isValidAppointmentUpdate(new Appointment("10/10/2024 10:15"),
                                                        new Appointment("10/10/2024 10:14"))); // no clash

        assertTrue(calendar.isValidAppointmentUpdate(new Appointment(null),
                                                        new Appointment("10/10/2024 10:00")));
    }

}
