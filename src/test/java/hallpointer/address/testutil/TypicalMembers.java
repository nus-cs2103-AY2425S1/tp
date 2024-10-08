package hallpointer.address.testutil;

import static hallpointer.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_ROOM_AMY;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_HANDLE_AMY;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_HANDLE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hallpointer.address.model.AddressBook;
import hallpointer.address.model.member.Member;

/**
 * A utility class containing a list of {@code Member} objects to be used in tests.
 */
public class TypicalMembers {

    public static final Member ALICE = new MemberBuilder().withName("Alice Pauline")
            .withRoom("6/3/20")
            .withTelegramHandle("94351253")
            .withTags("friends").build();
    public static final Member BENSON = new MemberBuilder().withName("Benson Meier")
            .withRoom("6/10/95")
            .withTelegramHandle("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Member CARL = new MemberBuilder().withName("Carl Kurz").withTelegramHandle("95352563")
            .withRoom("7/8/10").build();
    public static final Member DANIEL = new MemberBuilder().withName("Daniel Meier").withTelegramHandle("87652533")
            .withRoom("1/3/3").withTags("friends").build();
    public static final Member ELLE = new MemberBuilder().withName("Elle Meyer").withTelegramHandle("9482224")
            .withRoom("4/5/55").build();
    public static final Member FIONA = new MemberBuilder().withName("Fiona Kunz").withTelegramHandle("9482427")
            .withRoom("8/10/300").build();
    public static final Member GEORGE = new MemberBuilder().withName("George Best").withTelegramHandle("9482442")
            .withRoom("90/2/8").build();

    // Manually added
    public static final Member HOON = new MemberBuilder().withName("Hoon Meier").withTelegramHandle("8482424")
            .withRoom("7/7/45").build();
    public static final Member IDA = new MemberBuilder().withName("Ida Mueller").withTelegramHandle("8482131")
            .withRoom("6/4/38").build();

    // Manually added - Member's details found in {@code CommandTestUtil}
    public static final Member AMY = new MemberBuilder().withName(VALID_NAME_AMY)
            .withTelegramHandle(VALID_TELEGRAM_HANDLE_AMY)
            .withRoom(VALID_ROOM_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Member BOB = new MemberBuilder().withName(VALID_NAME_BOB)
            .withTelegramHandle(VALID_TELEGRAM_HANDLE_BOB)
            .withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalMembers() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical members.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Member member : getTypicalMembers()) {
            ab.addMember(member);
        }
        return ab;
    }

    public static List<Member> getTypicalMembers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
