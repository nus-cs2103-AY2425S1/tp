package tuteez.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import tuteez.model.person.Person;
import tuteez.testutil.PersonBuilder;

public class UiUtilTest {

    @BeforeAll
    public static void initToolkit() {
        Platform.startup(() -> {});
    }

    @Test
    public void setNameText_validAndInvalidPerson_nameLabelIsUpdated() {
        Label nameLabel = new Label();
        // Equivalence partition: Valid person with name
        Person john = new PersonBuilder().withName("John Doe").build();
        UiUtil.setNameText(nameLabel, john);
        assertEquals("John Doe", nameLabel.getText());
        // Equivalence partition: Null parameters
        assertThrows(AssertionError.class, () -> UiUtil.setNameText(nameLabel, null));
        assertThrows(AssertionError.class, () -> UiUtil.setNameText(null, john));
    }
    @Test
    public void setPhoneText_validPerson_phoneLabelIsUpdated() {
        Label phoneLabel = new Label();
        // Equivalence partition: Valid person with phone
        Person john = new PersonBuilder().withName("John Doe").withPhone("82223238").build();
        UiUtil.setPhoneText(phoneLabel, john);
        assertEquals("82223238", phoneLabel.getText());
        // Equivalence partition: Null parameters
        assertThrows(AssertionError.class, () -> UiUtil.setPhoneText(phoneLabel, null));
        assertThrows(AssertionError.class, () -> UiUtil.setPhoneText(null, john));
    }

    @Test
    public void setAddressText_validAndInvalidAddresses_addressLabelVisibility() {
        // Equivalence partition: Person without address
        Label addressLabel = new Label();
        Person personWithoutAddress = new PersonBuilder().withAddress(null).build();
        UiUtil.setAddressText(addressLabel, personWithoutAddress);
        assertFalse(addressLabel.isVisible());
        // Equivalence partition: Person with valid address
        Person personWithAddress = new PersonBuilder().withAddress("123 Main St").build();
        UiUtil.setAddressText(addressLabel, personWithAddress);
        assertTrue(addressLabel.isVisible(), "Address label should be visible for person with valid address");
        assertEquals("123 Main St", addressLabel.getText(), "Address label should display the correct address");

        // Equivalence partition: Null parameters
        assertThrows(AssertionError.class, () -> UiUtil.setAddressText(null, personWithAddress));
        assertThrows(AssertionError.class, () -> UiUtil.setAddressText(addressLabel, null));
    }

    @Test
    public void setEmailText_validAndInvalidEmails_emailLabelVisibility() {
        // Equivalence partition: Person without email
        Label emailLabel = new Label();
        Person personWithoutEmail = new PersonBuilder().withEmail(null).build();
        UiUtil.setEmailText(emailLabel, personWithoutEmail);
        assertFalse(emailLabel.isVisible(), "Email label should be hidden for person without email");

        // Equivalence partition: Person with valid email
        Person personWithEmail = new PersonBuilder().withEmail("john.doe@example.com").build();
        UiUtil.setEmailText(emailLabel, personWithEmail);
        assertTrue(emailLabel.isVisible(), "Email label should be visible for person with valid email");
        assertEquals("john.doe@example.com", emailLabel.getText(), "Email label should display the correct email");

        // Equivalence partition: Null parameters
        assertThrows(AssertionError.class, () -> UiUtil.setEmailText(null, personWithEmail));
        assertThrows(AssertionError.class, () -> UiUtil.setEmailText(emailLabel, null));
    }

    @Test
    public void setTelegramUsernameText_validAndInvalidUsernames_telegramLabelVisibility() {
        // Equivalence partition: Person without Telegram username
        Label telegramLabel = new Label();
        Person personWithoutUsername = new PersonBuilder().withTelegram(null).build();
        UiUtil.setTelegramUsernameText(telegramLabel, personWithoutUsername);
        assertFalse(telegramLabel.isVisible(), "Telegram label should be hidden for person without username");

        // Equivalence partition: Person with valid Telegram username
        Person personWithValidUsername = new PersonBuilder().withTelegram("john_doe").build();
        UiUtil.setTelegramUsernameText(telegramLabel, personWithValidUsername);
        assertTrue(telegramLabel.isVisible(), "Telegram label should be visible for person with valid username");
        assertEquals("@john_doe", telegramLabel.getText(), "Telegram label should display the correct username");

        // Equivalence partition: Null parameters
        assertThrows(AssertionError.class, () -> UiUtil.setTelegramUsernameText(null, personWithoutUsername));
        assertThrows(AssertionError.class, () -> UiUtil.setTelegramUsernameText(telegramLabel, null));
    }
    @Test
    public void setTags_validAndInvalidTags_tagsPaneUpdatedCorrectly() {
        // Equivalence partition: Person with no tags
        FlowPane tagsPaneEmpty = new FlowPane();
        Person personWithoutTags = new PersonBuilder().withTags().build();
        UiUtil.setTags(tagsPaneEmpty, personWithoutTags);
        assertTrue(tagsPaneEmpty.getChildren().isEmpty(), "Tags pane should be empty for person without tags");

        // Equivalence partition: Person with multiple tags
        FlowPane tagsPaneWithTags = new FlowPane();
        Person personWithTags = new PersonBuilder().withTags("a", "b", "c").build();
        UiUtil.setTags(tagsPaneWithTags, personWithTags);
        assertEquals(3, tagsPaneWithTags.getChildren().size(), "Tags pane should have 3 tags");
        assertEquals("a", ((Label) (tagsPaneWithTags.getChildren().get(0))).getText());
        assertEquals("b", ((Label) (tagsPaneWithTags.getChildren().get(1))).getText());
        assertEquals("c", ((Label) (tagsPaneWithTags.getChildren().get(2))).getText());

        // Equivalence Partition: Null parameters
        assertThrows(AssertionError.class, () -> UiUtil.setTags(null, personWithTags));
        assertThrows(AssertionError.class, () -> UiUtil.setTags(tagsPaneWithTags, null));
    }

}
