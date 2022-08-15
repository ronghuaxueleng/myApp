package com.ronghuaxueleng.common;

import com.ronghuaxueleng.utils.GsonUtil;
import java.util.HashMap;

/**host
 * 键值对上传类
 */

public class JSONS {

    public static String login(String userName, String password) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("userName", userName);
        map.put("password", password);
        map.put("userType", 1);
        return GsonUtil.ser(map);
    }


}
