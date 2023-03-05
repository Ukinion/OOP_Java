package fabric.command;

import calculator.Calculator;
import context.ProgramContext;
import exceptions.DivisionByZeroException;
import exceptions.NotEnoughOperandsException;
import exceptions.UndefinedExecutionContextException;

import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Stack;

public class DivisionCommand implements Command
{
    private final static int NUM_OPERANDS = 2;

    @Override
    public void execute(ProgramContext context)
            throws DivisionByZeroException, NotEnoughOperandsException, UndefinedExecutionContextException
    {
        try {
            @SuppressWarnings("unchecked")
            var stack = (Stack<String>) context.lookupContext(Calculator.STACK_CONTEXT);
            @SuppressWarnings("unchecked")
            var variableMap = (HashMap<String, Double>) context.lookupContext(Calculator.VARIABLE_CONTEXT);

            if (stack.size() < NUM_OPERANDS)
            { throw new NotEnoughOperandsException(); }

            String op1 = stack.pop();
            String op2 = stack.peek();

            Double divider = variableMap.get(op1);
            if (divider == 0)
            { throw new DivisionByZeroException(); }

            variableMap.put(op2, variableMap.get(op2) / divider);
        }
        catch (NamingException ex)
        {
            _logger.severe("Could not find required context!\n\t\t" + ex.getMessage());
            throw new UndefinedExecutionContextException();
        }
    }
}
