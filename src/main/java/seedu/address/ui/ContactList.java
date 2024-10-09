package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;

/**
 * An UI component that displays the details and list of {@code Person}.
 */
public class ContactList extends UiPart<Region> {

    private static final String FXML = "ContactList.fxml";

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;

    // FXML componenets
    @FXML
    private StackPane personListPanelPlaceholder;

    /**
     * Creates a {@code ContactList} given a list of {@code Person}.
     *
     * @param logic the component responsible for handling backend requests.
     */
    public ContactList(Logic logic) {
        super(FXML);

        this.logic = logic;

        this.initaliseComponents();
    }

    /**
     * Create a list of {@code Person} to be displayed to the user.
     */
    private void initaliseComponents() {

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }

    /**
     * Retrieves the panel containing the lists of people.
     *
     * @return a panel containing the list of people.
     */
    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }
}
