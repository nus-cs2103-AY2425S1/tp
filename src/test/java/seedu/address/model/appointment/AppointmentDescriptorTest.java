package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.BENSON_P;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppointmentDescriptorTest {
    private AppointmentType validType;
    private LocalDateTime validDateTime;
    private Sickness validSickness;
    private Medicine validMedicine;
    private AppointmentDescriptor validDescriptor;

    @BeforeEach
    void setUp() {
        validType = new AppointmentType("Check up");
        validDateTime = LocalDateTime.of(2024, 10, 16, 12, 30);
        validSickness = new Sickness("Common Cold");
        validMedicine = new Medicine("Paracetamol");
        validDescriptor = new AppointmentDescriptor(validType, validDateTime, validSickness, validMedicine);
    }

    @Test
    void constructor_nullAppointmentType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AppointmentDescriptor(null, validDateTime, validSickness, validMedicine));
    }

    @Test
    void constructor_validInputs_createsAppointmentDescriptor() {
        assertEquals(validType, validDescriptor.getAppointmentType());
        assertEquals(validDateTime, validDescriptor.getAppointmentDateTime());
        assertEquals(validSickness, validDescriptor.getSickness());
        assertEquals(validMedicine, validDescriptor.getMedicine());
    }

    @Test
    void isSameAppointment_differentObject_returnsTrue() {
        assertTrue(validDescriptor.isSameAppointment(new Appointment(1, BENSON_P, validDescriptor)));
    }

    @Test
    void isSameAppointment_differentDateTime_returnsFalse() {
        AppointmentDescriptor otherDescriptor = new AppointmentDescriptor(
                validType,
                LocalDateTime.of(2024, 11, 16, 12, 30),
                validSickness,
                validMedicine
        );

        assertFalse(validDescriptor.isSameAppointment(otherDescriptor));
    }

    @Test
    void equals_null_returnsFalse() {
        assertFalse(validDescriptor == null);
    }

    @Test
    void equals_allFieldsSame_returnsTrue() {
        AppointmentDescriptor otherDescriptor = new AppointmentDescriptor(
                validType,
                validDateTime,
                validSickness,
                validMedicine
        );

        assertTrue(validDescriptor.equals(otherDescriptor));
    }

    @Test
    void hashCode_sameValues_sameHashCode() {
        AppointmentDescriptor otherDescriptor = new AppointmentDescriptor(
                validType,
                validDateTime,
                validSickness,
                validMedicine
        );

        assertEquals(validDescriptor.hashCode(), otherDescriptor.hashCode());
    }

    @Test
    void toString_containsAllFields() {
        String toString = validDescriptor.toString();

        assertTrue(toString.contains(validType.toString()));
        assertTrue(toString.contains(validDateTime.toString()));
        assertTrue(toString.contains(validSickness.toString()));
        assertTrue(toString.contains(validMedicine.toString()));
    }
}
