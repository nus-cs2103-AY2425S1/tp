package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.person.Person;

/**
 * An UI component that displays the details and list of {@code Person}.
 */
public class ContactList extends UiPart<Region> {

    private static final String FXML = "ContactList.fxml";

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;

    private ObservableList<Person> personList;

    // FXML componenets
    @FXML
    private StackPane personListPanelPlaceholder;

    /**
     * Creates a {@code ContactList} given a list of {@code Person}.
     *
     * @param logic the component responsible for handling backend requests.
     */
    public ContactList(ObservableList<Person> personList) {
        super(FXML);

        this.personList = personList;

        this.initaliseComponents();
    }

    /**
     * Create a list of {@code Person} to be displayed to the user.
     */
    private void initaliseComponents() {

        personListPanel = new PersonListPanel(personList);
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
