package seedu.address.ui;

import java.util.HashSet;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Address;
import seedu.address.model.person.DesiredRole;
import seedu.address.model.person.Email;
import seedu.address.model.person.Experience;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Skills;
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of persons.
 */
public class OverviewPanel extends UiPart<Region> {
    private static final String FXML = "OverviewPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private Person placeholder = new Person(new Name("name"), new Phone("12345678"), new Email("email@exm.com"),
            new Address("address"), new DesiredRole("desiredRole"), new Skills("skills"), new Experience("experience"),
            new Status("Applied"), new Note("note"), new HashSet<Tag>());

    private ObservableList<Person> personList = FXCollections.observableArrayList();

    @FXML
    private ListView<Person> overviewPanel;


    /**
     * Creates a {@code OverviewPanel} with the given {@code ObservableList}.
     */
    public OverviewPanel(ObservableList<Person> personList) {
        super(FXML);
        overviewPanel.setItems(personList);
        overviewPanel.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Creates a {@code OverviewPanel} with the given {@code Person}.
     */
    public OverviewPanel(Person person) {
        super(FXML);
        ObservableList<Person> newPersonDetails = FXCollections.observableArrayList();
        newPersonDetails.add(person);
        overviewPanel.setItems(personList);
        overviewPanel.setCellFactory(listView -> new PersonListViewCell());
        overviewPanel.setVisible(false);
    }

    /**
     * Creates a {@code OverviewPanel} with a placeholder {@code Person}.
     */
    public OverviewPanel() {
        super(FXML);
        personList.add(placeholder);
        overviewPanel.setItems(personList);
        overviewPanel.setCellFactory(listView -> new PersonListViewCell());
        overviewPanel.setVisible(false);
    }

    /**
     * Updates the overview panel with the details of the given {@code Person}.
     */
    public void updateOverviewDetails(Person person) {
        personList.clear();
        personList.add(person);
        overviewPanel.setItems(personList);
        overviewPanel.setCellFactory(listView -> new PersonListViewCell());
        overviewPanel.setVisible(true);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code OverviewListCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OverviewListCard(person).getRoot());
            }
        }
    }

}
