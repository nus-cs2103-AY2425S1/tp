package seedu.hireme.testutil;

import static seedu.hireme.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.hireme.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.hireme.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hireme.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.hireme.logic.commands.AddCommand;
import seedu.hireme.logic.validator.DateValidator;
import seedu.hireme.model.internshipapplication.InternshipApplication;


/**
 * A utility class to help with adding InternshipApplication.
 */
public class InternshipApplicationUtil {

    /**
     * Creates an add command string for adding the {@code InternshipApplication}.
     */
    public static String getAddCommand(InternshipApplication application) {
        return AddCommand.COMMAND_WORD + " " + getApplicationDetails(application);
    }

    /**
     * Creates the part of command string for the given {@code InternshipApplication}'s details.
     */
    public static String getApplicationDetails(InternshipApplication application) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + application.getCompany().getName().toString() + " ");
        sb.append(PREFIX_ROLE + application.getRole().toString() + " ");
        sb.append(PREFIX_EMAIL + application.getCompany().getEmail().toString() + " ");
        sb.append(PREFIX_DATE + application.getDateOfApplication().toString() + " ");

        return sb.toString();
    }

}
