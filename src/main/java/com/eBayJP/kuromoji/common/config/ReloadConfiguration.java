package com.eBayJP.kuromoji.common.config;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.event.Event;
import org.apache.commons.configuration2.event.EventListener;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.config_ReloadConfiguration.java
 * </pre>
 * @date : 2019. 6. 28.
 * @author : hychoi
 */
@Component
public class ReloadConfiguration {
	
	private ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration> builder;
	
	@PostConstruct
    void init() {

        builder = new ReloadingFileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                .configure(new Parameters().fileBased().setFile(new File("src/main/resources/ebayJPDictionary.csv")));

        builder.addEventListener(ConfigurationBuilderEvent.CONFIGURATION_REQUEST, new EventListener<Event>() {
            @Override
            public void onEvent(Event event) {
                System.out.println(" getConfiguration()이 호출 될때마다 이벤트 발생");
            }
        });

        PeriodicReloadingTrigger configReloadingTrigger = new PeriodicReloadingTrigger(
                builder.getReloadingController(), null, 1, TimeUnit.SECONDS);

        configReloadingTrigger.start();

    }

    public Configuration getCompositeConfiguration() {
        try {
            return builder.getConfiguration();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
