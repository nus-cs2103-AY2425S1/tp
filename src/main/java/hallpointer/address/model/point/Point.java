package hallpointer.address.model.point;

import static hallpointer.address.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents points earned by a member.
 * Guarantees: immutable; points are valid as declared in {@link #isValidPoints(int)}
 */
public class Point {

    public static final String MESSAGE_CONSTRAINTS = "Points must be a non-negative integer.";
    public final int points;

    /**
     * Constructs a {@code Point}.
     *
     * @param points A valid points value.
     */
    public Point(int points) {
        requireNonNull(points);
        checkArgument(isValidPoints(points), MESSAGE_CONSTRAINTS);
        this.points = points;
    }

    /**
     * Returns true if the given points value is valid.
     */
    public static boolean isValidPoints(int test) {
        return test >= 0; // Points must be non-negative
    }

    /**
     * Takes in a Point object and returns a new Point object with the points added.
     */
    public Point add(Point other) {
        return new Point(points + other.points);
    }

    /**
     * Takes in a Point object and returns a new Point object with the points subtracted.
     */
    public Point subtract(Point other) {
        return new Point(points - other.points);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Point)) {
            return false;
        }

        Point otherPoint = (Point) other;
        return points == otherPoint.points;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(points);
    }

    public int getValue() {
        return points;
    }

    /**
     * Returns the points as a formatted string.
     */
    @Override
    public String toString() {
        return String.valueOf(points) + " points";
    }
}
