package seedu.address.model;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalPersons.*;

import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;


public class AppointmentBookTest {
    private final AppointmentBook appointmentBook = new AppointmentBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), appointmentBook.getAppointmentList());
    }

    @Test
    public void resetDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> appointmentBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAppointmentBook_replacesData() {
        AppointmentBook newData = getTypicalAppointmentBook();
        appointmentBook.resetData(newData);
        assertEquals(newData, appointmentBook);
    }

    @Test
    public void hasAppointment_nullAppointment_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> appointmentBook.hasAppointment((Appointment) null));
    }

    @Test
    public void hasAppointment_appointment_returnsTrue() {
        appointmentBook.addAppointment(new AppointmentBuilder().build());
        assertTrue(appointmentBook.hasAppointment(new AppointmentBuilder().build()));
    }

    @Test
    public void hasAppointment_appointmentDescriptor_returnsTrue() {
        appointmentBook.addAppointment(new AppointmentBuilder().build());
        assertTrue(appointmentBook.hasAppointment(new AppointmentBuilder().build().getAppointmentDescriptor()));
    }


    /**
     * A stub ReadOnlyAppointmentBook whose appointment list can violate interface constraints.
     */
    private static class AppointmentBookStub implements ReadOnlyAppointmentBook {
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        AppointmentBookStub(Collection<AppointmentDescriptor> appointments) {
            this.appointments.setAll(appointments.stream().map(p ->
                    new Appointment(getNextAppointmentId(), BENSON_P, p)).toList());
        }

        @Override
        public ObservableList<Appointment> getAppointmentList() {
            return appointments;
        }

        public Optional<Appointment> findAppointment(int appointmentId) {
            return appointments.stream()
                    .filter(appointment -> appointment.getAppointmentId() == appointmentId)
                    .findFirst();
        }

        @Override
        public int getNextAppointmentId() {
            return appointments.size();
        }
    }
}
