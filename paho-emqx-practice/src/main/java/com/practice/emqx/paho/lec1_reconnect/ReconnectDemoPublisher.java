package com.practice.emqx.paho.lec1_reconnect;

import com.practice.emqx.paho.MqttUtil;
import com.practice.emqx.paho.PahoMqttConstants;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;


public class ReconnectDemoPublisher {


    public static void main(String[] args) throws Exception {
        ReconnectDemoPublisher client = new ReconnectDemoPublisher();
        client.connect();

    }

    public void connect() throws MqttException {

        MqttConnectOptions options = MqttUtil.getOptions();


        final MqttClient client = new MqttClient(PahoMqttConstants.EMQX_SERVER_HOST, PahoMqttConstants.PUBLISHER_ID, new MemoryPersistence());
        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect) {
                    System.out.println("reconnect completed: " + serverURI);
                    try {
                        doWork(client);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                } else {
                    System.out.println("reconnect completed: " + serverURI);

                }

            }

            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("connectionLost, caused by: " + cause);

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                System.out.println("[messageArrived] topic = [" + topic + "], message = [" +
                        new String(message.getPayload(), StandardCharsets.UTF_8) + "]");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("deliveryComplete---------" + token.isComplete());

            }
        });

        client.connect(options);
        System.out.println(PahoMqttConstants.PUBLISHER_ID + " connected===========");

        doWork(client);

    }

    private void doWork(MqttClient client) throws MqttException {
        for (int i = 0; i < 11000; i++) {
            try {
                Thread.sleep(1000);
                doPublish(client, i);
            } catch (MqttException | InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


    private void doPublish(MqttClient client, int i) throws MqttException {
        MqttMessage message = new MqttMessage();
        //server.message.setQos(1);
        message.setQos(2);
        message.setRetained(true);
//        message.setRetained(false);
        message.setPayload((i + "").getBytes(StandardCharsets.UTF_8));

        MqttTopic topic = client.getTopic(PahoMqttConstants.TOPIC);

        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();

        StringJoiner joiner = new StringJoiner(",");
        assert token.isComplete();
        joiner.add("published successfully: " + i)
                .add("retained:" + message.isRetained())
        ;
        System.out.println(joiner.toString());

    }


}
