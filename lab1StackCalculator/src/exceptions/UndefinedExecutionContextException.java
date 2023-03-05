package exceptions;

import java.util.NoSuchElementException;

public class UndefinedExecutionContextException extends NoSuchElementException
{
    public UndefinedExecutionContextException()
    { super("ContextError: requested context does not exist!\n\t\t"); }
}
