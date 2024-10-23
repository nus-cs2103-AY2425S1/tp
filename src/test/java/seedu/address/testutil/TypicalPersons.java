package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withTelegram("alicePauline").withEmail("alice@example.com")
            .withPhone("94351253")
            .build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withTelegram("bensonMeier")
            .withEmail("johnd@example.com").withPhone("98765432")
            .build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withTelegram("carlKurz").withFavourite().build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withTelegram("danMeier").withFavourite().build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("94822224")
            .withEmail("werner@example.com").withTelegram("elleMeyer").withFavourite().build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("94822427")
            .withEmail("lydia@example.com").withTelegram("fionaKunz").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("94822442")
            .withEmail("anna@example.com").withTelegram("georgeBest").build();
    public static final Person JAVIER = new PersonBuilder().withName("Javier Tan").withPhone("93453421")
            .withEmail("javier@gmail.com").withTelegram("javierTan").withRoles("friend").build();
    public static final Person KELLY = new PersonBuilder().withName("Kelly Lim").withPhone("89453765")
            .withEmail("kelly@gmail.com").withTelegram("kellyLim").withRoles("relative").build();
    public static final Person LENOR = new PersonBuilder().withName("Lenor Kim").withPhone("90784567")
            .withEmail("lenor@gmail.com").withTelegram("lenor").withRoles("relative", "friend").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("84824242")
            .withEmail("stefan@example.com").withTelegram("hoonMeier").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("84821312")
            .withEmail("hans@example.com").withTelegram("idaMueller").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withTelegram(VALID_TELEGRAM_AMY).withRoles(VALID_ROLE_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withTelegram(VALID_TELEGRAM_BOB)
            .withRoles(VALID_ROLE_HUSBAND, VALID_ROLE_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Person> getTypicalPersonsWithTags() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, JAVIER, KELLY, LENOR));
    }

    /**
     * Returns an {@code AddressBook} with all the typical persons including tags.
     */
    public static AddressBook getTypicalAddressBookWithTags() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersonsWithTags()) {
            ab.addPerson(person);
        }
        return ab;
    }
}
