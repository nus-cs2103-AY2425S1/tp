package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.edit.AddModuleRoleOperation.AddModuleRoleDescriptor;
import static seedu.address.logic.commands.edit.DeleteModuleRoleOperation.DeleteModuleRoleDescriptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.edit.AddModuleRoleOperation;
import seedu.address.logic.commands.edit.DeleteModuleRoleOperation;
import seedu.address.logic.commands.edit.EditModuleRoleOperation;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Description;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.ModuleRolePair;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RoleType;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index '%1$s' is not a non-zero unsigned integer!";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(String.format(MESSAGE_INVALID_INDEX, oneBasedIndex));
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
        if (!Phone.isValidPhoneField(trimmedPhone)) {
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
        String trimmedLowerCaseEmail = email.trim().toLowerCase();
        if (!Email.isValidEmail(trimmedLowerCaseEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedLowerCaseEmail);
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
     * Parses {@code String keyword} into a {@code RoleType}.
     */
    public static RoleType parseRoleType(String keyword) throws ParseException {
        requireNonNull(keyword);
        String trimmedLowerCaseKeyword = keyword.trim().toLowerCase();
        if (!RoleType.isValidRoleTypeKeyword(trimmedLowerCaseKeyword)) {
            throw new ParseException(ModuleRoleMap.MESSAGE_CONSTRAINTS);
        }
        switch (trimmedLowerCaseKeyword) {
        case "": // Fall through
        case "student":
            return RoleType.STUDENT;
        case "tutor": // Fall through
        case "ta":
            return RoleType.TUTOR;
        case "professor": // Fall through
        case "prof":
            return RoleType.PROFESSOR;
        default:
            throw new ParseException(ModuleRoleMap.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if the given {@code moduleRolePair} contains only the module code.
     */
    public static boolean isOnlyModuleCodePresent(String moduleRolePair) {
        return moduleRolePair.indexOf('-') == -1;
    }

    /**
     * Parses a {@code String moduleCode} into a {@code ModuleCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code moduleCode} is invalid.
     */
    public static ModuleCode parseModuleCode(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedModuleCode = moduleCode.trim();
        if (!ModuleCode.isValidModuleCode(trimmedModuleCode)) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        return new ModuleCode(trimmedModuleCode);
    }

    /**
     * Parses a {@code String moduleRolePair} into a {@code ModuleRolePair}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code moduleRolePair} is invalid.
     */
    public static ModuleRolePair parseModuleRolePair(String moduleRolePair)
            throws ParseException {
        requireNonNull(moduleRolePair);

        String trimmed = moduleRolePair.trim();

        // Assume that if the - separator is missing, only module code is provided.
        // e.g. r/CS1101S => CS1101S student.
        boolean onlyModuleCodePresent = trimmed.indexOf('-') == -1;

        ModuleCode moduleCode;
        RoleType roleType;

        if (onlyModuleCodePresent) {
            if (!ModuleCode.isValidModuleCode(trimmed)) {
                throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
            }

            moduleCode = new ModuleCode(trimmed);
            roleType = RoleType.STUDENT;
        } else {
            // Verify able to split by -
            String[] parsed = trimmed.split("-");
            if (parsed.length != 2) {
                throw new ParseException(ModuleRoleMap.MESSAGE_CONSTRAINTS);
            }

            if (!ModuleCode.isValidModuleCode(parsed[0])) {
                throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
            }

            moduleCode = new ModuleCode(parsed[0]);
            roleType = parseRoleType(parsed[1]);
        }

        return new ModuleRolePair(moduleCode, roleType);
    }

    /**
     * Parses {@code Collection<String> moduleRolePairs} into a {@code ModuleRoleMap}.
     * This method will check for duplicate module codes.
     */
    public static ModuleRoleMap parseModuleRoleMap(Collection<String> moduleRolePairs) throws ParseException {
        requireNonNull(moduleRolePairs);

        final LinkedHashMap<ModuleCode, RoleType> hashMap = new LinkedHashMap<>();
        final List<ModuleCode> moduleCodes = new ArrayList<>();

        for (String moduleRolePair : moduleRolePairs) {
            ModuleRolePair parsedPair = parseModuleRolePair(moduleRolePair);
            hashMap.put(parsedPair.moduleCode, parsedPair.roleType);
            moduleCodes.add(parsedPair.moduleCode);
        }

        Set<ModuleCode> uniqueModuleCodes = new HashSet<>(moduleCodes);
        if (uniqueModuleCodes.size() != moduleCodes.size()) {
            throw new ParseException(ModuleRoleMap.MESSAGE_SINGLE_ROLE_PER_MODULE_CONSTRAINTS);
        }

        return new ModuleRoleMap(hashMap);
    }

    /**
     * Parses {@code Collection<String> moduleRolePairs} into a {@code List<ModuleRolePair>}.
     * This method will not check for duplicate module codes.
     */
    public static List<ModuleRolePair> parseModuleRolePairs(Collection<String> moduleRolePairs) throws ParseException {
        requireNonNull(moduleRolePairs);

        final List<ModuleRolePair> moduleRolePairList = new ArrayList<>();
        for (String moduleRolePair : moduleRolePairs) {
            moduleRolePairList.add(parseModuleRolePair(moduleRolePair));
        }

        return moduleRolePairList;
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean areAllPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if any of the specified {@code Prefix}es are present in the {@code ArgumentMultimap}.
     */
    public static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses a list of {@code String moduleRolePairs} into an {@code AddModuleRoleDescriptor}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param moduleRolePairs the list of strings representing the module role pairs.
     * @return the corresponding {@code AddModuleRoleDescriptor}.
     * @throws ParseException if the given {@code moduleRolePairs} is invalid.
     */
    public static AddModuleRoleDescriptor parseAddModuleRoleDescriptor(List<String> moduleRolePairs)
            throws ParseException {
        return new AddModuleRoleDescriptor(parseModuleRoleMap(moduleRolePairs).getData());
    }

    /**
     * Parses a list of {@code String moduleRolePairs} into a {@code DeleteModuleRoleDescriptor}.
     * Leading and trailing whitespaces will be trimmed.
     * For each {@code String moduleRolePair}, if the role is not specified, the returned descriptor
     * will specify that any role type associated with the module should be deleted later.
     *
     * @param moduleRolePairs the list of strings representing the module role pairs.
     * @return the corresponding {@code DeleteModuleRoleDescriptor}.
     * @throws ParseException if the given {@code moduleRolePairs} is invalid.
     */
    public static DeleteModuleRoleDescriptor parseDeleteModuleRoleDescriptor(
            List<String> moduleRolePairs) throws ParseException {
        List<ModuleRolePair> toDeletes = new ArrayList<>();
        List<ModuleCode> toDeleteAnyRoles = new ArrayList<>();

        List<ModuleCode> moduleCodes = new ArrayList<>();

        for (String moduleRolePair : moduleRolePairs) {
            if (isOnlyModuleCodePresent(moduleRolePair)) {
                ModuleCode moduleCode = parseModuleCode(moduleRolePair);
                toDeleteAnyRoles.add(moduleCode);
                moduleCodes.add(moduleCode);
            } else {
                ModuleRolePair parsedPair = parseModuleRolePair(moduleRolePair);
                toDeletes.add(parsedPair);
                moduleCodes.add(parsedPair.moduleCode);
            }
        }

        Set<ModuleCode> uniqueModuleCodes = new HashSet<>(moduleCodes);
        if (uniqueModuleCodes.size() != moduleCodes.size()) {
            throw new ParseException(ModuleRoleMap.MESSAGE_SINGLE_ROLE_PER_MODULE_CONSTRAINTS);
        }

        return new DeleteModuleRoleDescriptor(toDeletes, toDeleteAnyRoles);
    }

    /**
     * Parses a {@code String moduleRoleOperations} into an {@code EditModuleRoleOperation}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param moduleRoleOperations the string representing the module role operations.
     *                             The first character must be a '+'.
     * @return the corresponding {@code EditModuleRoleOperation}.
     * @throws ParseException if the given {@code moduleRoleOperations} is invalid.
     */
    public static EditModuleRoleOperation parseEditModuleRoleOperation(String moduleRoleOperations)
            throws ParseException {
        if (!EditModuleRoleOperation.isValidModuleRoleOperation(moduleRoleOperations)) {
            throw new ParseException(EditModuleRoleOperation.MESSAGE_VALID_OPERATION_CONSTRAINT);
        }
        moduleRoleOperations = moduleRoleOperations.trim();
        switch (moduleRoleOperations.charAt(0)) {
        case '+':
            List<String> moduleRolesToAdd =
                    Arrays.asList(moduleRoleOperations.substring(1).trim().split("\\s+"));
            return new AddModuleRoleOperation(parseAddModuleRoleDescriptor(moduleRolesToAdd));
        case '-':
            List<String> moduleRolesToRemove =
                    Arrays.asList(moduleRoleOperations.substring(1).trim().split("\\s+"));
            return new DeleteModuleRoleOperation(parseDeleteModuleRoleDescriptor(moduleRolesToRemove));
        default:
            throw new RuntimeException();
        }
    }
}
