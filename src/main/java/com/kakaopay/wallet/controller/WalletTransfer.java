package com.kakaopay.wallet.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.cmm.entity.EgovMap;
import com.cmm.entity.ResultVO;
import com.cmm.util.CommonUtil;
import com.cmm.util.NetUtil;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferCTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferRO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferRTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferUTO;
import com.kakaopay.wallet.service.WalletTransferService;

/** @formatter:off */
/**
 * <pre>
 * WalletTransfer
 * </pre>
 *
 * @ClassName	: WalletTransfer.java
 * @Description	: Sending Wallet Transaction Information
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
@Controller
@RequestMapping("/wallet")
public class WalletTransfer extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(WalletTransfer.class);

	@Resource(name = "walletTransferService")
	protected WalletTransferService walletTransferService;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;

	/**
	 * Retrieve a selected record list for Sending Wallet Transaction Information
	 */
	@RequestMapping(value = { "/v1/wallet_transfer/list" },
			method = { RequestMethod.GET },
	        produces={ MediaType.APPLICATION_JSON_VALUE }
	)
	public String getWalletTransferList(@ModelAttribute("walletTransferRTO") WalletTransferRTO walletTransferVO
		, BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		model.addAttribute("walletTransferRTO", null);
		ResultVO resultVO = new ResultVO();

		logger.debug("walletTransferVO : {}", walletTransferVO.serialize());

		// Check the input validation for entity
		beanValidator.validate(walletTransferVO, bindingResult);

		if (bindingResult.hasErrors()) {
			resultVO.setCode(400)
			.setMessage("BZT_WALLETTRANSFER_LIST_400_FAIL")
			.setData(getErrorMessage(bindingResult))
			.toModel(model);

			return "jsonView";
		}

		// Retrieve WalletTransfer data
		int pageSize = walletTransferVO.getPageSize();
		List<Map<String, Object>> walletTransferList = null;
		int totalCnt = -1;

		if (pageSize > -1) {
			// Retrieve a total count of getWalletTransferList
			totalCnt = walletTransferService.getWalletTransferCount(walletTransferVO);
			setPaginationInfo(walletTransferVO, totalCnt);
		}

		if (totalCnt != 0) {
			// Retrieve the data of getWalletTransferList
			walletTransferList = walletTransferService.getWalletTransferList(walletTransferVO);
		}

		// Return the result model
		resultVO.setCode(200)
		.setMessage("BZT_WALLETTRANSFER_LIST_200_SUCCESS")
		.setData(WalletTransferRO.returnList(walletTransferList))
		.toModel(model);

		return "jsonView";
	}

	/**
	 * Create a new record for Sending Wallet Transaction Information
	 */
	@RequestMapping(value = { "/v1/wallet_transfer/insert" },
			method = { RequestMethod.POST },
	        consumes={ MediaType.APPLICATION_JSON_VALUE },
	        produces={ MediaType.APPLICATION_JSON_VALUE }
	)
	public String insertWalletTransfer(@RequestBody WalletTransferCTO walletTransferVO
		, BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResultVO resultVO = new ResultVO();

		// Check the input validation for entity
		beanValidator.validate(walletTransferVO, bindingResult);

		if (bindingResult.hasErrors()) {
			resultVO.setCode(400)
			.setMessage("BZT_WALLETTRANSFER_INSERT_400_FAIL")
			.setData(getErrorMessage(bindingResult))
			.toModel(model);

			return "jsonView";
		}

		// Bind the request input to VO
		EgovMap entityMap = new EgovMap(NetUtil.toJson(walletTransferVO));
		logger.trace("Bind the request input to VO :{}", new Object[] { NetUtil.toJson(entityMap) });

		// Insert WalletTransfer data
		int result = walletTransferService.insertWalletTransfer(entityMap);

		if (result < 1) {
			resultVO.setCode(501)
			.setMessage("BZT_WALLETTRANSFER_INSERT_501_FAIL")
			.setData(result)
			.toModel(model);

			return "jsonView";
		}

		// Return the result model
		resultVO.setCode(200)
		.setMessage("BZT_WALLETTRANSFER_INSERT_200_SUCCESS")
		.setData(result)
		.toModel(model);

		return "jsonView";
	}

	/**
	 * Modify a selected record for Sending Wallet Transaction Information
	 */
	@RequestMapping(value = { "/v1/wallet_transfer/update" },
			method = { RequestMethod.PUT },
	        consumes={ MediaType.APPLICATION_JSON_VALUE },
	        produces={ MediaType.APPLICATION_JSON_VALUE }
	)
	public String updateWalletTransfer(@RequestBody WalletTransferUTO walletTransferVO
		, BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResultVO resultVO = new ResultVO();

		// Check the input validation for entity
		beanValidator.validate(walletTransferVO, bindingResult);

		if (bindingResult.hasErrors()) {
			resultVO.setCode(400)
			.setMessage("BZT_WALLETTRANSFER_UPDATE_400_FAIL")
			.setData(getErrorMessage(bindingResult))
			.toModel(model);

			return "jsonView";
		}

		// Bind the request input to VO
		EgovMap entityMap = new EgovMap(NetUtil.toJson(walletTransferVO));
		logger.trace("Bind the request input to VO :{}", new Object[] { NetUtil.toJson(entityMap) });

		// Update WalletTransfer data
		int result = walletTransferService.updateWalletTransfer(entityMap);

		if (result < 1) {
			resultVO.setCode(501)
			.setMessage("BZT_WALLETTRANSFER_UPDATE_501_FAIL")
			.setData(result)
			.toModel(model);

			return "jsonView";
		}

		// Return the result model
		resultVO.setCode(200)
		.setMessage("BZT_WALLETTRANSFER_UPDATE_200_SUCCESS")
		.setData(result)
		.toModel(model);

		return "jsonView";
	}
}