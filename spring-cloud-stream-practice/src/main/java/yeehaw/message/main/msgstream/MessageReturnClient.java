package yeehaw.message.main.msgstream;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import team.union.tool.json.JsonUtils;
import yeehaw.message.main.MessageSender;
import yeehaw.message.main.message.o.BaseMessage;


@EnableBinding(MessageClient.class)
public class MessageReturnClient {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MessageSender messageSender;
	@Autowired
	MessageClient messageClient;

	//** 死信队列处理1 消息消费失败，进入死信队列，再发往延迟队列再处理  start ** // 
	@StreamListener(MessageClient.PUSH_RETURN)
	public void pushRecevier(Object message) throws Exception {
		logger.info("\r\n push回调收到： " + JsonUtils.to(message));
		throw new Exception("test failure");
	}
	
	 @RabbitListener(queues  = "push_exc.push.dlq")
	public void DLXpushRecevier(Message bytes) throws Exception {
		String message = new String(bytes.getBody());
		logger.info("\r\n 接收push发送失败的消息:： " +message);
		logger.info("\r\n 讲失败消息发完延迟队列 5秒以后处理:： " +message);
		JsonUtils.from(message, Map.class);
		messageClient.delaytest().send(MessageBuilder.withPayload(message).setHeader("x-delay", 5000).build());
	}
	

	/**
	 * 
	 * @param payload
	 */
	@StreamListener(MessageClient.DELAY_RETURN)
	public void delayTestRecevier(Object payload) {
		logger.info("\r\n 延迟队列消息回调收到： " + payload);
	}
	
	//** 死信队列处理1 end ** // 
	
	
	//** 死信队列处理2 start ** // 
	// @RabbitListener(bindings = {@QueueBinding(value = @Queue(value = "push_exc.push.dlq"), exchange = @Exchange(value = "DLX"), key = "push_exc.push")})
	// @RabbitListener(queues  = "push_exc.push.dlq")
	public void rabDLXpushRecevier(Message bytes) throws Exception {
		logger.info("\r\n push回调收到 DLX:： "+new String(bytes.getBody()));
		
	} 
	
	// @RabbitListener(queues  = "push_exc.push.dlq")
	public void rabDLXpushRecevier2(@Payload BaseMessage body, @Headers Map<String,Object> headers) throws Exception {
		logger.info("\r\n push回调收到 DLX body:： "+JsonUtils.to(body));
		logger.info("\r\n push回调收到 DLX headers:： "+headers);
	}
	
	

	@StreamListener(MessageClient.SMS_RETURN)
	public void smsRecevier(Object payload) throws Exception {
		logger.info("\r\n sms回调收到： " + payload);
		throw new Exception("smsRecevier test failure");
	}
	/*@RabbitListener(queues  = "dlx_sms_return")
	public void dlxsmsRecevier(Object payload) {
		logger.info("\r\n dlxsmsRecevier sms回调收到： " + payload);
	}*/
	@RabbitListener(queues  = "sms_exc.sms.dlq")
	public void dlxsmsRecevier(Message bytes) {
		logger.info("\r\n dlxsmsRecevier sms回调收到： " + new String(bytes.getBody()));
	}
	
	/*
	 * 自定义队列名称    实验失败
	 */
	/*@RabbitListener(queues  = "queuq.sms_exc_dlq")
	public void dlxsmsRecevier(Message bytes) {
		logger.info("\r\n dlxsmsRecevier sms回调收到： " + new String(bytes.getBody()));
	}*/
	
	@StreamListener(MessageClient.BIND_RETURN)
	public void bindRecevier(Object payload) {
		logger.info("\r\n bind回调收到： " + payload);
	}



}
