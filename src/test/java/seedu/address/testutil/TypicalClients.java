package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIER_REJECT;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.CommandCommons;
import seedu.address.model.AgentAssist;
import seedu.address.model.client.Client;

/**
 * A utility class containing a list of {@code Client} objects to be used in tests.
 */
public class TypicalClients {

    public static final Client ALICE = new ClientBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withJob("Engineer")
            .withIncome(BigInteger.valueOf(3))
            .withTier("GOLD").withRemark("She is stingy").build();
    public static final Client BENSON = new ClientBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withIncome(BigInteger.valueOf(40)).withJob("Doctor")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTier("Bronze").withRemark("He is rich").build();
    public static final Client CARL = new ClientBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withJob("Lawyer")
            .withIncome(BigInteger.valueOf(40))
            .withRemark(CommandCommons.DEFAULT_REMARK).build();
    public static final Client DANIEL = new ClientBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withJob("Artist")
            .withIncome(BigInteger.valueOf(30))
            .withTier("REJECT")
            .withRemark(CommandCommons.DEFAULT_REMARK).build();
    public static final Client ELLE = new ClientBuilder().withName("Elle Meyer").withPhone("94822240")
            .withEmail("werner@example.com").withAddress("michegan ave").withJob("Nurse")
            .withIncome(BigInteger.valueOf(20))
            .withRemark(CommandCommons.DEFAULT_REMARK).build();
    public static final Client FIONA = new ClientBuilder().withName("Fiona Kunz").withPhone("94824271")
            .withEmail("lydia@example.com").withAddress("little tokyo").withJob("Chef")
            .withIncome(BigInteger.valueOf(45))
            .withRemark("Rich japanese client").build();
    public static final Client GEORGE = new ClientBuilder().withName("George Best").withPhone("94823442")
            .withEmail("anna@example.com").withAddress("4th street").withJob("Teacher")
            .withIncome(BigInteger.valueOf(50))
            .withRemark(CommandCommons.DEFAULT_REMARK).build();

    // Manually added
    public static final Client HOON = new ClientBuilder().withName("Hoon Meier").withPhone("84824234")
            .withEmail("stefan@example.com").withAddress("little india").withJob("Architect")
            .withIncome(BigInteger.valueOf(40))
            .withRemark(CommandCommons.DEFAULT_REMARK).build();
    public static final Client IDA = new ClientBuilder().withName("Ida Mueller").withPhone("84822131")
            .withEmail("hans@example.com").withAddress("chicago ave").withJob("Writer")
            .withIncome(BigInteger.valueOf(50))
            .withRemark(CommandCommons.DEFAULT_REMARK).build();
    // Manually added - Client's details found in {@code CommandTestUtil}
    public static final Client AMY = new ClientBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withJob("Teacher")
            .withIncome(BigInteger.valueOf(999999999))
            .withTier(VALID_TIER_REJECT)
            .withRemark(CommandCommons.DEFAULT_REMARK).build();
    public static final Client BOB = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withJob("Developer")
            .withIncome(BigInteger.valueOf(9))
            .withTier(VALID_TIER_REJECT)
            .withRemark(CommandCommons.DEFAULT_REMARK).build();

    private TypicalClients() {} // prevents instantiation

    /**
     * Returns an {@code AgentAssist} with all the typical clients.
     */
    public static AgentAssist getTypicalAgentAssist() {
        AgentAssist ab = new AgentAssist();
        for (Client client : getTypicalClients()) {
            ab.addClient(client);
        }
        return ab;
    }

    public static List<Client> getTypicalClients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

}
