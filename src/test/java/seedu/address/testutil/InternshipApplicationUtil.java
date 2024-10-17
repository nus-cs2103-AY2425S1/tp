package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.validator.DateValidator;
import seedu.address.model.internshipapplication.InternshipApplication;


/**
 * A utility class to help with adding InternshipApplication.
 */
public class InternshipApplicationUtil {

    /**
     * Returns an add command string for adding the {@code InternshipApplication}.
     */
    public static String getAddCommand(InternshipApplication application) {
        return AddCommand.COMMAND_WORD + " " + getApplicationDetails(application);
    }

    /**
     * Returns the part of command string for the given {@code InternshipApplication}'s details.
     */
    public static String getApplicationDetails(InternshipApplication application) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + application.getCompany().getName().getValue() + " ");
        sb.append(PREFIX_ROLE + application.getRole().getValue() + " ");
        sb.append(PREFIX_EMAIL + application.getCompany().getEmail().getValue() + " ");
        sb.append(PREFIX_DATE + application.getDateOfApplication().getValue().format(DateValidator.FORMATTER) + " ");

        return sb.toString();
    }

    // Todo when EDIT feature is implemented
    //
    //    /**
    //     * Returns the part of command string for the given {@code EditPApplicationDescriptor}'s details.
    //     */
    //    public static String getEditApplicationDescriptorDetails(EditCommand.EditPersonDescriptor descriptor) {
    //        StringBuilder sb = new StringBuilder();
    //        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.getValue()).append(" "));
    //        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
    //        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.getValue()).append(" "));
    //        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
    //        if (descriptor.getTags().isPresent()) {
    //            Set<Tag> tags = descriptor.getTags().get();
    //            if (tags.isEmpty()) {
    //                sb.append(PREFIX_TAG);
    //            } else {
    //                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
    //            }
    //        }
    //        return sb.toString();
    //    }
}
