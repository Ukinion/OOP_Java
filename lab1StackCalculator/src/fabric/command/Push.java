package fabric.command;

import calculator.Calculator;
import context.ProgramContext;
import exceptions.InvalidCommandArgumentListException;
import exceptions.UndefinedExecutionContextException;

import javax.naming.NamingException;
import java.util.Stack;

public class PushVariableOnStackCommand implements Command
{
    private final static int NUM_ARGS = 2;
    private final static int VARIABLE = 1;

    @Override
    public void execute(ProgramContext context) throws InvalidCommandArgumentListException, UndefinedExecutionContextException
    {
        try {
            @SuppressWarnings("unchecked")
            var stack = (Stack<String>) context.lookupContext(Calculator.STACK_CONTEXT);
            @SuppressWarnings("unchecked")
            var argsCommand = (String[]) context.lookupContext(Calculator.VARIABLE_CONTEXT);

            if (argsCommand.length < NUM_ARGS)
            { throw new InvalidCommandArgumentListException(); }

            stack.push(argsCommand[VARIABLE]);
        }
        catch (NamingException ex)
        {
            _logger.severe("Could not find required context!\n\t\t" + ex.getMessage());
            throw new UndefinedExecutionContextException();
        }

    }
}
