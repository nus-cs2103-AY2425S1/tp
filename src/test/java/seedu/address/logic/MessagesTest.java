package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.Contact;
import seedu.address.testutil.ContactBuilder;

public class MessagesTest {
    @Test
    public void format_withoutNicknameWithOneRole_success() {
        Contact contact = new ContactBuilder().build();

        String expectedMessage = ContactBuilder.DEFAULT_NAME
                + "; Telegram: "
                + ContactBuilder.DEFAULT_TELEGRAM
                + "; Email: "
                + ContactBuilder.DEFAULT_EMAIL
                + "; Student Status: "
                + ContactBuilder.DEFAULT_STUDENT_STATUS
                + "; Roles: ["
                + ContactBuilder.DEFAULT_ROLES[0] + "]";

        assertEquals(Messages.format(contact), expectedMessage);
    }

    @Test
    public void format_withoutNicknameWithMultipleRoles_success() {
        Contact contact = new ContactBuilder()
                .withRoles("President", "Admin", "Marketing")
                .build();

        String expectedMessage = ContactBuilder.DEFAULT_NAME
                + "; Telegram: "
                + ContactBuilder.DEFAULT_TELEGRAM
                + "; Email: "
                + ContactBuilder.DEFAULT_EMAIL
                + "; Student Status: "
                + ContactBuilder.DEFAULT_STUDENT_STATUS
                + "; Roles: [President][Admin][Marketing]";

        assertEquals(Messages.format(contact), expectedMessage);
    }

    @Test
    public void format_withNicknameWithOneRole_success() {
        String validNickname = "John";
        Contact contact = new ContactBuilder()
                .withNickname(validNickname)
                .build();

        String expectedMessage = ContactBuilder.DEFAULT_NAME
                + "; Telegram: "
                + ContactBuilder.DEFAULT_TELEGRAM
                + "; Email: "
                + ContactBuilder.DEFAULT_EMAIL
                + "; Student Status: "
                + ContactBuilder.DEFAULT_STUDENT_STATUS
                + "; Nickname: "
                + validNickname
                + "; Roles: ["
                + ContactBuilder.DEFAULT_ROLES[0] + "]";

        assertEquals(Messages.format(contact), expectedMessage);
    }

    @Test
    public void format_withNicknameWithMultipleRoles_success() {
        String validNickname = "John";
        Contact contact = new ContactBuilder()
                .withNickname(validNickname)
                .withRoles("President", "Admin", "Marketing")
                .build();

        String expectedMessage = ContactBuilder.DEFAULT_NAME
                + "; Telegram: "
                + ContactBuilder.DEFAULT_TELEGRAM
                + "; Email: "
                + ContactBuilder.DEFAULT_EMAIL
                + "; Student Status: "
                + ContactBuilder.DEFAULT_STUDENT_STATUS
                + "; Nickname: "
                + validNickname
                + "; Roles: [President][Admin][Marketing]";

        assertEquals(Messages.format(contact), expectedMessage);
    }
}
