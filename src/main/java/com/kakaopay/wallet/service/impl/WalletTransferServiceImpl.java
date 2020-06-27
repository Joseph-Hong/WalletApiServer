package com.kakaopay.wallet.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cmm.entity.EgovMap;
import com.cmm.util.CommonUtil;
import com.cmm.util.NetUtil;
import com.kakaopay.wallet.dao.WalletTransferMapper;
import com.kakaopay.wallet.exception.BizException;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseCTO;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseRTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferCTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferRO;
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

	@Override
	public void check(WalletTransferRTO vo) throws Exception {

		//[Step-10] Retrieve WalletTransfer data
		WalletTransferRTO walletTransferRTO = new WalletTransferRTO();
		walletTransferRTO.setRoomId(vo.getRoomId());
		walletTransferRTO.setSenderUserId(vo.getSenderUserId());
		walletTransferRTO.setToken(vo.getToken());
		walletTransferRTO.setRecordCountPerPage(-1);

		List<Map<String, Object>> walletTransferList = getWalletTransferList(walletTransferRTO);

		//[Step-20] Validate the input parameter
		//210) 뿌린 사람 자신만 조회를 할 수 있습니다. 다른사람의 뿌리기건이나 유효하지 않은 token에 대해서는 조회 실패 응답이 내려가야 합니다.
		if(CommonUtil.isEmpty(walletTransferList)) {
			throw new BizException("BZT_CHECK_NOT_FOUND_501_FAILED", "501", null, 500);
		}

		Map<String, Object> walletTransfer = walletTransferList.get(0);

		//211) 뿌린 건에 대한 조회는 7일 동안 할 수 있습니다.
		LocalDateTime nowDT = LocalDateTime.now();
		String then = CommonUtil.nvl(walletTransfer.get("regDate")).replaceAll(" ", "T");
		LocalDateTime thenDT = new LocalDateTime().parse(then);
		int diffSeconds = Seconds.secondsBetween(thenDT, nowDT).getSeconds();

		if(diffSeconds > (60 * 60 * 24 * 7)) {
			throw new BizException("BZT_CHECK_TIME_OVER_506_FAILED", "506", null, 500);
		}

		//[Step-30] Retrieve WalletTransfer, list of WalletDispense data
		//token에 해당하는 뿌리기 건의 현재 상태를 응답값으로 내려줍니다. 현재상태는 다음의 정보를 포함합니다.
		//뿌린 시각, 뿌린 금액, 받기 완료된 금액, 받기 완료된 정보 ([받은 금액, 받은 사용자 아이디] 리스트)
		WalletDispenseRTO walletDispenseVO = new WalletDispenseRTO();
		walletDispenseVO.setTransferNo(CommonUtil.nvl(walletTransfer.get("transferNo"), 0));
		walletDispenseVO.setSenderUserId(vo.getSenderUserId());
		walletDispenseVO.setSearchCondition("NOT_NULL");
		walletDispenseVO.setSearchKeyword("RECIPIENT_USER_ID");
		walletDispenseVO.setPageSize(-1);

		List<Map<String, Object>> walletDispenseList = walletDispenseService.getWalletDispenseList(walletDispenseVO);

		if(CommonUtil.isEmpty(walletDispenseList)) {
			throw new BizException("BZT_CHECK_NOT_FOUND_RESPONSE_502_FAILED", "502", null, 500);
		}

		vo.setWalletDispenses(walletDispenseList);

		WalletTransferRO walletTransferRO = new WalletTransferRO(walletTransfer);
		vo.setRegDate(walletTransferRO.getRegDate());
		vo.setAmount(walletTransferRO.getAmount());

		double dispenseTotalAmount = 0.0;

		for(Map<String, Object> walletDispense : walletDispenseList) {
			dispenseTotalAmount += CommonUtil.nvl(walletDispense.get("amount"), 0.0);
		}
		vo.setOutput(CommonUtil.nvl(dispenseTotalAmount));
	}

	@Transactional (value = TxType.REQUIRED, rollbackOn = Throwable.class)
	@Override
	public void transfer(WalletTransferCTO vo) throws Exception {

		//[Step-10] Insert WalletTransfer data
		if(insertWalletTransfer(vo) != 1) {
			throw new BizException("BZT_TRANSFER_INSERT_501_FAILED", "501", null, 500);
		}

		//[Step-20] Insert WalletDispense data
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