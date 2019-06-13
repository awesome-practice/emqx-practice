package com.practice.emqx.paho.lec1_reconnect;

import com.practice.emqx.paho.MqttUtil;
import com.practice.emqx.paho.PahoMqttConstants;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;


public class ReconnectDemoSubscriber {

    public static void main(String[] args) throws MqttException {
        ReconnectDemoSubscriber client = new ReconnectDemoSubscriber();
        client.connect();
    }


    public void connect() throws MqttException {
        final MqttClient client = new MqttClient(PahoMqttConstants.EMQX_SERVER_HOST, PahoMqttConstants.SUBSCRIBER_ID, new MemoryPersistence());

        MqttConnectOptions options = MqttUtil.getOptions();

        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectionLost(Throwable cause) {

                System.out.println("connectionLost, caused by: " + cause);

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                StringJoiner joiner = new StringJoiner(",");
                joiner.add("[messageArrived] topic = [" + topic + "]")
                        .add("qos = [" + message.getQos() + "]")
                        .add("message = [" + new String(message.getPayload(), StandardCharsets.UTF_8) + "]")
                        .add("retained:" + message.isRetained())
                ;

                System.out.println(joiner.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("deliveryComplete---------" + token.isComplete());

            }

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect) {
                    try {
                        doSubscribe(client);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("connect completed: " + serverURI);

            }
        });

        client.connect(options);

        doSubscribe(client);

    }

    private void doSubscribe(MqttClient client) throws MqttException {
        int[] Qos = {2};
        String[] topic1 = {PahoMqttConstants.TOPIC};
        client.subscribe(topic1, Qos);
    }
}
