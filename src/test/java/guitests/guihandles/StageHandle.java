package guitests.guihandles;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import guitests.GuiRobot;
import guitests.guihandles.exceptions.NodeNotFoundException;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Provides a handle to interact with a {@code Stage} in GUI tests. This abstract class serves as a base for specific
 * stage handles, enabling interaction with and querying of JavaFX {@code Stage} components.
 */
public abstract class StageHandle {

    /** An instance of {@code GuiRobot} to perform GUI interactions. */
    protected final GuiRobot guiRobot = new GuiRobot();

    private final Stage stage;

    /**
     * Constructs a {@code StageHandle} for the given {@code Stage}.
     *
     * @param stage The {@code Stage} to be handled. Must not be {@code null}.
     * @throws NullPointerException if the provided {@code Stage} is {@code null}.
     */
    public StageHandle(Stage stage) {
        this.stage = requireNonNull(stage);
    }

    /**
     * Closes the associated {@code Stage} and asserts that it is no longer showing.
     */
    public void close() {
        guiRobot.interact(stage::close);
        assertFalse(stage.isShowing());
    }

    /**
     * Requests focus for the associated {@code Stage}.
     */
    public void focus() {
        guiRobot.interact(stage::requestFocus);
    }

    /**
     * Checks if the associated {@code Stage} currently has focus.
     *
     * @return {@code true} if the {@code Stage} is focused; {@code false} otherwise.
     */
    public boolean isFocused() {
        return stage.isFocused();
    }

    /**
     * Retrieves a child {@code Node} from the {@code Stage}'s scene graph based on the given query.
     *
     * @param <T>   The type of the node to retrieve.
     * @param query The CSS selector query used to search for the node.
     * @return The node matching the query.
     * @throws NodeNotFoundException if no node matching the query is found.
     */
    protected <T extends Node> T getChildNode(String query) {
        Optional<T> node = guiRobot.from(stage.getScene().getRoot()).lookup(query).tryQuery();
        return node.orElseThrow(NodeNotFoundException::new);
    }
}
