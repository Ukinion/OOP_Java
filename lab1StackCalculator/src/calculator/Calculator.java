package calculator;

import exceptions.*;
import fabric.CommandFabric;
import fabric.command.Command;
import context.ProgramContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.Scanner;


public class Calculator
{
    private final Logger _logger;
    private final HashMap<String, Command> _commandMap;

    private final static String COMMAND_LINE_DELIMITER = " ";
    private final static int COMMAND = 0;

    public final static String STACK_CONTEXT = "stackContext";
    public final static String VARIABLE_CONTEXT = "variableContext";
    public final static String COMMAND_INFO_CONTEXT = "commandContext";

    public Calculator(String configName) throws RuntimeException
    {
        try
        {
            _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            _logger.info("Calculator is launching");
            CommandFabric fabric = new CommandFabric(configName);
            _commandMap = fabric.createCommandMap();
            _logger.info("Calculator is launched\n");
        }
        catch (RuntimeException | ReflectiveOperationException | IOException ex)
        { throw new FabricInternalErrorException(); }
    }

    public void calculate(InputStream inputStream) throws RuntimeException
    {
        _logger.info("Start Calculating\n");

        Scanner commandFile = new Scanner(inputStream);
        HashMap<String, Double> variableMap = new HashMap<String, Double>();
        Stack<String> stack = new Stack<>();
        ProgramContext context = new ProgramContext();

        context.addContext(STACK_CONTEXT, stack);
        context.addContext(VARIABLE_CONTEXT, variableMap);

        int cntLine = 0;
        String curLine;
        String[] commandAndArguments;
        Command command;

        while (commandFile.hasNextLine())
        {
            ++cntLine;
            curLine = commandFile.nextLine();
            commandAndArguments = curLine.split(COMMAND_LINE_DELIMITER);

            if (!_commandMap.containsKey(commandAndArguments[COMMAND]))
            { throw new UnsupportedCommandException(commandAndArguments[COMMAND]); }

            context.addContext(COMMAND_INFO_CONTEXT, commandAndArguments);
            command = _commandMap.get(commandAndArguments[COMMAND]);

            try
            {
                _logger.info("Executing command: " + command.getClass().getName());
                command.execute(context);
                _logger.info("End executing\n");
            }
            catch (InvalidCommandArgumentListException ex)
            {
                _logger.severe(ex.getMessage() + "Change argument list\n");
                throw new ExecutionCommandErrorException();
            }
            catch (NotEnoughOperandsException ex)
            {
                _logger.severe(ex.getMessage() + "Aborting command execution...\n");
                throw new ExecutionCommandErrorException();
            }
            catch (UndefinedExecutionContextException ex)
            {
                _logger.severe(ex.getMessage() + "No further execution without context");
                throw new ExecutionCommandErrorException();
            }
            catch (DivisionByZeroException | SquareNegativeNumberException ex)
            {
                _logger.severe(ex.getMessage());
                throw new ExecutionCommandErrorException();
            }
        }

        _logger.info("Ending calculating\n");
        _logger.info("Work is done\n");
    }
}

