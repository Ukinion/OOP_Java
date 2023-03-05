package exceptions;

public class InvalidCommandArgumentListException extends IllegalArgumentException
{
    public InvalidCommandArgumentListException()
    { super("TypeError: wrong command argument count\n\t\t"); }
}
