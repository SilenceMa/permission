package com.mpf.permission.util;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;

@Slf4j
public class JsonMapper {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //config
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }

    /**
     * 将对象类型转换为String即json串
     * @param src 要转换的对象
     * @param <T> 对象类型用T代替
     * @return T 所对应的json串
     */
    public static <T> String obj2String(T src){
        if (src == null){
            return null;
        }else {
            try {
                return src instanceof String ? (String) src : objectMapper.writeValueAsString(src);
            } catch (IOException e) {
                log.error("parse object to string exception error is {},Src is {}",e,src);
                return null;
            }
        }
    }

    /**
     * 将json串转换为对应的对象
     * @param src json串
     * @param typeReference 目标类型
     * @param <T>
     * @return 目标类型
     */
    public static <T> T string2Obj(String src, TypeReference<T> typeReference){
        if (src == null || typeReference ==null){
            return null;
        }
        try {
            return typeReference.getType().equals(String.class) ? (T) src : objectMapper.readValue(src,typeReference);
        }catch (IOException e){
            log.error("parse String to Object ,String is {} ,TypeReference is {} ,error is {}",src,typeReference,e);
            return null;
        }
    }
}
