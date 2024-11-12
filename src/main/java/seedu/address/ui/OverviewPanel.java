package seedu.address.ui;

import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing either the list of persons or summary data of application statuses.
 * This class allows toggling between displaying detailed person information and
 * a summarized view of applicant status counts.
 */
public class OverviewPanel extends UiPart<Region> {
    private static final String FXML = "OverviewPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    // Observable list that can hold either Person objects or summary data as strings.
    private ObservableList<Object> displayList = FXCollections.observableArrayList();

    @FXML
    private ListView<Object> overviewPanel;

    /**
     * Initializes an empty {@code OverviewPanel}.
     * By default, the panel is empty and can later be populated with person details or summary data.
     */
    public OverviewPanel() {
        super(FXML);
        overviewPanel.setItems(displayList);
    }

    /**
     * Updates the overview panel to display the details of a single {@code Person}.
     * Clears any existing data and sets up a cell factory for displaying person details.
     *
     * @param person The person whose details are to be displayed.
     */
    public void updateOverviewDetails(Person person) {
        displayList.clear();
        displayList.add(person);
        overviewPanel.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Displays a summary of the application statuses in the overview panel.
     * Calculates and shows the total number of applicants, followed by the count
     * for each application status.
     *
     * @param summaryData A map of application statuses to their respective counts.
     */
    public void showSummary(Map<String, Long> summaryData) {
        displayList.clear();

        // Calculate the total number of applicants and add it as the first entry.
        long totalApplicants = summaryData.values().stream().mapToLong(Long::longValue).sum();
        displayList.add("Total number of applicants: " + totalApplicants);

        // Add each status and its count to the display list.
        summaryData.forEach((status, count) -> displayList.add(status + ": " + count));
        overviewPanel.setCellFactory(listView -> new SummaryListCell());
    }

    /**
     * Custom {@code ListCell} for displaying the details of a {@code Person} in the overview.
     * This cell type uses {@code OverviewListCard} to render the person's information.
     */
    class PersonListViewCell extends ListCell<Object> {
        @Override
        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || !(item instanceof Person)) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OverviewListCard((Person) item).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} for displaying summary data in the overview.
     * Applies CSS styling and displays summary items as text.
     */
    class SummaryListCell extends ListCell<Object> {
        @Override
        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || !(item instanceof String)) {
                setGraphic(null);
                setText(null);
                getStyleClass().remove("summary-list-cell"); // Remove style when not needed
            } else {
                setText((String) item);
                if (!getStyleClass().contains("summary-list-cell")) {
                    getStyleClass().add("summary-list-cell"); // Add the custom style class
                }
            }
        }
    }
}
