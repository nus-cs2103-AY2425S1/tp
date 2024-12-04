package seedu.address.logic.commands.clientcommands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.model.person.Seller;


/**
 * Adds a person to the address book.
 */
public class AddSellerProfileCommand extends AddClientProfileCommand {

    public static final String COMMAND_WORD = "seller";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a seller to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New seller added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This seller already exists in the address book";

    public AddSellerProfileCommand(Seller seller) {
        super(seller);
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
