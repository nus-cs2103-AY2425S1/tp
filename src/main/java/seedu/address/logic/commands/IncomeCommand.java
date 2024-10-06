package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;


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
        int feesOwed = 0;
        int feesEarned = 0;
        //ObservableList<Person> personList = model.getFilteredPersonList();
        //for (Person person: personList) {
        //fees_owed += person.getFeeOwed();
        //fees_earned += person.getFeeEarned();
        //}
        return new CommandResult("Fees owed: " + feesOwed + ", fees earned: "
                + feesEarned);
    }
}
