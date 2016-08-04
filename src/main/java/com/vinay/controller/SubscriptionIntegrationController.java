package com.vinay.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinay.exceptions.AccountAlreadyCancelledException;
import com.vinay.exceptions.AccountAlreadyExistException;
import com.vinay.exceptions.AccountNotFoundException;
import com.vinay.model.SubscriptionEvent;
import com.vinay.model.SubscriptionResponseDto;
import com.vinay.service.SubscriptionService;

@RestController
public class SubscriptionIntegrationController {

	private final Logger LOGGER = Logger.getLogger(SubscriptionIntegrationController.class);

	@Autowired
	private SubscriptionService subscriptionService;

	@RequestMapping(value = "/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public SubscriptionResponseDto createSubscription(@RequestParam String eventUrl) {
		try {
			LOGGER.info("Create Subscription Called!!!");
			SubscriptionEvent event = fetchSubscriptionEventDetails(eventUrl);
			Assert.notNull(event, "Unable to parse Event");
			Assert.isTrue("SUBSCRIPTION_ORDER".equals(event.getType()), "Incorrect Subscription Type.");
			String newAccountUuid = subscriptionService.createSubscription(event);
			return new SubscriptionResponseDto(true, newAccountUuid);
		} catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException e) {
			LOGGER.error("Unable to process subscription event due to: " + e.getMessage(), e);
			return new SubscriptionResponseDto(false, "UNAUTHORIZED", "Un-authorizes request received.");
		} catch (AccountAlreadyExistException aae) {
			LOGGER.error("Unable to process subscription as account for the company already exist");
			return new SubscriptionResponseDto(false, "UNKNOWN_ERROR", "Account for the company already exist");
		} catch (IOException ioe) {
			LOGGER.error("Unable to process create subscription event due to: " + ioe.getMessage(), ioe);
			return new SubscriptionResponseDto(false, "UNKNOWN_ERROR", "Technical error has occoured");
		}
	}

	@RequestMapping(value = "/change", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public SubscriptionResponseDto changeSubscription(@RequestParam String eventUrl) {
		try {
			SubscriptionEvent event = fetchSubscriptionEventDetails(eventUrl);
			Assert.notNull(event, "Unable to parse Event");
			Assert.isTrue("SUBSCRIPTION_CHANGE".equals(event.getType()), "Incorrect Subscription Type.");
			subscriptionService.changeScbscription(event);
			return new SubscriptionResponseDto(true);
		} catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException e) {
			LOGGER.error("Unable to process subscription event due to: " + e.getMessage(), e);
			return new SubscriptionResponseDto(false, "UNAUTHORIZED", "Un-authorizes request received.");
		} catch (AccountNotFoundException e) {
			LOGGER.error("Unable to process subscription as account for the company does not exist");
			return new SubscriptionResponseDto(false, "ACCOUNT_NOT_FOUND", "account for the company does not exist");
		} catch (IOException ioe) {
			LOGGER.error("Unable to process change subscription event due to: " + ioe.getMessage(), ioe);
			return new SubscriptionResponseDto(false, "UNKNOWN_ERROR", "Technical error has occoured");
		}
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public SubscriptionResponseDto cancelSubscription(@RequestParam String eventUrl) {
		try {
			SubscriptionEvent event = fetchSubscriptionEventDetails(eventUrl);
			Assert.notNull(event, "Unable to parse Event");
			Assert.isTrue("SUBSCRIPTION_CANCEL".equals(event.getType()), "Incorrect Subscription Type.");
			subscriptionService.cancelSubscription(event);
			return new SubscriptionResponseDto(true);
		} catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException e) {
			LOGGER.error("Unable to process cancel subscription event due to: " + e.getMessage(), e);
			return new SubscriptionResponseDto(false, "UNAUTHORIZED", "Un-authorizes request received.");
		} catch (AccountNotFoundException e) {
			LOGGER.error("Unable to process cancel subscription as account for the company does not exist");
			return new SubscriptionResponseDto(false, "ACCOUNT_NOT_FOUND", "account for the company does not exist");
		} catch (IOException ioe) {
			LOGGER.error("Unable to process cancel subscription event due to: " + ioe.getMessage(), ioe);
			return new SubscriptionResponseDto(false, "UNKNOWN_ERROR", "Technical error has occoured");
		} catch (AccountAlreadyCancelledException e) {
			LOGGER.error("Unable to process cancel subscription as account for the company is already cancelled.");
			return new SubscriptionResponseDto(false, "UNKNOWN_ERROR", "account already cancelled.");
		}
	}

	private SubscriptionEvent fetchSubscriptionEventDetails(String eventUrl)
			throws ClientProtocolException, IOException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
		OAuthConsumer consumer = new DefaultOAuthConsumer("code-challenge--vinay-gupta-129744", "kw6HU6cw6F1TVNYt");
		URL url = new URL(eventUrl);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.addRequestProperty("Accept", MediaType.APPLICATION_JSON_VALUE);
		consumer.sign(request);
		request.connect();
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println("Response from appdirect: " + result);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(result.toString(), SubscriptionEvent.class);
	}
}
