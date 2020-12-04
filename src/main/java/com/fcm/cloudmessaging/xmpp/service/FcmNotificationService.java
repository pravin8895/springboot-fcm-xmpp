package com.fcm.cloudmessaging.xmpp.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.SSLSocketFactory;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class FcmNotificationService {

	@Autowired
	private Environment environment;

	public void notify(Map<String, Object> requestData) {
		String to = requestData.get("fcm_id").toString();
		String serverUserName = environment.getProperty("server_username");
		String server_key = environment.getProperty("server_key");
		ConnectionConfiguration config = createConnectionConfig();
		XMPPConnection connection = new XMPPConnection(config);
		try {
			connection.connect();
			connection.login(serverUserName, server_key);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		Map<String, Object> message = formNotificationPayload(requestData);
		Packet request = new FcmPacketExtension(JSONValue.toJSONString(message)).toPacket();
		connection.sendPacket(request);
	}

	private ConnectionConfiguration createConnectionConfig() {
		String hostUrl = environment.getProperty("host_url");
		int port = Integer.parseInt(environment.getProperty("port").toString());
		ConnectionConfiguration config = new ConnectionConfiguration(hostUrl, port);
		config.setSecurityMode(SecurityMode.enabled);
		config.setReconnectionAllowed(true);
		config.setRosterLoadedAtLogin(false);
		config.setSendPresence(false);
		config.setSocketFactory(SSLSocketFactory.getDefault());
		config.setDebuggerEnabled(true);
		return config;

	}

	private Map<String, Object> formNotificationPayload(Map<String, Object> requestData) {
		Map<String, Object> message = new HashMap<>();
		Map<String, Object> notificationPayload = new HashMap<>();
		message.put("to", requestData.get("fcm_id").toString());
		message.put("message_id", UUID.randomUUID().toString());
		notificationPayload.put("title", requestData.get("title").toString());
		notificationPayload.put("body", requestData.get("body").toString());
		message.put("notification", notificationPayload);
		message.put("delivery_receipt_requested", true);
		return message;
	}
}
