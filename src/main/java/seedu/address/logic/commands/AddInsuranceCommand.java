package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Adds an InsurancePlan to an existing person in the address book.
 */
public class AddInsuranceCommand extends Command {
    public static final String COMMAND_WORD = "addInsurance";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from AddInsurance");
    }
}
