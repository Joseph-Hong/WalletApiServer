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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.cmm.entity.ResultVO;
import com.cmm.util.CommonUtil;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseUTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferCTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferRTO;
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
	/** @formatter:off */
	@RequestMapping(value = { "/v1/transfer" },
			method = { RequestMethod.POST },
	        consumes={ MediaType.APPLICATION_JSON_VALUE },
	        produces={ MediaType.APPLICATION_JSON_VALUE }
	)
	public String transfer(@RequestBody WalletTransferCTO walletTransferVO, @RequestHeader(value="X-USER-ID", required=true) Integer userId
			, @RequestHeader(value="X-ROOM-ID", required=true) String roomId, BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception
	/** @formatter:on */
	{
		ResultVO resultVO = new ResultVO();

		walletTransferVO.setSenderUserId(userId);
		walletTransferVO.setRoomId(roomId);

		// Check the input validation for entity
		beanValidator.validate(walletTransferVO, bindingResult);

		if (bindingResult.hasErrors()) {
			/** @formatter:off */
			resultVO.setCode(400)
					.setMessage("BZT_TRANSFER_400_FAILED")
					.setData(getErrorMessage(bindingResult))
					.toModel(model);
			/** @formatter:on */

			return "jsonView";
		}

		// Insert WalletTransfer and WalletDispense data
		walletTransferService.transfer(walletTransferVO);

		// Return the result model
		/** @formatter:off */
		resultVO.setCode(200)
				.setMessage("BZT_TRANSFER_200_SUCCESS")
				.setData(CommonUtil.map()
									.add("token", walletTransferVO.getToken())
									.build())
				.toModel(model);
		/** @formatter:on */

		return "jsonView";
	}

	/**
	 * 받기 API
	 */
	/** @formatter:off */
	@RequestMapping(value = { "/v1/dispense" },
			method = { RequestMethod.PUT },
	        consumes={ MediaType.APPLICATION_JSON_VALUE },
	        produces={ MediaType.APPLICATION_JSON_VALUE }
	)
	public String dispense(@RequestBody WalletDispenseUTO walletDispenseVO, @RequestHeader(value="X-USER-ID", required=true) Integer userId
			, @RequestHeader(value="X-ROOM-ID", required=true) String roomId, BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception
	/** @formatter:on */
	{
		ResultVO resultVO = new ResultVO();

		walletDispenseVO.setRecipientUserId(userId);
		walletDispenseVO.setRoomId(roomId);

		// Check the input validation for entity
		beanValidator.validate(walletDispenseVO, bindingResult);

		if (bindingResult.hasErrors()) {
			/** @formatter:off */
			resultVO.setCode(400)
					.setMessage("BZT_DISPENSE_400_FAILED")
					.setData(getErrorMessage(bindingResult))
					.toModel(model);
			/** @formatter:on */

			return "jsonView";
		}

		// Update WalletDispense data
		walletDispenseService.dispense(walletDispenseVO);

		// Return the result model
		/** @formatter:off */
		resultVO.setCode(200)
				.setMessage("BZT_DISPENSE_200_SUCCESS")
				.setData(CommonUtil.map()
									.add("amount", walletDispenseVO.getAmount())
									.build())
				.toModel(model);
		/** @formatter:on */

		return "jsonView";
	}

	/**
	 * 조회 API
	 */
	/** @formatter:off */
	@RequestMapping(value = { "/v1/check" },
			method = { RequestMethod.GET },
	        produces={ MediaType.APPLICATION_JSON_VALUE }
	)
	public String check(@ModelAttribute("walletTransferRTO") WalletTransferRTO walletTransferVO, @RequestHeader(value="X-USER-ID", required=true) Integer userId
			, @RequestHeader(value="X-ROOM-ID", required=true) String roomId, BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception
	/** @formatter:on */
	{
		ResultVO resultVO = new ResultVO();

		walletTransferVO.setSenderUserId(userId);
		walletTransferVO.setRoomId(roomId);

		// Check the input validation for entity
		beanValidator.validate(walletTransferVO, bindingResult);

		if (bindingResult.hasErrors()) {
			/** @formatter:off */
			resultVO.setCode(400)
					.setMessage("BZT_CHECK_400_FAILED")
					.setData(getErrorMessage(bindingResult))
					.toModel(model);
			/** @formatter:on */

			return "jsonView";
		}

		// Retrieve WalletTransfer data
		walletTransferService.check(walletTransferVO);

		// Return the result model
		/** @formatter:off */
		resultVO.setCode(200)
				.setMessage("BZT_CHECK_200_SUCCESS")
				.setData(CommonUtil.map()
									.add("transfer_dt", walletTransferVO.getRegDate()) //뿌린 시각
									.add("transfer_amount", walletTransferVO.getAmount()) //뿌린 금액
									.add("dispense_total_amount", CommonUtil.nvl(walletTransferVO.getOutput(), 0.0)) //받기 완료된 금액
									.add("dispenses", walletTransferVO.getWalletDispenses()) //받기 완료된 정보 ([받은 금액, 받은 사용자 아이디] 리스트)
									.build())
				.toModel(model);
		/** @formatter:on */

		model.addAttribute("walletTransferRTO", null);

		return "jsonView";
	}
}