package com.abuseapp.speaknativeappapijava.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectHelper {
    public static String stringify(Object obj) {
        try {
            if (obj == null)
                return null;

            var mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        }catch (Exception ex) {
            return "not serializable object";
        }
    }
}
