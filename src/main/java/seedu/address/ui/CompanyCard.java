package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.company.Company;

/**
 * An UI component that displays information of a {@code Company}.
 */
public class CompanyCard extends UiPart<Region> {

    private static final String FXML = "CompanyCard.fxml";

    public final Company company;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label industry;
    @FXML
    private Label type; // Label to indicate type (Company)
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code CompanyCard} with the given {@code Company} and index to display.
     */
    public CompanyCard(Company company, int displayedIndex) {
        super(FXML);
        this.company = company;

        type.setText("Company");
        id.setText(displayedIndex + ". ");
        name.setText(company.getName().fullName);
        phone.setText(company.getPhone().value);
        address.setText(company.getAddress().value);
        email.setText(company.getEmail().value);
        industry.setText("Industry: " + company.getIndustry().value);
        company.getTags().stream()
                .sorted((tag1, tag2) -> tag1.tagName.compareTo(tag2.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
