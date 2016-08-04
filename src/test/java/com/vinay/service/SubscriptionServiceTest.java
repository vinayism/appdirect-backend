package com.vinay.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinay.exceptions.AccountAlreadyExistException;
import com.vinay.model.SubscriptionEvent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionServiceTest {
	
	private Logger LOGGER = Logger.getLogger(SubscriptionServiceTest.class);
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@Test(expected=AccountAlreadyExistException.class)
	public void testCreateSubscription() throws JsonParseException, JsonMappingException, IOException, AccountAlreadyExistException {
		LOGGER.debug("Saving new Subscription");
		String uuid = subscriptionService.createSubscription(getSampleEvent());
		Assert.notNull(uuid);
		LOGGER.debug("Trying to ave duplicate Subscription");
		subscriptionService.createSubscription(getSampleEvent());
	}
	
	private SubscriptionEvent getSampleEvent() throws JsonParseException, JsonMappingException, IOException {
		
		String sampleEventStr = "{ " +
				 " \"type\": \"SUBSCRIPTION_ORDER\", " +
				 " \"marketplace\": { " +
				 " \"baseUrl\": \"https://www.acme.com\", " +
				 " \"partner\": \"APPDIRECT\" " +
					 "  }, " +
					 " \"creator\": { " +
					 "  \"address\": { " +
					 " \"firstName\": \"Sample\", " +
					 " \"fullName\": \"Sample Tester\", " +
					 " \"lastName\": \"Tester\" " +
					 " }, " +
					 " \"email\": \"sampletester@testco.com\", " +
					 " \"firstName\": \"Sample\", " +
					 " \"language\": \"en\", " +
					 " \"lastName\": \"Tester\", " +
					 " \"openId\": \"https://www.acme.com/openid/id/211aa367-f53b-4606-8887-80a381e0ef69\", " +
					 " \"uuid\": \"211aa369-f53b-4606-8887-80a361e0ef66\" " +
					 " }, " +
					 " \"payload\": { " +
					 " \"company\": { " +
					 " \"country\": \"US\", " +
					 " \"phoneNumber\": \"1234567890\", " +
					 " \"email\": \"contact@testerco.com\", " +
					 " \"name\": \"Sample Testing co.\", " +
					 " \"uuid\": \"bd58b532-323b-4627-a828-57729489b27b\", " +
					 " \"website\": \"www.testerco.com\" " +
					 " }, " +
					 " \"order\": { " +
					 " \"editionCode\": \"FREE\", " +
					 " \"pricingDuration\": \"MONTHLY\" " +
					 "  } " +
					 "  } " +
					 " }";
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(sampleEventStr.toString(), SubscriptionEvent.class);
	}
	

}
