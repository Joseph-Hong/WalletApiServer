package com.kakaopay.wallet.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StopWatch;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WalletTests {

	private static final Logger logger = LoggerFactory.getLogger(WalletTests.class);

	@Autowired
	protected MockMvc mockMvc;

	@Test
	void walletTransferList() throws Exception {

		logger.debug("Start of walletTransferList ++++++++++++++++++++++++++++++++++++++");
		StopWatch sw = new StopWatch();
		sw.start();

		/** @formatter:off */
		this.mockMvc.perform(MockMvcRequestBuilders.get("/wallet/v1/wallet_transfer/list"))
													.andDo(MockMvcResultHandlers.print())
													.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
													.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
		/** @formatter:on */

		sw.stop();
		logger.debug("End of walletTransferList : {} --------------------------------------", sw.getTotalTimeSeconds());
	}
}
