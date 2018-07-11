package com.xmasye.ours.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Iterator;

public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    public static final String EMPTY_Object = "{}";
    public static final String EMPTY_ARRAY = "[]";
    public static ObjectMapper mapper = null;

    private JsonUtil() {
    }

    public static JsonObject toJsonObject(String text) {
        Reader reader = new StringReader(text);
        JsonReader jsonReader = Json.createReader(reader);

        JsonObject var3;
        try {
            var3 = jsonReader.readObject();
        } finally {
            try {
                if (null != jsonReader) {
                    jsonReader.close();
                }

                if (null != reader) {
                    reader.close();
                }
            } catch (IOException var10) {
                ;
            }

        }

        return var3;
    }

    public static JsonObject toJsonObject(Object object) {
        String json = toJSONString(object);
        return toJsonObject(json);
    }

    public static JsonArray toJsonArray(String text) {
        Reader reader = new StringReader(text);
        JsonReader jsonReader = Json.createReader(reader);

        JsonArray var3;
        try {
            var3 = jsonReader.readArray();
        } finally {
            try {
                if (null != jsonReader) {
                    jsonReader.close();
                }

                if (null != reader) {
                    reader.close();
                }
            } catch (IOException var10) {
                ;
            }

        }

        return var3;
    }

    public static <T> T parseObject(JsonObject json, Class<T> clazz) {
        return null == json ? null : parseObject(json.toString(), clazz);
    }

    public static <T> T parseObject(String text, TypeReference<T> tr) {
        try {
            return mapper.readValue(text, tr);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        try {
            return mapper.readValue(text, clazz);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static boolean isJSON(String json) {
        boolean valid = false;

        try {
            JsonParser parser = (new ObjectMapper()).getFactory().createParser(json);

            while(true) {
                if (parser.nextToken() == null) {
                    valid = true;
                    break;
                }
            }
        } catch (JsonParseException var3) {
            var3.printStackTrace();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return valid;
    }

    public static String toJSONString(Object object) {
        if (null == object) {
            return null;
        } else {
            try {
                return mapper.writeValueAsString(object);
            } catch (JsonProcessingException var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    public static JsonObjectBuilder toJsonObjectBuilder(String text) {
        JsonObject json = toJsonObject(text);
        return toJsonObjectBuilder(json);
    }

    public static JsonObjectBuilder toJsonObjectBuilder(JsonObject json, String name) {
        return toJsonObjectBuilder(json.getJsonObject(name));
    }

    public static JsonObjectBuilder toJsonObjectBuilder(JsonObject json) {
        JsonObjectBuilder jsonObjBuild = Json.createObjectBuilder();
        if (null != json) {
            Iterator var2 = json.keySet().iterator();

            while(var2.hasNext()) {
                String key = (String)var2.next();
                jsonObjBuild.add(key, (JsonValue)json.get(key));
            }
        }

        return jsonObjBuild;
    }

    public static JsonArrayBuilder toJsonArrayBuilder(String text) {
        JsonArray json = toJsonArray(text);
        return toJsonArrayBuilder(json);
    }

    public static JsonArrayBuilder toJsonArrayBuilder(JsonObject json, String name) {
        return toJsonArrayBuilder(json.getJsonArray(name));
    }

    public static JsonArrayBuilder toJsonArrayBuilder(JsonArray array) {
        JsonArrayBuilder jsonArrayBuild = Json.createArrayBuilder();
        if (null != array) {
            for(int i = 0; i < array.size(); ++i) {
                jsonArrayBuild.add((JsonValue)array.get(i));
            }
        }

        return jsonArrayBuild;
    }

    static {
        try {
            mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        } catch (Exception var1) {
            logger.error("初始化错误！", var1);
        }

    }

    public static class ObjectT<T> extends TypeReference<T> {
        private Class<T> clz;

        public ObjectT(Class<T> clz) {
            this.clz = clz;
        }

        public Type getType() {
            return this.clz;
        }
    }

}
