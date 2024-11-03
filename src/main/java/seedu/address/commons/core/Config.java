package seedu.address.commons.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Level;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Config values used by the app.
 */
public class Config {

    public static final Path DEFAULT_CONFIG_FILE = Paths.get("config.json");

    // Config values customizable through config file
    private Level logLevel = Level.INFO;
    private Path userPrefsFilePath = Paths.get("preferences.json");
    private Path ingredientCatalogueFilePath = Paths.get("data/ingredientCatalogue.json");
    private Path pastryCatalogueFilePath = Paths.get("data/pastryCatalogue.json");

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public Path getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    public void setUserPrefsFilePath(Path userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    public Path getIngredientCatalogueFilePath() {
        return ingredientCatalogueFilePath;
    }

    public void setIngredientCatalogueFilePath(Path ingredientCatalogueFilePath) {
        this.ingredientCatalogueFilePath = ingredientCatalogueFilePath;
    }

    public Path getPastryCatalogueFilePath() {
        return pastryCatalogueFilePath;
    }

    public void setPastryCatalogueFilePath(Path pastryCatalogueFilePath) {
        this.pastryCatalogueFilePath = pastryCatalogueFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Config)) {
            return false;
        }

        Config otherConfig = (Config) other;
        return Objects.equals(logLevel, otherConfig.logLevel)
                && Objects.equals(userPrefsFilePath, otherConfig.userPrefsFilePath)
                && Objects.equals(ingredientCatalogueFilePath, otherConfig.ingredientCatalogueFilePath)
                && Objects.equals(pastryCatalogueFilePath, otherConfig.pastryCatalogueFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logLevel, userPrefsFilePath, ingredientCatalogueFilePath, pastryCatalogueFilePath);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("logLevel", logLevel)
                .add("userPrefsFilePath", userPrefsFilePath)
                .add("ingredientCatalogueFilePath", ingredientCatalogueFilePath)
                .add("pastryCatalogueFilePath", pastryCatalogueFilePath)
                .toString();
    }
}
