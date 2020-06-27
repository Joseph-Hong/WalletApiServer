package com.kakaopay.wallet.model.wallet_transfer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cmm.entity.SearchVO;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseRO;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseRTO;

/** @formatter:off */
/**
 * <pre>
 * WalletTransferRTO
 * </pre>
 *
 * @ClassName   : WalletTransferRTO.java
 * @Description : Sending Wallet Transaction Information value object
 * @author      : Joseph.Hong
 * @since       : 2020.06.26
 * @version     : 1.0
 * @Modification Information
 * <pre>
 *	since			author			description
 *	===========		=============	===========================
 *  2020.06.26		Joseph.Hong		the first written
 * </pre>
 */
/** @formatter:on */
@SuppressWarnings("serial")
public class WalletTransferRTO extends SearchVO implements Serializable {

	/**
	 * Auto increment transfer number
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
	private String token;    //TOKEN() : BINARY(3 Byte)

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
	 * Count for distribution
	 */
	private Integer recipientCount;    //RECIPIENT_COUNT() : INT(10)

		/**
		 * @return the recipientCount
		 */
		public Integer getRecipientCount() {
			return recipientCount;
		}
		/**
		 * @param recipientCount the recipientCount to set
		 */
		public void setRecipientCount(Integer recipientCount)  {
			this.recipientCount = recipientCount;
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

	private List<WalletDispenseRO> walletDispenses;

		public List<WalletDispenseRO> getWalletDispenses() {
			return walletDispenses;
		}
		public void setWalletDispenses(List<Map<String, Object>> walletDispenseList) {
			this.walletDispenses = WalletDispenseRO.returnList(walletDispenseList);
		}

	/*
	<form name="walletTransferRTO">

		<field property="transferNo" depends="required">
			<arg0 key="walletTransferRTO.transferNo" />
		</field>
		<field property="token" depends="required">
			<arg0 key="walletTransferRTO.token" />
		</field>
		<field property="roomId" depends="required">
			<arg0 key="walletTransferRTO.roomId" />
		</field>
		<field property="senderUserId" depends="required">
			<arg0 key="walletTransferRTO.senderUserId" />
		</field>
		<field property="amount" depends="required">
			<arg0 key="walletTransferRTO.amount" />
		</field>
		<field property="recipientCount" depends="required">
			<arg0 key="walletTransferRTO.recipientCount" />
		</field>
		<field property="regDate" depends="required">
			<arg0 key="walletTransferRTO.regDate" />
		</field>
		<field property="modDate" depends="required">
			<arg0 key="walletTransferRTO.modDate" />
		</field>
	</form>
	*/
}