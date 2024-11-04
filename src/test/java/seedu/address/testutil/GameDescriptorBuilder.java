package seedu.address.testutil;

import seedu.address.logic.commands.AddGameCommand.GameDescriptor;
import seedu.address.model.game.Game;
import seedu.address.model.game.Role;
import seedu.address.model.game.SkillLevel;
import seedu.address.model.game.Username;


/**
 * A utility class to help with building GameDescriptor objects.
 */
public class GameDescriptorBuilder {

    private GameDescriptor descriptor;

    public GameDescriptorBuilder() {
        descriptor = new GameDescriptor();
    }

    public GameDescriptorBuilder(GameDescriptor descriptor) {
        this.descriptor = new GameDescriptor(descriptor);
    }

    /**
     * Returns an {@code GameDescriptor} with fields containing {@code game}'s details
     */
    public GameDescriptorBuilder(Game game) {
        descriptor = new GameDescriptor();
        descriptor.setGame(game.getGameName());
        descriptor.setUsername(game.getUsername());
        descriptor.setRole(game.getRole());
        descriptor.setSkillLevel(game.getSkillLevel());
    }

    /**
     * Sets the {@code Username} of the {@code GameDescriptor} that we are building.
     */
    public GameDescriptorBuilder withGame(String game) {
        descriptor.setGame(game);
        return this;
    }

    /**
     * Sets the {@code Username} of the {@code GameDescriptor} that we are building.
     */
    public GameDescriptorBuilder withUsername(String username) {
        descriptor.setUsername(new Username(username));
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code GameDescriptor} that we are building.
     */
    public GameDescriptorBuilder withRole(String role) {
        descriptor.setRole(new Role(role));
        return this;
    }

    /**
     * Sets the {@code SkillLevel} of the {@code GameDescriptor} that we are building.
     */
    public GameDescriptorBuilder withSkillLevel(String skillLevel) {
        descriptor.setSkillLevel(new SkillLevel(skillLevel));
        return this;
    }

    public GameDescriptor build() {
        return descriptor;
    }
}
