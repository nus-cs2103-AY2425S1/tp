package seedu.internbuddy.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.internbuddy.commons.core.LogsCenter;
import seedu.internbuddy.model.company.Company;

/**
 * Panel containing the list of companies.
 */
public class CompanyListPanel extends UiPart<Region> {
    private static final String FXML = "CompanyListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CompanyListPanel.class);

    @FXML
    private ListView<Company> companyListView;

    /**
     * Creates a {@code companyListPanel} with the given {@code ObservableList}.
     */
    public CompanyListPanel(ObservableList<Company> companyList) {
        super(FXML);
        companyListView.setItems(companyList);
        companyListView.setCellFactory(listView -> new CompanyListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code company} using a {@code companyCard}.
     */
    class CompanyListViewCell extends ListCell<Company> {
        @Override
        protected void updateItem(Company company, boolean empty) {
            super.updateItem(company, empty);

            if (empty || company == null) {
                setGraphic(null);
                setText(null);
                setStyle("-fx-background-color: transparent;");
            } else {
                setGraphic(new CompanyCard(company, getIndex() + 1).getRoot());
                setStyle("-fx-background-color: #102a43;");
            }
        }
    }
}
