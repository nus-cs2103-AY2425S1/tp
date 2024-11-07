package keycontacts;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import keycontacts.commons.core.Config;
import keycontacts.commons.core.LogsCenter;
import keycontacts.commons.core.Version;
import keycontacts.commons.exceptions.DataLoadingException;
import keycontacts.commons.util.ConfigUtil;
import keycontacts.commons.util.StringUtil;
import keycontacts.logic.Logic;
import keycontacts.logic.LogicManager;
import keycontacts.model.Model;
import keycontacts.model.ModelManager;
import keycontacts.model.ReadOnlyStudentDirectory;
import keycontacts.model.ReadOnlyUserPrefs;
import keycontacts.model.StudentDirectory;
import keycontacts.model.UserPrefs;
import keycontacts.model.util.SampleDataUtil;
import keycontacts.storage.JsonStudentDirectoryStorage;
import keycontacts.storage.JsonUserPrefsStorage;
import keycontacts.storage.Storage;
import keycontacts.storage.StorageManager;
import keycontacts.storage.StudentDirectoryStorage;
import keycontacts.storage.UserPrefsStorage;
import keycontacts.ui.Ui;
import keycontacts.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing KeyContacts ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        StudentDirectoryStorage studentDirectoryStorage =
                new JsonStudentDirectoryStorage(userPrefs.getStudentDirectoryFilePath());
        storage = new StorageManager(studentDirectoryStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s {@code studentDirectory}
     * and {@code userPrefs}. <br>
     * The data from the sample student directory will be used instead if {@code storage}'s {@code studentDirectory}
     * is not found, or an empty student directory will be used instead if errors occur when reading {@code storage}'s
     * {@code studentDirectory}.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getStudentDirectoryFilePath());

        Optional<ReadOnlyStudentDirectory> studentDirectoryOptional;
        ReadOnlyStudentDirectory initialData;
        try {
            studentDirectoryOptional = storage.readStudentDirectory();
            if (!studentDirectoryOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getStudentDirectoryFilePath()
                        + " populated with a sample student directory.");
            }
            initialData = studentDirectoryOptional.orElseGet(SampleDataUtil::getSampleStudentDirectory);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getStudentDirectoryFilePath() + " could not be loaded."
                    + " Will be starting with an empty student directory.");
            initialData = new StudentDirectory();
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
        logger.info("Starting KeyContacts " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping KeyContacts ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
