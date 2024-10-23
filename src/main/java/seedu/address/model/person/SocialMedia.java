package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's social media handle.
 */
public class SocialMedia {

    public static final String MESSAGE_CONSTRAINTS = "Social media handles should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    private String handle;
    private Platform platform;

    /**
     * Definite platforms of social media that can be used.
     */
    public enum Platform {
        INSTAGRAM, FACEBOOK, CAROUSELL, UNNAMED
    }

    /**
     * Constructs a {@code SocialMedia}.
     *
     * @param handle A valid handle name.
     */
    public SocialMedia(String handle, Platform platform) {
        requireNonNull(handle);
        if (!platform.equals(Platform.UNNAMED)) {
            checkArgument(isValidHandleName(handle), MESSAGE_CONSTRAINTS);
        }
        this.handle = handle;
        this.platform = platform;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidHandleName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public Platform getPlatform() {
        return platform;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SocialMedia)) {
            return false;
        }

        SocialMedia otherSocialMedia = (SocialMedia) other;
        return handle.equals(otherSocialMedia.handle) && platform.equals(otherSocialMedia.platform);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {

        switch(platform) {
        case INSTAGRAM:
            return "[ig-" + handle + ']';
        case FACEBOOK:
            return "[fb-" + handle + ']';
        case CAROUSELL:
            return "[cs-" + handle + ']';
        default:
            return handle;
        }

    }
}
