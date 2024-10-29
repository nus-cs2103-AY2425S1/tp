package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Address;
import seedu.address.model.student.Days;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.OwedAmount;
import seedu.address.model.student.PaidAmount;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Rate;
import seedu.address.model.student.Schedule;
import seedu.address.model.student.Subject;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_HOUR = "Number of hours should be a positive multiple of 0.5";
    public static final String MESSAGE_OVERFLOW = "Number of hours exceeds max limit of the machine";

    private static final Logger logger = Logger.getLogger(ParserUtil.class.getName());
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
            logger.finer("Name is invalid: " + trimmedName);
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
     * Parses a {@code String day} into a {@code Days}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code day} is invalid.
     */
    public static Days parseDay(String day) throws ParseException {
        requireNonNull(day);
        assert !day.isEmpty();

        String trimmedDay = day.trim();
        if (!Days.isValidDay(trimmedDay)) {
            logger.finer("Day is invalid: " + trimmedDay);
            throw new ParseException(Days.MESSAGE_CONSTRAINTS);
        }
        return Days.valueOf(trimmedDay.toUpperCase());
    }
    /**
     * Parses a {@code String schedule} into an {@code Schedule}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code schedule} is invalid.
     */
    public static Schedule parseSchedule(String schedule) throws ParseException {
        requireNonNull(schedule);
        String trimmedSchedule = schedule.trim();
        if (!Schedule.isValidSchedule(trimmedSchedule)) {
            throw new ParseException(Schedule.MESSAGE_CONSTRAINTS);
        }
        return new Schedule(trimmedSchedule);
    }

    /**
     * Parses a {@code String subject} into an {@code Subject}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code subject} is invalid.
     */
    public static Subject parseSubject(String subject) throws ParseException {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!Subject.isValidSubject(trimmedSubject)) {
            throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
        }
        return new Subject(trimmedSubject);
    }

    /**
     * Parses a {@code String rate} into a {@code Rate}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code rate} is invalid.
     */
    public static Rate parseRate(String rate) throws ParseException {
        requireNonNull(rate);
        String trimmedRate = rate.trim();
        if (!Rate.isValidRate(trimmedRate)) {
            throw new ParseException(Rate.MESSAGE_CONSTRAINTS);
        }
        return new Rate(trimmedRate);
    }
    /**
     * Parses a {@code String paidAmount} into a {@code PaidAmount}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code paidAmount} is invalid.
     */
    public static PaidAmount parsePaidAmount(String paidAmount) throws ParseException {
        requireNonNull(paidAmount);
        String trimmedPaidAmount = paidAmount.trim();
        if (!PaidAmount.isValidPaidAmount(trimmedPaidAmount)) {
            throw new ParseException(PaidAmount.MESSAGE_CONSTRAINTS);
        }
        return new PaidAmount(trimmedPaidAmount);
    }

    /**
     * Parses a {@code String owedAmount} into an {@code OwedAmount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the {@code owedAmount} is invalid.
     */
    public static OwedAmount parseOwedAmount(String owedAmount) throws ParseException {
        requireNonNull(owedAmount);
        String trimmedOwedAmount = owedAmount.trim();
        if (!OwedAmount.isValidOwedAmount(trimmedOwedAmount)) {
            throw new ParseException(OwedAmount.MESSAGE_CONSTRAINTS);
        }
        return new OwedAmount(trimmedOwedAmount);
    }

    /**
     * Parses a {@code String amount} into an {@code double amount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the {@code amount} is invalid.
     */
    public static double parseAmount(String amount) throws ParseException {
        String trimmedAmount = amount.trim();
        double amountDouble = Double.parseDouble(trimmedAmount);
        if (amountDouble <= 0) {
            throw new ParseException("Amount has to positive");
        }
        return amountDouble;
    }

    /**
     * Parses a {@code String hoursPaid} into a {@code double}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the {@code hoursPaid} is invalid.
     */
    public static double parseHour(String hour) throws ParseException {
        requireNonNull(hour);
        String trimmedHour = hour.trim();
        if (!StringUtil.isPositiveMultipleOfHalfHour(trimmedHour)) {
            throw new ParseException(MESSAGE_INVALID_HOUR);
        }
        if (Double.parseDouble(trimmedHour) >= Double.MAX_VALUE) {
            throw new ParseException(MESSAGE_OVERFLOW);
        }
        return Double.parseDouble(trimmedHour);
    }

    /**
     * Parses a {@code Collection<String> nameStrings} into a {@code Set<String>}.
     * Duplicate names will be ignored.
     *
     * @throws ParseException if the {@code names} are invalid.
     */
    public static Set<String> parseNameStrings(Collection<String> nameStrings) throws ParseException {
        requireNonNull(nameStrings);
        assert !nameStrings.isEmpty();
        assert !nameStrings.contains("");

        HashSet<String> nameSet = new HashSet<>();

        for (String nameString : nameStrings) {
            addToNameHashSet(nameString, nameSet);
        }
        return nameSet;
    }

    /**
     * Parses a {@code Collection<String> days} into a {@code Set<Days>}.
     *
     * @throws ParseException if the {@code days} are invalid.
     */
    public static Set<Days> parseDays(Collection<String> days) throws ParseException {
        requireNonNull(days);
        assert !days.isEmpty();
        assert !days.contains("");

        HashSet<Days> daySet = new HashSet<>();

        for (String dayString : days) {
            addToDayHashSet(dayString, daySet);
        }
        return daySet;
    }

    private static void addToNameHashSet(String nameString, HashSet<String> nameSet) throws ParseException {
        String[] names = nameString.split("\\s+");
        for (String name : names) {
            parseName(name); // Check if name is valid
            nameSet.add(name); // Add to set to ensure uniqueness
        }
    }

    private static void addToDayHashSet(String dayString, HashSet<Days> daySet) throws ParseException {
        String[] dayStrings = dayString.split("\\s+");
        for (String day : dayStrings) {
            daySet.add(parseDay(day)); // Convert and add to the set
        }
    }
}
