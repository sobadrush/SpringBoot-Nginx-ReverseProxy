package com.nanshan.springbootnginxreverseproxy;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author RogerLo
 * @date 2023/6/18
 */
@Component
@ConfigurationProperties(prefix = "application") // 對應 application.yaml 中的 key: application
@Data // 將 yaml 中的值物件化，需搭配 get/setter
public class ApplicationProps {

    // 對應到 application 下的 key: servers (屬性名稱要 mapping)
    private List<Map<String, Object>> servers;

    // dash 會自動 mapping 為底線
    private Map<String, String> purchase_info;

}
