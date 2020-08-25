package com.zenvia.sdk.examples.echobot;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zenvia.api.sdk.client.Channel;
import com.zenvia.api.sdk.client.apache.Client;
import com.zenvia.api.sdk.contents.Content;
import com.zenvia.api.sdk.contents.ContentType;
import com.zenvia.api.sdk.contents.TextContent;
import com.zenvia.api.sdk.webhook.MessageEvent;
import com.zenvia.api.sdk.webhook.MessageEventCallback;

@Component
public class MessageHandler implements MessageEventCallback {

	@Autowired
	private Client zenviaClient;

	@Override
	public void onMessageEvent(MessageEvent message) {
		Channel channel = zenviaClient.getChannel(message.channel);
		String textMessage = message.message.contents
			.stream()
			.filter(m ->  ContentType.text.equals(m.type) )
			.map(m -> m.ofText().text)
			.collect(Collectors.joining(" "));
		Content content = new TextContent(textMessage);
		channel.sendMessage(message.message.to, message.message.from, content);
	}

}
