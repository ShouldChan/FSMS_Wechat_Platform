package org.cxj.pojo;

/**
 * ��ʱ��ά���pojo��
 * @author Administrator->cxj
 * @date 2016/07/04
 *
 */
public class WeixinQRCode {

	//��ȡ�Ķ�ά��ticket
	private String ticket;
	//��ά�����Чʱ�� ��λΪ�� ��󲻳���1800s
	private int expireSeconds;
	
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpireSeconds() {
		return expireSeconds;
	}
	public void setExpireSeconds(int expireSeconds) {
		this.expireSeconds = expireSeconds;
	}
	
}
