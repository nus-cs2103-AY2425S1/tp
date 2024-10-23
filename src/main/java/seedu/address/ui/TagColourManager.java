package seedu.address.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;

/**
 * A manager containing the list of colours for tags.
 */
public class TagColourManager {
    private static final List<Color> COLOURS = List.of(
            Color.web("#FF6F61"), // Red
            Color.web("#6B5B95"), // Purple
            Color.web("#88B04B"), // Green
            Color.web("#CE90D4"), // Pink
            Color.web("#2596be"), // Blue
            Color.web("#B5B526"), // Yellow
            Color.web("#D65076"), // Magenta
            Color.web("#45B8AC"), // Teal
            Color.web("#C17D27"), // Orange
            Color.web("#20874D"), // Eucalyptus
            Color.web("#208782"), // Elm
            Color.web("#2D6096"), // Dark Blue
            Color.web("#B35972"), // Dark Pink
            Color.web("#B1393A"), // Dark Red
            Color.web("#716D68"), // Flint
            Color.web("#E8A858")// Light Orange
    );

    private static final Map<String, Color> tagColourMap = new HashMap<>();

    private static final Random random = new Random();

    /**
     * Gets the colour associated with the given tag name. If the name does not already have a colour, a new one is
     * randomly assigned from the list.
     *
     * @param tagName the name of the tag
     * @return the colour associated with the tag
     */
    public static Color getColourForTag(String tagName) {
        return tagColourMap.computeIfAbsent(tagName, key -> {
            return COLOURS.get(random.nextInt(COLOURS.size()));
        });
    }
}
