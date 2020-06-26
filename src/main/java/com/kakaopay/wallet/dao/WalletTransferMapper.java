package com.kakaopay.wallet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cmm.entity.EgovMap;

/** @formatter:off */
/**
 * <pre>
 * WalletTransferMapper
 * </pre>
 *
 * @ClassName   : WalletTransferMapper.java
 * @Description : Sending Wallet Transaction Information data access layer
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
@Mapper
public interface WalletTransferMapper {

	/**
	 * Create a new record for WalletTransfer Bulletin
	 *
	 * @param vo
	 * @return int
	 * @throws Exception
	 */
	int insertWalletTransfer(EgovMap vo) throws Exception;

	/**
	 * Modify a selected record for WalletTransfer Bulletin
	 *
	 * @param vo
	 * @return int
	 * @throws Exception
	 */
	int updateWalletTransfer(EgovMap vo) throws Exception;

	/**
	 * Retrieve a selected records list for WalletTransfer Bulletin
	 *
	 * @param vo
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	List<Map<String, Object>> getWalletTransferList(EgovMap vo) throws Exception;

	/**
	 * Retrieve a selected records count for WalletTransfer Bulletin
	 *
	 * @param vo
	 * @return int
	 * @throws Exception
	 */
	int getWalletTransferCount(EgovMap vo) throws Exception;
}