package com.kakaopay.wallet.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cmm.entity.EgovMap;
import com.cmm.util.CommonUtil;
import com.cmm.util.NetUtil;
import com.kakaopay.wallet.dao.WalletDispenseMapper;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseCTO;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseRTO;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseUTO;
import com.kakaopay.wallet.service.WalletDispenseService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/** @formatter:off */
/**
 * <pre>
 * WalletDispenseServiceImpl
 * </pre>
 *
 * @ClassName	: WalletDispenseServiceImpl.java
 * @Description	: Receiving Wallet Transaction Information service implementation layer
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
@Service("walletDispenseService")
public class WalletDispenseServiceImpl extends EgovAbstractServiceImpl implements WalletDispenseService
{
	private static final Logger logger 	= LoggerFactory.getLogger(WalletDispenseServiceImpl.class);

	@Resource(name="walletDispenseMapper")
	private WalletDispenseMapper walletDispenseDAO;

	/**
	 * Create a new record for WalletDispense Bulletin
	 *
	 * @param vo, map
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int insertWalletDispense(WalletDispenseCTO vo) throws Exception {
		EgovMap map = new EgovMap(NetUtil.toJson(vo));
		int result = insertWalletDispense(map);
		vo.setDispenseNo(CommonUtil.nvl(map.get("dispenseNo"), 0));
		return result;
	}
	@Override
	public int insertWalletDispense(EgovMap map) throws Exception {
		return walletDispenseDAO.insertWalletDispense(map);
	}

	/**
	 * Modify a selected record for WalletDispense Bulletin
	 *
	 * @param vo, map
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int updateWalletDispense(WalletDispenseUTO vo) throws Exception {
		return updateWalletDispense(new EgovMap(NetUtil.toJson(vo)));
	}
	@Override
	public int updateWalletDispense(EgovMap map) throws Exception {
		return walletDispenseDAO.updateWalletDispense(map);
	}

	/**
	 *
	 * Retrieve a selected records list for WalletDispense Bulletin
	 *
	 * @param vo, map
	 * @return List
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> getWalletDispenseList(WalletDispenseRTO vo) throws Exception {
		return getWalletDispenseList(new EgovMap(NetUtil.toJson(vo)));
	}
	@Override
	public List<Map<String, Object>> getWalletDispenseList(EgovMap map) throws Exception {
		return walletDispenseDAO.getWalletDispenseList(map);
	}

	/**
	 * Retrieve a selected records count for WalletDispense Bulletin
	 *
	 * @param vo, map
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int getWalletDispenseCount(WalletDispenseRTO vo) throws Exception {
		return getWalletDispenseCount(new EgovMap(NetUtil.toJson(vo)));
	}
	@Override
	public int getWalletDispenseCount(EgovMap map) throws Exception {
		return walletDispenseDAO.getWalletDispenseCount(map);
	}
}