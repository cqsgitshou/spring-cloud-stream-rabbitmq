package yeehaw.message.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import team.union.tool.exception.BaseException;
import team.union.tool.response.ClientResponse;
import yeehaw.message.main.message.o.ChallengeMessage;
import yeehaw.message.main.message.o.IMessage;
import yeehaw.message.main.message.o.Terminal;
import yeehaw.message.main.message.o.TextMessage;
import yeehaw.message.main.msgstream.MessageClient;

@EnableBinding(value = { MessageClient.class })
@Component("messageSender")
public class MessageSenderImpl implements MessageSender {

	@Autowired
	MessageClient messageClient;

	@Autowired
	@Qualifier(MessageClient.PUSH)
	private MessageChannel push;

	@Autowired
	@Qualifier(MessageClient.BIND)
	private MessageChannel bind;

	@Autowired
	@Qualifier(MessageClient.DELAY)
	private MessageChannel challenge;

	@Autowired
	@Qualifier(MessageClient.SMS)
	private MessageChannel sms;

	@Override
	public ClientResponse bind(Terminal terminal) throws BaseException {
		try {
			messageClient.bind().send(MessageBuilder.withPayload(terminal).build());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException("访问消息中心失败。");
		}
		return ClientResponse.newInstance().ok().builder();
	}

	@Override
	public ClientResponse sendDelayTestMessage(String sn, String source, String cell, String text) throws BaseException {
		try {
			ChallengeMessage message = new ChallengeMessage(sn, source, cell, text);
			messageClient.delaytest().send(MessageBuilder.withPayload(message).setHeader("x-delay", 5000).build());
			// challenge.send(MessageBuilder.withPayload(message).build());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException("访问消息中心失败。");
		}
		return ClientResponse.newInstance().ok().builder();
	}

	@Override
	public ClientResponse sendSM(TextMessage message) throws BaseException{
		try {
			messageClient.sms().send(MessageBuilder.withPayload(message).build());
			// sms.send(MessageBuilder.withPayload(message).build());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException("访问消息中心失败。");
		}
		return ClientResponse.newInstance().ok().builder();
	}

	@Override
	public ClientResponse push(IMessage message) throws BaseException {
		try {
			messageClient.push().send(MessageBuilder.withPayload(message).build());
			// push.send(MessageBuilder.withPayload(message).build());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException("访问消息中心失败。");
		}
		return ClientResponse.newInstance().ok().builder();
	}

	@Override
	public void pushAsyn(IMessage message) throws BaseException {

	}

}
