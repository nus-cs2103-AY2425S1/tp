package seedu.hireme.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.hireme.commons.core.LogsCenter;
import seedu.hireme.model.internshipapplication.InternshipApplication;

/**
 * Panel containing the list of persons.
 */
public class InternshipListPanel extends UiPart<Region> {
    private static final String FXML = "InternshipListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(InternshipListPanel.class);

    @FXML
    private ListView<InternshipApplication> internshipListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public InternshipListPanel(ObservableList<InternshipApplication> internshipList) {
        super(FXML);
        internshipListView.setItems(internshipList);
        internshipListView.setCellFactory(listView -> new InternshipListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class InternshipListViewCell extends ListCell<InternshipApplication> {
        @Override
        protected void updateItem(InternshipApplication internship, boolean empty) {
            super.updateItem(internship, empty);

            if (empty || internship == null) {
                logger.warning("Internship application is null or empty in InternshipListViewCell.");
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new InternshipCard(internship, getIndex() + 1).getRoot());
            }
        }
    }

}
