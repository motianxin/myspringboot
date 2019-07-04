/**
 * FileName: JsonUtils
 * Author:   Administrator
 * Date:     2019/7/4 10:35
 * Description: JsonUtils
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.utils.strutils;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈JsonUtils〉
 *
 * @author Administrator
 * @create 2019/7/4 10:35
 * @version 3.2.2
 */
public class JsonUtils {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);//支持 \n \t
        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER,true);//支持   双反斜杠的转义
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    /**
     * 解析json数组，如: [{...}]
     * @param json
     * @param clazz
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> List<T> parseValueList(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return new ArrayList<T>();
        }
        try {
            return objectMapper.readValue(json, getCollectionType(ArrayList.class, clazz));
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 解析json数组，如: [{...}]
     * @param json
     * @param clazz
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> List<T> json2ObjList(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return new ArrayList<T>();
        }
        try {
            return objectMapper.readValue(json, getCollectionType(ArrayList.class, clazz));
        } catch (Exception e) {
            return null;
        }

    }



    /**
     * 解析集合类型json
     *
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }





    /**
     * json转字符串
     *
     * @param jsonStr
     * @param classType
     * @return
     */
    public static <T> T json2Obj(String jsonStr, Class<T> classType) {
        if(StringUtils.isBlank(jsonStr)){
            return null;
        }

        try {
            return objectMapper.readValue(jsonStr, classType);
        } catch (Exception e) {
            return null;
        }



    }



    /**
     * 解析JSON对象 {...}
     * @param json
     * @param clazz
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> T parseValue(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        if (StringUtils.isNotBlank(json)) {
            return objectMapper.readValue(json, clazz);
        } else {
            return null;
        }
    }




    /**
     * 将对象转换成json字符串
     *
     * @param obj
     * @return
     */
    public static String obj2Json(Object obj) {
        if(obj == null){
            return null;
        }

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return null;
        }

    }


    /**
     * 获取ObjectMapper
     * @return
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * 是否有效的json格式
     * @param json
     * @return
     */
    public static boolean isValidJson(String json){
        return true;
    }

}
