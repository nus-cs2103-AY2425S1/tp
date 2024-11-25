package seedu.hireme.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

import seedu.hireme.commons.util.ToStringBuilder;

/**
 * A Serializable class that contains the GUI settings.
 * This class stores information such as window width, height, and position.
 * Guarantees: immutable.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;

    private final double windowWidth;
    private final double windowHeight;
    private final Point windowCoordinates;

    /**
     * Constructs a {@code GuiSettings} with the default height, width, and position.
     * The window position will be unspecified (i.e., null).
     */
    public GuiSettings() {
        windowWidth = DEFAULT_WIDTH;
        windowHeight = DEFAULT_HEIGHT;
        windowCoordinates = null; // null represents no coordinates
    }

    /**
     * Constructs a {@code GuiSettings} with the specified window dimensions and position.
     *
     * @param windowWidth The width of the window.
     * @param windowHeight The height of the window.
     * @param xPosition The X-coordinate of the window's position.
     * @param yPosition The Y-coordinate of the window's position.
     */
    public GuiSettings(double windowWidth, double windowHeight, int xPosition, int yPosition) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        windowCoordinates = new Point(xPosition, yPosition);
    }

    /**
     * Returns the width of the window.
     *
     * @return The width of the window.
     */
    public double getWindowWidth() {
        return windowWidth;
    }

    /**
     * Returns the height of the window.
     *
     * @return The height of the window.
     */
    public double getWindowHeight() {
        return windowHeight;
    }

    /**
     * Returns the coordinates of the window's position.
     * If the window's position is unspecified, this method will return {@code null}.
     *
     * @return The coordinates of the window, or {@code null} if not set.
     */
    public Point getWindowCoordinates() {
        return windowCoordinates != null ? new Point(windowCoordinates) : null;
    }

    /**
     * Compares this object to the specified object for equality.
     * Returns true if the specified object is also a {@code GuiSettings} instance
     * with the same window dimensions and position.
     *
     * @param other The object to compare to.
     * @return {@code true} if the objects are equal, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GuiSettings)) {
            return false;
        }

        GuiSettings otherGuiSettings = (GuiSettings) other;
        return windowWidth == otherGuiSettings.windowWidth
                && windowHeight == otherGuiSettings.windowHeight
                && Objects.equals(windowCoordinates, otherGuiSettings.windowCoordinates);
    }

    /**
     * Returns the hash code value for this object, based on the window dimensions and position.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates);
    }

    /**
     * Returns the string representation of the {@code GuiSettings} object.
     * The string will include the window dimensions and position.
     *
     * @return A string describing the {@code GuiSettings}.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("windowWidth", windowWidth)
                .add("windowHeight", windowHeight)
                .add("windowCoordinates", windowCoordinates)
                .toString();
    }
}
