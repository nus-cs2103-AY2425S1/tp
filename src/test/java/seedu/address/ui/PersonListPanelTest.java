package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ListViewMatchers.hasItems;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.HashSet;
import java.util.Set;

public class PersonListPanelTest extends ApplicationTest {

    private ListView<Person> personListView;

    @Override
    public void start(Stage stage) {
        // Create tags for Alice
        Set<Tag> aliceTags = new HashSet<>();
        aliceTags.add(new Tag("friend"));
        aliceTags.add(new Tag("teamLead"));

        // Create tags for Bob
        Set<Tag> bobTags = new HashSet<>();
        bobTags.add(new Tag("colleague"));
        bobTags.add(new Tag("mentor"));

        Person alice = new Person(
                new Name(CommandTestUtil.VALID_NAME_AMY),
                new Phone(CommandTestUtil.VALID_PHONE_AMY),
                new Email(CommandTestUtil.VALID_EMAIL_AMY),
                new Role(CommandTestUtil.VALID_ROLE_AMY),
                new Major(CommandTestUtil.VALID_MAJOR_AMY),
                new Address(CommandTestUtil.VALID_ADDRESS_AMY),
                aliceTags);

        Person bob = new Person(
                new Name(CommandTestUtil.VALID_NAME_BOB),
                new Phone(CommandTestUtil.VALID_PHONE_BOB),
                new Email(CommandTestUtil.VALID_EMAIL_BOB),
                new Role(CommandTestUtil.VALID_ROLE_BOB),
                new Major(CommandTestUtil.VALID_MAJOR_BOB),
                new Address(CommandTestUtil.VALID_ADDRESS_BOB),
                bobTags);

        personListView = new ListView<>(FXCollections.observableArrayList(alice, bob));
        PersonListPanel personListPanel = new PersonListPanel(personListView.getItems());
        StackPane root = new StackPane(personListPanel.getRoot());
        stage.setScene(new Scene(root));
        stage.show();
    }

}