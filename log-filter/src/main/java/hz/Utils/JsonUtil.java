package hz.Utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author hz
 * @date 2021/4/30
 */
public class JsonUtil {

    public static String toJson(Object o) {
        ObjectMapper jackson = new ObjectMapper();
        String ret = "";
        try {
            // 变量为null -> ""
            jackson.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
                @Override
                public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
                    arg1.writeString("");
                }
            });
            ret = jackson.writeValueAsString(o);
        } catch (Exception e1) {

        }
        return ret;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            throw new RuntimeException("解析报文失败，未传Json");
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        //忽略与实体类中不一样的字段
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {

            throw new RuntimeException("解析报文失败", e);
        }
    }

    public static <T> List<T> fromList(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            throw new RuntimeException("解析报文失败，未传Json");
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        //忽略与实体类中不一样的字段
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            return mapper.readValue(json, listType);
        } catch (IOException e) {

            throw new RuntimeException("解析报文失败", e);
        }
    }


}
