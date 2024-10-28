package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * Controller class for displaying a person's details in the UI. Encapsulates
 * all features for the person object, and displays the details line by line.
 */
public class PersonDetails {
    @FXML
    private Label birthdayLabel;
    @FXML
    private VBox history;
    @FXML
    private Label remarkLabel;
    @FXML
    private FlowPane tags;
    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private VBox propertyList;

    /**
     * Sets the details of the specified {@code Person} in the respective UI labels.
     *
     * @param person The person whose details are to be displayed.
     *               This includes the person's name, phone, email, and address.
     */
    public void setPersonDetails(Person person) {
        nameLabel.setText(person.getName().fullName);
        phoneLabel.setText(person.getPhone().value);
        emailLabel.setText(person.getEmail().value);
        addressLabel.setText(person.getAddress().value);
        birthdayLabel.setText(person.getBirthday().value.toString());
        remarkLabel.setText(person.getRemark().value);

        // Set consistent styles for email, address, and remark
        String commonStyle = "\"-fx-font-size: 13px; -fx-text-fill: #D9B08C;";
        phoneLabel.setStyle(commonStyle);
        emailLabel.setStyle(commonStyle);
        addressLabel.setStyle(commonStyle);
        birthdayLabel.setStyle(commonStyle);
        remarkLabel.setStyle(commonStyle);

        person.getPropertyList().getProperties().forEach(property -> {
            FlowPane propertyLabel = new FlowPane();
            propertyLabel.setHgap(10); // Horizontal gap between labels
            propertyLabel.setVgap(5); // Vertical gap between labels
            propertyLabel.setStyle("-fx-padding: 10; -fx-background-color: #293f3f; "
                    + "-fx-border-color: #D9B08C; -fx-border-radius: 8; "
                    + "-fx-background-radius: 8; -fx-text-fill: #D9B08C;");

            Label addressLabel = new Label("Address: " + property.getAddress());
            addressLabel.setStyle(commonStyle); // Set the same style

            Label townLabel = new Label("Town: " + property.getTown());
            townLabel.setStyle(commonStyle); // Set the same style

            Label typeLabel = new Label("Type: " + property.getPropertyType());
            typeLabel.setStyle(commonStyle); // Set the same style

            Label sizeLabel = new Label("Size: " + property.getSize() + " sqm");
            sizeLabel.setStyle(commonStyle); // Set the same style

            Label bedroomsLabel = new Label("Bedrooms: " + property.getNumberOfBedrooms());
            bedroomsLabel.setStyle(commonStyle); // Set the same style

            Label bathroomsLabel = new Label("Bathrooms: " + property.getNumberOfBathrooms());
            bathroomsLabel.setStyle(commonStyle); // Set the same style

            Label priceLabel = new Label("Price: $" + property.getPrice());
            priceLabel.setStyle(commonStyle); // Set to the same style as others

            propertyLabel.getChildren().addAll(addressLabel, townLabel, typeLabel,
                    sizeLabel, bedroomsLabel, bathroomsLabel, priceLabel);
            propertyList.getChildren().add(propertyLabel);
        });

        person.getHistory().getHistoryEntries().forEach((date, activities) -> {
            Label historyLabel = new Label(date.toString());
            historyLabel.setStyle("-fx-background-color: #293f3f; -fx-text-fill: #D9B08C; -fx-padding: 5");
            history.getChildren().add(historyLabel);

            activities.forEach(entry -> {
                Label activityLabel = new Label("\t - " + entry);
                activityLabel.setStyle("-fx-font-size: 1em; -fx-text-fill: #D9B08C; -fx-padding: 2");
                history.getChildren().add(activityLabel);
            });
        });

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName)).forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    if (tag.tagName.equalsIgnoreCase("favourite")) {
                        tagLabel.getStyleClass().add("favourite-tag");
                    }
                    tags.getChildren().add(tagLabel);
                });
    }
}
