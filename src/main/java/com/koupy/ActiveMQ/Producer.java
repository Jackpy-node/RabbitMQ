package com.koupy.ActiveMQ;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @auther: kpy
 * @version: 1.0
 * @Package: com.koupy.ActiveMQ
 * @data: 2020-5-24 19:16
 * @discription:
 **/
public class Producer {

    // m默认用户
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;

    //默认密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;

    //默认URL
    private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {

        // 连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, URL);

        try {
            // 连接
            Connection connection = connectionFactory.createConnection();

            // 启动连接
            connection.start();

            // 创建Session
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

            // 消息目的地
            Destination destination = session.createQueue("FirstQueue");

            // 消息生产者
            MessageProducer messageProducer = session.createProducer(destination);

            // 设置不持久化
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            for (int i = 0; i < 10; i++) {

                // 创建文本消息
                TextMessage textMessage = session.createTextMessage("ActiveMQ: 这是第 " + i + " 条消息");

                System.out.println("ActiveMQ: 这是第 " + i + " 条消息");

                // 生产者发送消息
                messageProducer.send(textMessage);
            }

            session.commit();

            session.close();

            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
