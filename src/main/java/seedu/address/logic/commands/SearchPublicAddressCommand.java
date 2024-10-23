package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes the searchPublicAddress of an existing person in the address book.
 */
public class SearchPublicAddressCommand extends Command {

    public static final String COMMAND_WORD = "searchPublicAddress";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Searches for a public address and returns the user, network and tag "
            + "throughout all the public addresses of all networks in the address book.\n"
            + "Parameters: PUBLIC_ADDRESS (must be a string) " + PREFIX_PUBLIC_ADDRESS
            + "[PUBLIC_ADDRESS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PUBLIC_ADDRESS + "0x28f91d6e72eaf4372892e6c6e45dc41b574163e9fcdf94f4997958b46d772fa2";

    public static final String MESSAGE_ARGUMENTS = "Public Address: %1$s";


    private final String publicAddress;

    /**
     * @param publicAddress of the person to be updated to
     */
    public SearchPublicAddressCommand(String publicAddress) {
        requireAllNonNull(publicAddress);


        this.publicAddress = publicAddress;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, publicAddress));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SearchPublicAddressCommand e)) {
            return false;
        }

        return publicAddress.equals(e.publicAddress);
    }

}
