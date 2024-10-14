package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class MessagesTest {
    @Test
    public void format_withoutNicknameWithOneRole_success() {
        Person person = new PersonBuilder().build();

        String expectedMessage = PersonBuilder.DEFAULT_NAME
                + "; Telegram: "
                + PersonBuilder.DEFAULT_TELEGRAM
                + "; Email: "
                + PersonBuilder.DEFAULT_EMAIL
                + "; Student Status: "
                + PersonBuilder.DEFAULT_STUDENT_STATUS
                + "; Roles: ["
                + PersonBuilder.DEFAULT_ROLES[0] + "]";

        assertEquals(Messages.format(person), expectedMessage);
    }

    @Test
    public void format_withoutNicknameWithMultipleRoles_success() {
        Person person = new PersonBuilder()
                .withRoles("President", "Admin", "Marketing")
                .build();

        String expectedMessage = PersonBuilder.DEFAULT_NAME
                + "; Telegram: "
                + PersonBuilder.DEFAULT_TELEGRAM
                + "; Email: "
                + PersonBuilder.DEFAULT_EMAIL
                + "; Student Status: "
                + PersonBuilder.DEFAULT_STUDENT_STATUS
                + "; Roles: [President][Admin][Marketing]";

        assertEquals(Messages.format(person), expectedMessage);
    }

    @Test
    public void format_withNicknameWithOneRole_success() {
        String validNickname = "John";
        Person person = new PersonBuilder()
                .withNickname(validNickname)
                .build();

        String expectedMessage = PersonBuilder.DEFAULT_NAME
                + "; Telegram: "
                + PersonBuilder.DEFAULT_TELEGRAM
                + "; Email: "
                + PersonBuilder.DEFAULT_EMAIL
                + "; Student Status: "
                + PersonBuilder.DEFAULT_STUDENT_STATUS
                + "; Nickname: "
                + validNickname
                + "; Roles: ["
                + PersonBuilder.DEFAULT_ROLES[0] + "]";

        assertEquals(Messages.format(person), expectedMessage);
    }

    @Test
    public void format_withNicknameWithMultipleRoles_success() {
        String validNickname = "John";
        Person person = new PersonBuilder()
                .withNickname(validNickname)
                .withRoles("President", "Admin", "Marketing")
                .build();

        String expectedMessage = PersonBuilder.DEFAULT_NAME
                + "; Telegram: "
                + PersonBuilder.DEFAULT_TELEGRAM
                + "; Email: "
                + PersonBuilder.DEFAULT_EMAIL
                + "; Student Status: "
                + PersonBuilder.DEFAULT_STUDENT_STATUS
                + "; Nickname: "
                + validNickname
                + "; Roles: [President][Admin][Marketing]";

        assertEquals(Messages.format(person), expectedMessage);
    }
}
