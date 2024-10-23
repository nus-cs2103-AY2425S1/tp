package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.FunctionWithException;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INCOMPLETE_INDEX = "Index is incomplete.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
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
     * Parses {@code oneBasedIndexes} into a list of {@code Index} and returns it. Leading and trailing whitespaces will
     * be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static ArrayList<Index> parseMultipleIndexSeperatedByComma(String oneBasedIndexes) throws ParseException {
        String trimmedIndexes = oneBasedIndexes.trim();
        if (trimmedIndexes.endsWith(",")) {
            throw new ParseException(MESSAGE_INCOMPLETE_INDEX);
        }
        String[] indexes = trimmedIndexes.split(",");
        LinkedHashSet<Index> indexSet = new LinkedHashSet<>();
        for (String index : indexes) {
            if (!StringUtil.isNonZeroUnsignedInteger(index.trim())) {
                throw new ParseException(MESSAGE_INVALID_INDEX);
            }
            indexSet.add(Index.fromOneBased(Integer.parseInt(index.trim())));
        }
        return new ArrayList<>(indexSet);
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
     * Parses a {@code String remark} into a {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (!Remark.isValidRemark(trimmedRemark)) {
            throw new ParseException(Remark.MESSAGE_CONSTRAINTS);
        }
        return new Remark(trimmedRemark);
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
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**

     * Parses an {@code Optional} String value using the provided parser {@code FunctionWithException},
     * returning a default value if the {@code Optional} is empty.
     */
    public static <T> T parseOptionalValue(Optional<String> value, FunctionWithException<String, T,
            ParseException> parser, T defaultValue) throws ParseException {
        if (value.isPresent()) {
            return parser.apply(value.get());
        } else {
            return defaultValue;
        }
    }

     * Checks if a command word matches any known invalid variants and throws a ParseException if found.
     * @param commandWord The command word to validate
     * @throws ParseException If the command word matches any invalid variant, with a message suggesting
     *                       the correct command
     */
    public static void parseInvalidVariants(String commandWord) throws ParseException {
        if (AddCommand.INVALID_VARIANTS.contains(commandWord)) {
            throw new ParseException(Command.generateInvalidVariantMessage(commandWord,
                    AddCommand.SHORT_COMMAND_WORD, AddCommand.LONG_COMMAND_WORD));
        } else if (DeleteCommand.INVALID_VARIANTS.contains(commandWord)) {
            throw new ParseException(Command.generateInvalidVariantMessage(commandWord,
                    DeleteCommand.SHORT_COMMAND_WORD, DeleteCommand.LONG_COMMAND_WORD));
        } else if (EditCommand.INVALID_VARIANTS.contains(commandWord)) {
            throw new ParseException(Command.generateInvalidVariantMessage(commandWord,
                    EditCommand.SHORT_COMMAND_WORD, EditCommand.LONG_COMMAND_WORD));
        } else if (ExitCommand.INVALID_VARIANTS.contains(commandWord)) {
            throw new ParseException(Command.generateInvalidVariantMessage(commandWord,
                    ExitCommand.COMMAND_WORD));
        } else if (FindCommand.INVALID_VARIANTS.contains(commandWord)) {
            throw new ParseException(Command.generateInvalidVariantMessage(commandWord,
                    FindCommand.COMMAND_WORD));
        } else if (HelpCommand.INVALID_VARIANTS.contains(commandWord)) {
            throw new ParseException(Command.generateInvalidVariantMessage(commandWord,
                    HelpCommand.COMMAND_WORD));
        } else if (ListCommand.INVALID_VARIANTS.contains(commandWord)) {
            throw new ParseException(Command.generateInvalidVariantMessage(commandWord,
                    ListCommand.SHORT_COMMAND_WORD, ListCommand.LONG_COMMAND_WORD));
        } else {
            return;
        }
    }

}
