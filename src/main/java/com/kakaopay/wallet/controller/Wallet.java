package com.kakaopay.wallet.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.cmm.entity.ResultVO;
import com.cmm.util.CommonUtil;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseUTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferCTO;
import com.kakaopay.wallet.service.WalletDispenseService;
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
public class Wallet extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(Wallet.class);

	@Resource(name = "walletTransferService")
	protected WalletTransferService walletTransferService;

	@Resource(name = "walletDispenseService")
	protected WalletDispenseService walletDispenseService;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;

	/**
	 * 뿌리기 API
	 */
	@RequestMapping(value = { "/v1/transfer" },
			method = { RequestMethod.POST },
	        consumes={ MediaType.APPLICATION_JSON_VALUE },
	        produces={ MediaType.APPLICATION_JSON_VALUE }
	)
	public String transfer(@RequestBody WalletTransferCTO walletTransferVO, @RequestHeader(value="X-USER-ID", required=true) Integer userId
			, @RequestHeader(value="X-ROOM-ID", required=true) String roomId, BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResultVO resultVO = new ResultVO();

		walletTransferVO.setSenderUserId(userId);
		walletTransferVO.setRoomId(roomId);

		// Check the input validation for entity
		beanValidator.validate(walletTransferVO, bindingResult);

		if (bindingResult.hasErrors()) {
			resultVO.setCode(400)
					.setMessage("BZT_TRANSFER_400_FAILED")
					.setData(getErrorMessage(bindingResult))
					.toModel(model);

			return "jsonView";
		}

		// Insert WalletTransfer and WalletDispense data
		if (walletTransferService.transfer(walletTransferVO) < 1) {
			resultVO.setCode(501)
					.setMessage("BZT_TRANSFER_501_FAILED")
					.toModel(model);

			return "jsonView";
		}

		// Return the result model
		resultVO.setCode(200)
				.setMessage("BZT_TRANSFER_200_SUCCESS")
				.setData(CommonUtil.map()
									.add("token", walletTransferVO.getToken())
									.build())
				.toModel(model);

		return "jsonView";
	}

	/**
	 * 받기 API
	 */
	@RequestMapping(value = { "/v1/dispense" },
			method = { RequestMethod.PUT },
	        consumes={ MediaType.APPLICATION_JSON_VALUE },
	        produces={ MediaType.APPLICATION_JSON_VALUE }
	)
	public String dispense(@RequestBody WalletDispenseUTO walletDispenseVO, @RequestHeader(value="X-USER-ID", required=true) Integer userId
			, @RequestHeader(value="X-ROOM-ID", required=true) String roomId, BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResultVO resultVO = new ResultVO();

		walletDispenseVO.setRecipientUserId(userId);
		walletDispenseVO.setRoomId(roomId);

		// Check the input validation for entity
		beanValidator.validate(walletDispenseVO, bindingResult);

		if (bindingResult.hasErrors()) {
			resultVO.setCode(400)
			.setMessage("BZT_DISPENSE_400_FAILED")
			.setData(getErrorMessage(bindingResult))
			.toModel(model);

			return "jsonView";
		}

		// Update WalletDispense data
		if (walletDispenseService.dispense(walletDispenseVO) < 1) {
			resultVO.setCode(501)
			.setMessage("BZT_DISPENSE_501_FAILED")
			.toModel(model);

			return "jsonView";
		}

		// Return the result model
		resultVO.setCode(200)
		.setMessage("BZT_DISPENSE_200_SUCCESS")
		.setData(CommonUtil.map()
							.add("amount", walletDispenseVO.getAmount())
							.build())
		.toModel(model);

		return "jsonView";
	}



}