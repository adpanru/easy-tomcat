package Tools;

import java.util.HashMap;

/**
 * http请求参数仿写
 *
 * @author 小如
 * @date 2023/08/15
 */
public class HttpRequestDemo {

    public HashMap<String, String> map = new HashMap<>();

    public String getParameter(String key) {
        return map.get(key);
    }

}