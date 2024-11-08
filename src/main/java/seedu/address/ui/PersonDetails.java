package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Controller class for displaying a newPerson's details in the UI. Encapsulates
 * all features for the newPerson object, and displays the details line by line.
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

    private Person newPerson;

    /**
     * Sets the details of the specified {@code Person} in the respective UI labels.
     *
     * @param person The newPerson whose details are to be displayed.
     *               This includes the newPerson's name, phone, email, and address.
     */
    public void setPersonDetails(Person person) {
        // Check if history and property list are empty
        boolean hasHistory = !person.getHistory().hasNoEntry();
        boolean hasProperty = !person.getPropertyList().isEmpty();
        this.newPerson = person;

        if (hasHistory) {
            person.getHistory().getHistoryEntries().forEach((date, activities) -> {
                Label historyDateLabel = new Label(date.toString());
                historyDateLabel.setStyle("-fx-background-color: #293f3f; -fx-text-fill: #D9B08C; -fx-padding: 5");
                history.getChildren().add(historyDateLabel);

                activities.forEach(entry -> {
                    Label activityLabel = new Label("- " + entry);
                    activityLabel.setStyle("-fx-font-size: 1em; -fx-text-fill: #D9B08C; -fx-padding: 1 8 1 4");
                    activityLabel.setWrapText(true);
                    activityLabel.setMaxWidth(Double.MAX_VALUE);
                    VBox.setVgrow(activityLabel, Priority.ALWAYS);
                    history.getChildren().add(activityLabel);
                });
            });
        } else {
            if (!history.getStyleClass().contains("hidden-list")) {
                history.getStyleClass().add("hidden-list");
            }
            Label placeholder = new Label("No history entries to display.");
            placeholder.getStyleClass().add("placeholder-text");
            history.getChildren().add(placeholder);
        }
        String commonStyle = "-fx-font-size: 13px; -fx-text-fill: #D9B08C;";

        // Apply the placeholder for property list if empty
        if (hasProperty) {
            propertyList.getStyleClass().remove("hidden-list");
            person.getPropertyList().getProperties().forEach(property -> {
                FlowPane propertyLabel = new FlowPane();
                propertyLabel.setHgap(10);
                propertyLabel.setVgap(5);
                propertyLabel.setStyle("-fx-padding: 10; -fx-background-color: #293f3f; "
                        + "-fx-border-color: #D9B08C; -fx-border-radius: 8; "
                        + "-fx-background-radius: 8; -fx-text-fill: #D9B08C;");
                Label addressLabel = new Label("Address: " + property.getAddress());
                addressLabel.setStyle(commonStyle);

                Label townLabel = new Label("Town: " + property.getTown());
                townLabel.setStyle(commonStyle);

                Label typeLabel = new Label("Type: " + property.getPropertyType());
                typeLabel.setStyle(commonStyle);

                Label sizeLabel = new Label("Size: " + property.getSize() + " sqm");
                sizeLabel.setStyle(commonStyle);

                Label bedroomsLabel = new Label("Bedrooms: " + property.getNumberOfBedrooms());
                bedroomsLabel.setStyle(commonStyle);

                Label bathroomsLabel = new Label("Bathrooms: " + property.getNumberOfBathrooms());
                bathroomsLabel.setStyle(commonStyle);

                Label priceLabel = new Label("Price: $" + property.getPrice());
                priceLabel.setStyle(commonStyle);

                propertyLabel.getChildren().addAll(addressLabel, townLabel, typeLabel,
                        sizeLabel, bedroomsLabel, bathroomsLabel, priceLabel);
                propertyList.getChildren().add(propertyLabel);
            });
        } else {
            if (!propertyList.getStyleClass().contains("hidden-list")) {
                propertyList.getStyleClass().add("hidden-list");
            }
            Label placeholder = new Label("No properties to display.");
            placeholder.getStyleClass().add("placeholder-text");
            propertyList.getChildren().add(placeholder);
        }
        nameLabel.setText(person.getName().fullName);
        phoneLabel.setText(person.getPhone().value);
        emailLabel.setText(person.getEmail().value);
        addressLabel.setText(person.getAddress().value);
        birthdayLabel.setText(person.getBirthday().value.toString());
        remarkLabel.setText(person.getRemark().value);
        person.getTags().stream()
                .sorted(Comparator.comparing((Tag tag) -> !tag.tagName.equalsIgnoreCase("favourite")))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    if (tag.tagName.equalsIgnoreCase("favourite")) {
                        tagLabel.getStyleClass().add("favourite-tag");
                    }
                    tags.getChildren().add(tagLabel);
                });
    }

    /**
     * updates the current view window with the contents of the new {@code Person}
     * @param newPerson
     */
    public void refresh(Person newPerson) {
        propertyList.getChildren().clear();
        history.getChildren().clear();
        tags.getChildren().clear();
        setPersonDetails(newPerson);
    }

}
