package com.kakaopay.wallet.model.wallet_dispense;

import java.io.Serializable;

/** @formatter:off */
/**
 * <pre>
 * WalletDispenseUTO
 * </pre>
 *
 * @ClassName   : WalletDispenseUTO.java
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
public class WalletDispenseUTO extends WalletDispenseRTO implements Serializable {
	/*
	<form name="walletDispenseUTO">

		<field property="dispenseNo" depends="required">
			<arg0 key="walletDispenseUTO.dispenseNo" />
		</field>
		<field property="transferNo" depends="required">
			<arg0 key="walletDispenseUTO.transferNo" />
		</field>
		<field property="token" depends="required">
			<arg0 key="walletDispenseUTO.token" />
		</field>
		<field property="roomId" depends="required">
			<arg0 key="walletDispenseUTO.roomId" />
		</field>
		<field property="senderUserId" depends="required">
			<arg0 key="walletDispenseUTO.senderUserId" />
		</field>
		<field property="amount" depends="required">
			<arg0 key="walletDispenseUTO.amount" />
		</field>
		<field property="recipientUserId" depends="required">
			<arg0 key="walletDispenseUTO.recipientUserId" />
		</field>
		<field property="regDate" depends="required">
			<arg0 key="walletDispenseUTO.regDate" />
		</field>
		<field property="modDate" depends="required">
			<arg0 key="walletDispenseUTO.modDate" />
		</field>
	</form>
	*/
}