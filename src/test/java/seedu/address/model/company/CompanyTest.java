package seedu.address.model.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCompanies.NUS;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CompanyBuilder;

public class CompanyTest {
    @Test
    public void isSameCompany() {
        // same object -> returns true
        assertTrue(NUS.isSameCompany(NUS));

        // null -> returns false;
        assertFalse(NUS.isSameCompany(null));

        // different address -> returns true
        assertTrue(NUS.isSameCompany(new CompanyBuilder()
                .withAddress("20 Clementi Ave 1, Singapore 129957")
                .build()));

        // different billing date -> returns true
        assertTrue(NUS.isSameCompany(new CompanyBuilder().withBillingDate("6").build()));

        // different phone -> returns true
        assertTrue(NUS.isSameCompany(new CompanyBuilder().withPhone("65161709").build()));

        // different name -> returns false
        assertFalse(NUS.isSameCompany(new CompanyBuilder().withName("NUS High").build()));
    }

    @Test
    public void equals() {
        // same values -> true
        Company nusCopy = new CompanyBuilder().build();
        assertTrue(NUS.equals(nusCopy));

        // same object -> true
        assertTrue(NUS.equals(NUS));

        // null -> false
        assertFalse(NUS.equals(null));

        // TODO add more tests for equality
    }

    @Test
    public void toStringMethod() {
        String expected = Company.class.getCanonicalName()
                + "{name=" + NUS.getName()
                + ", address=" + NUS.getAddress()
                + ", billing date=" + NUS.getBillingDate()
                + ", phone=" + NUS.getPhone()
                + "}";
        assertEquals(expected, NUS.toString());
    }
}
