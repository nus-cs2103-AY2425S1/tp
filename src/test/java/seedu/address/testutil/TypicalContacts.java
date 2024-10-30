package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_ADMIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_PRESIDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_HANDLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_HANDLE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.contact.Contact;

/**
 * A utility class containing a list of {@code Contact} objects to be used in tests.
 */
public class TypicalContacts {
    public static final String ALICE_TELEGRAMHANDLE = "94351253";
    public static final String ALICE_EMAIL = "alice@example.com";
    public static final String BOB_NICKNAME = "booby";

    public static final Contact ALICE = new ContactBuilder().withName("Alice Pauline")
            .withStudentStatus("undergraduate 1").withEmail(ALICE_EMAIL)
            .withTelegramHandle(ALICE_TELEGRAMHANDLE)
            .withRoles("Admin").build();
    public static final Contact BENSON = new ContactBuilder().withName("Benson Meier")
            .withStudentStatus("undergraduate 2")
            .withEmail("johnd@example.com").withTelegramHandle("98765432")
            .withRoles("President", "Marketing").build();
    public static final Contact CARL = new ContactBuilder().withName("Carl Kurz").withTelegramHandle("95352563")
            .withEmail("heinz@example.com")
            .withStudentStatus("undergraduate 3")
            .withRoles("External Relations")
            .build();
    public static final Contact DANIEL = new ContactBuilder().withName("Daniel Meier").withTelegramHandle("87652533")
            .withEmail("cornelia@example.com")
            .withStudentStatus("undergraduate 4")
            .withRoles("Events (external)")
            .build();
    public static final Contact ELLE = new ContactBuilder().withName("Elle Meyer").withTelegramHandle("9482224")
            .withEmail("werner@example.com")
            .withStudentStatus("undergraduate 5")
            .withRoles("Events (internal)")
            .build();
    public static final Contact FIONA = new ContactBuilder().withName("Fiona Kunz").withTelegramHandle("9482427")
            .withEmail("lydia@example.com")
            .withStudentStatus("masters")
            .withRoles("Events (external)")
            .build();
    public static final Contact GEORGE = new ContactBuilder().withName("George Best").withTelegramHandle("9482442")
            .withEmail("anna@example.com")
            .withStudentStatus("phd")
            .withRoles("Vice President")
            .build();

    // Manually added
    public static final Contact HOON = new ContactBuilder().withName("Hoon Meier").withTelegramHandle("8482424")
            .withEmail("stefan@example.com").withStudentStatus("undergraduate 6").build();
    public static final Contact IDA = new ContactBuilder().withName("Ida Mueller").withTelegramHandle("8482131")
            .withEmail("hans@example.com").withStudentStatus("undergraduate 1").build();

    // Manually added - Contact's details found in {@code CommandTestUtil}
    public static final Contact AMY = new ContactBuilder().withName(VALID_NAME_AMY)
            .withTelegramHandle(VALID_TELEGRAM_HANDLE_AMY)
            .withEmail(VALID_EMAIL_AMY).withStudentStatus(VALID_STUDENT_STATUS_AMY).withRoles(VALID_ROLE_ADMIN).build();
    public static final Contact BOB = new ContactBuilder().withName(VALID_NAME_BOB)
            .withTelegramHandle(VALID_TELEGRAM_HANDLE_BOB)
            .withEmail(VALID_EMAIL_BOB).withStudentStatus(VALID_STUDENT_STATUS_BOB)
            .withRoles(VALID_ROLE_PRESIDENT, VALID_ROLE_ADMIN)
            .build();
    public static final Contact BOB_HASSAMETELE_ALICE = new ContactBuilder().withName(VALID_NAME_BOB + "1")
            .withTelegramHandle(ALICE_TELEGRAMHANDLE)
            .withEmail(VALID_EMAIL_BOB + "1").withStudentStatus(VALID_STUDENT_STATUS_BOB)
            .withRoles(VALID_ROLE_ADMIN)
            .build();
    public static final Contact BOB_HASSAMEEMAIL_ALICE = new ContactBuilder().withName(VALID_NAME_BOB + "2")
            .withTelegramHandle(VALID_TELEGRAM_HANDLE_BOB)
            .withEmail(ALICE_EMAIL).withStudentStatus(VALID_STUDENT_STATUS_BOB)
            .withRoles(VALID_ROLE_ADMIN)
            .build();
    public static final Contact BOBNICK = new ContactBuilder().withName(VALID_NAME_BOB)
            .withTelegramHandle(VALID_TELEGRAM_HANDLE_BOB + "3")
            .withEmail(VALID_EMAIL_BOB + "3").withStudentStatus(VALID_STUDENT_STATUS_BOB)
            .withRoles(VALID_ROLE_PRESIDENT, VALID_ROLE_ADMIN)
            .withNickname(BOB_NICKNAME)
            .build();
    public static final Contact BOB_HASSAMENICK_BOBNICK = new ContactBuilder().withName(VALID_NAME_BOB)
            .withTelegramHandle(VALID_TELEGRAM_HANDLE_BOB + "4")
            .withEmail(VALID_EMAIL_BOB + "4").withStudentStatus(VALID_STUDENT_STATUS_BOB)
            .withRoles(VALID_ROLE_ADMIN)
            .withNickname(BOB_NICKNAME)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalContacts() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical contacts.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Contact contact : getTypicalContacts()) {
            ab.addContact(contact);
        }
        return ab;
    }

    public static List<Contact> getTypicalContacts() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
