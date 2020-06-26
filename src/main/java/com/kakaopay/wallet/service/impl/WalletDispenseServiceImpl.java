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
import com.kakaopay.wallet.dao.WalletDispenseMapper;
import com.kakaopay.wallet.exception.BizException;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseCTO;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseRTO;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseUTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferRTO;
import com.kakaopay.wallet.service.WalletDispenseService;
import com.kakaopay.wallet.service.WalletTransferService;

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

	@Resource(name = "walletTransferService")
	protected WalletTransferService walletTransferService;

	@Transactional (value = TxType.REQUIRED, rollbackOn = Throwable.class)
	@Override
	public int dispense(WalletDispenseUTO vo) throws Exception {

		//Select WalletTransfer data by transaction lock
		WalletTransferRTO walletTransferRTO = new WalletTransferRTO();
		walletTransferRTO.setRoomId(vo.getRoomId());
		walletTransferRTO.setToken(vo.getToken());
		walletTransferRTO.setRecordCountPerPage(-1);
		walletTransferRTO.setSearchCondition("FOR_UPDATE");

		List<Map<String, Object>> walletTransferList = walletTransferService.getWalletTransferList(walletTransferRTO);

		//0) 뿌리기가 호출된 대화방과 동일한 대화방에 속한 사용자만이 받을 수 있습니다
		if(CommonUtil.isEmpty(walletTransferList)) {
			throw new BizException("BZT_DISPENSE_NOT_FOUND_501_FAILED", "501", null, 500);
		}

		Map<String, Object> walletTransfer = walletTransferList.get(0);

		// Validate the input parameter

		//1) 자신이 뿌리기한 건은 자신이 받을 수 없습니다
		if(vo.getRecipientUserId().equals(walletTransfer.get("senderUserId"))) {
			throw new BizException("BZT_DISPENSE_ACCESS_VIOLATION_502_FAILED", "502", null, 500);
		}

		//2) 뿌리기 당 한 사용자는 한번만 받을 수 있습니다.
		WalletDispenseRTO walletDispenseVO = new WalletDispenseRTO();
		walletDispenseVO.setTransferNo(CommonUtil.nvl(walletTransfer.get("transferNo"), 0));
		walletDispenseVO.setRecipientUserId(vo.getRecipientUserId());

		List<Map<String, Object>> walletDispenseList = getWalletDispenseList(walletDispenseVO);

		if(CommonUtil.isNotEmpty(walletDispenseList)) {
			throw new BizException("BZT_DISPENSE_DUPLICATED_RECIPIENT_503_FAILED", "503", null, 500);
		}

		// Update WalletDispense data - API를 호출한 사용자에게 할당하고
		WalletDispenseUTO walletDispenseUTO = new WalletDispenseUTO();
		walletDispenseUTO.setRecipientUserId(vo.getRecipientUserId());
		walletDispenseUTO.setSearchCondition("DISTRIBUTION_CONDITION");
		walletDispenseUTO.setSearchKeyword(CommonUtil.nvl(walletTransfer.get("transferNo"), "NO_TRANSFER_NO"));

		if(updateWalletDispense(walletDispenseUTO) != 1) {
			throw new BizException("BZT_DISPENSE_UPDATE_504_FAILED", "504", null, 500);
		}

		// Retrieve the result - 그 금액을 응답값으로 내려줍니다.
		walletDispenseVO = new WalletDispenseRTO();
		walletDispenseVO.setTransferNo(CommonUtil.nvl(walletTransfer.get("transferNo"), 0));
		walletDispenseVO.setRecipientUserId(vo.getRecipientUserId());

		walletDispenseList = getWalletDispenseList(walletDispenseVO);

		if(CommonUtil.isEmpty(walletDispenseList)) {
			throw new BizException("BZT_DISPENSE_NOT_FOUND_505_FAILED", "505", null, 500);
		}

		// Return the amount
		Map<String, Object> walletDispense =  walletDispenseList.get(0);
		vo.setAmount(CommonUtil.nvl(walletDispense.get("amount"), 0.0));

		return 1;
	}

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