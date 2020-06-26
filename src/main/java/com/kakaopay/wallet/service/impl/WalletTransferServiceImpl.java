package com.kakaopay.wallet.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cmm.entity.EgovMap;
import com.cmm.util.CommonUtil;
import com.cmm.util.NetUtil;
import com.kakaopay.wallet.dao.WalletTransferMapper;
import com.kakaopay.wallet.exception.BizException;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseCTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferCTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferRTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferUTO;
import com.kakaopay.wallet.service.WalletDispenseService;
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

	@Resource(name = "walletDispenseService")
	protected WalletDispenseService walletDispenseService;

	@Transactional (value = TxType.REQUIRED, rollbackOn = Throwable.class)
	@Override
	public int transfer(WalletTransferCTO vo) throws Exception {

		// Insert WalletTransfer data
		if(insertWalletTransfer(vo) > 0) {
			throw new BizException("BZT_TRANSFER_INSERT_501_FAILED", "501", null, 500);
		}

		// Insert WalletDispense data
		WalletDispenseCTO dispenseCTO = null;
		Double dispenseAmountTotal = 0.0;

		for(int i = 0 ; i < vo.getRecipientCount() ; i++) {
			dispenseCTO = new WalletDispenseCTO();
			dispenseCTO.setTransferNo(vo.getTransferNo());
			dispenseCTO.setToken(vo.getToken());
			dispenseCTO.setRoomId(vo.getRoomId());
			dispenseCTO.setSenderUserId(vo.getSenderUserId());

			double dispenseAmount = 0.0;
			int remainder = vo.getAmount().intValue() - dispenseAmountTotal.intValue();

			if(i == vo.getRecipientCount() -1) {
				//The last one
				dispenseAmount = remainder;
			} else {
				dispenseAmount = CommonUtil.randomNumeric(1, remainder - (vo.getRecipientCount() - i));
			}

			dispenseAmountTotal += dispenseAmount;
			dispenseCTO.setAmount(dispenseAmount);

			if(walletDispenseService.insertWalletDispense(dispenseCTO) != 1) {
				throw new BizException("BZT_TRANSFER_INSERT_502_FAILED", "501", null, 500);
			}
		}


		return 1;
	}

	/**
	 * Create a new record for WalletTransfer Bulletin
	 *
	 * @param vo, map
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int insertWalletTransfer(WalletTransferCTO vo) throws Exception {
		EgovMap map = new EgovMap(NetUtil.toJson(vo));
		int result = insertWalletTransfer(map);
		vo.setTransferNo(CommonUtil.nvl(map.get("transferNo"), 0));
		return result;
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