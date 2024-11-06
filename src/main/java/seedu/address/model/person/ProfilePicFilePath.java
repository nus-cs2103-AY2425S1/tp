package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a Person's profile picture file path in the address book.
 * Guarantees: immutable; is valid as declared in the {@link #ProfilePicFilePath(Path)} constructor.
 */
public class ProfilePicFilePath {

    public final Path filePath;

    /**
     * Constructs a {@code ProfilePicFilePath}.
     *
     * @param filePath A valid file path for the profile picture.
     */
    public ProfilePicFilePath(Path filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    /**
     * Returns a {@code ProfilePicFilePath} with the default profile picture.
     *
     * @return A ProfilePicFilePath pointing to /images/profilepicture.png.
     */
    public static ProfilePicFilePath getDefaultProfilePic() {
        return new ProfilePicFilePath(Paths.get("images", "profilepicture.png"));
    }

    @Override
    public String toString() {
        return filePath.toString().replace("\\", "/");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProfilePicFilePath)) {
            return false;
        }

        ProfilePicFilePath otherProfilePicFilePath = (ProfilePicFilePath) other;
        return filePath.equals(otherProfilePicFilePath.filePath);
    }

    @Override
    public int hashCode() {
        return filePath.hashCode();
    }

}
