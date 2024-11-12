package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BILLING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.company.Company;

/**
 * Adds a company to the address book.
 */
public class AddCompanyCommand extends AddCommand<Company> {

    public static final String ENTITY_WORD = "company";
    public static final String FULL_COMMAND = COMMAND_WORD + " " + ENTITY_WORD;
    public static final String MESSAGE_USAGE = FULL_COMMAND + ": Adds a company to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_BILLING_DATE + "BILLING_DATE "
            + PREFIX_PHONE + "PHONE\n"
            + "Example: " + FULL_COMMAND + " "
            + PREFIX_NAME + "Apple "
            + PREFIX_ADDRESS + "12 Ang Mo Kio Street 64, Singapore 569088 "
            + PREFIX_BILLING_DATE + "5 "
            + PREFIX_PHONE + "64815511";

    public static final String MESSAGE_SUCCESS = "Company added: %1$s; Address: %2$s; "
            + "Billing date: %3$s; Phone: %4$s;";

    public static final String MESSAGE_DUPLICATE_COMPANY = "This company already exists in the address book";

    /**
     * Creates an AddCompanyCommand to add the specified {@code Company}
     */
    public AddCompanyCommand(Company company) {
        super(company);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCompany(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_COMPANY);
        }
        String name = toAdd.getName().toString();
        String address = toAdd.getAddress().toString();
        String billingDate = toAdd.getBillingDate().toString();
        String phone = toAdd.getPhone().toString();

        model.addCompany(toAdd);
        String successMessage = String.format(MESSAGE_SUCCESS, name, address, billingDate, phone);

        return new CommandResult(successMessage);
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
