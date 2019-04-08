package yeehaw.message.main.msgstream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
/**
 * 发送通道
 * @ClassName: MessageOut 
 * @Description: TODO 
 * @author yangyihao
 * @date 2017年7月28日 上午9:15:43 
 *
 */
public interface MessageOut {
	
	String PUSH = "push_sender";
	String SMS = "sms_sender";
	String DELAY = "delay_sender";
	String BIND = "bind_sender";
	
	@Output(MessageOut.PUSH)
	MessageChannel push();
	
	@Output(MessageOut.SMS)
	MessageChannel sms();

	@Output(MessageOut.DELAY)
	MessageChannel delaytest();

	@Output(MessageOut.BIND)
	MessageChannel bind();
}
