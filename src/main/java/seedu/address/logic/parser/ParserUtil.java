package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagName;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Event;
import seedu.address.model.task.ParsedTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.Todo;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

    // =======================================================================
    // Person Parsing Methods
    // =======================================================================

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
     * Parses a {@code String date} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static String parseDate(String address) throws ParseException {
        requireNonNull(address);
        return address.trim();
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
            throw new ParseException(TagName.MESSAGE_CONSTRAINTS);
        }
        return new Tag(new TagName(trimmedTag));
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

    // =======================================================================
    // Task Parsing Methods
    // =======================================================================

    /**
     * Parses a task description to determine the task type and details.
     *
     * @throws ParseException if the task format is invalid.
     */
    public static Task parseTask(String taskDescription) throws ParseException {
        requireNonNull(taskDescription);
        ParsedTask parsedTask = parseTaskTypeAndDetails(taskDescription);
        String taskType = parsedTask.getTaskType();
        String taskDetails = parsedTask.getTaskDetails();

        switch (taskType) {
        case "todo":
            return parseTodoTask(taskDetails);
        case "deadline":
            return parseDeadlineTask(taskDetails);
        case "event":
            return parseEventTask(taskDetails);
        default:
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_TASK_TYPE, taskType));
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Task> parseTasks(List<String> taskDescriptions) throws ParseException {
        final Set<Task> taskSet = new HashSet<>();
        for (String taskDescription : taskDescriptions) {
            taskSet.add(parseTask(taskDescription));
        }
        return taskSet;
    }

    /**
     * Parses the task description to extract the task type and task details.
     *
     * @throws ParseException if the format is invalid.
     */
    private static ParsedTask parseTaskTypeAndDetails(String taskDescription) throws ParseException {
        requireNonNull(taskDescription);
        // Splits the descriptions into a maximum of 2 tokens, based on first whitespace present
        String[] tokens = taskDescription.split("\\s+", 2);

        if (tokens.length < 2) {
            throw new ParseException(Messages.MESSAGE_INCOMPLETE_TASK_DESCRIPTION);
        }

        String taskType = tokens[0].toLowerCase();
        String taskDetails = tokens[1].trim();

        return new ParsedTask(taskType, taskDetails);
    }

    /**
     * Parses the task details for a "Todo" task and returns a {@code Todo} object.
     *
     * @param taskDetails The task details to parse.
     * @return A Todo object.
     */
    private static Todo parseTodoTask(String taskDetails) {
        return new Todo(taskDetails.trim());
    }

    /**
     * Parses the task details for a "Deadline" task and returns a {@code Deadline} object.
     *
     * @param taskDetails The task details to parse.
     * @return A Deadline object.
     * @throws ParseException if the format is invalid.
     */
    private static Deadline parseDeadlineTask(String taskDetails) throws ParseException {
        String[] deadlineParts = taskDetails.split("/by ", 2);
        // Check if both description and deadline are present
        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty()) {
            throw new ParseException(Messages.MESSAGE_INVALID_DEADLINE_FORMAT);
        }
        String description = deadlineParts[0].trim();
        String byDate = deadlineParts[1].trim();

        validateDateFormat(byDate);
        return new Deadline(description, byDate);
    }

    /**
     * Parses the task details for an "Event" task and returns an {@code Event} object.
     *
     * @param taskDetails The task details to parse.
     * @return An Event object.
     * @throws ParseException if the format is invalid.
     */
    private static Event parseEventTask(String taskDetails) throws ParseException {
        String[] eventParts = taskDetails.split("/from", 2);
        if (eventParts.length < 2 || !eventParts[1].contains("/to")) {
            throw new ParseException(Messages.MESSAGE_INVALID_EVENT_FORMAT);
        }
        String description = eventParts[0].trim();
        String[] dateParts = eventParts[1].split("/to", 2);
        String startDate = dateParts[0].trim();
        String endDate = dateParts[1].trim();
        validateDateFormat(startDate, endDate);

        return new Event(description, startDate, endDate);
    }

    /**
     * Validates the date format against the predefined pattern (yyyy-MM-dd).
     *
     * @throws ParseException if the date format is invalid.
     */
    private static void validateDateFormat(String date) throws ParseException {
        try {
            LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ParseException(Messages.MESSAGE_INVALID_DATE_FORMAT);
        }
    }

    /**
     * Validates the date format for two dates and checks if the "from" date is before the "to" date.
     *
     * @throws ParseException if the date format is invalid or "from" date is not before "to" date.
     */
    private static void validateDateFormat(String fromDate, String toDate) throws ParseException {
        LocalDate from;
        LocalDate to;
        try {
            from = LocalDate.parse(fromDate, DATE_FORMATTER);
            to = LocalDate.parse(toDate, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ParseException(Messages.MESSAGE_INVALID_DATE_FORMAT);
        }

        if (!from.isBefore(to)) {
            throw new ParseException(Messages.MESSAGE_TO_BEFORE_FROM_INVALID);
        }
    }

    // =======================================================================
    // Wedding Parsing Methods
    // =======================================================================

    /**
     * Parses {@code String} wedding into {@code Wedding} object.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code String} wedding is an invalid wedding name.
     */
    public static Wedding parseWedding(String wedding) throws ParseException {
        return new Wedding(parseWeddingName(wedding));
    }

    /**
     * Parses {@code String} wedding into {@code Wedding} object.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code String} wedding is an invalid wedding name.
     */
    public static WeddingName parseWeddingName(String weddingName) throws ParseException {
        requireNonNull(weddingName);
        String trimmedWedding = weddingName.trim();
        if (!Wedding.isValidWeddingName(trimmedWedding)) {
            throw new ParseException(WeddingName.MESSAGE_CONSTRAINTS);
        }
        return new WeddingName(trimmedWedding);
    }


    /**
     * Parses {@code Collection<String> weddings} into a {@code Set<Wedding>}.
     */
    public static Set<Wedding> parseWeddings(Collection<String> weddings) throws ParseException {
        requireNonNull(weddings);
        final Set<Wedding> weddingSet = new HashSet<>();
        for (String weddingName : weddings) {
            weddingSet.add(parseWedding(weddingName));
        }
        return weddingSet;
    }
}
