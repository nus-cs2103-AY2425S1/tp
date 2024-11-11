package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.company.Company;

/**
 * An UI component that displays information of a {@code Company}.
 */
public class CompanyCard extends UiPart<Region> {

    private static final String FXML = "CompanyListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     *      issue on AddressBook level 4</a>
     */

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
    private Label careerPageUrl;
    @FXML
    private Label email;
    @FXML
    private ImageView bookmarkIcon;
    @FXML
    private Label applicationStatus;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code CompanyCode} with the given {@code Company} and index to
     * display.
     */
    public CompanyCard(Company company, int displayedIndex) {
        super(FXML);
        this.company = company;
        id.setText(displayedIndex + ". ");
        name.setText(company.getName().fullName);
        phone.setText(company.getPhone().value);
        address.setText(company.getAddress().value);
        careerPageUrl.setText(company.getCareerPageUrl().value);
        bookmarkIcon.setVisible(company.getIsBookmark().getIsBookmarkValue());
        applicationStatus.setText(company.getApplicationStatus().getStatusValue());
        remark.setText(company.getRemark().getRemarkValue());
        email.setText(company.getEmail().value);
        company.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.toString()))
                .forEach(tag -> tags.getChildren().add(new Label(tag.toString())));
    }
}
