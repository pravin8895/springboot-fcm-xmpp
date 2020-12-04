package com.fcm.cloudmessaging.xmpp.service;


import org.jivesoftware.smack.packet.DefaultPacketExtension;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

/**
 * XMPP Packet Extension for FCM Cloud Connection Server
 */
public class FcmPacketExtension extends DefaultPacketExtension {

	private String json;

	public FcmPacketExtension(String json) {
		super("gcm", "google:mobile:data");
		this.json = json;
	}

	public String getJson() {
		return json;
	}

	@Override
	public String toXML() {
		return String.format("<%s xmlns=\"%s\">%s</%s>", "gcm", "google:mobile:data", json,
				"gcm");
	}

	public Packet toPacket() {
		Message message = new Message();
		message.addExtension(this);
		return message;
	}
}
