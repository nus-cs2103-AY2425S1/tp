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
 * Panel containing the list of internship applications.
 */
public class InternshipApplicationListPanel extends UiPart<Region> {
    private static final String FXML = "InternshipApplicationListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(InternshipApplicationListPanel.class);

    @FXML
    private ListView<InternshipApplication> internshipApplicationListView;

    /**
     * Creates a {@code InternshipApplicationListPanel} with the given {@code ObservableList}.
     */
    public InternshipApplicationListPanel(ObservableList<InternshipApplication> internshipList) {
        super(FXML);
        internshipApplicationListView.setItems(internshipList);
        internshipApplicationListView.setCellFactory(listView -> new InternshipApplicationListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code InternshipApplication} using a
     *     {@code InternshipApplicationCard}.
     */
    class InternshipApplicationListViewCell extends ListCell<InternshipApplication> {
        @Override
        protected void updateItem(InternshipApplication internshipApplication, boolean empty) {
            super.updateItem(internshipApplication, empty);

            if (empty || internshipApplication == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new InternshipApplicationCard(internshipApplication, getIndex() + 1).getRoot());
            }
        }
    }

}
