package com.kakaopay.wallet.model.wallet_transfer;

import java.io.Serializable;

import com.cmm.entity.SearchVO;

/** @formatter:off */
/**
 * <pre>
 * WalletTransferCTO
 * </pre>
 *
 * @ClassName   : WalletTransferCTO.java
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
public class WalletTransferCTO extends WalletTransferRTO implements Serializable {
	/*
	<form name="walletTransferCTO">

		<field property="transferNo" depends="required">
			<arg0 key="walletTransferCTO.transferNo" />
		</field>
		<field property="token" depends="required">
			<arg0 key="walletTransferCTO.token" />
		</field>
		<field property="roomId" depends="required">
			<arg0 key="walletTransferCTO.roomId" />
		</field>
		<field property="senderUserId" depends="required">
			<arg0 key="walletTransferCTO.senderUserId" />
		</field>
		<field property="amount" depends="required">
			<arg0 key="walletTransferCTO.amount" />
		</field>
		<field property="recipientCount" depends="required">
			<arg0 key="walletTransferCTO.recipientCount" />
		</field>
		<field property="regDate" depends="required">
			<arg0 key="walletTransferCTO.regDate" />
		</field>
		<field property="modDate" depends="required">
			<arg0 key="walletTransferCTO.modDate" />
		</field>
	</form>
	*/
}