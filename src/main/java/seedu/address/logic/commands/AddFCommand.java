package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRECORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRISK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book with optional fields.
 */
public class AddFCommand extends Command {

    public static final String COMMAND_WORD = "addf";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_BIRTHDATE + "BIRTHDATE "
            + PREFIX_SEX + "SEX "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_BLOODTYPE + "BLOOD TYPE] "
            + "[" + PREFIX_NOKNAME + "NEXT-OF-KIN NAME] "
            + "[" + PREFIX_NOKPHONE + "NEXT-OF-KIN PHONE] "
            + "[" + PREFIX_ALLERGY + "ALLERGY] "
            + "[" + PREFIX_HEALTHRISK + "HEALTH RISK] "
            + "[" + PREFIX_HEALTHRECORD + "PAST HEALTH RECORD] "
            + "[" + PREFIX_NOTE + "ADDITIONAL NOTES]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NRIC + "T0123456A "
            + PREFIX_BIRTHDATE + "2001-12-31 "
            + PREFIX_SEX + "M "
            + PREFIX_PHONE + "81234567 "
            + PREFIX_EMAIL + "johndoe123@gmail.com "
            + PREFIX_ADDRESS + "Block 123, NUS Road, S123123 "
            + PREFIX_BLOODTYPE + "A+ "
            + PREFIX_NOKNAME + "Jack Doe "
            + PREFIX_NOKPHONE + "91234567 "
            + PREFIX_ALLERGY + "nuts, shellfish "
            + PREFIX_HEALTHRISK + "HIGH "
            + PREFIX_HEALTHRECORD + "Diabetes "
            + PREFIX_NOTE + "Patient needs extra care";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddFCommand to add the specified {@code Person}
     */
    public AddFCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddFCommand)) {
            return false;
        }

        AddFCommand otherAddFCommand = (AddFCommand) other;
        return toAdd.equals(otherAddFCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
