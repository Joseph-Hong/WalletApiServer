package com.cmm.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Map Builder
 *
 * @author TeamUp
 */
public class MapBuilder
{
    final Map<String, Object> param;

    private MapBuilder()
    {
        super();
        param = new HashMap<>();
    }

    private MapBuilder( final Map<String, Object> map )
    {
        super();
        param = map;
    }

    public static MapBuilder map()
    {
        return new MapBuilder();
    }

    public static MapBuilder map( final Map<String, Object> map )
    {
        return new MapBuilder();
    }

    public MapBuilder add( String key, Object value )
    {
        param.put( key, value );
        return this;
    }

    public MapBuilder add( Map<String, Object> value )
    {
        if ( value != null )
        {
            param.putAll( value );
        }
        return this;
    }

    public Map<String, Object> build()
    {
        return param;
    }
}