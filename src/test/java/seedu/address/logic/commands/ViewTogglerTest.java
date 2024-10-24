package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalOwners.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;

public class ViewTogglerTest {

    @Test
    public void test_validViewToggleCreation() {
        assertEquals(new ViewToggler(ListPetCommand.MESSAGE_SUCCESS).getCommandType(),
                ViewToggler.LIST_PET_COMMAND);
        assertEquals(new ViewToggler(ListOwnerCommand.MESSAGE_SUCCESS).getCommandType(),
                ViewToggler.LIST_OWNER_COMMAND);

        assertEquals(new ViewToggler(ListBothCommand.MESSAGE_SUCCESS).getCommandType(),
                ViewToggler.LIST_BOTH_COMMAND);

        assertEquals(new ViewToggler(String.format(LinkCommand.MESSAGE_SUCCESS, 4,
                        Messages.format(ALICE))).getCommandType(),
                ViewToggler.LINK_OWNER_TO_PET_COMMAND);

        assertEquals(new ViewToggler(AddCommand.MESSAGE_SUCCESS).getCommandType(),
                ViewToggler.OTHER_COMMAND);
    }
}
