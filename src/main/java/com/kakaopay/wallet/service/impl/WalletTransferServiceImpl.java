package com.kakaopay.wallet.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cmm.entity.EgovMap;
import com.cmm.util.NetUtil;
import com.kakaopay.wallet.dao.WalletTransferMapper;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferCTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferRTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferUTO;
import com.kakaopay.wallet.service.WalletTransferService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/** @formatter:off */
/**
 * <pre>
 * WalletTransferServiceImpl
 * </pre>
 *
 * @ClassName	: WalletTransferServiceImpl.java
 * @Description	: Sending Wallet Transaction Information service implementation layer
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
@Service("walletTransferService")
public class WalletTransferServiceImpl extends EgovAbstractServiceImpl implements WalletTransferService
{
	private static final Logger logger 	= LoggerFactory.getLogger(WalletTransferServiceImpl.class);

	@Resource(name="walletTransferMapper")
	private WalletTransferMapper walletTransferDAO;

	/**
	 * Create a new record for WalletTransfer Bulletin
	 *
	 * @param vo, map
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int insertWalletTransfer(WalletTransferCTO vo) throws Exception {
		return insertWalletTransfer(new EgovMap(NetUtil.toJson(vo)));
	}
	@Override
	public int insertWalletTransfer(EgovMap map) throws Exception {
		return walletTransferDAO.insertWalletTransfer(map);
	}

	/**
	 * Modify a selected record for WalletTransfer Bulletin
	 *
	 * @param vo, map
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int updateWalletTransfer(WalletTransferUTO vo) throws Exception {
		return updateWalletTransfer(new EgovMap(NetUtil.toJson(vo)));
	}
	@Override
	public int updateWalletTransfer(EgovMap map) throws Exception {
		return walletTransferDAO.updateWalletTransfer(map);
	}

	/**
	 *
	 * Retrieve a selected records list for WalletTransfer Bulletin
	 *
	 * @param vo, map
	 * @return List
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> getWalletTransferList(WalletTransferRTO vo) throws Exception {
		return getWalletTransferList(new EgovMap(NetUtil.toJson(vo)));
	}
	@Override
	public List<Map<String, Object>> getWalletTransferList(EgovMap map) throws Exception {
		return walletTransferDAO.getWalletTransferList(map);
	}

	/**
	 * Retrieve a selected records count for WalletTransfer Bulletin
	 *
	 * @param vo, map
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int getWalletTransferCount(WalletTransferRTO vo) throws Exception {
		return getWalletTransferCount(new EgovMap(NetUtil.toJson(vo)));
	}
	@Override
	public int getWalletTransferCount(EgovMap map) throws Exception {
		return walletTransferDAO.getWalletTransferCount(map);
	}
}