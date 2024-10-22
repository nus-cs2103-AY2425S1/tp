package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.DESC_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.DESC_BOTTLE;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.sellsavvy.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.commands.generalcommands.ClearCommand;
import seedu.sellsavvy.logic.commands.ordercommands.EditOrderCommand.EditOrderDescriptor;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;
import seedu.sellsavvy.model.UserPrefs;

public class EditOrderCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    //TODO execute test

    @Test
    public void equals() {
        final EditOrderCommand standardCommand = new EditOrderCommand(INDEX_FIRST_ORDER, DESC_ATLAS);

        // same values -> returns true
        EditOrderDescriptor copyDescriptor = new EditOrderDescriptor(DESC_ATLAS);
        EditOrderCommand commandWithSameValues = new EditOrderCommand(INDEX_FIRST_ORDER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditOrderCommand(INDEX_SECOND_ORDER, DESC_ATLAS)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditOrderCommand(INDEX_FIRST_ORDER, DESC_BOTTLE)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditOrderDescriptor editOrderDescriptor = new EditOrderDescriptor();
        EditOrderCommand editOrderCommand = new EditOrderCommand(index, editOrderDescriptor);
        String expected = EditOrderCommand.class.getCanonicalName() + "{index=" + index + ", editOrderDescriptor="
                + editOrderDescriptor + "}";
        assertEquals(expected, editOrderCommand.toString());
    }
}
