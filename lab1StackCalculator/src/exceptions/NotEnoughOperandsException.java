package exceptions;

public class NotEnoughOperandsException extends IllegalArgumentException
{
    public NotEnoughOperandsException()
    { super("StackError: not enough elements in the stack\n\t\t"); }
}
