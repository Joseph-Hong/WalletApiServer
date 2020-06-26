package com.kakaopay.wallet.model.wallet_dispense;

import java.io.Serializable;
import java.util.Date;

import com.cmm.entity.SearchVO;

/** @formatter:off */
/**
 * <pre>
 * WalletDispenseRTO
 * </pre>
 *
 * @ClassName   : WalletDispenseRTO.java
 * @Description : Receiving Wallet Transaction Information value object
 * @author      : Joseph.Hong
 * @since       : 2020.06.27
 * @version     : 1.0
 * @Modification Information
 * <pre>
 *	since			author			description
 *	===========		=============	===========================
 *  2020.06.27		Joseph.Hong		the first written
 * </pre>
 */
/** @formatter:on */
@SuppressWarnings("serial")
public class WalletDispenseRTO extends SearchVO implements Serializable {

	/**
	 * Auto increment distribution number
	 */
	private Integer dispenseNo;    //DISPENSE_NO() : BIGINT(20)

		/**
		 * @return the dispenseNo
		 */
		public Integer getDispenseNo() {
			return dispenseNo;
		}
		/**
		 * @param dispenseNo the dispenseNo to set
		 */
		public void setDispenseNo(Integer dispenseNo)  {
			this.dispenseNo = dispenseNo;
		}

	/**
	 * PK of transfer number(FK)
	 */
	private Integer transferNo;    //TRANSFER_NO() : BIGINT(20)

		/**
		 * @return the transferNo
		 */
		public Integer getTransferNo() {
			return transferNo;
		}
		/**
		 * @param transferNo the transferNo to set
		 */
		public void setTransferNo(Integer transferNo)  {
			this.transferNo = transferNo;
		}

	/**
	 * Unique token per 7 days
	 */
	private String token;    //TOKEN() : VARBINARY(3 Byte)

		/**
		 * @return the token
		 */
		public String getToken() {
			return token;
		}
		/**
		 * @param token the token to set
		 */
		public void setToken(String token)  {
			this.token = token;
		}

	/**
	 * chat room id
	 */
	private String roomId;    //ROOM_ID() : VARCHAR(255 Byte)

		/**
		 * @return the roomId
		 */
		public String getRoomId() {
			return roomId;
		}
		/**
		 * @param roomId the roomId to set
		 */
		public void setRoomId(String roomId)  {
			this.roomId = roomId;
		}

	/**
	 * Sending user id
	 */
	private Integer senderUserId;    //SENDER_USER_ID() : INT(10)

		/**
		 * @return the senderUserId
		 */
		public Integer getSenderUserId() {
			return senderUserId;
		}
		/**
		 * @param senderUserId the senderUserId to set
		 */
		public void setSenderUserId(Integer senderUserId)  {
			this.senderUserId = senderUserId;
		}

	/**
	 * Total dispensing amount
	 */
	private Double amount;    //AMOUNT() : DECIMAL(18)

		/**
		 * @return the amount
		 */
		public Double getAmount() {
			return amount;
		}
		/**
		 * @param amount the amount to set
		 */
		public void setAmount(Double amount)  {
			this.amount = amount;
		}

	/**
	 * Received user id
	 */
	private Integer recipientUserId;    //RECIPIENT_USER_ID() : INT(10)

		/**
		 * @return the recipientUserId
		 */
		public Integer getRecipientUserId() {
			return recipientUserId;
		}
		/**
		 * @param recipientUserId the recipientUserId to set
		 */
		public void setRecipientUserId(Integer recipientUserId)  {
			this.recipientUserId = recipientUserId;
		}

	/**
	 *
	 */
	private Date regDate;    //REG_DATE() : datetime

		/**
		 * @return the regDate
		 */
		public Date getRegDate() {
			return regDate;
		}
		/**
		 * @param regDate the regDate to set
		 */
		public void setRegDate(Date regDate)  {
			this.regDate = regDate;
		}

	/**
	 *
	 */
	private Date modDate;    //MOD_DATE() : datetime

		/**
		 * @return the modDate
		 */
		public Date getModDate() {
			return modDate;
		}
		/**
		 * @param modDate the modDate to set
		 */
		public void setModDate(Date modDate)  {
			this.modDate = modDate;
		}

	/*
	<form name="walletDispenseRTO">

		<field property="dispenseNo" depends="required">
			<arg0 key="walletDispenseRTO.dispenseNo" />
		</field>
		<field property="transferNo" depends="required">
			<arg0 key="walletDispenseRTO.transferNo" />
		</field>
		<field property="token" depends="required">
			<arg0 key="walletDispenseRTO.token" />
		</field>
		<field property="roomId" depends="required">
			<arg0 key="walletDispenseRTO.roomId" />
		</field>
		<field property="senderUserId" depends="required">
			<arg0 key="walletDispenseRTO.senderUserId" />
		</field>
		<field property="amount" depends="required">
			<arg0 key="walletDispenseRTO.amount" />
		</field>
		<field property="recipientCount" depends="required">
			<arg0 key="walletDispenseRTO.recipientCount" />
		</field>
		<field property="recipientUserId" depends="required">
			<arg0 key="walletDispenseRTO.recipientUserId" />
		</field>
		<field property="regDate" depends="required">
			<arg0 key="walletDispenseRTO.regDate" />
		</field>
		<field property="modDate" depends="required">
			<arg0 key="walletDispenseRTO.modDate" />
		</field>
	</form>
	*/
}