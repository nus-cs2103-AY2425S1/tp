package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


public class IncomeCommandTest {
    private final Model model = new ModelManager();
    @Test
    public void execute_emptyAddressBook_noPaidAmountNoOwedAmount() {
        CommandResult commandResult = new IncomeCommand().execute(model);
        assertEquals(commandResult.getFeedbackToUser(), "Total Paid: 0.0   Total Owed: 0.0");
    }

    @Test
    public void execute_addPerson_showUpdatedPaidAndOwed() {
        Person validPerson = new PersonBuilder().build();
        model.addPerson(validPerson);
        CommandResult commandResult = new IncomeCommand().execute(model);

        String expectedOutput = "Total Paid: " + validPerson.getPaid().value
                + "   Total Owed: " + validPerson.getOwedAmount().value;

        assertEquals(commandResult.getFeedbackToUser(), expectedOutput);


    }
}
