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
