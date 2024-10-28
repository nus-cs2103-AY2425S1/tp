package hallpointer.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import hallpointer.address.commons.exceptions.IllegalValueException;
import hallpointer.address.model.point.Point;

/**
 * Jackson-friendly version of {@link Point}.
 */
public class JsonAdaptedPoint {

    private final String points;

    /**
     * Constructs a {@code JsonAdaptedPoint} with the given points.
     *
     * @param points The integer value representing the points.
     */
    @JsonCreator
    public JsonAdaptedPoint(@JsonProperty("points") String points) {
        this.points = points;
    }

    /**
     * Converts a given {@code Point} into this class for Jackson use.
     *
     * @param source The Point object to be converted into a {@code JsonAdaptedPoint}.
     */
    public JsonAdaptedPoint(Point source) {
        this.points = Integer.toString(source.getValue());
    }

    /**
     * Converts this Jackson-friendly adapted point object into the model's {@code Point} object.
     *
     * @return The model-type Point corresponding to this JSON-adapted point.
     * @throws IllegalValueException If any data constraints are violated in the adapted point.
     */
    public Point toModelType() throws IllegalValueException {
        if (!Point.isValidPoints(points)) {
            throw new IllegalValueException(Point.MESSAGE_CONSTRAINTS);
        }
        return new Point(points);
    }
}
