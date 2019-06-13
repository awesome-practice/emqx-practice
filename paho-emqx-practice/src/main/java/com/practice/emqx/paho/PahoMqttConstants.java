package com.practice.emqx.paho;

/**
 * @author Luo Bao Ding
 * @since 2018/11/29
 */
public class PahoMqttConstants {

//        public static final String EMQX_SERVER_HOST = "tcp://emq-beta.ufotosoft.com:1883";
    public static final String EMQX_SERVER_HOST = "tcp://dev.ufotosoft.com:1883";
    //    public static final String EMQX_SERVER_HOST = "tcp://54.85.214.136:1883";
//    public static final String EMQX_SERVER_HOST = "tcp://3.81.161.192:1883";
    //****** prod **********
//    public static final String EMQX_SERVER_HOST = "tcp://3.90.57.184:1883";
//    public static final String EMQX_SERVER_HOST = "tcp://52.207.97.179:1883";

    public static final String USER_NAME = "1111";
    public static final String PASSWORD = "ufoto888888";

    //  ------------------------ reconnect demo----------------
    public static final String TOPIC = "pos_message_callback";
    public static final String PUBLISHER_ID = "publisher-test";
    public static final String SUBSCRIBER_ID = "subscriber-test";

    //  -------------- shared subscription demo----------------
    public static final String TP_PUB_SHARED_SUBSCRIPTION = "TP-test-shared-subscription";

    public static final String TP_SUB_SHARED_SUBSCRIPTION = "$share/group_test/TP-test-shared-subscription";
//        public static final String TP_SUB_SHARED_SUBSCRIPTION = "$queue/TP-test-shared-subscription";

    public static final String ID_PUBLISHER_SHARED_SUBSCRIPTION = "SharedSubscriptionDemoPublisher";
    public static final String ID_SUBSCRIBER_SHARED_SUBSCRIPTION = "SharedSubscriptionDemoSubscriber";

    public static final String PERSISTENCE_FILE_DIR = "database";
    public static final int QOS = 2;
}
