package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.dateformatter.DateFormatter.MM_DD_YYYY_FORMATTER;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.dateformatter.DateFormatter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyType;
import seedu.address.model.tag.Tag;

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
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parse a {@code String policy} into a {@code PolicyType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code policy} is invalid.
     */
    public static PolicyType parsePolicyType(String policy) throws ParseException {
        requireNonNull(policy);
        String trimmedPolicy = policy.trim();
        try {
            return PolicyType.fromString(trimmedPolicy);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Policy.POLICY_TYPE_MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code Optional<String>} policy into a {@code PolicyType}.
     *
     * @param policies The List of string representing the policy type.
     *               The policy string will be trimmed and converted to lowercase
     *               for comparison with predefined {@code PolicyType} values.
     * @return The {@code PolicyType} corresponding to the given policy string.
     * @throws ParseException If the given {@code policy} is empty or does not match
     *                        any valid {@code PolicyType}.
     */
    public static Set<PolicyType> parsePolicyTypes(List<String> policies) throws ParseException {
        requireNonNull(policies);
        if (policies.isEmpty()) {
            throw new ParseException(Policy.POLICY_TYPE_MESSAGE_CONSTRAINTS);
        }

        final Set<PolicyType> policyTypes = new HashSet<>();
        for (String policy : policies) {
            if (!policyTypes.add(parsePolicyType(policy))) {
                throw new ParseException(AddPolicyCommand.MESSAGE_DUPLICATES);
            }
        }
        return Collections.unmodifiableSet(policyTypes);
    }

    /**
     * Parse a {@code String amount} into a {@code double}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static double parsePolicyAmount(String amount) throws ParseException {
        requireNonNull(amount);
        if (amount == "") {
            return -1.0;
        }

        String trimmedAmount = amount.trim();
        try {
            double parsedDouble = Double.parseDouble(trimmedAmount);
            if (parsedDouble < 0) {
                throw new ParseException(Policy.AMOUNT_MESSAGE_CONSTRAINTS);
            }
            return parsedDouble;
        } catch (NumberFormatException e) {
            throw new ParseException(Policy.AMOUNT_MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parse a {@code String expiryDate} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code expiryDate} is invalid.
     */
    public static LocalDate parseExpiryDate(String expiryDate) throws ParseException {
        requireNonNull(expiryDate);
        if (expiryDate == "") {
            return null;
        }

        String trimmedExpiryDate = expiryDate.trim();
        try {
            return LocalDate.parse(trimmedExpiryDate, MM_DD_YYYY_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ParseException(DateFormatter.MM_DD_YYYY_MESSAGE_CONSTRAINTS);
        }
    }
    /**
     * Parses a {@code String} into a {@code ClaimStatus}.
     * This method trims the input string and attempts to convert it into a valid {@code ClaimStatus}
     * using the {@code ClaimStatus.fromString()} method. If the string is not a valid claim status,
     * a {@code ParseException} is thrown with an appropriate error message.
     * @param claimStatus The string to parse into a {@code ClaimStatus}.
     * @return The corresponding {@code ClaimStatus} after parsing.
     * @throws ParseException if the string does not represent a valid {@code ClaimStatus}.
     */
    public static ClaimStatus parseClaimStatus(String claimStatus) throws ParseException {
        requireNonNull(claimStatus);
        String trimmedClaimStatus = claimStatus.trim();
        try {
            return ClaimStatus.fromString(trimmedClaimStatus);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Claim.CLAIM_STATUS_MESSAGE_CONSTRAINTS);
        }
    }
}
