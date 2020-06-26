package com.kakaopay.wallet.model.wallet_dispense;

import java.io.Serializable;

/** @formatter:off */
/**
 * <pre>
 * WalletDispenseCTO
 * </pre>
 *
 * @ClassName   : WalletDispenseCTO.java
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
public class WalletDispenseCTO extends WalletDispenseRTO implements Serializable {
	/*
	<form name="walletDispenseCTO">

		<field property="dispenseNo" depends="required">
			<arg0 key="walletDispenseCTO.dispenseNo" />
		</field>
		<field property="transferNo" depends="required">
			<arg0 key="walletDispenseCTO.transferNo" />
		</field>
		<field property="token" depends="required">
			<arg0 key="walletDispenseCTO.token" />
		</field>
		<field property="roomId" depends="required">
			<arg0 key="walletDispenseCTO.roomId" />
		</field>
		<field property="senderUserId" depends="required">
			<arg0 key="walletDispenseCTO.senderUserId" />
		</field>
		<field property="amount" depends="required">
			<arg0 key="walletDispenseCTO.amount" />
		</field>
		<field property="recipientUserId" depends="required">
			<arg0 key="walletDispenseCTO.recipientUserId" />
		</field>
		<field property="regDate" depends="required">
			<arg0 key="walletDispenseCTO.regDate" />
		</field>
		<field property="modDate" depends="required">
			<arg0 key="walletDispenseCTO.modDate" />
		</field>
	</form>
	*/
}