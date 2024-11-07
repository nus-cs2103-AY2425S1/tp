package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;



public class PersonDetailedViewContentManagerTest {

    private PersonDetailedViewContentManager contentManager;
    private Person testPerson;

    @BeforeEach
    public void setUp() {
        testPerson = TypicalPersons.ALICE;
        contentManager = new PersonDetailedViewContentManager(testPerson);
    }

    @Test
    public void setupProfileImage_setsProfileImageCorrectly() {
        ImageView profileImageView = new ImageView();
        contentManager.setupProfileImage(profileImageView);

        assertNotNull(profileImageView.getImage(), "Profile image should be set.");

        assertTrue(profileImageView.getClip() instanceof Circle, "Profile image should have a circular clip.");
    }

    @Test
    public void getName_returnsCorrectName() {
        assertEquals("Alice Pauline",
                contentManager.getName(), "Name should match the person's full name.");
    }

    @Test
    public void getPhone_returnsCorrectPhone() {
        assertEquals("+65 94351253",
                contentManager.getPhone(), "Phone should be formatted with country code.");
    }

    @Test
    public void getAddress_returnsCorrectAddress() {
        assertEquals("Address: 123, Jurong West Ave 6, #08-111",
                contentManager.getAddress(), "Address should be prefixed correctly.");
    }

    @Test
    public void getBirthday_returnsCorrectBirthday() {
        assertEquals("Birthday: 01 01 2002",
                contentManager.getBirthday(), "Birthday should match the person's date.");
    }

    @Test
    public void getAge_returnsCorrectAge() {
        assertEquals("Age: " + testPerson.getAge().value,
                contentManager.getAge(), "Age should be formatted correctly.");
    }

    @Test
    public void getEmail_returnsCorrectEmail() {
        assertEquals("alice@example.com",
                contentManager.getEmail(), "Email should match the person's email.");
    }

    @Test
    public void getHasPaidStatus_returnsCorrectStatus() {
        assertEquals("Paid status: Not Paid",
                contentManager.getHasPaidStatus(), "Paid status should indicate 'Not Paid'.");
    }

    @Test
    public void getFrequency_returnsCorrectFrequency() {
        assertEquals("Policy Renewal Frequency: 0 month(s)",
                contentManager.getFrequency(),
                "Frequency should indicate renewal period in months.");
    }

    @Test
    public void getYoungAdultMessage_returnsCorrectMessage() {
        String expectedMessage = "Hi Alice Pauline! ☕\n\n"
                + "I’d love to grab a coffee with you sometime to "
                + "chat about ways to set up a strong financial foundation as "
                + "you start your career. No pressure—just a relaxed conversation to "
                + "answer any questions you might have about planning for the future.\n\n"
                + "Let me know if you're up for it, and we can pick a time that works for you!";
        assertEquals(expectedMessage,
                contentManager.getYoungAdultMessage(),
                "Young adult message should match the expected format.");
    }

    @Test
    public void getMidCareerMessage_returnsCorrectMessage() {
        String expectedMessage = "Hi Alice Pauline,\n\n"
                + "Hope all’s well! I thought this might be a good time to "
                + "check in and discuss ways to keep your financial goals on track. "
                + "Whether it’s planning for upcoming life changes or just staying ahead of things, "
                + "a quick chat could be helpful.\n\n"
                + "Let me know when you’re available, and we can set up a time that suits you best!";
        assertEquals(expectedMessage,
                contentManager.getMidCareerMessage(),
                "Mid-career message should match the expected format.");
    }

    @Test
    public void getPreRetireeMessage_returnsCorrectMessage() {
        String expectedMessage = "Hello Alice Pauline,\n\n"
                + "I’d love to meet up and go over any financial questions "
                + "you might have as you look toward retirement. Whether it’s planning travel, "
                + "helping family, or just managing things day-to-day, "
                + "there are a lot of options we can explore together.\n\n"
                + "Let me know if you’d like to catch up soon"
                + "—I’m happy to meet whenever it’s convenient for you!";
        assertEquals(expectedMessage,
                contentManager.getPreRetireeMessage(),
                "Pre-retiree message should match the expected format.");
    }

    @Test
    public void getYoungAdultButtonText_returnsCorrectLabel() {
        assertEquals("Young Adult",
                contentManager.getYoungAdultButtonText(), "Young adult button text should match.");
    }

    @Test
    public void getMidCareerButtonText_returnsCorrectLabel() {
        assertEquals("Mid-Career",
                contentManager.getMidCareerButtonText(), "Mid-career button text should match.");
    }

    @Test
    public void getPreRetireeButtonText_returnsCorrectLabel() {
        assertEquals("Pre-Retiree",
                contentManager.getPreRetireeButtonText(), "Pre-retiree button text should match.");
    }
}
