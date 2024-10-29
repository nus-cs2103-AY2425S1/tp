package seedu.hireme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hireme.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.hireme.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.hireme.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hireme.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.hireme.commons.util.ToStringBuilder;
import seedu.hireme.logic.Messages;
import seedu.hireme.logic.commands.exceptions.CommandException;
import seedu.hireme.model.Model;
import seedu.hireme.model.internshipapplication.InternshipApplication;

/**
 * Adds an internship application to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "/a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an internship application to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "COMPANY "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_EMAIL + "COMPANY_EMAIL "
            + PREFIX_DATE + "DATE_OF_APPLICATION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "ABC Company "
            + PREFIX_ROLE + "Software Engineer Intern "
            + PREFIX_EMAIL + "abc_company@example.com "
            + PREFIX_DATE + "01/01/24";

    public static final String MESSAGE_SUCCESS = "New internship application added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPLICATION =
            "This internship application already exists in the address book";

    private final InternshipApplication toAdd;

    /**
     * Creates an AddCommand to add the specified {@code InternshipApplication}
     */
    public AddCommand(InternshipApplication internship) {
        requireNonNull(internship);
        toAdd = internship;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasItem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPLICATION);
        }

        model.addItem(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)), false,
                false, false, model.getInsights());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
