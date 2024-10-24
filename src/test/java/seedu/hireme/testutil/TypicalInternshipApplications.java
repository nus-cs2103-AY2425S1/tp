package seedu.hireme.testutil;

import static seedu.hireme.logic.commands.CommandTestUtil.VALID_COMPANY_EMAIL_APPLE;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_COMPANY_EMAIL_BOFA;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_APPLE;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_BOFA;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_ROLE_APPLE;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_ROLE_BOFA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.hireme.model.AddressBook;
import seedu.hireme.model.internshipapplication.InternshipApplication;

/**
 * A utility class containing a list of {@code InternshipApplication} objects to be used in tests.
 */
public class TypicalInternshipApplications {

    public static final InternshipApplication AIRBNB = new InternshipApplicationBuilder().withName("Airbnb")
            .withEmail("airbnb@example.com")
            .withRole("Software Engineer Intern")
            .withDate("01/01/01")
            .build();
    public static final InternshipApplication CITIBANK = new InternshipApplicationBuilder().withName("Citibank")
            .withEmail("citibank@example.com")
            .withRole("Backend Developer Intern")
            .withDate("01/01/02")
            .build();
    public static final InternshipApplication DELL = new InternshipApplicationBuilder().withName("Dell")
            .withEmail("dell@example.com")
            .withRole("Computer Engineer Intern")
            .withDate("01/01/03")
            .build();
    public static final InternshipApplication EY = new InternshipApplicationBuilder().withName("Ernest & Young")
            .withEmail("ey@example.com")
            .withRole("Fintech Intern")
            .withDate("01/01/04")
            .build();
    public static final InternshipApplication FIGMA = new InternshipApplicationBuilder().withName("Figma")
            .withEmail("figma@example.com")
            .withRole("UI/UX Intern")
            .withDate("01/01/05")
            .build();
    public static final InternshipApplication GOOGLE = new InternshipApplicationBuilder().withName("Google")
            .withEmail("google@gmail.com")
            .withDate("01/01/06")
            .withRole("SWE")
            .build();

    public static final InternshipApplication YAHOO = new InternshipApplicationBuilder().withName("Yahoo")
            .withEmail("yahoo@yahoo.com")
            .withDate("01/01/07")
            .withRole("Clerk")
            .build();

    public static final InternshipApplication BYTEDANCE = new InternshipApplicationBuilder().withName("ByteDance")
            .withEmail("bytedance@example.com")
            .withRole("Frontend Developer Intern")
            .withDate("01/01/08")
            .build();


    // Manually added - InternshipApplication's details found in {@code CommandTestUtil}
    public static final InternshipApplication APPLE = new InternshipApplicationBuilder()
            .withName(VALID_COMPANY_NAME_APPLE)
            .withEmail(VALID_COMPANY_EMAIL_APPLE).withRole(VALID_ROLE_APPLE).withDate("01/01/09").build();
    public static final InternshipApplication BOFA = new InternshipApplicationBuilder()
            .withName(VALID_COMPANY_NAME_BOFA)
            .withEmail(VALID_COMPANY_EMAIL_BOFA).withRole(VALID_ROLE_BOFA).withDate("01/01/10").build();

    private TypicalInternshipApplications() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical internship applications.
     */
    public static AddressBook<InternshipApplication> getTypicalAddressBook() {
        AddressBook<InternshipApplication> ab = new AddressBook<>();
        for (InternshipApplication internshipApp : getTypicalInternshipApplications()) {
            ab.addItem(internshipApp);
        }
        return ab;
    }

    public static AddressBook<InternshipApplication> getClonedAddressBook() {
        AddressBook<InternshipApplication> clonedAb = new AddressBook<>();
        for (InternshipApplication internshipApp : getTypicalInternshipApplications()) {
            clonedAb.addItem(internshipApp.deepCopy());
        }
        return clonedAb;
    }

    public static List<InternshipApplication> getTypicalInternshipApplications() {
        return new ArrayList<>(Arrays.asList(APPLE, BOFA, CITIBANK, DELL, EY, FIGMA, YAHOO));
    }
}
