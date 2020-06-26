package com.kakaopay.wallet.model.wallet_transfer;

import java.io.Serializable;

/** @formatter:off */
/**
 * <pre>
 * WalletTransferUTO
 * </pre>
 *
 * @ClassName   : WalletTransferUTO.java
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
public class WalletTransferUTO extends WalletTransferRTO implements Serializable {
	/*
	<form name="walletTransferUTO">

		<field property="transferNo" depends="required">
			<arg0 key="walletTransferUTO.transferNo" />
		</field>
		<field property="token" depends="required">
			<arg0 key="walletTransferUTO.token" />
		</field>
		<field property="roomId" depends="required">
			<arg0 key="walletTransferUTO.roomId" />
		</field>
		<field property="senderUserId" depends="required">
			<arg0 key="walletTransferUTO.senderUserId" />
		</field>
		<field property="amount" depends="required">
			<arg0 key="walletTransferUTO.amount" />
		</field>
		<field property="recipientCount" depends="required">
			<arg0 key="walletTransferUTO.recipientCount" />
		</field>
		<field property="regDate" depends="required">
			<arg0 key="walletTransferUTO.regDate" />
		</field>
		<field property="modDate" depends="required">
			<arg0 key="walletTransferUTO.modDate" />
		</field>
	</form>
	*/
}