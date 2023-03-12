package fabric;

import fabric.command.Command;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import java.util.Scanner;
import java.util.HashMap;
import java.io.File;

public class CommandFabric
{
    private final Logger _logger;
    private final Scanner _cfgScanner;

    private final static String CONFIG_DELIMITER = "=";
    private final static int COMMAND_USER_NAME = 0;
    private final static int COMMAND_CLASS_NAME = 1;

    public CommandFabric(String configName) throws FileNotFoundException
    {
        _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        _logger.info("Creating fabric\n");
        try
        {
            _logger.info("Opening fabric");
            _cfgScanner = new Scanner(new File(configName));
            _logger.info("Config is opened\n");
            _logger.info("Fabric is created\n");
        }
        catch (FileNotFoundException ex)
        {
            _logger.severe(ex.getMessage() + "Scanner could not open config file!\n\t\tHave to start aborting...\n\t\t");
            throw ex;
        }
    }

    public HashMap<String, Command> createCommandMap() throws RuntimeException, ReflectiveOperationException
    {
        _logger.info("Load command from config");
        HashMap<String, Command> commandMap = new HashMap<>();

        int cntLine = 0;
        String curLine;
        String[] configData;
        Class<?> commandClass;

        while (_cfgScanner.hasNextLine())
        {
            ++cntLine;
            curLine = _cfgScanner.nextLine();
            configData = curLine.split(CONFIG_DELIMITER);

            try
            {
                commandClass = Class.forName("fabric.command."+configData[COMMAND_CLASS_NAME]);
                commandMap.put(configData[COMMAND_USER_NAME], (Command) commandClass.getConstructor().newInstance());
            }
            catch (ArrayIndexOutOfBoundsException ex)
            {
                _logger.severe(ex.getMessage() + "\n\t\tIncorrect command line: №" + cntLine + "\n\t\t" + "Stop processing...\n");
                throw ex;
            }
            catch (ClassNotFoundException ex)
            {
                _logger.severe(ex.getMessage() + "\n\t\tConfig contains unsupported command.\n");
                throw ex;
            }
            catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException ex)
            {
                _logger.severe(ex.getMessage()+ "\n\t\tSystem detected internal error.\n");
                throw ex;
            }
        }

        _logger.info("Command Map is drawn\n");
        return commandMap;
    }
}
