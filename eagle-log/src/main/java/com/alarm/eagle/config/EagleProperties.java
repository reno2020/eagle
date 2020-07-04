package com.alarm.eagle.config;

import org.apache.flink.api.java.utils.ParameterTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luxiaoxun on 2020/01/28.
 */
public class EagleProperties {
    private static final Logger logger = LoggerFactory.getLogger(EagleProperties.class);

    private ParameterTool parameter;
    private static String mode = "";

    private static class PropertiesHolder {
        private static final EagleProperties holder = new EagleProperties();
    }

    public static EagleProperties getInstance(String flinkMode) {
        mode = flinkMode;
        return PropertiesHolder.holder;
    }

    private EagleProperties() {
        init();
    }

    private void init() {
        try {
            logger.info("load parameters from system ...");
            Map<String, String> map = new HashMap<>();
            switch (mode) {
                case ConfigConstant.MODE_DEV:
                    map.put(ConfigConstant.FLINK_MODE, ConfigConstant.MODE_DEV);
                    map.put(ConfigConstant.KAFKA_BOOTSTRAP_SERVERS, "168.61.45.26:9092,168.61.45.27:9092,168.61.45.28:9092");
                    map.put(ConfigConstant.KAFKA_GROUP_ID, "log-dev");
                    map.put(ConfigConstant.KAFKA_TOPIC, "YWKF-ISCS-LOG");
                    map.put(ConfigConstant.KAFKA_TOPIC_PARALLELISM, "3");
                    break;
                case ConfigConstant.MODE_TEST:
                    map.put(ConfigConstant.FLINK_MODE, ConfigConstant.MODE_TEST);
                    map.put(ConfigConstant.KAFKA_BOOTSTRAP_SERVERS, "168.61.45.26:9092,168.61.45.27:9092,168.61.45.28:9092");
                    map.put(ConfigConstant.KAFKA_GROUP_ID, "log-test");
                    map.put(ConfigConstant.KAFKA_TOPIC, "YWKF-ISCS-LOG");
                    map.put(ConfigConstant.KAFKA_TOPIC_PARALLELISM, "10");
                    break;
            }
            map.put(ConfigConstant.FLINK_PARALLELISM, "2");
            map.put(ConfigConstant.FLINK_ENABLE_CHECKPOINT, "true");

            map.put(ConfigConstant.STREAM_PROCESS_PARALLELISM, "2");
            map.put(ConfigConstant.STREAM_RULE_URL, "http://localhost:9080/eagle-api/log/rules");

            map.put(ConfigConstant.REDIS_WINDOW_TIME_SECONDS, "60");
            map.put(ConfigConstant.REDIS_WINDOW_TRIGGER_COUNT, "10000");
            map.put(ConfigConstant.REDIS_CLUSTER_HOSTS, "168.61.45.26:7000,168.61.45.26:7003,168.61.45.27:7001,168.61.45.27:7004,168.61.45.28:7002,168.61.45.28:7005");
            map.put(ConfigConstant.REDIS_SINK_PARALLELISM, "2");

            map.put(ConfigConstant.ELASTICSEARCH_HOSTS, "168.61.45.26:9200");
            map.put(ConfigConstant.ELASTICSEARCH_BULK_FLUSH_MAX_ACTIONS, "5000");
            map.put(ConfigConstant.ELASTICSEARCH_BULK_FLUSH_MAX_SIZE_MB, "50");
            map.put(ConfigConstant.ELASTICSEARCH_BULK_FLUSH_INTERVAL_MS, "1000");
            map.put(ConfigConstant.ELASTICSEARCH_SINK_PARALLELISM, "2");
            map.put(ConfigConstant.ELASTICSEARCH_INDEX_POSTFIX, "_log-test");

            parameter = ParameterTool.fromMap(map);
        } catch (Exception ex) {
            logger.error("load parameters from system error: " + ex.toString());
        }
    }

    public ParameterTool getParameter() {
        return parameter;
    }

}
