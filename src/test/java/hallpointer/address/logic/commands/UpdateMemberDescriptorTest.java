package hallpointer.address.logic.commands;

import static hallpointer.address.logic.commands.CommandTestUtil.DESC_AMY;
import static hallpointer.address.logic.commands.CommandTestUtil.DESC_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hallpointer.address.logic.commands.UpdateMemberCommand.UpdateMemberDescriptor;
import hallpointer.address.testutil.UpdateMemberDescriptorBuilder;

public class UpdateMemberDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        UpdateMemberDescriptor descriptorWithSameValues = new UpdateMemberDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        UpdateMemberDescriptor updatedAmy = new UpdateMemberDescriptorBuilder(DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(updatedAmy));

        // different telegram -> returns false
        updatedAmy = new UpdateMemberDescriptorBuilder(DESC_AMY).withTelegram(VALID_TELEGRAM_BOB).build();
        assertFalse(DESC_AMY.equals(updatedAmy));

        // different room -> returns false
        updatedAmy = new UpdateMemberDescriptorBuilder(DESC_AMY).withRoom(VALID_ROOM_BOB).build();
        assertFalse(DESC_AMY.equals(updatedAmy));

        // different tags -> returns false
        updatedAmy = new UpdateMemberDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(updatedAmy));
    }

    @Test
    public void toStringMethod() {
        UpdateMemberDescriptor updateMemberDescriptor = new UpdateMemberDescriptor();
        String expected = UpdateMemberDescriptor.class.getCanonicalName() + "{name="
            + updateMemberDescriptor.getName().orElse(null)
            + ", telegram=" + updateMemberDescriptor.getTelegram().orElse(null)
            + ", room=" + updateMemberDescriptor.getRoom().orElse(null)
            + ", tags=" + updateMemberDescriptor.getTags().orElse(null)
            + ", totalPoints=" + updateMemberDescriptor.getTotalPoints().orElse(null)
            + ", sessions=" + updateMemberDescriptor.getSessions().orElse(null)
            + "}";
        assertEquals(expected, updateMemberDescriptor.toString());
    }
}
