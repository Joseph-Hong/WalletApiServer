package com.kakaopay.wallet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cmm.entity.EgovMap;

/** @formatter:off */
/**
 * <pre>
 * WalletDispenseMapper
 * </pre>
 *
 * @ClassName   : WalletDispenseMapper.java
 * @Description : Receiving Wallet Transaction Information data access layer
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
@Mapper
public interface WalletDispenseMapper {

	/**
	 * Create a new record for WalletDispense Bulletin
	 *
	 * @param vo
	 * @return int
	 * @throws Exception
	 */
	int insertWalletDispense(EgovMap vo) throws Exception;

	/**
	 * Modify a selected record for WalletDispense Bulletin
	 *
	 * @param vo
	 * @return int
	 * @throws Exception
	 */
	int updateWalletDispense(EgovMap vo) throws Exception;

	/**
	 * Retrieve a selected records list for WalletDispense Bulletin
	 *
	 * @param vo
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	List<Map<String, Object>> getWalletDispenseList(EgovMap vo) throws Exception;

	/**
	 * Retrieve a selected records count for WalletDispense Bulletin
	 *
	 * @param vo
	 * @return int
	 * @throws Exception
	 */
	int getWalletDispenseCount(EgovMap vo) throws Exception;
}