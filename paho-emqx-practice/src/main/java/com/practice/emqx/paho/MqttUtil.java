package com.practice.emqx.paho;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author Luo Bao Ding
 * @since 2019/3/26
 */
public class MqttUtil {
    public static MqttConnectOptions buildOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
//        options.setCleanSession(true);
        options.setCleanSession(false);

        options.setUserName(PahoMqttConstants.USER_NAME);
        options.setPassword(PahoMqttConstants.PASSWORD.toCharArray());

        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(4);
        options.setKeepAliveInterval(4);

        options.setMaxInflight(100);

        return options;
    }

    public static MqttClient buildMqttClient(String id) throws MqttException {
        return new MqttClient(PahoMqttConstants.EMQX_SERVER_HOST, id, new MemoryPersistence());
    }
}
