package com.koupy.ActiveMQ;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @auther: kpy
 * @version: 1.0
 * @Package: com.koupy.ActiveMQ
 * @data: 2020-5-24 19:33
 * @discription:
 **/
public class Consumer {

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
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 消息目的地
            Destination destination = session.createQueue("FirstQueue");

            // 消息消费者
            MessageConsumer messageConsumer = session.createConsumer(destination);

            while (true) {
                // 创建接收文本消息
                TextMessage textMessage = (TextMessage) messageConsumer.receive();

                if (textMessage != null) {
                    System.out.println("接收ActiveMQ: " + textMessage.getText());
                } else {
                    break;
                }
            }
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
