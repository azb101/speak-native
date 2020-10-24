package com.abuseapp.speaknativeapi.helpers;

import org.springframework.stereotype.Component;

@Component
public class StringHelpers {
    public static boolean isBlank(String string)
    {
        return isEmpty(string) || isWhitespace(string);
    }

    public static boolean isEmpty(String string)
    {
        return string == null || string.length() == 0;
    }

    private static boolean isWhitespace(String string)
    {
        for(int i=0; i < string.length(); i++) {
            if(string.charAt(i) != ' ')
                return false;
        }

        return true;
    }
}
