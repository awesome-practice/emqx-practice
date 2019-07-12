package com.practice.emqx.paho.lec1_reconnect;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

/**
 * @author Luo Bao Ding
 * @since 2019/7/12
 */
public class MyCallback implements MqttCallbackExtended {

    @Override
    public void connectionLost(Throwable cause) {

        System.out.println("[connectionLost], caused by: " + cause);

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(buildOutput(topic, message));
    }

    private String buildOutput(String topic, MqttMessage message) {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add("[messageArrived] topic = [" + topic + "]")
                .add("qos = [" + message.getQos() + "]")
                .add("message = [" + new String(message.getPayload(), StandardCharsets.UTF_8) + "]")
                .add("retained:" + message.isRetained())
        ;

        return joiner.toString();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());

    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        if (reconnect) {
            System.out.println("[reconnected] completed: " + serverURI);
        } else {
            System.out.println("[connected] completed: " + serverURI);

        }

    }
}
