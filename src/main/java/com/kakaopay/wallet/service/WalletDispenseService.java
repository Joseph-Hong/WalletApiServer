package com.kakaopay.wallet.service;

import java.util.List;
import java.util.Map;

import com.cmm.entity.EgovMap;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseCTO;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseRTO;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseUTO;

/** @formatter:off */
/**
 * <pre>
 * WalletDispenseService
 * </pre>
 *
 * @ClassName	: WalletDispenseService.java
 * @Description	: Receiving Wallet Transaction Information service layer
 * @author		: Joseph.Hong
 * @since		: 2020.06.27
 * @version		: 1.0
 * @Modification Information
 * <pre>
 *	since			author			description
 *	===========		=============	===========================
 *	2020.06.27		Joseph.Hong		the first written
 * </pre>
 */
/** @formatter:on */
public interface WalletDispenseService
{
	public int dispense(WalletDispenseUTO vo) throws Exception;

	/**
	 * Create a new record for Receiving Wallet Transaction Information
	 *
	 * @param vo, map
	 * @return int
	 * @throws Exception
	 */
	public int insertWalletDispense(WalletDispenseCTO vo) throws Exception;
	public int insertWalletDispense(EgovMap map) throws Exception;

	/**
	 * Modify a selected record for Receiving Wallet Transaction Information
	 *
	 * @param vo, map
	 * @return int
	 * @throws Exception
	 */
	public int updateWalletDispense(WalletDispenseUTO vo) throws Exception;
	public int updateWalletDispense(EgovMap map) throws Exception;

	/**
	 *
	 * Retrieve a selected records list for Receiving Wallet Transaction Information
	 *
	 * @param vo, map
	 * @return List
	 * @throws Exception
	 */
	public List<Map<String, Object>> getWalletDispenseList(WalletDispenseRTO vo) throws Exception;
	public List<Map<String, Object>> getWalletDispenseList(EgovMap map) throws Exception;

	/**
	 * Retrieve a selected records count for Receiving Wallet Transaction Information
	 *
	 * @param vo, map
	 * @return int
	 * @throws Exception
	 */
	public int getWalletDispenseCount(WalletDispenseRTO vo) throws Exception;
	public int getWalletDispenseCount(EgovMap map) throws Exception;
}