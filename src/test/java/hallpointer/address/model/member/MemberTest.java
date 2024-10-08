package hallpointer.address.model.member;

import static hallpointer.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_HANDLE_BOB;
import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalMembers.ALICE;
import static hallpointer.address.testutil.TypicalMembers.BOB;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hallpointer.address.testutil.MemberBuilder;

public class MemberTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Member member = new MemberBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> member.getTags().remove(0));
    }

    @Test
    public void isSameMember() {
        // same object -> returns true
        assertTrue(ALICE.isSameMember(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameMember(null));

        // same name, all other attributes different -> returns true
        Member editedAlice = new MemberBuilder(ALICE).withTelegramHandle(VALID_TELEGRAM_HANDLE_BOB)
                .withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameMember(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new MemberBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameMember(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Member editedBob = new MemberBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameMember(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new MemberBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameMember(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Member aliceCopy = new MemberBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different member -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Member editedAlice = new MemberBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different telegram handle -> returns false
        editedAlice = new MemberBuilder(ALICE).withTelegramHandle(VALID_TELEGRAM_HANDLE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new MemberBuilder(ALICE).withRoom(VALID_ROOM_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new MemberBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Member.class.getCanonicalName() + "{name=" + ALICE.getName()
                + ", telegramHandle=" + ALICE.getTelegramHandle() + ", room=" + ALICE.getRoom()
                + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
