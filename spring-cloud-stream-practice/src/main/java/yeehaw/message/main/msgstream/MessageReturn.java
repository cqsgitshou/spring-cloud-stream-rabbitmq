package yeehaw.message.main.msgstream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
/**
 * 接收回调通道
 * @ClassName: MessageReturn 
 * @Description: TODO 
 * @author yangyihao
 * @date 2017年7月28日 上午9:15:56 
 *
 */
public interface MessageReturn {

	String PUSH_RETURN = "push_return";
	String BIND_RETURN = "bind_return";
	String SMS_RETURN = "sms_return";
	String DELAY_RETURN = "delay_return";
	
	@Input(MessageReturn.PUSH_RETURN)
	SubscribableChannel pushRes();
	
	@Input("dlx_push_return")
	SubscribableChannel DLXpushRes();
/*	
	String DLX_SMS_RETURN = "dlx_sms_return";
	@Input(DLX_SMS_RETURN)
	SubscribableChannel DLXsmsRes();*/
	
	
	@Input(MessageReturn.SMS_RETURN)
	SubscribableChannel smsRes();
	
	@Input(MessageReturn.DELAY_RETURN)
	SubscribableChannel delayTest();
	
	@Input(MessageReturn.BIND_RETURN)
	SubscribableChannel bindRes();
	
}
