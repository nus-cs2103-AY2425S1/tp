package seedu.address.model.contactdate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ContactDateListTest {
    @Test
    public void getLastContacted() {
        ContactDateList contactDateList = new ContactDateList();

        // test 1
        ContactDate contactDate = new ContactDate("2021-10-10");
        contactDateList.add(contactDate);
        assertEquals(contactDate, contactDateList.getLastContacted());

        // test 2
        ContactDate newContactDate = new ContactDate("2021-10-11");
        contactDateList.add(newContactDate);
        assertEquals(newContactDate, contactDateList.getLastContacted());
    }

    @Test
    public void markAsContacted() {
        ContactDateList contactDateList = new ContactDateList();
        ContactDate contactDate = ContactDate.getCurrentDate();
        contactDateList.markAsContacted();
        assertEquals(1, contactDateList.size());
        assertEquals(contactDate, contactDateList.getLastContacted());
    }
}
