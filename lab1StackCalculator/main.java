import calculator.Calculator;
import exceptions.ExecutionCommandErrorException;
import exceptions.FabricInternalErrorException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main
{
    private final static Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static void setupGlobalLogger()
    {
        try
        {
            _logger.setUseParentHandlers(false);
            _logger.setLevel(Level.ALL);

            FileHandler fileHandler = new FileHandler("log.conf");
            fileHandler.setFormatter(new SimpleFormatter());
            _logger.addHandler(fileHandler);
        }
        catch (SecurityException | IOException ex)
        { _logger.severe(ex.getMessage()); }
    }
    
    public static void main(String[] args)
    {
        setupGlobalLogger();

        try (FileInputStream inputStream = new FileInputStream("expression.data");)
        {
            Calculator calculator = new Calculator("commands.conf");
            calculator.calculate(inputStream);
        }
        catch (ExecutionCommandErrorException | FabricInternalErrorException | IOException ex)
        {
            _logger.severe(ex.getMessage());
            _logger.info("Work is aborted\n");
        }
    }
}
