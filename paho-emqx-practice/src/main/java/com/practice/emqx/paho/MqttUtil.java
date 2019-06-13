package com.practice.emqx.paho;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 * @author Luo Bao Ding
 * @since 2019/3/26
 */
public class MqttUtil {
    public static MqttConnectOptions getOptions() {
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
}
