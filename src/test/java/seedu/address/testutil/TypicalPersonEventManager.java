package seedu.address.testutil;

import seedu.address.model.types.common.PersonEventManager;
import seedu.address.model.types.event.Event;
import seedu.address.model.types.person.Person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A utility class containing a list of {@code Person} linked to {@code Event} objects to be used in tests.
 */
public class TypicalPersonEventManager {
    public static final Event ANIME = new EventBuilder().withName("Anime Expo")
            .withAddress("123, Jurong West Ave 6, #08-111").withStartTime(nowPlusDays(2))
            .withTags("hobby").build();
    public static final Event BARBEQUE = new EventBuilder().withName("Barbeque Party")
            .withAddress("231, Yishun Ave 2, #10-03").withStartTime(nowPlusDays(4))
            .withTags("hobby", "friends").build();
    public static final Event CONCERT = new EventBuilder().withName("Concert Night")
            .withAddress("123, Orchard Rd, #05-01").withStartTime(nowPlusDays(6))
            .withTags("music").build();

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();

    private TypicalPersonEventManager() {}; //prevents instantiation

    public static PersonEventManager getTypicalPersonEventManager() {
        PersonEventManager pem = new PersonEventManager();

        pem.addEvent(ANIME);
        pem.addEvent(BARBEQUE);
        pem.addEvent(CONCERT);

        pem.addPersonToEvent(ALICE, ANIME);

        pem.addPersonToEvent(ALICE, BARBEQUE);
        pem.addPersonToEvent(BENSON, BARBEQUE);

        pem.addPersonToEvent(CARL, BARBEQUE);

        return pem;
    }

    public static String nowPlusDays(long x) {
        LocalDateTime added = LocalDateTime.now().plusDays(x);
        return added.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
