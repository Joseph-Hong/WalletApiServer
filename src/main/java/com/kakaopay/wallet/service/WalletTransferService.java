package com.kakaopay.wallet.service;

import java.util.List;
import java.util.Map;

import com.cmm.entity.EgovMap;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferCTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferRTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferUTO;

/** @formatter:off */
/**
 * <pre>
 * WalletTransferService
 * </pre>
 *
 * @ClassName	: WalletTransferService.java
 * @Description	: Sending Wallet Transaction Information service layer
 * @author		: Joseph.Hong
 * @since		: 2020.06.26
 * @version		: 1.0
 * @Modification Information
 * <pre>
 *	since			author			description
 *	===========		=============	===========================
 *	2020.06.26		Joseph.Hong		the first written
 * </pre>
 */
/** @formatter:on */
public interface WalletTransferService
{
	public void check(WalletTransferRTO vo) throws Exception;

	public void transfer(WalletTransferCTO vo) throws Exception;

	/**
	 * Create a new record for Sending Wallet Transaction Information
	 *
	 * @param vo, map
	 * @return int
	 * @throws Exception
	 */
	public int insertWalletTransfer(WalletTransferCTO vo) throws Exception;
	public int insertWalletTransfer(EgovMap map) throws Exception;

	/**
	 * Modify a selected record for Sending Wallet Transaction Information
	 *
	 * @param vo, map
	 * @return int
	 * @throws Exception
	 */
	public int updateWalletTransfer(WalletTransferUTO vo) throws Exception;
	public int updateWalletTransfer(EgovMap map) throws Exception;

	/**
	 *
	 * Retrieve a selected records list for Sending Wallet Transaction Information
	 *
	 * @param vo, map
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> getWalletTransferList(WalletTransferRTO vo) throws Exception;
	public List<Map<String, Object>> getWalletTransferList(EgovMap map) throws Exception;

	/**
	 * Retrieve a selected records count for Sending Wallet Transaction Information
	 *
	 * @param vo, map
	 * @return int
	 * @throws Exception
	 */
	public int getWalletTransferCount(WalletTransferRTO vo) throws Exception;
	public int getWalletTransferCount(EgovMap map) throws Exception;
}