package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ClientBook;
import seedu.address.model.client.Client;

/**
 * A utility class containing a list of {@code Client} objects to be used in tests.
 */
public class TypicalClients {
    public static final Client ALICE = new ClientBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .buildBuyer();
    public static final Client BENSON = new ClientBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withPhone("98765432")
            .buildSeller();
    public static final Client CARL = new ClientBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").buildBuyer();
    public static final Client DANIEL = new ClientBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").buildSeller();
    public static final Client ELLE = new ClientBuilder().withName("Elle Meyer").withPhone("94822242")
            .withEmail("werner@example.com").buildBuyer();
    public static final Client FIONA = new ClientBuilder().withName("Fiona Kunz").withPhone("94824272")
            .withEmail("lydia@example.com").buildSeller();
    public static final Client GEORGE = new ClientBuilder().withName("George Best").withPhone("94824422")
            .withEmail("anna@example.com").buildBuyer();

    // Manually added
    public static final Client HOON = new ClientBuilder().withName("Hoon Meier").withPhone("84824242")
            .withEmail("stefan@example.com").buildBuyer();
    public static final Client IDA = new ClientBuilder().withName("Ida Mueller").withPhone("84821312")
            .withEmail("hans@example.com").buildSeller();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Client AMY = new ClientBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).buildBuyer();
    public static final Client BOB = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .buildSeller();

    private TypicalClients() {} // prevents instantiation

    /**
     * Returns an {@code ClientBook} with all the typical buyer.
     */
    public static ClientBook getTypicalClientBook() {
        ClientBook cb = new ClientBook();
        for (Client client : getTypicalClients()) {
            cb.addClient(client);
        }
        return cb;
    }

    public static List<Client> getTypicalClients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
