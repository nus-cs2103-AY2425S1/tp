package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Email;
import seedu.address.model.client.NameWithoutNumber;
import seedu.address.model.client.Phone;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;
import seedu.address.model.property.Ask;
import seedu.address.model.property.Bid;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Type;
import seedu.address.model.property.Unit;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String postalCode} into a {@code postalCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code postalCode} is invalid.
     */
    public static PostalCode parsePostalCode(String postalCode) throws ParseException {
        requireNonNull(postalCode);
        String trimmedPostalCode = postalCode.trim();
        if (!PostalCode.isValidPostalCode(trimmedPostalCode)) {
            throw new ParseException(PostalCode.MESSAGE_CONSTRAINTS);
        }
        return new PostalCode(trimmedPostalCode);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     *
     * @param argumentMultimap The argument multimap that holds the parsed arguments.
     * @param prefixes The prefixes to check for presence.
     * @return True if all prefixes contain non-empty values, false otherwise.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if number of tokens in args string exceeds specified prefixes.
     */
    public static boolean hasExcessToken(String args, Prefix... prefixes) {
        String[] splits = args.trim().split("\\s(?=\\S+/)");
        if (splits[0].equals("/")) {
            return false;
        }
        return splits.length > prefixes.length;
    }
    /**
     * Determines if there are more valid tokens in the input arguments than allowed by the specified prefixes.
     *
     * @param args The input arguments as a single string.
     * @param prefixes The allowed prefixes that define valid tokens.
     * @return True if there are excess tokens beyond the allowed prefixes, false otherwise.
     */
    public static boolean hasExcessTokenName(String args, Prefix... prefixes) {
        String[] tokens = splitArgsByPrefix(args);
        int validTokenCount = countValidTokens(tokens);

        return isInvalidTokenSequence(tokens) || hasMoreTokensThanPrefixes(validTokenCount, prefixes.length);
    }

    /**
     * Splits the input arguments into tokens based on prefix pattern.
     *
     * @param args The input arguments as a single string.
     * @return An array of tokens split by prefix pattern.
     */
    private static String[] splitArgsByPrefix(String args) {
        return args.trim().split("\\s(?=\\S+/)");
    }

    /**
     * Counts the number of valid tokens in the array, excluding any tokens that contain
     * "s/o" or "d/o" in invalid positions.
     *
     * @param tokens The array of tokens to analyze.
     * @return The count of valid tokens, or Integer.MAX_VALUE if an invalid token is found.
     */
    private static int countValidTokens(String[] tokens) {
        int validTokenCount = 0;
        String previousPrefix = "";

        for (String token : tokens) {
            if (isInvalidToken(token, previousPrefix)) {
                return Integer.MAX_VALUE; // Exceeds valid count immediately if invalid
            }

            if (!containsRelationshipToken(token)) {
                validTokenCount++;
            }

            previousPrefix = token;
        }

        return validTokenCount;
    }

    /**
     * Determines if a token is invalid due to containing a relationship identifier ("s/o" or "d/o")
     * that appears without an "n/" prefix.
     *
     * @param token The current token to check.
     * @param previousPrefix The previous token's prefix to verify context.
     * @return True if the token is invalid, false otherwise.
     */
    private static boolean isInvalidToken(String token, String previousPrefix) {
        return (token.contains("s/o") || token.contains("d/o")) && !previousPrefix.contains("n/");
    }

    /**
     * Checks if a token contains a relationship identifier, such as "s/o" or "d/o".
     *
     * @param token The token to check.
     * @return True if the token contains a relationship identifier, false otherwise.
     */
    private static boolean containsRelationshipToken(String token) {
        return token.toLowerCase().contains("s/o") || token.toLowerCase().contains("d/o");
    }

    /**
     * Determines if the token sequence is invalid based on the starting character.
     * An invalid sequence starts with a standalone "/".
     *
     * @param tokens The array of tokens to check.
     * @return True if the sequence is invalid, false otherwise.
     */
    private static boolean isInvalidTokenSequence(String[] tokens) {
        return tokens[0].equals("/");
    }

    /**
     * Checks if the number of valid tokens exceeds the count of allowed prefixes.
     *
     * @param validTokenCount The count of valid tokens.
     * @param prefixCount The count of allowed prefixes.
     * @return True if there are more valid tokens than allowed prefixes, false otherwise.
     */
    private static boolean hasMoreTokensThanPrefixes(int validTokenCount, int prefixCount) {
        return validTokenCount > prefixCount;
    }


    /**
     * Parses a {@code String unitNumber} into a {@code unitNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code unitNumber} is invalid.
     */
    public static Unit parseUnit(String unitNumber) throws ParseException {
        requireNonNull(unitNumber);
        String trimmedUnitNumber = unitNumber.trim();
        if (!Unit.isValidUnit(trimmedUnitNumber)) {
            throw new ParseException(Unit.MESSAGE_CONSTRAINTS);
        }
        return new Unit(trimmedUnitNumber);
    }

    /**
     * Parses a {@code String type} into a {@code Type}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Type} is invalid.
     */
    public static Type parseType(String type) throws ParseException {
        requireNonNull(type);
        String trimmedType = type.trim();
        if (!Type.isValidType(trimmedType)) {
            throw new ParseException(Type.MESSAGE_CONSTRAINTS);
        }
        return new Type(trimmedType);
    }

    /**
     * Parses a {@code String ask} into a {@code Ask}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Ask} is invalid.
     */
    public static Ask parseAsk(String ask) throws ParseException {
        requireNonNull(ask);
        String trimmedAsk = ask.trim();
        if (!Ask.isValidAsk(trimmedAsk)) {
            throw new ParseException(Ask.MESSAGE_CONSTRAINTS);
        }
        return new Ask(trimmedAsk);
    }

    /**
     * Parses a {@code String bid} into a {@code Bid}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Bid} is invalid.
     */
    public static Bid parseBid(String bid) throws ParseException {
        requireNonNull(bid);
        String trimmedBid = bid.trim();
        if (!Bid.isValidBid(trimmedBid)) {
            throw new ParseException(Bid.MESSAGE_CONSTRAINTS);
        }
        return new Bid(trimmedBid);
    }

    /**
     * Parses a client name, ensuring it does not contain numbers and follows naming constraints.
     *
     * @param name The input name to be parsed.
     * @return A new NameWithoutNumber instance if the name is valid.
     * @throws ParseException If the name does not meet the required constraints.
     */
    public static NameWithoutNumber parseClientNameWithoutNumber(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();

        if (containsRelationshipPrefix(trimmedName, "s/o")) {
            return parseRelationshipName(trimmedName, "s/o");
        } else if (containsRelationshipPrefix(trimmedName, "d/o")) {
            return parseRelationshipName(trimmedName, "d/o");
        } else {
            validateName(trimmedName);
            return new NameWithoutNumber(trimmedName);
        }
    }

    /**
     * Checks if the name contains a specified relationship prefix (e.g., "s/o" or "d/o").
     *
     * @param name The name to check.
     * @param prefix The relationship prefix to look for.
     * @return True if the name contains the specified prefix, false otherwise.
     */
    private static boolean containsRelationshipPrefix(String name, String prefix) {
        return name.contains(prefix);
    }

    /**
     * Parses a name containing a relationship prefix and validates each part.
     *
     * @param name The name containing the relationship prefix.
     * @param prefix The relationship prefix (e.g., "s/o" or "d/o").
     * @return A new NameWithoutNumber instance if both parts of the name are valid.
     * @throws ParseException If any part of the name does not meet the required constraints.
     */
    private static NameWithoutNumber parseRelationshipName(String name, String prefix) throws ParseException {
        String[] parts = name.split(prefix.toLowerCase(), 2);
        String beforePrefix = parts[0].trim();
        String afterPrefix = parts[1].trim();

        if (!isValidNamePart(beforePrefix) || !isValidNamePart(afterPrefix)) {
            throw new ParseException(NameWithoutNumber.MESSAGE_CONSTRAINTS);
        }

        return new NameWithoutNumber(name);
    }

    /**
     * Validates a name part to ensure it meets naming constraints.
     *
     * @param namePart The part of the name to validate.
     * @return True if the name part is valid, false otherwise.
     */
    private static boolean isValidNamePart(String namePart) {
        return NameWithoutNumber.isValidNameWithoutNumber(namePart);
    }

    /**
     * Validates the name as a whole, throwing an exception if it does not meet the required constraints.
     *
     * @param name The name to validate.
     * @throws ParseException If the name does not meet the required constraints.
     */
    private static void validateName(String name) throws ParseException {
        if (!NameWithoutNumber.isValidNameWithoutNumber(name)) {
            throw new ParseException(NameWithoutNumber.MESSAGE_CONSTRAINTS);
        }
    }


    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parseClientPhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseClientEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String meetingTitle} into an {@code MeetingTitle}.
     * Titles should only contain alphanumeric characters and spaces, and it should not be blank.
     *
     * @throws ParseException if the given {@code meetingTitle} is invalid.
     */
    public static MeetingTitle parseMeetingTitle(String meetingTitle) throws ParseException {
        requireNonNull(meetingTitle);
        String trimmedMeetingTitle = meetingTitle.trim();
        if (!MeetingTitle.isValidMeetingTitle(trimmedMeetingTitle)) {
            throw new ParseException(MeetingTitle.MESSAGE_CONSTRAINTS);
        }
        return new MeetingTitle(trimmedMeetingTitle);
    }

    /**
     * Parses a {@code String meetingDate} into an {@code MeetingDate}.
     * Meeting dates should be in the format dd-MM-yyyy and must be a valid date.
     *
     * @throws ParseException if the given {@code meetingDate} is invalid.
     */
    public static MeetingDate parseMeetingDate(String meetingDate) throws ParseException {
        requireNonNull(meetingDate);
        String trimmedMeetingDate = meetingDate.trim();
        if (!MeetingDate.isValidMeetingDate(trimmedMeetingDate)) {
            throw new ParseException(MeetingDate.MESSAGE_CONSTRAINTS);
        }
        return new MeetingDate(trimmedMeetingDate);
    }
}
