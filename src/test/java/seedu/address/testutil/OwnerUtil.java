package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddOwnerCommand;
import seedu.address.model.owner.Owner;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Owner.
 */
public class OwnerUtil {

    /**
     * Returns an add command string for adding the {@code owner}.
     */
    public static String getAddOwnerCommand(Owner owner) {
        return AddOwnerCommand.COMMAND_WORD + " " + getOwnerDetails(owner);
    }

    /**
     * Returns the part of command string for the given {@code owner}'s details.
     */
    public static String getOwnerDetails(Owner owner) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + owner.getName().fullName + " ");
        sb.append(PREFIX_PHONE + owner.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + owner.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + owner.getAddress().value + " ");
        return sb.toString();
    }

}
