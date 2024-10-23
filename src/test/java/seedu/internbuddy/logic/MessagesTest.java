package seedu.internbuddy.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.logic.parser.Prefix;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.testutil.ApplicationBuilder;
import seedu.internbuddy.testutil.CompanyBuilder;

public class MessagesTest {

    @Test
    public void getErrorMessageForDuplicatePrefixes_validPrefixes_returnsExpectedMessage() {
        Prefix[] duplicatePrefixes = {new Prefix("n/"), new Prefix("d/")};
        String expectedMessage = "Multiple values specified for the following single-valued field(s): n/ d/";
        String actualMessage = Messages.getErrorMessageForDuplicatePrefixes(duplicatePrefixes);

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void formatCompany_validCompany_returnsExpectedFormat() {
        Company company = new CompanyBuilder().withName("Google")
                                              .withPhone("12345678")
                                              .withEmail("google@example.com")
                                              .withAddress("1 Google Way")
                                              .withStatus("APPLIED")
                                              .build();

        String expectedFormat = "Google; Phone: 12345678; Email: google@example.com;"
                + " Address: 1 Google Way; Status: APPLIED; Tags: ";
        String actualFormat = Messages.format(company);

        assertEquals(expectedFormat, actualFormat);
    }

    @Test
    public void formatApplication_validApplication_returnsExpectedFormat() {
        Application application = new ApplicationBuilder().withName("Full Stack Engineer")
                                                          .withDescription("Knowledge in ReactJS")
                                                          .withAppStatus("APPLIED")
                                                          .build();

        String expectedFormat = "Full Stack Engineer; Description: Knowledge in ReactJS; App Status: APPLIED";
        String actualFormat = Messages.format(application);

        assertEquals(expectedFormat, actualFormat);
    }
}
