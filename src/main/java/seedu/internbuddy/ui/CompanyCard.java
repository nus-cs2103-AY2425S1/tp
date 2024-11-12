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
import seedu.internbuddy.model.company.StatusType;

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

        // Initialize contents of CompanyCard
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
        String companyStatus = company.getStatus().toString();
        status.setText(companyStatus);
        if (status.getText().equals(StatusType.INTERESTED.name())) {
            status.setStyle("-fx-background-color: purple;");
        } else if (status.getText().equals(StatusType.APPLIED.name())) {
            status.setStyle("-fx-background-color: green;");
        } else if (status.getText().equals(StatusType.CLOSED.name())) {
            status.setStyle("-fx-background-color: #db0303;");
        }
        tags.getChildren().add(status);
    }

    private void setApplication() {
        List<Application> applications = company.getApplications();
        application.setText("Applications: ");
        if (applications.isEmpty()) {
            assert status.getText() != null;
            appendLabel(application, status.getText().equals(StatusType.CLOSED.name())
                    ? "Closed"
                    : "Have not applied");
        } else {
            String applicationString = company.getIsShowingDetails()
                    ? getDetailedApplication(applications)
                    : getStandardApplication(applications);
            appendLabel(application, applicationString);
        }
    }

    private String getDetailedApplication(List<Application> applications) {
        return IntStream.range(0, applications.size())
                .mapToObj(i -> {
                    Application app = applications.get(i);
                    return String.format("\n\t%d. %s (%s)\n\t\t%s",
                             i + 1,
                             app.getName(),
                             app.getAppStatus(),
                             app.getDescription());
                })
                .collect(Collectors.joining());
    }

    private String getStandardApplication(List<Application> applications) {
        return IntStream.range(0, applications.size())
                .mapToObj(i -> {
                    Application app = applications.get(i);
                    return String.format("\n\t%d. %s (%s)",
                            i + 1,
                            app.getName(),
                            app.getAppStatus());
                })
                .collect(Collectors.joining());
    }

    private void appendLabel(Label label, String text) {
        label.setText(label.getText() + text);
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
