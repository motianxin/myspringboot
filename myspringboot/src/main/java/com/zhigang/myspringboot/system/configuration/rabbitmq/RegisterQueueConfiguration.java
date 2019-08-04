package com.zhigang.myspringboot.system.configuration.rabbitmq;

import com.zhigang.myspringboot.utils.common.ExchangeEnum;
import com.zhigang.myspringboot.utils.common.QueueEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/3 23:45
 * @Version 3.2.2
 **/
@Configuration
public class RegisterQueueConfiguration {
    /**
     * 配置路由交换对象实例
     *
     * @return
     */
    private DirectExchange registerDirectExchange() {
        return new DirectExchange(ExchangeEnum.MY_EXCHANGE.getExchangeName());
    }

    private FanoutExchange registerFanoutExchange() {
        return new FanoutExchange(ExchangeEnum.MY_EXCHANGE.getExchangeName());
    }

    private TopicExchange registerTopicExchange() {
        return new TopicExchange(ExchangeEnum.MY_EXCHANGE.getExchangeName());
    }

    /**
     * 配置用户注册队列对象实例
     * 并设置持久化队列
     *
     * @return
     */
    @Bean
    public Queue registerQueue() {
        return new Queue(QueueEnum.MY_QUEUE.getQueueName(), true);
    }

    /**
     * 将用户注册队列绑定到路由交换配置上并设置指定路由键进行转发
     *
     * @return
     */
    @Bean
    public Binding registerBinding() {
        return BindingBuilder.bind(registerQueue()).to(registerDirectExchange()).with(QueueEnum.MY_QUEUE.getRoutingKey());
    }
}
