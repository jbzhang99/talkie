package com.meta.busi2web;

import com.cloud.talkie.data.queue.Sync2Web;
import com.cloud.talkie.data.queue.SyncDataQueueName;
import com.taobao.diamond.client.processor.ServerAddressProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

@Configuration
public class Sync2WebContainer {

    @Bean
    public MessageListenerAdapter DataSyncAdapter(Sync2Web sync2Web) {
        MessageListenerAdapter dataSyncAdapter = new MessageListenerAdapter();
        dataSyncAdapter.setDelegate(sync2Web);
        dataSyncAdapter.setDefaultListenerMethod("consumer");
        return dataSyncAdapter;
    }

    @Bean
    public DefaultMessageListenerContainer DataSyncContainer(CachingConnectionFactory cf, MessageListenerAdapter da) {
        DefaultMessageListenerContainer dataSyncContainer = new DefaultMessageListenerContainer();
        dataSyncContainer.setConnectionFactory(cf);
        dataSyncContainer.setMessageListener(da);
        dataSyncContainer.setDestinationName(SyncDataQueueName.getSync2WebQueueName());
        return dataSyncContainer;
    }
}


