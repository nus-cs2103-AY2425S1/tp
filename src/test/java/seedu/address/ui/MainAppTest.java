package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import seedu.address.MainApp;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.testutil.PersonBuilder;

public class MainAppTest extends ApplicationTest {

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

    @Test
    public void personHasAllDetailsShown() {
        //adds a person
        String name = PersonBuilder.DEFAULT_NAME;
        String phone = PersonBuilder.DEFAULT_PHONE;
        String email = PersonBuilder.DEFAULT_EMAIL;
        String address = PersonBuilder.DEFAULT_ADDRESS;
        String schedule = PersonBuilder.DEFAULT_SCHEDULE;
        String subject = PersonBuilder.DEFAULT_SUBJECT;
        String fee = PersonBuilder.DEFAULT_FEE;
        String paid = PersonBuilder.DEFAULT_PAID;
        String owed = PersonBuilder.DEFAULT_OWED_AMOUNT;

        //Add a person to the list
        clickOn("#commandTextField");

        write(AddCommand.COMMAND_WORD);
        write(" n/" + name);
        write(" p/" + phone);
        write(" e/" + email);
        write(" a/" + address);
        write(" time/" + schedule);
        write(" subject/" + subject);
        write(" fee/" + fee);
        write(" paid/" + paid);
        write(" owed/" + owed);

        push(KeyCode.ENTER);

        // Ensure that the Labels are visible
        verifyThat("#personList #personListView #name", isVisible());
        verifyThat("#personList #personListView #phone", isVisible());
        verifyThat("#personList #personListView #email", isVisible());
        verifyThat("#personList #personListView #address", isVisible());
        verifyThat("#personList #personListView #schedule", isVisible());
        verifyThat("#personList #personListView #subject", isVisible());
        verifyThat("#personList #personListView #rate", isVisible());
        verifyThat("#personList #personListView #paid", isVisible());
        verifyThat("#personList #personListView #owedAmount", isVisible());

        // Lookup Labels by their fx:id
        Label nameLabel = lookup("#personList #personListView #name").queryAs(Label.class);
        Label phoneLabel = lookup("#personList #personListView #name").queryAs(Label.class);
        Label emailLabel = lookup("#personList #personListView #name").queryAs(Label.class);
        Label addressLabel = lookup("#personList #personListView #name").queryAs(Label.class);
        Label scheduleLabel = lookup("#personList #personListView #name").queryAs(Label.class);
        Label subjectLabel = lookup("#personList #personListView #name").queryAs(Label.class);
        Label rateLabel = lookup("#personList #personListView #name").queryAs(Label.class);
        Label paidLabel = lookup("#personList #personListView #name").queryAs(Label.class);
        Label owedAmountLabel = lookup("#personList #personListView #name").queryAs(Label.class);

        //Ensure Labels contain the correct texts
        assertEquals(nameLabel.getText(), name);
        assertEquals(phoneLabel.getText(), name);
        assertEquals(emailLabel.getText(), name);
        assertEquals(addressLabel.getText(), name);
        assertEquals(scheduleLabel.getText(), name);
        assertEquals(subjectLabel.getText(), name);
        assertEquals(rateLabel.getText(), name);
        assertEquals(paidLabel.getText(), name);
        assertEquals(owedAmountLabel.getText(), name);
    }
}
