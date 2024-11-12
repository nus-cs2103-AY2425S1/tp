package seedu.address.ui;

import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Group;
import seedu.address.model.person.Person;

/**
 * Inspired by PersonCard
 * A UI component that displays information of a {@code Group}.
 */
public class GroupCard extends UiPart<Region> {

    private static final String FXML = "GroupListCard.fxml";

    public final Group group;

    @FXML
    private HBox cardPane;
    @FXML
    private Label groupName;
    @FXML
    private Label id;
    @FXML
    private Label groupSize;
    @FXML
    private Label membersList;

    /**
     * Creates a {@code GroupCard} with the given {@code Group} and index to display.
     */
    public GroupCard(Group group, int displayedIndex) {
        super(FXML);
        this.group = group;
        id.setText(displayedIndex + ". ");
        groupName.setText(group.getName());

        // Retrieve list of members in the group
        List<Person> members = group.asUnmodifiableObservableList();
        groupSize.setText(String.valueOf(members.size()));
        groupSize.getStyleClass().add("groupList");

        // Display first 3 members of the member list
        String membersDisplayText = members.stream()
                .limit(3)
                .map(member -> member.getName().fullName)
                .collect(Collectors.joining(", "));

        // If member list has more than 3ppl, we show first 3 then rest just "..."
        if (members.size() > 3) {
            membersDisplayText += ", ...";
        }

        // Set the combined text to the membersPane label
        membersList.setText(membersDisplayText);
        membersList.getStyleClass().add("membersList");
    }
}
