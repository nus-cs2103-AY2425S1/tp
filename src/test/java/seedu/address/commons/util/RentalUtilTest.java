package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.rentalinformation.CustomerList;

public class RentalUtilTest {
    @Test
    public void convertStringToLocalDate() {
        LocalDate localDate = RentalUtil.convertStringToLocalDate("01/01/2024");

        // null String
        assertThrows(NullPointerException.class, () -> RentalUtil.convertStringToLocalDate(null));

        // same LocalDate
        assertEquals(localDate, LocalDate.of(2024, 1, 1));

        // different LocalDate
        assertNotEquals(localDate, LocalDate.of(2024, 1, 2));
    }

    @Test
    public void convertLocalDateToStringWithFormat() {
        String str = RentalUtil.convertLocalDateToStringWithFormat(LocalDate.of(2024, 1, 1), "dd MMM yyyy");

        // null LocalDate or String or both
        assertThrows(NullPointerException.class, () ->
                RentalUtil.convertLocalDateToStringWithFormat(null, "dd MMM yyyy"));
        assertThrows(NullPointerException.class, () ->
                RentalUtil.convertLocalDateToStringWithFormat(LocalDate.of(2024, 1, 1), null));
        assertThrows(NullPointerException.class, () ->
                RentalUtil.convertLocalDateToStringWithFormat(null, null));

        // same LocalDate and String
        assertEquals(str, "01 Jan 2024");

        // different LocalDate or String
        assertNotEquals(str, "02 Jan 2024");
        assertNotEquals(str, "01 01 2024");
        assertNotEquals(str, "01/01/2024");
    }

    @Test
    public void convertStringToCustomerArrayList() {
        ArrayList<String> list = RentalUtil.convertStringToCustomerArrayList("David;Steven");

        // null String
        assertThrows(NullPointerException.class, () -> RentalUtil.convertStringToCustomerArrayList(null));

        // same ArrayList
        assertEquals(list, new ArrayList<>(List.of("David", "Steven")));

        // different ArrayList
        assertNotEquals(list, new ArrayList<>(List.of("Steven", "David")));
    }

    @Test
    public void isCustomerListSame() {
        CustomerList sampleList = new CustomerList("David;Steven");
        CustomerList sameAsSample = new CustomerList("David;Steven");
        CustomerList differentOrder = new CustomerList("Steven;David");
        CustomerList differentList = new CustomerList("Andrew;Bryan");

        // null one of CustomerList or both
        assertThrows(NullPointerException.class, () -> RentalUtil.isCustomerListSame(null, sampleList));
        assertThrows(NullPointerException.class, () -> RentalUtil.isCustomerListSame(sampleList, null));
        assertThrows(NullPointerException.class, () -> RentalUtil.isCustomerListSame(null, null));

        // same customer list
        assertTrue(RentalUtil.isCustomerListSame(sampleList, sampleList));
        assertTrue(RentalUtil.isCustomerListSame(sampleList, sameAsSample));
        assertTrue(RentalUtil.isCustomerListSame(sampleList, differentOrder));

        // different customer list
        assertFalse(RentalUtil.isCustomerListSame(sampleList, differentList));
    }
}
