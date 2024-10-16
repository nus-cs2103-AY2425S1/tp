package keycontacts.model.pianopiece;

import static java.util.Objects.requireNonNull;
import static keycontacts.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a Piano Piece in the student directory.
 * Guarantees: immutable; name is valid as declared in {@link #isValidPianoPieceName(String)}
 */
public class PianoPiece {

    public static final String MESSAGE_CONSTRAINTS = "Piano piece name should not be empty";
    /*
     * The first character of the piano piece must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String pianoPieceName;

    /**
     * Constructs a {@code PianoPiece}.
     *
     * @param pianoPieceName A valid piano piece name.
     */
    public PianoPiece(String pianoPieceName) {
        requireNonNull(pianoPieceName);
        checkArgument(isValidPianoPieceName(pianoPieceName), MESSAGE_CONSTRAINTS);
        this.pianoPieceName = pianoPieceName;
    }

    /**
     * Returns true if a given string is a valid piano piece name.
     */
    public static boolean isValidPianoPieceName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PianoPiece)) {
            return false;
        }

        PianoPiece otherPiece = (PianoPiece) other;
        return pianoPieceName.equals(otherPiece.pianoPieceName);
    }

    @Override
    public int hashCode() {
        return pianoPieceName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return pianoPieceName;
    }

    /**
     * Returns a piano piece set containing the list of strings given.
     */
    public static Set<PianoPiece> getPianoPieceSet(String... strings) {
        return Arrays.stream(strings)
                .map(PianoPiece::new)
                .collect(Collectors.toSet());
    }
}
