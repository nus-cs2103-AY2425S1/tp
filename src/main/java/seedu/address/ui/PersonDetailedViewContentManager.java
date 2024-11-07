package seedu.address.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Comparator;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import seedu.address.model.person.Person;



/**
 * Manages content generation for a {@code Person}'s detailed view,
 * including strings for fields, templates, and button labels.
 */
public class PersonDetailedViewContentManager {
    private final Person person;

    /**
     * Constructs a {@code PersonDetailedViewContentManager} with the specified {@code Person}.
     * @param person The person whose details are displayed.
     */
    public PersonDetailedViewContentManager(Person person) {
        this.person = person;
    }

    /**
     * Sets up the profile image for the person and applies a circular clip.
     * If a profile image file is not found, a default image is used.
     * @param profileImage The ImageView component to display the profile image.
     */
    public void setupProfileImage(ImageView profileImage) {
        try {
            File profileFile = new File(person.getProfilePicFilePath().toString());
            Image profileImg = new Image(new FileInputStream(profileFile));
            profileImage.setImage(profileImg);
        } catch (FileNotFoundException e) {
            profileImage.setImage(new Image(getClass().getResourceAsStream("/images/profilepicture.png")));
        }

        Circle clip = new Circle(profileImage.getFitWidth() / 2,
                profileImage.getFitHeight() / 2, profileImage.getFitWidth() / 2);
        profileImage.setClip(clip);
    }

    /**
     * Sets up a birthday tooltip for the person's name label
     * if their birthday is within the next seven days.
     * @param nameLabel The Label component displaying the person's name.
     * @param isVisualsEnabled A boolean indicating whether visuals are enabled.
     */
    public void setupBirthdayTooltip(Label nameLabel, boolean isVisualsEnabled) {
        if (isVisualsEnabled && person.getBirthday().hasBirthdayWithin7Days()) {
            nameLabel.setStyle("-fx-text-fill: #ffa500");
            Tooltip birthdayTooltip = new Tooltip("Birthday soon!");
            birthdayTooltip.setShowDelay(javafx.util.Duration.millis(10));
            Tooltip.install(nameLabel, birthdayTooltip);
        }
    }

    /**
     * Populates the tags pane with labels representing the person's tags
     * and applies the relevant styles.
     * @param tagsPane The FlowPane container to which the tags are added.
     * @param isVisualsEnabled A boolean indicating whether visual styles are enabled.
     */
    public void setupTags(FlowPane tagsPane, boolean isVisualsEnabled) {
        tagsPane.getChildren().clear();
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);

                    // Apply specific styling for net worth categories if visuals are enabled
                    if (isVisualsEnabled) {
                        switch (tag.tagName.toLowerCase()) {
                        case "highnetworth":
                            tagLabel.getStyleClass().add("tag-high");
                            break;
                        case "midnetworth":
                            tagLabel.getStyleClass().add("tag-mid");
                            break;
                        case "lownetworth":
                            tagLabel.getStyleClass().add("tag-low");
                            break;
                        default:
                            // No specific styling for other tags
                            break;
                        }
                    }

                    tagsPane.getChildren().add(tagLabel);
                });
    }

    public String getName() {
        return person.getName().fullName;
    }

    public String getPhone() {
        return "+65 " + person.getPhone().value;
    }

    public String getAddress() {
        return "Address: " + person.getAddress().value;
    }

    public String getBirthday() {
        return "Birthday: " + person.getBirthday().value;
    }

    public String getAge() {
        return "Age: " + person.getAge().value;
    }

    public String getEmail() {
        return person.getEmail().value;
    }

    public String getHasPaidStatus() {
        return "Paid status: " + (person.getHasPaid() ? "Paid" : "Not Paid");
    }

    public String getFrequency() {
        return "Policy Renewal Frequency: " + person.getFrequency().value + " month(s)";
    }

    public String getYoungAdultButtonText() {
        return "Young Adult";
    }

    public String getMidCareerButtonText() {
        return "Mid-Career";
    }

    public String getPreRetireeButtonText() {
        return "Pre-Retiree";
    }

    public String getYoungAdultMessage() {
        return "Hi " + person.getName().fullName + "! ☕\n\n"
                + "I’d love to grab a coffee with you sometime to "
                + "chat about ways to set up a strong financial foundation as "
                + "you start your career. No pressure—just a relaxed conversation to "
                + "answer any questions you might have about planning for the future.\n\n"
                + "Let me know if you're up for it, and we can pick a time that works for you!";
    }

    public String getMidCareerMessage() {
        return "Hi " + person.getName().fullName + ",\n\n"
                + "Hope all’s well! I thought this might be a good time to "
                + "check in and discuss ways to keep your financial goals on track. "
                + "Whether it’s planning for upcoming life changes or just staying ahead of things, "
                + "a quick chat could be helpful.\n\n"
                + "Let me know when you’re available, and we can set up a time that suits you best!";
    }

    public String getPreRetireeMessage() {
        return "Hello " + person.getName().fullName + ",\n\n"
                + "I’d love to meet up and go over any financial questions "
                + "you might have as you look toward retirement. Whether it’s planning travel, "
                + "helping family, or just managing things day-to-day, "
                + "there are a lot of options we can explore together.\n\n"
                + "Let me know if you’d like to catch up soon"
                + "—I’m happy to meet whenever it’s convenient for you!";
    }
}
