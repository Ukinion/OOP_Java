package exceptions;

public class UnsupportedCommandException extends UnsupportedOperationException
{
    public UnsupportedCommandException()
    { super("OperationError: no such command in operation base!\n\t\t"); }
}
