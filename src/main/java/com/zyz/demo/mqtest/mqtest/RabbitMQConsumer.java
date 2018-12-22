package com.zyz.demo.mqtest.mqtest;/**
 * Created by zhangyangze on 2018/12/22
 */

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * class name RabbitMQConsumer
 *
 * @author zhang yang ze
 * @date 2018/12/22
 */
public class RabbitMQConsumer {

    /**队列名*/
    private static final String QUEUE_NAME= "quere_demo";
    /**MQ地址*/
    private static final String IP_ADDRESS = "140.143.0.143";
    /**MQ端口号*/
    private static final int PORT = 5672;

    public static void main(String[] args){


                /**设置地址*/
        Address[] addresses = new Address[]{
                new Address(IP_ADDRESS,PORT)
        };

        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setUsername("root");
            connectionFactory.setPassword("root");
            Connection connection = connectionFactory.newConnection(addresses);
            final Channel channel = connection.createChannel();
            /**设置客户端最多接收未被ack的消息的个数*/
            channel.basicQos(64);
            /**创建消费者*/
            Consumer consumer = new DefaultConsumer(channel){

                @Override
                public void handleDelivery(String consumerTag,Envelope envelope,AMQP.BasicProperties properties,byte[] body)throws IOException{
                    System.out.println("recv message : " + new String(body));
                    try{
                        TimeUnit.SECONDS.sleep(1);
                    }catch (InterruptedException ie){
                        ie.printStackTrace();
                    }

                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            };
            channel.basicConsume(QUEUE_NAME,consumer);
            TimeUnit.SECONDS.sleep(5);

            channel.close();
            connection.close();

        }catch (IOException ioe){
            ioe.printStackTrace();
        }catch (TimeoutException te){
            te.printStackTrace();
        }catch (InterruptedException ine){
            ine.printStackTrace();
        }



    }



}
