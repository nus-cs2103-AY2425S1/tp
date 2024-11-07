package seedu.address.ui;

import java.util.Comparator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * A UI Component that displays a detailed view of a {@code Person}
 */
public class PersonDetailedView extends UiPart<Region> {
    private static final String FXML = "PersonDetailedView.fxml";
    public final Person person;
    private boolean isVisualsEnabled;

    private final PersonDetailedViewContentManager contentManager;

    private final ResultDisplay resultDisplay;
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
    private Label birthday;
    @FXML
    private Label age;
    @FXML
    private Label hasPaid;
    @FXML
    private FlowPane tags;
    @FXML
    private Label frequency;
    @FXML
    private Label placeholderLabel;
    @FXML
    private ImageView profileImage;
    @FXML
    private Button templateButton1;
    @FXML
    private Button templateButton2;
    @FXML
    private Button templateButton3;
    @FXML
    private HBox notificationBox;
    @FXML
    private Label notificationLabel;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} to display.
     */

    public PersonDetailedView(Person person, boolean isVisualsEnabled, ResultDisplay resultDisplay) {
        super(FXML);
        this.person = person;
        this.contentManager = new PersonDetailedViewContentManager(person);
        this.resultDisplay = resultDisplay;
        this.isVisualsEnabled = isVisualsEnabled;

        initialiseView();
        setupTemplateButtons();
    }

    private void initialiseView() {
        Image profileImg = new Image(getClass()
                .getResourceAsStream("/" + this.person.getProfilePicFilePath().toString()));

        profileImage.setImage(profileImg);

        name.setText(contentManager.getName());
        phone.setText(contentManager.getPhone());
        address.setText(contentManager.getAddress());
        email.setText(contentManager.getEmail());
        birthday.setText(contentManager.getBirthday());
        age.setText(contentManager.getAge());
        hasPaid.setText(contentManager.getHasPaidStatus());
        frequency.setText(contentManager.getFrequency());
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);

                    if (isVisualsEnabled) {
                        if ("highnetworth".equalsIgnoreCase(tag.tagName)) {
                            tagLabel.getStyleClass().add("tag-high");
                        } else if ("midnetworth".equalsIgnoreCase(tag.tagName)) {
                            tagLabel.getStyleClass().add("tag-mid");
                        } else if ("lownetworth".equalsIgnoreCase(tag.tagName)) {
                            tagLabel.getStyleClass().add("tag-low");
                        }
                    }
                    tags.getChildren().add(tagLabel);
                });
    }

    /**
     * Sets up the template buttons with their text labels and action handlers.
     */
    private void setupTemplateButtons() {
        templateButton1.setText(contentManager.getYoungAdultButtonText());
        templateButton2.setText(contentManager.getMidCareerButtonText());
        templateButton3.setText(contentManager.getPreRetireeButtonText());

        templateButton1.setOnAction(event -> showCopyNotification(contentManager.getYoungAdultMessage()));
        templateButton2.setOnAction(event -> showCopyNotification(contentManager.getMidCareerMessage()));
        templateButton3.setOnAction(event -> showCopyNotification(contentManager.getPreRetireeMessage()));
    }

    /**
     * Displays a temporary notification indicating that the specified message
     * has been copied to the clipboard. The notification appears as a tooltip
     * and automatically closes after a short duration.
     * @param message The message text to copy to the clipboard and notify the user about.
     */
    public void showCopyNotification(String message) {
        copyTemplateToClipboard(message);
        resultDisplay.setFeedbackToUser("Template Message copied to clipboard!");
    }

    /**
     * Copies a given message to the system clipboard.
     */
    public void copyTemplateToClipboard(String message) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(message);
        clipboard.setContent(content);
    }
}
