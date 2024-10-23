package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import spleetwaise.address.model.person.Person;

/**
 * Handle for the person list panel UI component in the application. This class provides methods to interact with and
 * test the state of the list of persons, including navigation, selection, and card retrieval functionalities.
 */
public class PersonListPanelHandle extends NodeHandle<ListView<Person>> {
    /** CSS selector for the person list view node. */
    public static final String PERSON_LIST_VIEW_ID = "#personListView";
    /** CSS selector for the card pane node. */
    private static final String CARD_PANE_ID = "#cardPane";
    /** The last remembered selected person card, used for tracking selections. */
    private Optional<Person> lastRememberedSelectedPersonCard;

    /**
     * Creates a {@code PersonListPanelHandle} for the given person list panel node.
     *
     * @param personListPanelNode The root node of the person list panel.
     */
    public PersonListPanelHandle(ListView<Person> personListPanelNode) {
        super(personListPanelNode);
    }

    /**
     * Navigates to the person card at the specified index in the list view.
     *
     * @param index The index of the person card to navigate to.
     * @throws IllegalArgumentException If the index is out of bounds.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Retrieves the {@code PersonCardHandle} for the person card at the specified index.
     *
     * @param index The index of the person card.
     * @return The {@code PersonCardHandle} for the specified person card.
     * @throws IllegalStateException If the person card cannot be found.
     */
    public PersonCardHandle getPersonCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(PersonCardHandle::new)
                .filter(handle -> handle.equals(getPerson(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Retrieves the person object at the specified index.
     *
     * @param index The index of the person.
     * @return The person object at the specified index.
     */
    private Person getPerson(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Retrieves all card nodes in the list view.
     *
     * @return A set of all card nodes in the person list panel.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the currently selected person card in the list view. If no items are selected, the remembered card will
     * be set to empty.
     */
    public void rememberSelectedPersonCard() {
        List<Person> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.isEmpty()) {
            lastRememberedSelectedPersonCard = Optional.empty();
        } else {
            lastRememberedSelectedPersonCard = Optional.of(selectedItems.get(0));
        }
    }
}
