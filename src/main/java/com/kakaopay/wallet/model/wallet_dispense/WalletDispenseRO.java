package com.kakaopay.wallet.model.wallet_dispense;

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
 * WalletDispenseRO
 * </pre>
 *
 * @ClassName   : WalletDispenseRO.java
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
public class WalletDispenseRO extends BaseRO implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(WalletDispenseRO.class);

	public WalletDispenseRO(Map<String, Object> inputParam) {
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
					//logger.debug(">>> key : {}, value : {}", key, value);
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

			WalletDispenseRO vo = new WalletDispenseRO(inputEntity);
			entity = NetUtil.toJson(vo);
		}

		return entity;
	}

	public static List<WalletDispenseRO> returnList(List<Map<String, Object>> inputList) {
		List<WalletDispenseRO> list = null;

		if (inputList != null) {
			list = new ArrayList();

			for (Map entry : inputList) {
				WalletDispenseRO vo = new WalletDispenseRO(entry);
				list.add(vo);
			}
		}

		return list;
	}


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

}