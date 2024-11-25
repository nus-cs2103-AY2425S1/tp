package seedu.hireme.model.internshipapplication.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.hireme.model.ReadOnlyAddressBook;
import seedu.hireme.model.internshipapplication.Company;
import seedu.hireme.model.internshipapplication.Date;
import seedu.hireme.model.internshipapplication.Email;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.Name;
import seedu.hireme.model.internshipapplication.Status;
import seedu.hireme.model.util.SampleDataUtil;
import seedu.hireme.testutil.InternshipApplicationBuilder;

public class SampleDataUtilTest {
    private final InternshipApplication sampleInternshipApplication;

    public SampleDataUtilTest() {
        Email email = new Email("company1@mail.com");
        Name name = new Name("Company 1");
        Company company = new Company(email, name);
        Date date = new Date(LocalDate.now());
        this.sampleInternshipApplication = new InternshipApplicationBuilder().withCompany(company)
                .withDate(date.toString()).withRole("role 1").build();
    }

    @Test
    public void getSampleInternships() {
        InternshipApplication[] sampleInternshipApplications = SampleDataUtil.getSampleInternships();
        assertEquals(1, sampleInternshipApplications.length);

        // Internship application with different field values
        sampleInternshipApplication.setStatus(Status.REJECTED);
        assertNotEquals(sampleInternshipApplication, sampleInternshipApplications[0]);
        sampleInternshipApplication.setStatus(Status.PENDING);

        // Internship application with same field values
        assertEquals(sampleInternshipApplication, sampleInternshipApplications[0]);
    }

    @Test
    public void getSampleAddressBook() {
        ReadOnlyAddressBook addressBook = SampleDataUtil.getSampleAddressBook();
        ObservableList<InternshipApplication> sampleInternshipApplications = addressBook.getList();
        assertEquals(1, sampleInternshipApplications.size());

        // Internship application with different field values
        sampleInternshipApplication.setStatus(Status.REJECTED);
        assertNotEquals(sampleInternshipApplication, sampleInternshipApplications.get(0));
        sampleInternshipApplication.setStatus(Status.PENDING);

        // Internship application with same field values
        assertEquals(sampleInternshipApplication, sampleInternshipApplications.get(0));
    }
}
