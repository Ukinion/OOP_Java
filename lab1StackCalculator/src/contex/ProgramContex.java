package contex;

import javax.naming.NamingException;
import java.util.HashMap;

public class ProgramContex
{
    public HashMap<String, Object> _contexMap;

    public ProgramContex()
    { _contexMap = new HashMap<String, Object>(); }

    public HashMap<String, Object> addContex(String key, Object value)
    {
        _contexMap.put(key, value);
        return _contexMap;
    }

    public Object lookupContex(String key) throws NamingException
    {
        Object object = _contexMap.get(key);
        if (object == null)
        { throw new NamingException(); }
        return object;
    }
}
