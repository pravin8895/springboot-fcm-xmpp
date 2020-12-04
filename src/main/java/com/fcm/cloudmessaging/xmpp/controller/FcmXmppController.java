package com.fcm.cloudmessaging.xmpp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fcm.cloudmessaging.xmpp.service.FcmNotificationService;

@RestController
@RequestMapping(value = "/fcmxmpp")
public class FcmXmppController {

	@Autowired
	private FcmNotificationService fcmNotificationService;

	@PostMapping(value = "/notifyByFcm", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void notifyByFcm(Map<String, Object> request) {
		fcmNotificationService.notify(request);

	}

}
