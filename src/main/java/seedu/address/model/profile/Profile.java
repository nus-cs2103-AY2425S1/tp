package seedu.address.model.profile;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.model.profile.exceptions.IllegalProfileNameException;
import seedu.address.model.profile.exceptions.IllegalProfilePathException;

/**
 * Represents a user profile with a name.
 */
public class Profile {
    public static final String MESSAGE_CONSTRAINTS = "Profile names must be between 1 and 30 characters and can "
            + "only include letters (a-z, A-Z), numbers (0-9), hyphens (-), and underscores (_).";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9-_]+$";
    private static final String DEFAULT_PROFILE = "addressbook";
    private static final String PROFILE_PARENT_DIR = "data";
    private static final String PROFILE_EXTENSION = ".json";
    private final String profileName;

    /**
     * Constructs a Profile instance with the default profile name.
     */
    public Profile() {
        this.profileName = DEFAULT_PROFILE;
    }

    /**
     * Constructs a Profile with the specified name after validation.
     *
     * @param profileName A valid profile name.
     * @throws IllegalArgumentException if profile name is invalid.
     */
    public Profile(String profileName) {
        requireNonNull(profileName);
        checkArgument(isValidProfileName(profileName), MESSAGE_CONSTRAINTS);
        this.profileName = profileName;
    }

    /**
     * Checks if the given profile name is valid based on the following criteria:
     * <ul>
     *     <li>The profile name must be alphanumeric (a-z, A-Z, 0-9)
     *     and may include hyphens (-) or underscores (_).</li>
     *     <li>The profile name must not exceed 30 characters in length.</li>
     * </ul>
     *
     * @param profileName the name of the new profile to validate.
     * @return true if the profile name is valid according to the criteria, false otherwise
     */
    public static boolean isValidProfileName(String profileName) {
        Pattern pattern = Pattern.compile(VALIDATION_REGEX);
        return profileName.length() <= 30 && pattern.matcher(profileName).matches();
    }

    /**
     * Validates if the given path represents a valid profile path.
     * <p>
     * A valid profile path must:
     * <ul>
     *   <li>Follow the structure {@code data/{profileName}.json}.</li>
     *   <li>Contain a valid profile name as specified in {@link Profile} class</li>
     * </ul>
     *
     * @param filePath the file path to validate
     * @return {@code true} if the path is valid, {@code false} otherwise
     */
    public static boolean isValidProfileFromPath(Path filePath) {
        if (!isSimpleProfileFilePath(filePath)) {
            return false;
        }
        String profileName = extractProfileName(filePath);
        return isValidProfileName(profileName);
    }

    /**
     * Extracts the profile name from the given file path as a single-element stream if valid, and
     * returns an empty stream if the path or name is invalid.
     * <p>
     * Intended for use in stream pipelines where invalid paths can be safely ignored.
     * </p>
     *
     * @param filePath The file path to extract the profile name from.
     * @return A stream containing the profile name if valid, or an empty stream if invalid.
     */
    public static Stream<String> extractNameFromPathOrIgnore(Path filePath) {
        if (isValidProfileFromPath(filePath)) {
            return Stream.of(extractProfileName(filePath));
        }
        return Stream.empty();
    }

    /**
     * Extracts a profile name from the specified path, throwing exceptions if
     * the path format or name validity requirements are violated.
     *
     * @param filePath The path from which to extract the profile name.
     * @return The extracted profile name.
     * @throws IllegalProfilePathException if the path format is invalid.
     * @throws IllegalProfileNameException if the profile name is invalid.
     */
    public static String extractProfileNameFromPathOrThrow(Path filePath) {
        if (!isSimpleProfileFilePath(filePath)) {
            throw new IllegalProfilePathException();
        }
        String profileName = extractProfileName(filePath);
        if (!isValidProfileName(profileName)) {
            throw new IllegalProfileNameException();
        }
        return profileName;
    }

    /**
     * Checks if the provided file path follows a simple profile structure, i.e., {@code data/{profileName}.json}.
     * <p>
     * Example of a valid path: {@code data/addressbook.json}
     * Example of an invalid path: {@code data/dir/addressbook.json}
     * </p>
     *
     * @param filePath The path to validate.
     * @return true if the path follows the required format, false otherwise.
     */
    private static boolean isSimpleProfileFilePath(Path filePath) {
        int nameCount = filePath.getNameCount();
        if (nameCount < 2) {
            return false;
        }

        // Subdirectories for 'data' is not allowed
        long dataDirectoryCount = 0;
        for (int i = 0; i < nameCount; i++) {
            if (PROFILE_PARENT_DIR.equals(filePath.getName(i).toString())) {
                dataDirectoryCount++;
            }
        }
        if (dataDirectoryCount > 1) {
            return false;
        }

        String fileName = filePath.getFileName().toString();
        String parentDir = filePath.getName(nameCount - 2).toString();
        return PROFILE_PARENT_DIR.equals(parentDir) && fileName.endsWith(PROFILE_EXTENSION);
    }


    /**
     * Helper method to extract the profile name from a file path by removing the
     * ".json" extension. Assumes the path format is valid.
     *
     * @param filePath The path from which to extract the profile name.
     * @return The profile name without the extension.
     */
    private static String extractProfileName(Path filePath) {
        String fileName = filePath.getFileName().toString();
        assert isSimpleProfileFilePath(filePath) : "Attempted to extract profile name from an unsupported file path";
        return fileName.substring(0, fileName.length() - PROFILE_EXTENSION.length());
    }

    /**
     * Returns the file path for this profile, formatted as "data/{profileName}.json".
     *
     * @return The file path of the profile.
     */
    public Path toPath() {
        return profileStringToPath(profileName);
    }

    /**
     * Converts a profile name into a file path in the format "data/{profileName}.json".
     *
     * @param profileName The name to convert to a file path.
     * @return The corresponding profile path.
     */
    public static Path profileStringToPath(String profileName) {
        return Paths.get(PROFILE_PARENT_DIR, profileName + PROFILE_EXTENSION);
    }

    /**
     * Returns the singleton instance of the {@code EmptyProfile} class.
     *
     * @return An instance of {@code EmptyProfile}.
     */
    public static EmptyProfile getEmptyProfile() {
        return EmptyProfile.getInstance();
    }

    /**
     * A singleton class that represents an empty profile.
     */
    public static class EmptyProfile extends Profile {
        private static final EmptyProfile instance = new EmptyProfile();

        /**
         * Constructs an {@code EmptyProfile} with an arbitrary name.
         */
        private EmptyProfile() {
            super("EMPTY");
        }

        /**
         * Returns the singleton instance of the {@code EmptyProfile} class.
         *
         * @return The single instance of {@code EmptyProfile}.
         */
        private static EmptyProfile getInstance() {
            return instance;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Profile otherProfile)) {
            return false;
        }

        return profileName.equalsIgnoreCase(otherProfile.profileName);
    }

    @Override
    public int hashCode() {
        return profileName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return profileName;
    }
}

