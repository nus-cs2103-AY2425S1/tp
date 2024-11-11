package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Address;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.insurance.claim.Claim;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
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
     * Parses a {@code String insurancePlanId} into a {@code Integer}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code insurancePlanId} is invalid.
     */
    public static int parseInsurancePlan(String insurancePlanId) throws ParseException {
        requireNonNull(insurancePlanId);
        int trimmedInsurancePlanId;

        try {
            trimmedInsurancePlanId = Integer.parseInt(insurancePlanId.trim());
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.MESSAGE_INVALID_INSURANCE_ID);
        }

        if (trimmedInsurancePlanId < 0) {
            throw new ParseException(Messages.MESSAGE_INVALID_INSURANCE_ID);
        }

        return trimmedInsurancePlanId;
    }

    /**
     * Parses {@code String claimId} into a valid claimId.
     * Leading and trailing whitespaces will be trimmed. Claim ID will be in uppercase.
     *
     * @throws ParseException if the given claimId is invalid based on preset conventions.
     */
    public static String parseClaimId(String claimId) throws ParseException {
        requireNonNull(claimId);
        String processedClaimId = claimId.trim().toUpperCase();
        Claim.checkValidClaimId(processedClaimId);
        return processedClaimId;
    }

    /**
     * Parses {@code String claimId} into a valid claimId.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the claim amount is not a valid positive integer
     */
    public static int parseClaimAmount(String claimAmount) throws ParseException {
        requireNonNull(claimAmount);
        String[] claimAmountString = claimAmount.trim().split("\\.");

        if (claimAmountString.length > 2) {
            throw new ParseException(Claim.MESSAGE_TOO_MANY_DECIMALS);
        } else if (claimAmountString.length == 1) {
            throw new ParseException(Claim.INVALID_CLAIM_AMOUNT);
        }

        int claimAmountInt;

        try {
            int centsInADollar = 100;
            int claimAmountDollars = Integer.parseInt(claimAmountString[0].trim());

            String claimAmountCentsString = claimAmountString[1].trim();
            if (claimAmountCentsString.length() != 2) {
                throw new ParseException(Claim.MESSAGE_INVALID_CENTS);
            }
            int claimAmountCents = Integer.parseInt(claimAmountCentsString);

            claimAmountInt = claimAmountDollars * centsInADollar + claimAmountCents;
        } catch (NumberFormatException e) {
            throw new ParseException(Claim.INVALID_CLAIM_AMOUNT);
        }

        Claim.checkValidClaimAmount(claimAmountInt);
        return claimAmountInt;
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
}
