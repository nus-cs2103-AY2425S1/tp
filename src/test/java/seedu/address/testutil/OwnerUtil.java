package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IC_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.AddOwnerCommand;
import seedu.address.logic.commands.EditOwnerCommand;
import seedu.address.model.owner.Owner;

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
        sb.append(PREFIX_IC_NUMBER + owner.getIdentificationNumber().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditOwnerDescriptor}'s details.
     */
    public static String getEditOwnerDescriptorDetails(EditOwnerCommand.EditOwnerDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        return sb.toString();
    }

}
