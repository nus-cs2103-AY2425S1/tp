package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.model.person.Buyer;

/**
 * Adds a person to the address book.
 */
public class AddBuyerProfile extends AddClientProfile {

    public static final String COMMAND_WORD = "buyer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a buyer to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney "
            + PREFIX_EMAIL + "johnd@example.com";

    public static final String MESSAGE_SUCCESS = "New buyer added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This buyer already exists in the address book";

    /**
     * Creates an AddClientProfile to add the specified {@code Person}
     */
    public AddBuyerProfile(Buyer buyer) {
        super(buyer);
    }

    @Override
    protected String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }

    @Override
    protected String getDuplicatePersonMessage() {
        return MESSAGE_DUPLICATE_PERSON;
    }
}
