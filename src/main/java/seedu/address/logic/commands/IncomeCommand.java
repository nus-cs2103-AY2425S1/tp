package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


/**
 * Displays total money earned, as well as total money
 * owed
 */
public class IncomeCommand extends Command {
    public static final String COMMAND_WORD = "income";

    public static final String MESSAGE_SUCCESS = "Showed total money earned and owed";
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        double totalOwed = 0;
        double totalPaid = 0;
        ObservableList<Person> personList = model.getFilteredPersonList();
        for (Person person: personList) {
        totalOwed += person.getOwedAmount().value;
        totalPaid += person.getPaid().value;
        }
        return new CommandResult("Total Paid: " + totalPaid + "   Total Owed: " + totalOwed);
    }
}
