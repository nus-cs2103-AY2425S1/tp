package seedu.address.logic.commands;

import seedu.address.commons.exceptions.NotImplementedException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.company.Company;

/**
 * Adds a company to the address book.
 */
public class AddCompanyCommand extends AddCommand<Company> {

    public static final String ENTITY_WORD = "company";
    public static final String FULL_COMMAND = COMMAND_WORD + " " + ENTITY_WORD;
    public static final String MESSAGE_USAGE = FULL_COMMAND + ": Adds a company to the address book. ";
    public static final String MESSAGE_SUCCESS = "New company added: %1$s";
    public static final String MESSAGE_DUPLICATE_COMPANY = "This company already exists in the address book";

    /**
     * Creates an AddCompanyCommand to add the specified {@code Company}
     */
    public AddCompanyCommand(Company company) {
        super(company);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new NotImplementedException();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCompanyCommand)) {
            return false;
        }

        AddCompanyCommand otherAddCompanyCommand = (AddCompanyCommand) other;
        return toAdd.equals(otherAddCompanyCommand.toAdd);
    }
}
