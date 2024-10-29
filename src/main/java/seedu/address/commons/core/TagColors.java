package seedu.address.commons.core;

import java.io.Serializable;

/**
 * Immutable wrapper that tracks the available colors Tags can take in the GUI
 */
public class TagColors implements Serializable {
    private static final String[] DEFAULT_COLORS = {
        "#e2e1df", "#bbbab9", "#dcc0b3", "#fddfcc",
        "#fbeecc", "#cce7e1", "#cce4f9", "#e1d3f8",
        "#f8cce6", "#ffccd1"
    };
    private final String[] colors;

    public TagColors() {
        this.colors = DEFAULT_COLORS;
    }

    public TagColors(String[] colors) {
        this.colors = colors;
    }

    public String[] getTagColors() {
        return this.colors;
    }

    public int getColorsLength() {
        return this.colors.length;
    }
}
