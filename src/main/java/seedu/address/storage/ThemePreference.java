package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.JsonUtil;

/**
 * Stores and loads the theme preference
 */
public class ThemePreference {

    private static final Path THEME_PREF_FILE_PATH = Paths.get("data", "themePreference.json");
    private static final Logger logger = Logger.getLogger(ThemePreference.class.getName());
    private String theme;

    /**
     * Load theme preference from file when this object is created
     * Default theme will be light
     */
    public ThemePreference() {
        this.theme = loadThemePreference().orElse("light");
    }

    /**
     * Loads the theme preference from the JSON file.
     *
     * @return An Optional containing the theme string, or empty if not found.
     */
    private Optional<String> loadThemePreference() {
        try {
            if (!Files.exists(THEME_PREF_FILE_PATH)) {
                return Optional.empty();
            }
            return JsonUtil.readJsonFile(THEME_PREF_FILE_PATH, String.class);
        } catch (DataLoadingException e) {
            logger.log(Level.WARNING, "Error loading theme preference, defaulting to LIGHT", e);
            return Optional.empty();
        }
    }

    /**
     * Saves the current theme preference to the JSON file.
     *
     * @param theme The theme to save ('light' or 'dark').
     */
    public void saveThemePreference(String theme) {
        this.theme = theme;
        try {
            JsonUtil.saveJsonFile(theme, THEME_PREF_FILE_PATH);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Unable to save theme preference", e);
        }
    }

    /**
     * Returns the current theme ('light' or 'dark').
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Sets the current theme and saves it to the file.
     *
     * @param theme The theme to set and save ('light' or 'dark').
     */
    public void setTheme(String theme) {
        this.theme = theme;
        saveThemePreference(theme);
    }
}
