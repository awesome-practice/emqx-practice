package com.practice.emqx.paho.lec1_reconnect;

import com.practice.emqx.paho.MqttUtil;
import com.practice.emqx.paho.PahoMqttConstants;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;


public class ReconnectDemoSubscriber {

    public static void main(String[] args) throws MqttException {
        ReconnectDemoSubscriber client = new ReconnectDemoSubscriber();
        client.connect();
    }


    public void connect() throws MqttException {
        final MqttClient client = MqttUtil.buildMqttClient(PahoMqttConstants.SUBSCRIBER_ID);

        client.setCallback(new MyCallback());

        client.connect(MqttUtil.buildOptions());

        doSubscribe(client);

    }

    private void doSubscribe(MqttClient client) throws MqttException {
        int[] qos = {2};
        String[] topic1 = {PahoMqttConstants.TOPIC};
        client.subscribe(topic1, qos);
    }
}
