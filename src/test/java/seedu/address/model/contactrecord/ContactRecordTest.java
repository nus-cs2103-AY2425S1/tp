package seedu.address.model.contactrecord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTES;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.CallFrequency;
import seedu.address.testutil.ContactRecordBuilder;

public class ContactRecordTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ContactRecord(null, VALID_NOTES));
    }

    @Test
    public void isValidContactRecord() {
        // null tag contact date
        assertThrows(NullPointerException.class, () -> ContactRecord.isValidContactRecord(null));

        // invalid date in contact record
        assertFalse(ContactRecord.isValidContactRecord("2020-13-01"));
        assertFalse(ContactRecord.isValidContactRecord("2020-01-32"));
        assertFalse(ContactRecord.isValidContactRecord(""));
        assertFalse(ContactRecord.isValidContactRecord("2020-01-01 12:00"));

        // valid date in contact record
        assertTrue(ContactRecord.isValidContactRecord("2020-01-01"));
    }

    @Test
    public void isCurrentOrPastDate() {
        // future date
        assertFalse(ContactRecord.isCurrentOrPastDate(LocalDate.now().plusDays(1)));

        // current date
        assertTrue(ContactRecord.isCurrentOrPastDate(LocalDate.now()));

        // past date
        assertTrue(ContactRecord.isCurrentOrPastDate(LocalDate.now().minusDays(1)));
    }

    @Test
    public void createCurrentRecord() {
        LocalDate currentDate = LocalDate.now();
        ContactRecord currentRecord = ContactRecord.createCurrentRecord(VALID_NOTES);
        assertEquals(currentRecord.value, currentDate);
        assertEquals(currentRecord.getNotes(), VALID_NOTES);
    }

    public void addCallFrequency_validCallFrequency_success() {
        ContactRecord contactRecord = new ContactRecordBuilder().withDate("2020-01-01").build();
        CallFrequency callFrequency = new CallFrequency("7");
        LocalDate newContactRecord = contactRecord.add(callFrequency);
        ContactRecord targetContactRecord = new ContactRecordBuilder().withDate("2020-01-08").build();
        assertEquals(newContactRecord, targetContactRecord);
    }

    @Test
    public void equals() {
        ContactRecord contactRecord = new ContactRecordBuilder().build();

        // same values -> returns true
        ContactRecord contactRecordCopy = new ContactRecordBuilder().build();
        assertTrue(contactRecord.equals(contactRecordCopy));

        // same object -> returns true
        assertTrue(contactRecord.equals(contactRecord));

        // null -> returns false
        assertFalse(contactRecord.equals(null));

        // different type -> returns false
        assertFalse(contactRecord.equals(5.0f));

        // different date -> returns false
        ContactRecord differentDate = new ContactRecordBuilder().withDate("2020-01-02").build();
        assertFalse(contactRecord.equals(differentDate));

        // different notes -> returns false
        ContactRecord differentNotes = new ContactRecordBuilder().withNotes("Different notes").build();
        assertFalse(contactRecord.equals(differentNotes));
    }

}
