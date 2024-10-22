package hallpointer.address.ui;

import java.util.Comparator;

import hallpointer.address.model.member.Member;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Member}.
 */
public class MemberCard extends UiPart<Region> {

    private static final String FXML = "MemberListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Member member;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label telegram;
    @FXML
    private Label room;
    @FXML
    private Label points;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane sessions;

    /**
     * Creates a {@code MemberCode} with the given {@code Member} and index to display.
     */
    public MemberCard(Member member, int displayedIndex) {
        super(FXML);
        this.member = member;
        id.setText(displayedIndex + ". ");
        name.setText(member.getName().fullName);
        points.setText(member.getTotalPoints().toString());
        telegram.setText(member.getTelegram().value);
        room.setText(member.getRoom().value);

        member.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        member.getSessions().stream()
                .sorted(Comparator.comparing(session -> session.getDate().fullDate))
                .forEach(session -> sessions.getChildren().add(new Label(session.getSessionName().toString() + " " + session.getDate().toString())));
    }
}
