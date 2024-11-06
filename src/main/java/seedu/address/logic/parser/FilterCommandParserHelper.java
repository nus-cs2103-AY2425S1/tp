package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGISTER_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_CLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

public class FilterCommandParserHelper {
    public static List<String> parseNames(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_NAME).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    public static List<String> parsePhones(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_PHONE).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    public static List<String> parseEmails(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_EMAIL).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    public static List<String> parseAddresses(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_ADDRESS).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    public static List<String> parseRegisterNumbers(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_REGISTER_NUMBER).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    public static List<String> parseSexes(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_SEX).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    public static List<String> parseClasses(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_STUDENT_CLASS).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    public static List<String> parseEcNames(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_ECNAME).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    public static List<String> parseEcNumbers(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_ECNUMBER).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    public static List<String> parseTags(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_TAG).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }
}
