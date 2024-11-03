package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.PastryCatalogue;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.*;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 2, 2, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;


    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        IngredientCatalogueStorage ingredientCatalogueStorage = new JsonIngredientCatalogueStorage(config.getIngredientCatalogueFilePath());
        PastryCatalogueStorage pastryCatalogueStorage = new JsonPastryCatalogueStorage(config.getPastryCatalogueFilePath());
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());

        storage = new StorageManager(addressBookStorage, userPrefsStorage, ingredientCatalogueStorage, pastryCatalogueStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book, userPrefs, ingredientCatalogue, and pastryCatalogue.
     * Sample data is used if any data is not found, or an empty address book if errors occur.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getAddressBookFilePath());

        // Initialize AddressBook
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
            if (!addressBookOptional.isPresent()) {
                logger.info("Address book file not found. Starting with sample data.");
            }
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getAddressBookFilePath() + " could not be loaded. Starting with an empty AddressBook.");
            initialData = new AddressBook();
        }

        // Initialize IngredientCatalogue and PastryCatalogue
        IngredientCatalogue ingredientCatalogue;
        PastryCatalogue pastryCatalogue;

        try {
            ingredientCatalogue = storage.readIngredientCatalogue().orElseGet(() -> {
                logger.info("Ingredient catalogue file not found. Initializing with sample data.");
                return SampleDataUtil.getSampleIngredientCatalogue();
            });
        } catch (DataLoadingException e) {
            logger.warning("Could not load ingredient catalogue data. Using default sample catalogue.");
            ingredientCatalogue = SampleDataUtil.getSampleIngredientCatalogue();
        }

        try {
            pastryCatalogue = storage.readPastryCatalogue().orElseGet(() -> {
                logger.info("Pastry catalogue file not found. Initializing with sample data.");
                return SampleDataUtil.getSamplePastryCatalogue();
            });
        } catch (DataLoadingException e) {
            logger.warning("Could not load pastry catalogue data. Using default sample catalogue.");
            pastryCatalogue = SampleDataUtil.getSamplePastryCatalogue();
        }

        // Return the ModelManager with the loaded data
        return new ModelManager(initialData, userPrefs, ingredientCatalogue, pastryCatalogue, storage);
    }



    private Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified: " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded. Using default config.");
            initializedConfig = new Config();
        }

        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file: " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    private UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded. Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting AddressBook " + VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("Stopping application and saving data");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
            storage.saveIngredientCatalogue(model.getIngredientCatalogue());
            storage.savePastryCatalogue(model.getPastryCatalogue());
        } catch (IOException e) {
            logger.severe("Failed to save data on application stop: " + e.getMessage());
        }
    }
}
