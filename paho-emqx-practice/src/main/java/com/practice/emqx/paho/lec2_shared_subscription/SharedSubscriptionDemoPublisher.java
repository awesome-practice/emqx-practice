package com.practice.emqx.paho.lec2_shared_subscription;

import com.practice.emqx.paho.MqttUtil;
import com.practice.emqx.paho.PahoMqttConstants;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

/**
 * @author Luo Bao Ding
 * @since 2019/3/26
 */
public class SharedSubscriptionDemoPublisher {


    public static void main(String[] args) throws MqttException, InterruptedException {
        new SharedSubscriptionDemoPublisher().demo();
    }


    public void demo() throws MqttException, InterruptedException {
        MqttConnectOptions options = MqttUtil.buildOptions();
        // persistence for qos>0
//        MqttClientPersistence persistence = new MqttDefaultFilePersistence(MqttConstants.PERSISTENCE_FILE_DIR);
        MqttClientPersistence persistence = new MemoryPersistence();

        MqttClient client = new MqttClient(PahoMqttConstants.EMQX_SERVER_HOST,
                PahoMqttConstants.ID_PUBLISHER_SHARED_SUBSCRIPTION, persistence);
        client.connect(options);

        publish(client);

        client.disconnect();
        System.out.println("manually disconnected");
    }

    private void publish(MqttClient client) throws MqttException, InterruptedException {
        int index = 0;
        while (true) {
            String msg = "message " + (index++);

            MqttMessage mqttMessage = new MqttMessage(msg.getBytes(StandardCharsets.UTF_8));
            mqttMessage.setQos(PahoMqttConstants.QOS);
            mqttMessage.setRetained(true);
            try {
                client.publish(PahoMqttConstants.TP_PUB_SHARED_SUBSCRIPTION, mqttMessage);
            } catch (MqttException e) {
                e.printStackTrace();
            }
            System.out.println("published msg: " + msg + ", retained:" + mqttMessage.isRetained());

            Thread.sleep(1000);

        }
    }
}
