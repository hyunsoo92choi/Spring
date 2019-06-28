package com.eBayJP.kuromoji.common.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.eBayJP.kuromoji.util.FileUtil;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.config_ScheduleConfiguration.java
 * </pre>
 * @date : 2019. 6. 28.
 * @author : hychoi
 */
@Component
public class ScheduleConfiguration implements ApplicationContextAware {
	
	private static final Logger log = LoggerFactory.getLogger(ScheduleConfiguration.class);
	
	private ApplicationContext applicationContext;
	
	@Scheduled(cron = "*/10 * * * * *")
    @PostConstruct
    void init(){
		
		FileUtil.ebayJapanDictionary = FileUtil.getUserDictionary();
		log.info("[ScheduleConfiguration]: >>>> File Read:{}\n", FileUtil.ebayJapanDictionary );
		resetBean("EbayJPTokenizer");
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}
	
	private void resetBean(String beanName) {
		
		log.info("[ScheduleConfiguration]: >>>> resetBeanName:{}\n", beanName );
        
		GenericApplicationContext genericApplicationContext = (GenericApplicationContext) applicationContext;
        genericApplicationContext.getBean(RefreshScope.class).refresh(beanName);
    }
}
