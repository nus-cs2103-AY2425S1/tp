package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.APPOINTMENT_1;
import static seedu.address.testutil.TypicalAppointments.APPOINTMENT_2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;

public class UniqueAppointmentListTest {

    private final UniqueAppointmentList uniqueAppointmentList = new UniqueAppointmentList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.contains(null));
    }

    @Test
    public void contains_appointmentNotInList_returnsFalse() {
        assertFalse(uniqueAppointmentList.contains(APPOINTMENT_1));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueAppointmentList.add(APPOINTMENT_1);
        assertTrue(uniqueAppointmentList.contains(APPOINTMENT_1));
    }

    @Test
    public void add_duplicateAppointment_throwsDuplicateAppointmentException() {
        uniqueAppointmentList.add(APPOINTMENT_1);
        assertThrows(DuplicateAppointmentException.class, () -> uniqueAppointmentList.add(APPOINTMENT_1));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.add(null));
    }

    @Test
    public void setPerson_nullTargetAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.setAppointment(null,
                APPOINTMENT_1));
    }

    @Test
    public void setPerson_nullEditedAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.setAppointment(APPOINTMENT_1,
                null));
    }

    @Test
    public void setAppointment_targetAppointmentNotInList_throwsAppointmentNotFoundException() {
        assertThrows(AppointmentNotFoundException.class, () -> uniqueAppointmentList.setAppointment(APPOINTMENT_1,
                APPOINTMENT_1));
    }

    @Test
    public void setAppointment_editedAppointmentIsSameAppointment_success() {
        uniqueAppointmentList.add(APPOINTMENT_1);
        uniqueAppointmentList.setAppointment(APPOINTMENT_1, APPOINTMENT_1);
        UniqueAppointmentList expecteduniqueAppointmentList = new UniqueAppointmentList();
        expecteduniqueAppointmentList.add(APPOINTMENT_1);
        assertEquals(expecteduniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setPerson_editedAppointmentHasDifferentIdentity_success() {
        uniqueAppointmentList.add(APPOINTMENT_1);
        uniqueAppointmentList.setAppointment(APPOINTMENT_1, APPOINTMENT_2);
        UniqueAppointmentList expecteduniqueAppointmentList = new UniqueAppointmentList();
        expecteduniqueAppointmentList.add(APPOINTMENT_2);
        assertEquals(expecteduniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueAppointmentList.add(APPOINTMENT_1);
        uniqueAppointmentList.add(APPOINTMENT_2);
        assertThrows(DuplicateAppointmentException.class, () -> uniqueAppointmentList.setAppointment(APPOINTMENT_1,
                APPOINTMENT_2));
    }

    @Test
    public void remove_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.remove(null));
    }


    @Test
    public void remove_appointmentDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(AppointmentNotFoundException.class, () -> uniqueAppointmentList.remove(APPOINTMENT_1));
    }

    @Test
    public void remove_existingAppointment_removesAppointment() {
        uniqueAppointmentList.add(APPOINTMENT_1);
        uniqueAppointmentList.remove(APPOINTMENT_1);
        UniqueAppointmentList expecteduniqueAppointmentList = new UniqueAppointmentList();
        assertEquals(expecteduniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointments_nulluniqueAppointmentList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList
                .setAppointments((UniqueAppointmentList) null));
    }

    @Test
    public void setAppointments_uniqueAppointmentList_replacesOwnListWithProvideduniqueAppointmentList() {
        uniqueAppointmentList.add(APPOINTMENT_1);
        UniqueAppointmentList expecteduniqueAppointmentList = new UniqueAppointmentList();
        expecteduniqueAppointmentList.add(APPOINTMENT_2);
        uniqueAppointmentList.setAppointments(expecteduniqueAppointmentList);
        assertEquals(expecteduniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointments_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.setAppointments((List<Appointment>) null));
    }

    @Test
    public void setAppointments_list_replacesOwnListWithProvidedList() {
        uniqueAppointmentList.add(APPOINTMENT_1);
        List<Appointment> personList = Collections.singletonList(APPOINTMENT_2);
        uniqueAppointmentList.setAppointments(personList);
        UniqueAppointmentList expecteduniqueAppointmentList = new UniqueAppointmentList();
        expecteduniqueAppointmentList.add(APPOINTMENT_2);
        assertEquals(expecteduniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicateAppointmentException() {
        List<Appointment> listWithDuplicateAppointments = Arrays.asList(APPOINTMENT_1, APPOINTMENT_1);
        assertThrows(DuplicateAppointmentException.class, () -> uniqueAppointmentList
                .setAppointments(listWithDuplicateAppointments));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueAppointmentList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueAppointmentList.asUnmodifiableObservableList().toString(), uniqueAppointmentList.toString());
    }
}
