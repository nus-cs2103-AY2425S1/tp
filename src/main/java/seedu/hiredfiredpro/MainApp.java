package seedu.hiredfiredpro;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.hiredfiredpro.commons.core.Config;
import seedu.hiredfiredpro.commons.core.LogsCenter;
import seedu.hiredfiredpro.commons.core.Version;
import seedu.hiredfiredpro.commons.exceptions.DataLoadingException;
import seedu.hiredfiredpro.commons.util.ConfigUtil;
import seedu.hiredfiredpro.commons.util.StringUtil;
import seedu.hiredfiredpro.logic.Logic;
import seedu.hiredfiredpro.logic.LogicManager;
import seedu.hiredfiredpro.model.HiredFiredPro;
import seedu.hiredfiredpro.model.Model;
import seedu.hiredfiredpro.model.ModelManager;
import seedu.hiredfiredpro.model.ReadOnlyHiredFiredPro;
import seedu.hiredfiredpro.model.ReadOnlyUserPrefs;
import seedu.hiredfiredpro.model.UserPrefs;
import seedu.hiredfiredpro.model.util.SampleDataUtil;
import seedu.hiredfiredpro.storage.HiredFiredProStorage;
import seedu.hiredfiredpro.storage.JsonHiredFiredProStorage;
import seedu.hiredfiredpro.storage.JsonUserPrefsStorage;
import seedu.hiredfiredpro.storage.Storage;
import seedu.hiredfiredpro.storage.StorageManager;
import seedu.hiredfiredpro.storage.UserPrefsStorage;
import seedu.hiredfiredpro.ui.Ui;
import seedu.hiredfiredpro.ui.UiManager;

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
        logger.info("=============================[ Initializing HiredFiredPro ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        HiredFiredProStorage hiredFiredProStorage = new JsonHiredFiredProStorage(userPrefs.getHiredFiredProFilePath());
        storage = new StorageManager(hiredFiredProStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s hired fired pro and {@code userPrefs}. <br>
     * The data from the sample hired fired pro will be used instead if {@code storage}'s hired fired pro is not found,
     * or an empty hired fired pro will be used instead if errors occur when reading {@code storage}'s hired fired pro.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getHiredFiredProFilePath());

        Optional<ReadOnlyHiredFiredPro> hiredFiredProOptional;
        ReadOnlyHiredFiredPro initialData;
        try {
            hiredFiredProOptional = storage.readHiredFiredPro();
            if (!hiredFiredProOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getHiredFiredProFilePath()
                        + " populated with a sample HiredFiredPro.");
            }
            initialData = hiredFiredProOptional.orElseGet(SampleDataUtil::getSampleHiredFiredPro);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getHiredFiredProFilePath() + " could not be loaded."
                    + " Will be starting with an empty HiredFiredPro.");
            initialData = new HiredFiredPro();
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
        logger.info("Starting HiredFiredPro " + MainApp.VERSION);
        ui.start(primaryStage);

    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping HiredFiredPro ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
