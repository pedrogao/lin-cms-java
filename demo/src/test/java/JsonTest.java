import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JsonTest {

    @Test
    public void bool() {
        Map input = new HashMap();
        input.put("is", false);
        input.put("isPedro", "pedro");
        String output = JSON.toJSONString(input);
        log.info(output);
    }
}
