package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.company.Company;

/**
 * An UI component that displays information of a {@code Company}.
 */
public class CompanyCard extends UiPart<Region> {

    private static final String FXML = "CompanyCard.fxml";
    private final Logger logger = LogsCenter.getLogger(CompanyCard.class);

    private Company company;
    private int displayedIndex;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label category; // Label to indicate type (Company)


    /**
     * Creates a {@code CompanyCard} with the given {@code Company} and index to display.
     */
    public CompanyCard(Company company, int displayedIndex) {
        super(FXML);
        this.company = company;
        this.displayedIndex = displayedIndex;
        logger.info("Created CompanyCard for: " + company.getName().fullName + " at index: " + displayedIndex);
    }

    @FXML
    private void initialize() {
        category.setText("Company");
        id.setText(displayedIndex + ". ");
        if (company != null && company.getName() != null) {
            name.setText(company.getName().fullName);
        } else {
            logger.warning("Company or Company name is null for CompanyCard at index: " + displayedIndex);
            name.setText("Unknown Company");
        }
        logger.info("Created CompanyCard for: "
            + (company != null ? company.getName().fullName : "null company")
            + " at index: " + displayedIndex);

    }
}
