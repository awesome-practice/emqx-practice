package com.practice.emqx.springintegration.by_usual;

import com.practice.emqx.springintegration.SprMqttConstants;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.integration.mqtt.core.ConsumerStopAction;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

/**
 * @author Luo Bao Ding
 * @since 2019/4/2
 */
public class PahoUtil {
    public static MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{SprMqttConstants.EMQX_SERVER_HOST});
        options.setUserName(SprMqttConstants.USER_NAME);
        options.setPassword(SprMqttConstants.PASSWORD.toCharArray());
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setConnectionTimeout(2);
        options.setKeepAliveInterval(4);
        options.setMaxInflight(100);

        factory.setConnectionOptions(options);
        factory.setConsumerStopAction(ConsumerStopAction.UNSUBSCRIBE_CLEAN);
        return factory;
    }
}
