package com.zyz.demo.mqtest.mqtest;/**
 * Created by zhangyangze on 2018/12/22
 */

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * class name RabbitMQProducer
 *  消息生产者
 * @author zhang yang ze
 * @date 2018/12/22
 */
public class RabbitMQProducer {

    /**交换机名称*/
    private static final String EXCHANGE_NAME = "exchange_demo";
    /**路由键*/
    private static final String ROUTING_KEY = "routingKey_demo";
    /**队列名*/
    private static final String QUEUE_NAME= "quere_demo";
    /**MQ地址*/
    private static final String IP_ADDRESS = "140.143.0.143";
    /**MQ端口号*/
    private static final int PORT = 5672;


    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try{
            /**创建连接工厂*/
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(IP_ADDRESS);
            connectionFactory.setPort(PORT);
            connectionFactory.setUsername("root");
            connectionFactory.setPassword("root");
            /**创建连接*/
            connection = connectionFactory.newConnection();
            /**创建信道*/
            channel = connection.createChannel();
            /**创建交换器*/
            /**创建一个type=direct,持久化的，非自动删除的交换器EXCHANGE_NAME*/
            channel.exchangeDeclare(EXCHANGE_NAME,"direct",true,false,null);
            /**创建一个 持久化，非排他，非自动化删除的队列QUEUE_NAME*/
            channel.queueDeclare(QUEUE_NAME,true,false,false,null);
            /**交换器与队列通过路由键绑定*/
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);

            /**发送消息*/
            String message = "Hello Word";
            channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        }catch (IOException ioe){
            ioe.printStackTrace();
        }catch (TimeoutException timeoutException){
            timeoutException.printStackTrace();
        }finally {
            /**关闭资源*/
            if(channel != null){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }









    }




}
