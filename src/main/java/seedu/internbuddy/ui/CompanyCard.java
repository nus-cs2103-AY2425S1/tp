package seedu.internbuddy.ui;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.company.Company;

/**
 * An UI component that displays information of a {@code company}.
 */
public class CompanyCard extends UiPart<Region> {

    private static final String FXML = "CompanyListCard.fxml";
    private static final String FAVOURITE_STAR_FILLED = "/images/fstar.png";
    private static final String FAVOURITE_STAR_HOLLOW = "/images/hstar.png";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
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
    private Label email;
    @FXML
    private Label application;
    @FXML
    private Label status;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView favouriteStar;

    /**
     * Creates a {@code companyCode} with the given {@code company} and index to display.
     */
    public CompanyCard(Company company, int displayedIndex) {
        super(FXML);
        assert displayedIndex > -1 : "Displayed index is less than zero";
        assert company != null : "Company is null";

        this.company = company;
        id.setText(displayedIndex + ". ");
        name.setText(company.getName().fullName);

        setPhone();
        setAddress();

        email.setText(company.getEmail().value);

        setStatus();
        setFavourite();
        setTags();
        setApplication();
    }

    private void setFavourite() {
        if (company.getIsFavourite()) {
            favouriteStar.setImage(new Image(this.getClass().getResourceAsStream(FAVOURITE_STAR_FILLED)));
        } else {
            favouriteStar.setImage(new Image(this.getClass().getResourceAsStream(FAVOURITE_STAR_HOLLOW)));
        }
    }

    private void setStatus() {
        status.setText(company.getStatus().value);
        if ("INTERESTED".equals(status.getText())) {
            status.setStyle("-fx-background-color: purple;");
        } else if ("APPLIED".equals(status.getText())) {
            status.setStyle("-fx-background-color: green;");
        } else if ("CLOSED".equals(status.getText())) {
            status.setStyle("-fx-background-color: #db0303;");
        }
        tags.getChildren().add(status);
    }

    private void setApplication() {
        List<Application> applications = company.getApplications();
        application.setText(applications.isEmpty() ? "Applications: CLOSED"
                : "Applications: " + IntStream.range(0, applications.size())
                .mapToObj(i -> String.format("\n\t%d. %s (%s)",
                        i + 1,
                        applications.get(i).getName(),
                        applications.get(i).getAppStatus()))
                .collect(Collectors.joining(", ")));
    }

    private void setTags() {
        company.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void setPhone() {
        boolean hasPhoneNumber = !company.getPhone().getValue().equals("No Phone Number");
        if (hasPhoneNumber) {
            phone.setText(company.getPhone().getValue());
        } else {
            phone.setManaged(false);
            phone.setVisible(false);
        }
    }

    private void setAddress() {
        boolean hasAddress = !company.getAddress().getValue().equals("No Address");
        if (hasAddress) {
            address.setText(company.getAddress().getValue());
        } else {
            address.setManaged(false);
            address.setVisible(false);
        }
    }
}
