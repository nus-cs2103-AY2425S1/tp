package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.company.Company;
import seedu.address.model.person.student.Student;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using either a {@code StudentCard} or
     * a {@code CompanyCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (person instanceof Student) {
                    // If the person is a Student, use StudentCard
                    setGraphic(new StudentCard((Student) person, getIndex() + 1).getRoot());
                } else if (person instanceof Company) {
                    // If the person is a Company, use CompanyCard
                    setGraphic(new CompanyCard((Company) person, getIndex() + 1).getRoot());
                } else {
                    // Fallback case (in case new subclasses are added)
                    logger.warning("Unknown Person type: " + person.getClass().getName());
                }
            }
        }
    }

    /**
     * Returns the ListView for adding selection listeners in MainWindow.
     */
    public ListView<Person> getPersonListView() {
        return personListView;
    }

}
