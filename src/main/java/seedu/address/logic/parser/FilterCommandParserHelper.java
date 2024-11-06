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

/**
 * This class contains the parser helper methods to ensure format checking of filter attribute values
 */
public class FilterCommandParserHelper {

    /**
     * Parses and returns all name values associated with the prefix {@code PREFIX_NAME}.
     * Splits each name value by whitespace and trims each resulting substring.
     *
     * @param argMultimap The ArgumentMultimap containing the command arguments.
     * @return A list of parsed names as strings.
     */
    public static List<String> parseNames(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_NAME).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    /**
     * Parses and returns all phone values associated with the prefix {@code PREFIX_PHONE}.
     * Splits each phone value by whitespace and trims each resulting substring.
     *
     * @param argMultimap The ArgumentMultimap containing the command arguments.
     * @return A list of parsed phone numbers as strings.
     */
    public static List<String> parsePhones(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_PHONE).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    /**
     * Parses and returns all email values associated with the prefix {@code PREFIX_EMAIL}.
     * Splits each email value by whitespace and trims each resulting substring.
     *
     * @param argMultimap The ArgumentMultimap containing the command arguments.
     * @return A list of parsed emails as strings.
     */
    public static List<String> parseEmails(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_EMAIL).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    /**
     * Parses and returns all address values associated with the prefix {@code PREFIX_ADDRESS}.
     * Splits each address value by whitespace and trims each resulting substring.
     *
     * @param argMultimap The ArgumentMultimap containing the command arguments.
     * @return A list of parsed addresses as strings.
     */
    public static List<String> parseAddresses(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_ADDRESS).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    /**
     * Parses and returns all register number values associated with the prefix {@code PREFIX_REGISTER_NUMBER}.
     * Splits each register number value by whitespace and trims each resulting substring.
     *
     * @param argMultimap The ArgumentMultimap containing the command arguments.
     * @return A list of parsed register numbers as strings.
     */
    public static List<String> parseRegisterNumbers(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_REGISTER_NUMBER).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    /**
     * Parses and returns all sex values associated with the prefix {@code PREFIX_SEX}.
     * Splits each sex value by whitespace and trims each resulting substring.
     *
     * @param argMultimap The ArgumentMultimap containing the command arguments.
     * @return A list of parsed sex values as strings.
     */
    public static List<String> parseSexes(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_SEX).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    /**
     * Parses and returns all student class values associated with the prefix {@code PREFIX_STUDENT_CLASS}.
     * Splits each student class value by whitespace and trims each resulting substring.
     *
     * @param argMultimap The ArgumentMultimap containing the command arguments.
     * @return A list of parsed student classes as strings.
     */
    public static List<String> parseClasses(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_STUDENT_CLASS).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    /**
     * Parses and returns all emergency contact name values associated with the prefix {@code PREFIX_ECNAME}.
     * Splits each emergency contact name value by whitespace and trims each resulting substring.
     *
     * @param argMultimap The ArgumentMultimap containing the command arguments.
     * @return A list of parsed emergency contact names as strings.
     */
    public static List<String> parseEcNames(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_ECNAME).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    /**
     * Parses and returns all emergency contact number values associated with the prefix {@code PREFIX_ECNUMBER}.
     * Splits each emergency contact number value by whitespace and trims each resulting substring.
     *
     * @param argMultimap The ArgumentMultimap containing the command arguments.
     * @return A list of parsed emergency contact numbers as strings.
     */
    public static List<String> parseEcNumbers(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_ECNUMBER).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

    /**
     * Parses and returns all tag values associated with the prefix {@code PREFIX_TAG}.
     * Splits each tag value by whitespace and trims each resulting substring.
     *
     * @param argMultimap The ArgumentMultimap containing the command arguments.
     * @return A list of parsed tags as strings.
     */
    public static List<String> parseTags(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_TAG).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }
}
