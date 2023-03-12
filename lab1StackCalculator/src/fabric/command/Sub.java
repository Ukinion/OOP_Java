package fabric.command;

import calculator.Calculator;
import context.ProgramContext;
import exceptions.InvalidCommandArgumentListException;
import exceptions.NotEnoughOperandsException;
import exceptions.UndefinedExecutionContextException;

import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Stack;

public class SubscriptionCommand implements Command
{
    private final static int NUM_OPERANDS = 2;
    private final static int NUM_ARGS = 1;

    @Override
    public void execute(ProgramContext context) throws RuntimeException
    {
        try
        {
            @SuppressWarnings("unchecked")
            var stack = (Stack<String>) context.lookupContext(Calculator.STACK_CONTEXT);
            @SuppressWarnings("unchecked")
            var variableMap = (HashMap<String, Double>) context.lookupContext(Calculator.VARIABLE_CONTEXT);
            var argsCommand = (String[]) context.lookupContext(Calculator.COMMAND_INFO_CONTEXT);

            if (argsCommand.length != NUM_ARGS)
            { throw new InvalidCommandArgumentListException(); }

            if (stack.size() < NUM_OPERANDS)
            { throw new NotEnoughOperandsException(); }

            String op1 = stack.pop();
            String op2 = stack.peek();

            variableMap.put(op2, variableMap.get(op2) - variableMap.get(op1));
        }
        catch (NamingException ex)
        {
            _logger.severe(ex.getMessage() + "Could not find required context!\n");
            throw new UndefinedExecutionContextException();
        }
    }
}
