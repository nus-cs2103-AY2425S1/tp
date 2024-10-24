package careconnect;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import careconnect.commons.core.Config;
import careconnect.commons.core.LogsCenter;
import careconnect.commons.core.Version;
import careconnect.commons.exceptions.DataLoadingException;
import careconnect.commons.util.ConfigUtil;
import careconnect.commons.util.StringUtil;
import careconnect.logic.Logic;
import careconnect.logic.LogicManager;
import careconnect.model.AddressBook;
import careconnect.model.Model;
import careconnect.model.ModelManager;
import careconnect.model.ReadOnlyAddressBook;
import careconnect.model.ReadOnlyUserPrefs;
import careconnect.model.UserPrefs;
import careconnect.model.person.AppointmentDate;
import careconnect.model.util.SampleDataUtil;
import careconnect.storage.AddressBookStorage;
import careconnect.storage.JsonAddressBookStorage;
import careconnect.storage.JsonUserPrefsStorage;
import careconnect.storage.Storage;
import careconnect.storage.StorageManager;
import careconnect.storage.UserPrefsStorage;
import careconnect.ui.Ui;
import careconnect.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, false);

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

        // Setting AppointmentDate formatter to be strict
        // Perhaps can be made into userprefs in the future
        AppointmentDate.DATE_FORMAT.setLenient(false);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        storage = new StorageManager(addressBookStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getAddressBookFilePath());

        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getAddressBookFilePath()
                        + " populated with a sample AddressBook.");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getAddressBookFilePath() + " could not be loaded."
                    + " Will be starting with an empty AddressBook.");
            initialData = new AddressBook();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting AddressBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping AddressBook ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
