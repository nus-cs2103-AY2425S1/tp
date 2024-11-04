package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_GAME;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.GameDescriptorBuilder;

public class GameDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        AddGameCommand.GameDescriptor descriptorWithSameValues = new AddGameCommand.GameDescriptor(DESC_GAME);
        assertTrue(DESC_GAME.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_GAME.equals(DESC_GAME));

        // null -> returns false
        assertFalse(DESC_GAME.equals(null));

        // different types -> returns false
        assertFalse(DESC_GAME.equals(5));

        // different username -> returns false
        AddGameCommand.GameDescriptor editedGame = new GameDescriptorBuilder(DESC_GAME).withUsername("Bob").build();
        assertFalse(DESC_GAME.equals(editedGame));

        // different role -> returns false
        editedGame = new GameDescriptorBuilder(DESC_GAME).withRole("DPS").build();
        assertFalse(DESC_GAME.equals(editedGame));

        // different skillLevel -> returns false
        editedGame = new GameDescriptorBuilder(DESC_GAME).withSkillLevel("Noob").build();
        assertFalse(DESC_GAME.equals(editedGame));
    }

}
