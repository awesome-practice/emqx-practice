package com.practice.emqx.paho.lec2_shared_subscription;

import com.practice.emqx.paho.MqttUtil;
import com.practice.emqx.paho.PahoMqttConstants;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;
import java.util.concurrent.CountDownLatch;

/**
 * @author Luo Bao Ding
 * @since 2019/3/26
 */
public class SharedSubscriptionDemoSubscriber {
    public static void main(String[] args) throws MqttException, InterruptedException {
        new SharedSubscriptionDemoSubscriber().demo();

    }

    public void demo() throws MqttException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        MqttClient client = doSubscribe(PahoMqttConstants.ID_SUBSCRIBER_SHARED_SUBSCRIPTION, PahoMqttConstants.TP_SUB_SHARED_SUBSCRIPTION);

        latch.await();
        client.disconnect();
        System.out.println("manually disconnected");
    }

    private MqttClient doSubscribe(String clientId, String topic) throws MqttException {
        MqttConnectOptions options = MqttUtil.getOptions();
        // persistence for qos>0
//        MqttClientPersistence persistence = new MqttDefaultFilePersistence(MqttConstants.PERSISTENCE_FILE_DIR);
        MqttClientPersistence persistence = new MemoryPersistence();
        MqttClient client = new MqttClient(PahoMqttConstants.EMQX_SERVER_HOST,
                clientId, persistence);

        client.connect(options);

        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect) {
                    try {
                        client.subscribe(topic, PahoMqttConstants.QOS);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("connectionLost:"+cause.getMessage());
                try {
                    client.subscribe(topic, PahoMqttConstants.QOS);
                } catch (MqttException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                StringJoiner joiner = new StringJoiner(",");
                joiner
                        .add("qos = [" + message.getQos() + "]")
                        .add("message = [" + new String(message.getPayload(), StandardCharsets.UTF_8) + "]")
                        .add("retained:" + message.isRetained())
                ;
                System.out.println(joiner.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("deliveryComplete");

            }
        });
        client.subscribe(topic, PahoMqttConstants.QOS);


        return client;
    }
}
