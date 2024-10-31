package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DietaryPreference;
import seedu.address.model.person.Email;
import seedu.address.model.person.Information;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.Ingredients;
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
        if (address == null) {
            return new Address();
        }
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }
    /**
     * Parses a {@code String ingredientsSuppliedString} into an {@code Ingredients} object.
     * Splits the string by commas and trims whitespaces.
     *
     * @throws ParseException if the given {@code ingredientsSuppliedString} is invalid.
     */
    public static Ingredients parseIngredients(String ingredientsSuppliedString) throws ParseException {
        requireNonNull(ingredientsSuppliedString);
        String trimmedIngredients = ingredientsSuppliedString.trim();

        if (trimmedIngredients.isEmpty()) {
            throw new ParseException("Ingredients supplied cannot be empty.");
        }

        List<String> ingredientNames = Arrays.asList(trimmedIngredients.split("\\s*,\\s*"));
        List<Ingredient> ingredientList = new ArrayList<>();

        int ingredientId = 1;  // For now, we use hardcoded IDs.
        for (String ingredientName : ingredientNames) {
            Ingredient ingredient = new Ingredient(ingredientId++, ingredientName, 0.0); // Assuming cost is 0 for MVP
            ingredientList.add(ingredient);
        }

        return new Ingredients(ingredientList);
    }

    /**
     * Parses a {@code String dietaryPreference} into a {@code DietaryPreference}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dietaryPreference} is invalid.
     */
    public static DietaryPreference parsePreference(String dietaryPreference) throws ParseException {
        if (dietaryPreference == null) {
            return new DietaryPreference();
        }
        String trimmedPreference = dietaryPreference.trim();
        if (!DietaryPreference.isValidDietaryPreference(trimmedPreference)) {
            throw new ParseException(DietaryPreference.MESSAGE_CONSTRAINTS);
        }
        return new DietaryPreference(trimmedPreference);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        if (email == null) {
            return new Email();
        }
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }
    /**
     * Parses a {@code String information} into an {@code Information}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code information} is invalid.
     */
    public static Information parseInformation(String information) throws ParseException {
        if (information == null) {
            return new Information();
        }
        String trimmedInformation = information.trim();
        if (!Information.isValidInformation(trimmedInformation)) {
            throw new ParseException(Information.MESSAGE_CONSTRAINTS);
        }
        return new Information(trimmedInformation);
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
}
