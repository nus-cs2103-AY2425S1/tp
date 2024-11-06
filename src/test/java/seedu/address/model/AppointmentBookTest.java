package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalPersons.BENSON_P;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDescriptor;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.Assert;


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
        assertTrue(appointmentBook.hasAppointment(new AppointmentBuilder().build().getAppointmentDescriptor(),
            new AppointmentBuilder().build().getPerson()));
    }

    @Test
    public void hasAppointment_appointmentDescriptor_returnsTrue() {
        appointmentBook.addAppointment(new AppointmentBuilder().build());
        assertTrue(appointmentBook.hasAppointment(new AppointmentBuilder().build().getAppointmentDescriptor()));
    }

    @Test
    public void addAppointment_withPersonAndDescriptor_success() {
        AppointmentDescriptor descriptor = new AppointmentBuilder().build().getAppointmentDescriptor();
        Appointment addedAppointment = appointmentBook.addAppointment(BENSON_P, descriptor);

        assertEquals(0, addedAppointment.getAppointmentId()); // Assuming IDs start from 0
        assertTrue(appointmentBook.hasAppointment(addedAppointment));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(appointmentBook.equals(appointmentBook));
    }

    @Test
    public void equals_differentObjectSameData_returnsTrue() {
        AppointmentBook anotherAppointmentBook = new AppointmentBook();
        assertTrue(appointmentBook.equals(anotherAppointmentBook));
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        assertFalse(appointmentBook.equals(new Object()));
    }

    @Test
    public void hashCode_sameData_returnsSameHashCode() {
        AppointmentBook anotherAppointmentBook = new AppointmentBook();
        assertEquals(appointmentBook.hashCode(), anotherAppointmentBook.hashCode());
    }

    @Test
    public void hashCode_differentData_returnsDifferentHashCode() {
        AppointmentDescriptor descriptor = new AppointmentBuilder().build().getAppointmentDescriptor();
        AppointmentBook anotherAppointmentBook = new AppointmentBook();
        appointmentBook.addAppointment(BENSON_P, descriptor);

        assertNotEquals(appointmentBook.hashCode(), anotherAppointmentBook.hashCode());
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
