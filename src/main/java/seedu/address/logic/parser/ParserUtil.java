package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_POLICY_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_POLICY_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT_PAYMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_START_DATE;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Payment;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Policy;
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
     * Parses a {@code String name} into a {@code Name} with a custom error message.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param name The name to be parsed.
     * @param customErrorMessage The custom error message to be used if the name is invalid.
     * @return A {@code Name} object if the given {@code name} is valid.
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name, String customErrorMessage) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(customErrorMessage);
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
     * Parses {@code String birthday} into a {@code Birthday}.
     */
    public static Birthday parseBirthday(String birthday) throws ParseException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        System.out.println(trimmedBirthday);
        if (!Birthday.isValidBirthday(trimmedBirthday)) {
            throw new ParseException(Birthday.MESSAGE_CONSTRAINTS);
        }
        return new Birthday(birthday);
    }

    /**
     * Parses {@code String appointment} into a {@code Appointment}.
     */
    public static Appointment parseAppointment(String appointment) throws ParseException {
        requireNonNull(appointment);
        String trimmedAppointment = appointment.trim();
        System.out.println(trimmedAppointment);
        if (!Appointment.isValidAppointment(trimmedAppointment)) {
            throw new ParseException(Appointment.MESSAGE_CONSTRAINTS);
        }
        return new Appointment(appointment);
    }


    /**
     * Parses a {@code String policyArgs} into a {@code Policy}.
     *
     * @throws ParseException if the given {@code policyArgs} is invalid.
     */
    public static Policy parsePolicy(String policyArgs) throws ParseException {
        requireNonNull(policyArgs);
        String trimmedPolicy = policyArgs.trim();

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(trimmedPolicy, PREFIX_POLICY_NAME, PREFIX_POLICY_START_DATE,
                        PREFIX_POLICY_END_DATE, PREFIX_NEXT_PAYMENT_DATE, PREFIX_PAYMENT_AMOUNT);

        if (!arePrefixesPresent(argMultimap, PREFIX_POLICY_NAME, PREFIX_POLICY_START_DATE,
                PREFIX_POLICY_END_DATE, PREFIX_NEXT_PAYMENT_DATE, PREFIX_PAYMENT_AMOUNT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_POLICY_FORMAT));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POLICY_NAME, PREFIX_POLICY_START_DATE,
                PREFIX_POLICY_END_DATE, PREFIX_NEXT_PAYMENT_DATE, PREFIX_PAYMENT_AMOUNT);

        String nameString = argMultimap.getValue(PREFIX_POLICY_NAME).get();
        String startDateString = argMultimap.getValue(PREFIX_POLICY_START_DATE).get();
        String endDateString = argMultimap.getValue(PREFIX_POLICY_END_DATE).get();
        String paydateString = argMultimap.getValue(PREFIX_NEXT_PAYMENT_DATE).get();
        String paymentAmount = argMultimap.getValue(PREFIX_PAYMENT_AMOUNT).get();
        String insurancePayment = paydateString + " " + paymentAmount;
        if (!Payment.isValidInsurancePayment(insurancePayment)) {
            throw new ParseException(Payment.MESSAGE_CONSTRAINTS);
        }
        if (!Policy.isValidPolicy(nameString, startDateString, endDateString, insurancePayment)) {
            throw new ParseException(Policy.MESSAGE_CONSTRAINTS);
        }
        return new Policy(nameString, startDateString, endDateString, insurancePayment);
    }

    /**
     * Parses {@code Collection<String> policies} into a {@code List<Policy>}.
     */
    public static Map<Index, Policy> parsePolicies(Collection<String> policies) throws ParseException {
        requireNonNull(policies);
        final Map<Index, Policy> policyMap = new HashMap<>();
        final Set<Integer> toEditIndexSet = new HashSet<>();

        for (String policyArgs : policies) {
            String trimmedPolicy = policyArgs.trim();

            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(trimmedPolicy, PREFIX_POLICY_NAME);

            Index index;

            try {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_POLICY_FORMAT), pe);
            }

            if (toEditIndexSet.contains(index.getZeroBased())) {
                throw new ParseException(MESSAGE_DUPLICATE_POLICY_INDEX);
            }

            toEditIndexSet.add(index.getZeroBased());
            policyMap.put(index, parsePolicy(policyArgs));
        }

        return policyMap;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
