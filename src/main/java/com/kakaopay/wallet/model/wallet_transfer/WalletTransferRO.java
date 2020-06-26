package com.kakaopay.wallet.model.wallet_transfer;

import java.beans.PropertyEditor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmm.entity.BaseRO;
import com.cmm.util.CommonUtil;
import com.cmm.util.NetUtil;

/** @formatter:off */
/**
 * <pre>
 * WalletTransferRO
 * </pre>
 *
 * @ClassName   : WalletTransferRO.java
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
public class WalletTransferRO extends BaseRO implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(WalletTransferRO.class);

	public WalletTransferRO(Map<String, Object> inputParam) {
		for (String key : inputParam.keySet()) {
			try {
				Field field = getClass().getDeclaredField(key);
				Object value = inputParam.get(key);

				if (field.getType() == String.class) {
					field.set(this, value);
					continue;
				}

				PropertyEditor propertyEditor = defaultEditors.get(field.getType());
				if (propertyEditor != null) {
					propertyEditor.setAsText(CommonUtil.nvl(value, "0"));
					field.set(this, propertyEditor.getValue());
				}

			} catch (NoSuchFieldException e) {
				continue;
			} catch (Exception e) {
				logger.error("{}", e.fillInStackTrace());
			}
		}
	}

	public static Map returnEntity(Map<String, Object> inputEntity) {
		Map entity = null;

		if (inputEntity != null) {
			entity = new HashMap();

			WalletTransferRO vo = new WalletTransferRO(inputEntity);
			entity = NetUtil.toJson(vo);
		}

		return entity;
	}

	public static List<WalletTransferRO> returnList(List<Map<String, Object>> inputList) {
		List<WalletTransferRO> list = null;

		if (inputList != null) {
			list = new ArrayList();

			for (Map entry : inputList) {
				WalletTransferRO vo = new WalletTransferRO(entry);
				list.add(vo);
			}
		}

		return list;
	}


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

}