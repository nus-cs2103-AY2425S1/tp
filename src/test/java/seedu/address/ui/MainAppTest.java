package seedu.address.ui;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.hamcrest.core.StringContains.containsString;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import seedu.address.MainApp;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.Subject;
import seedu.address.testutil.PersonBuilder;

public class MainAppTest extends ApplicationTest {

    String name = PersonBuilder.DEFAULT_NAME;
    String phone = PersonBuilder.DEFAULT_PHONE;
    String email = PersonBuilder.DEFAULT_EMAIL;
    String address = PersonBuilder.DEFAULT_ADDRESS;
    String schedule = PersonBuilder.DEFAULT_SCHEDULE;
    String subject = PersonBuilder.DEFAULT_SUBJECT;
    String fee = PersonBuilder.DEFAULT_FEE;
    String paid = PersonBuilder.DEFAULT_PAID;
    String owedAmount = PersonBuilder.DEFAULT_OWED_AMOUNT;

    @BeforeAll
    public static void setup() throws Exception {
        // Use FxToolkit to launch the JavaFX Application correctly
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainApp::new);
    }

    @BeforeEach
    public void clearEverything() throws Exception {
        clickOn("#commandTextField");
        write(ClearCommand.COMMAND_WORD);
        push(KeyCode.ENTER);
    }

    public void addPerson() {
        //adds a person
        //Add a person to the list
        clickOn("#commandTextField");

        write(AddCommand.COMMAND_WORD);
        write(" n/" + name);
        write(" p/" + phone);
        write(" e/" + email);
        write(" a/" + address);
        write(" t/" + schedule);
        write(" s/" + subject);
        write(" f/" + fee);
        write(" paid/" + paid);
        write(" owed/" + owedAmount);

        push(KeyCode.ENTER);
    }

    @Test
    public void personHasAllDetailsShown() {
        addPerson();

        Subject testSubject = new Subject(subject);
        Schedule testSchedule = new Schedule(schedule);

        //verify visible
        verifyThat("#personList #personListView #name", Node::isVisible);
        verifyThat("#personList #personListView #subjectAndSchedule", Node::isVisible);
        verifyThat("#personList #personListView #phone", Node::isVisible);
        verifyThat("#personList #personListView #address", Node::isVisible);
        verifyThat("#personList #personListView #email", Node::isVisible);
        verifyThat("#personList #personListView #rate", Node::isVisible);
        verifyThat("#personList #personListView #paid", Node::isVisible);
        verifyThat("#personList #personListView #owedAmount", Node::isVisible);

        //verify contains text
        verifyThat("#personList #personListView #name", hasText(name));
        verifyThat("#personList #personListView #subjectAndSchedule", hasText(containsString(testSubject.toString())));
        verifyThat("#personList #personListView #subjectAndSchedule", hasText(containsString(testSchedule.toString())));
        verifyThat("#personList #personListView #phone", hasText(phone));
        verifyThat("#personList #personListView #address", hasText(address));
        verifyThat("#personList #personListView #email", hasText(email));
        verifyThat("#personList #personListView #rate", hasText(containsString(fee)));
        verifyThat("#personList #personListView #paid", hasText(containsString(paid)));
        verifyThat("#personList #personListView #owedAmount", hasText(containsString(owedAmount)));
    }
}
