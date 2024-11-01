package seedu.ddd.logic.commands;

import static seedu.ddd.logic.parser.CliFlags.FLAG_CLIENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_EVENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_VENDOR;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_CLIENTS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_VENDORS;

/**
 * This interface provides constants for add command-related keywords and flags.
 */
public abstract class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String FLAG_PARSE_ERROR = "to add a client contact, vendor contact, or event respectively.";

    public static final String COMMAND_USAGE = "To add contact: " + COMMAND_WORD + " {"
            + FLAG_CLIENT + " | "
            + FLAG_VENDOR + " " + PREFIX_SERVICE + "SERVICE} "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG ...]\n"
            + "To add event: " + COMMAND_WORD + " "
            + PREFIX_NAME + "NAME "
            + PREFIX_DESC + "DESCRIPTION "
            + PREFIX_DATE + "DATE "
            + PREFIX_CLIENTS + "CLIENT ... "
            + PREFIX_VENDORS + "VENDOR ...";;
}
