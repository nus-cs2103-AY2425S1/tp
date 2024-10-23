package guitests.guihandles;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import guitests.GuiRobot;
import guitests.guihandles.exceptions.NodeNotFoundException;
import javafx.scene.Node;

/**
 * A generic class that provides functionality to interact with a specific JavaFX {@code Node}. This abstract class
 * serves as a base for handling specific types of nodes in GUI tests.
 *
 * @param <T> The type of the node being handled, extending {@code Node}.
 */
public abstract class NodeHandle<T extends Node> {
    /** An instance of {@code GuiRobot} to perform GUI interactions. */
    protected final GuiRobot guiRobot = new GuiRobot();

    private final T rootNode;

    /**
     * Constructs a {@code NodeHandle} for the given root node.
     *
     * @param rootNode The root {@code Node} to be handled. Must not be {@code null}.
     * @throws NullPointerException if the provided {@code Node} is {@code null}.
     */
    protected NodeHandle(T rootNode) {
        this.rootNode = requireNonNull(rootNode);
    }

    /**
     * Retrieves the root node being handled.
     *
     * @return The root node of type {@code T}.
     */
    protected T getRootNode() {
        return rootNode;
    }

    /**
     * Retrieves a child {@code Node} from the root node based on the given query.
     *
     * @param <Q>   The type of the node to retrieve.
     * @param query The CSS selector query used to search for the child node.
     * @return The child node matching the query.
     * @throws NodeNotFoundException if no node matching the query is found.
     */
    protected <Q extends Node> Q getChildNode(String query) {
        Optional<Q> node = guiRobot.from(rootNode).lookup(query).tryQuery();
        return node.orElseThrow(NodeNotFoundException::new);
    }

    /**
     * Simulates a click on the root node.
     */
    public void click() {
        guiRobot.clickOn(rootNode);
    }
}
