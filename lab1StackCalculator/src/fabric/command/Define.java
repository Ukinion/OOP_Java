package fabric.command;

import context.ProgramContext;
import exceptions.InvalidCommandArgumentListException;
import javax.naming.NamingException;
import java.util.HashMap;

import calculator.Calculator;
import exceptions.UndefinedExecutionContextException;


public class DefineVariableCommand implements Command
{
    private final static int NUM_ARGS = 3;
    private final static int VARIABLE = 1;
    private final static int VAR_VALUE = 2;

    @Override
    public void execute(ProgramContext context) throws InvalidCommandArgumentListException, UndefinedExecutionContextException
    {
        try
        {
            var argsCommand = (String[]) context.lookupContext(Calculator.COMMAND_INFO_CONTEXT);
            @SuppressWarnings("unchecked")
            var variableMap = (HashMap<String, Double>) context.lookupContext(Calculator.VARIABLE_CONTEXT);

            if (argsCommand.length < NUM_ARGS)
            { throw new InvalidCommandArgumentListException(); }

            variableMap.put(argsCommand[VARIABLE], Double.valueOf(argsCommand[VAR_VALUE]));
        }
        catch (NamingException ex)
        {
            _logger.severe("Could not find required context!\n\t\t" + ex.getMessage());
            throw new UndefinedExecutionContextException();
        }
    }
}
