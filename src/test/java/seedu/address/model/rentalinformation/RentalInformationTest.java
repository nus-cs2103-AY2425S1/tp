package seedu.address.model.rentalinformation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RentalInformationTest {
    @Test
    public void equals() {
        RentalInformation sample = new RentalInformation("Ave 1 Bishan", "01/01/2024",
                "31/12/2024", "15", "3500", "7000", "David");
        RentalInformation sameAsSample = new RentalInformation("Ave 1 Bishan", "01/01/2024",
                "31/12/2024", "15", "3500", "7000", "David");
        RentalInformation differentFromSample = new RentalInformation("Ave 2 Bishan", "01/06/2024",
                "31/12/2024", "30", "2500", "7500", "Steven");

        RentalInformation sameAddressDifferentRentalStartDate = new RentalInformation("Ave 1 Bishan", "02/01/2024",
                "31/12/2024", "15", "3500", "7000", "David");
        RentalInformation sameAddressDifferentRentalEndDate = new RentalInformation("Ave 1 Bishan", "01/01/2024",
                "30/12/2024", "15", "3500", "7000", "David");
        RentalInformation sameAddressDifferentRentDueDate = new RentalInformation("Ave 1 Bishan", "01/01/2024",
                "31/12/2024", "20", "3500", "7000", "David");
        RentalInformation sameAddressDifferentMonthlyRent = new RentalInformation("Ave 1 Bishan", "01/01/2024",
                "31/12/2024", "15", "4000", "7000", "David");
        RentalInformation sameAddressDifferentDeposit = new RentalInformation("Ave 1 Bishan", "01/01/2024",
                "31/12/2024", "15", "3500", "9000", "David");
        RentalInformation sameAddressDifferentCustomerList = new RentalInformation("Ave 1 Bishan", "01/01/2024",
                "31/12/2024", "15", "3500", "7000", "Steven");

        RentalInformation differentAddressSameRentalStartDate = new RentalInformation("Ave 2 Bishan", "01/01/2024",
                "31/12/2024", "15", "3500", "7000", "David");
        RentalInformation differentAddressSameRentalEndDate = new RentalInformation("Ave 3 Bishan", "01/01/2024",
                "31/12/2024", "15", "3500", "7000", "David");
        RentalInformation differentAddressSameRentDueDate = new RentalInformation("Ave 4 Bishan", "01/01/2024",
                "31/12/2024", "15", "3500", "7000", "David");
        RentalInformation differentAddressSameMonthlyRent = new RentalInformation("Ave 5 Bishan", "01/01/2024",
                "31/12/2024", "15", "3500", "7000", "David");
        RentalInformation differentAddressSameDeposit = new RentalInformation("Ave 6 Bishan", "01/01/2024",
                "31/12/2024", "15", "3500", "7000", "David");
        RentalInformation differentAddressSameCustomerList = new RentalInformation("Ave 7 Bishan", "01/01/2024",
                "31/12/2024", "15", "3500", "7000", "David");

        // same values -> returns true
        assertTrue(sample.equals(sameAsSample));

        // same object -> returns true
        assertTrue(sample.equals(sample));

        // null -> returns false
        assertFalse(sample.equals(null));

        // different type -> returns false
        assertFalse(sample.equals(5));

        // different rental information -> returns false
        assertFalse(sample.equals(differentFromSample));

        // same address, different rental start date
        assertTrue(sample.equals(sameAddressDifferentRentalStartDate));

        // same address, different rental end date
        assertTrue(sample.equals(sameAddressDifferentRentalEndDate));

        // same address, different rent due date
        assertTrue(sample.equals(sameAddressDifferentRentDueDate));

        // same address, different monthly rent
        assertTrue(sample.equals(sameAddressDifferentMonthlyRent));

        // same address, different deposit
        assertTrue(sample.equals(sameAddressDifferentDeposit));

        // same address, different customer list
        assertTrue(sample.equals(sameAddressDifferentCustomerList));

        // different address, same rental start date
        assertFalse(sample.equals(differentAddressSameRentalStartDate));

        // different address, same rental end date
        assertFalse(sample.equals(differentAddressSameRentalEndDate));

        // different address, same rent due date
        assertFalse(sample.equals(differentAddressSameRentDueDate));

        // different address, same monthly rent
        assertFalse(sample.equals(differentAddressSameMonthlyRent));

        // different address, same deposit
        assertFalse(sample.equals(differentAddressSameDeposit));

        // different address, same customer list
        assertFalse(sample.equals(differentAddressSameCustomerList));
    }

    @Test
    public void hashcode() {
        RentalInformation rentalInformation = new RentalInformation("Ave 1 Bishan", "01/01/2024",
                "31/12/2024", "15", "3500", "7000", "David");

        // same rental information
        assertEquals(rentalInformation.hashCode(), new RentalInformation("Ave 1 Bishan", "01/01/2024",
                "31/12/2024", "15", "3500", "7000", "David").hashCode());

        // different rental information
        assertNotEquals(rentalInformation.hashCode(), new RentalInformation("Ave 2 Bishan", "01/06/2024",
                "31/12/2024", "30", "2500", "7500", "Steven").hashCode());
    }

    @Test
    public void isSameToString() {
        RentalInformation rentalInformation = new RentalInformation("Ave 1 Bishan", "01/01/2024",
                "31/12/2024", "15", "3500", "7000", "David");

        // same rental information
        assertEquals(rentalInformation.toString(), new RentalInformation("Ave 1 Bishan", "01/01/2024",
                "31/12/2024", "15", "3500", "7000", "David").toString());

        // different rental information
        assertNotEquals(rentalInformation.toString(), new RentalInformation("Ave 2 Bishan", "01/06/2024",
                "31/12/2024", "30", "2500", "7500", "Steven").toString());
    }
}
