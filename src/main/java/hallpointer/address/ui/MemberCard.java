package hallpointer.address.ui;

import java.util.Comparator;

import hallpointer.address.model.member.Member;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Duration;

/**
 * An UI component that displays information of a {@code Member}.
 */
public class MemberCard extends UiPart<Region> {

    private static final String FXML = "MemberListCard.fxml";

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
     * Creates a {@code MemberCard} with the given {@code Member} and index to display.
     */
    public MemberCard(Member member, int displayedIndex) {
        super(FXML);
        this.member = member;
        id.setText(displayedIndex + ". ");
        name.setText(member.getName().value);
        points.setText(member.getTotalPoints().toString());
        telegram.setText("@" + member.getTelegram().value);
        room.setText(member.getRoom().value);

        member.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        member.getSessions().stream()
                .sorted(Comparator.comparing(session -> session.getDate().fullDate))
                .forEach(session -> {
                    Label sessionLabel = new Label(
                        session.getSessionName().toString() + " " + session.getDate().toString());

                    // Create a Tooltip to show points on hover
                    Tooltip sessionTooltip = new Tooltip("Points: " + session.getPoints().toString());
                    sessionTooltip.setShowDelay(Duration.seconds(0.1));
                    Tooltip.install(sessionLabel, sessionTooltip);

                    sessions.getChildren().add(sessionLabel);
                });
    }
}
