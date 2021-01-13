package com.example.Generator;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedRate = 60000)
	public void reportCurrentTime() {
		log.info("Sending measure at {}", dateFormat.format(new Date()));
		try {
            Gerador.main(null);
        } catch (Exception e) {
            log.info("Http server is unreachable");
        }
	}
}