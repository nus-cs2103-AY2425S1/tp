package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import seedu.address.MainApp;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.person.Person;
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
    String owed = PersonBuilder.DEFAULT_OWED_AMOUNT;

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
        write(" time/" + schedule);
        write(" subject/" + subject);
        write(" fee/" + fee);
        write(" paid/" + paid);
        write(" owed/" + owed);

        push(KeyCode.ENTER);
    }

    @Test
    public void personHasAllDetailsShown() {
        addPerson();



        ListView<PersonCard> personListView = lookup("#personList #personListView").queryListView();
//        Label label = lookup("#personList #personListView #cardPane").query();
//        System.out.println("Label node name: " + label.getClass().getSimpleName());

        Person person = personListView.getItems().get(0).person;

        //name phone, address email subject and schedule, rate paid owedamount
        assertEquals(person.getName().fullName, name);
        assertEquals(person.getPhone().value, phone);
        assertEquals(person.getAddress().value, address);
        assertEquals(person.getEmail().value, email);
        assertEquals(person.getSubject().toString(), subject);
        assertEquals(person.getSchedule().value, schedule);
        assertEquals(person.getRate().toString(), fee);
        assertEquals(person.getPaid().toString(), subject);
        assertEquals(person.getOwedAmount().toString(), schedule);


    }
}
