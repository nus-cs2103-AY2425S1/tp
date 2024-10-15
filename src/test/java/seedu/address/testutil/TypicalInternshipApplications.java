package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOFA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOFA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOFA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOFA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalInternshipApplications.getTypicalInternshipApplications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.internshipapplication.InternshipApplication;

/**
 * A utility class containing a list of {@code InternshipApplication} objects to be used in tests.
 */
public class TypicalInternshipApplications {

    public static final InternshipApplication AIRBNB = new InternshipApplicationBuilder().withName("Airbnb")
            .withEmail("airbnb@example.com")
            .withRole("Software Engineer Intern")
            .withDate("01/01/24")
            .build();
    public static final InternshipApplication BYTEDANCE = new InternshipApplicationBuilder().withName("ByteDance")
            .withEmail("bytedance@example.com")
            .withRole("Frontend Developer Intern")
            .withDate("02/02/24")
            .build();
    public static final InternshipApplication CITIBANK = new InternshipApplicationBuilder().withName("Citibank")
            .withEmail("citibank@example.com")
            .withRole("Backend Developer Intern")
            .withDate("03/03/24")
            .build();
    public static final InternshipApplication DELL = new InternshipApplicationBuilder().withName("Dell")
            .withEmail("dell@example.com")
            .withRole("Computer Engineer Intern")
            .withDate("04/04/24")
            .build();
    public static final InternshipApplication EY = new InternshipApplicationBuilder().withName("Ernest & Young")
            .withEmail("ey@example.com")
            .withRole("Fintech Intern")
            .withDate("05/05/24")
            .build();
    public static final InternshipApplication FIGMA = new InternshipApplicationBuilder().withName("Figma")
            .withEmail("figma@example.com")
            .withRole("UI/UX Intern")
            .withDate("06/06/24")
            .build();
    public static final InternshipApplication GOOGLE = new InternshipApplicationBuilder().withName("Google")
            .withEmail("google@gmail.com")
            .withDate("04/09/98")
            .withRole("SWE")
            .build();

    // Manually added
    public static final InternshipApplication HOON = new InternshipApplicationBuilder().withName("Hoon Company")
            .withEmail("hoon@example.com")
            .withRole("Hoon Intern")
            .withDate("01/01/24")
            .build();
    public static final InternshipApplication IDA = new InternshipApplicationBuilder().withName("Ida Company")
            .withEmail("ida@example.com")
            .withRole("Ida Intern")
            .withDate("02/02/24")
            .build();

    public static final InternshipApplication YAHOO = new InternshipApplicationBuilder().withName("Yahoo")
            .withEmail("yahoo@yahoo.com")
            .withDate("01/01/94")
            .withRole("Clerk")
            .build();

    // Manually added - InternshipApplication's details found in {@code CommandTestUtil}
    public static final InternshipApplication APPLE = new InternshipApplicationBuilder().withName(VALID_NAME_APPLE)
            .withEmail(VALID_EMAIL_APPLE).withRole(VALID_ROLE_APPLE).build();
    public static final InternshipApplication BOFA = new InternshipApplicationBuilder().withName(VALID_NAME_BOFA)
            .withEmail(VALID_EMAIL_BOFA).withRole(VALID_ROLE_BOFA).build();

    public static final String KEYWORD_MATCHING_HOON = "HOON"; // A keyword that matches HOON

    private TypicalInternshipApplications() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook<InternshipApplication> getTypicalAddressBook() {
        AddressBook<InternshipApplication> ab = new AddressBook<>();
        for (InternshipApplication internshipApp : getTypicalInternshipApplications()) {
            ab.addItem(internshipApp);
        }
        return ab;
    }

    public static List<InternshipApplication> getTypicalInternshipApplications() {
        return new ArrayList<>(Arrays.asList(APPLE, BOFA, CITIBANK, DELL, EY, FIGMA, GOOGLE, YAHOO));
    }
}
