package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IC_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IC_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.PawPatrol;
import seedu.address.model.owner.Owner;

/**
 * A utility class containing a list of {@code Owner} objects to be used in tests.
 */
public class TypicalOwners {

    public static final Owner ALICE = new OwnerBuilder().withName("Alice Pauline")
        .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
        .withPhone("94351253").withIcNumber("S0000001I").build();
    public static final Owner BENSON = new OwnerBuilder().withName("Benson Meier")
        .withAddress("311, Clementi Ave 2, #02-25")
        .withEmail("johnd@example.com").withPhone("98765432").withIcNumber("S0000002G").build();
    public static final Owner CARL = new OwnerBuilder().withName("Carl Kurz").withPhone("95352563")
        .withEmail("heinz@example.com").withAddress("wall street").withIcNumber("S0000003E").build();
    public static final Owner DANIEL = new OwnerBuilder().withName("Daniel Meier").withPhone("87652533")
        .withEmail("cornelia@example.com").withAddress("10th street").withIcNumber("S0000004C").build();
    public static final Owner ELLE = new OwnerBuilder().withName("Elle Meyer").withPhone("9482224")
        .withEmail("werner@example.com").withAddress("michegan ave").withIcNumber("S0000005A").build();
    public static final Owner FIONA = new OwnerBuilder().withName("Fiona Kunz").withPhone("9482427")
        .withEmail("lydia@example.com").withAddress("little tokyo").withIcNumber("S0000006Z").build();
    public static final Owner GEORGE = new OwnerBuilder().withName("George Best").withPhone("9482442")
        .withEmail("anna@example.com").withAddress("4th street").withIcNumber("S0000007H").build();

    // Manually added
    public static final Owner HOON = new OwnerBuilder().withName("Hoon Meier").withPhone("8482424")
        .withEmail("stefan@example.com").withAddress("little india").withIcNumber("S0000008F").build();
    public static final Owner IDA = new OwnerBuilder().withName("Ida Mueller").withPhone("8482131")
        .withEmail("hans@example.com").withAddress("chicago ave").withIcNumber("S0000009D").build();

    // Manually added - Owner's details found in {@code CommandTestUtil}
    public static final Owner AMY = new OwnerBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
        .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withIcNumber(VALID_IC_NUMBER_AMY).build();
    public static final Owner BOB = new OwnerBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
        .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withIcNumber(VALID_IC_NUMBER_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalOwners() {
    } // prevents instantiation

    /**
     * Returns an {@code PawPatrol} with all the typical owners.
     */
    public static PawPatrol getTypicalPawPatrol() {
        PawPatrol ab = new PawPatrol();
        for (Owner owner : getTypicalOwners()) {
            ab.addOwner(owner);
        }
        return ab;
    }

    public static List<Owner> getTypicalOwners() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
