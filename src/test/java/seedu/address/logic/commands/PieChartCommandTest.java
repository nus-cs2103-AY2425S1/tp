package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PieChartCommandTest {

    @Test
    public void execute_piChartCommand_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CommandHistory());

        // Create new persons
        Person person1 = new PersonBuilder().withName("Amy").withPhone("12345678").withEmail("alice@example.com")
                .withAddress("123 Street").withFees("100").withClassId("1").build();
        Person person2 = new PersonBuilder().withName("Benny").withPhone("87654321").withEmail("bob@example.com")
                .withAddress("456 Avenue").withFees("200").withClassId("1").build();
        Person person3 = new PersonBuilder().withName("Chames").withPhone("12348765").withEmail("charlie@example.com")
                .withAddress("789 Boulevard").withFees("150").withClassId("2").build();
        Person person4 = new PersonBuilder().withName("Diana").withPhone("56781234").withEmail("diana@example.com")
                .withAddress("101 Road").withFees("250").withClassId("2").build();

        // Add persons to the model
        model.addPerson(person1);
        model.addPerson(person2);
        model.addPerson(person3);
        model.addPerson(person4);

        // Execute the command
        PieChartCommand pieChartCommand = new PieChartCommand();
        CommandResult result = pieChartCommand.execute(model);

        // Verify the result
        assertEquals(PieChartCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }
}
