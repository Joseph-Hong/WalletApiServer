package com.kakaopay.wallet.controller;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.jmock.lib.concurrent.Blitzer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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

import com.cmm.util.CommonUtil;
import com.kakaopay.wallet.model.wallet_dispense.WalletDispenseUTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferCTO;
import com.kakaopay.wallet.model.wallet_transfer.WalletTransferRTO;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WalletTests {

	private static final Logger logger = LoggerFactory.getLogger(WalletTests.class);

	@Autowired
	protected MockMvc mockMvc;

	static AtomicInteger c;
	static Blitzer blitzer = new Blitzer(1, 10);

	@BeforeAll
	static void setup() {
		c = new AtomicInteger(0);
	}

	@AfterAll
	static void tearDown() throws InterruptedException {
		blitzer.shutdown();
	}

	@Test
	void transfer() throws Exception {

		logger.debug("Start of transfer ++++++++++++++++++++++++++++++++++++++");
		StopWatch sw = new StopWatch();
		sw.start();

		blitzer.blitz(new Runnable() {
			public void run() {
				int uniqueNo = c.getAndIncrement() + 1;

				int userId = CommonUtil.randomNumeric(0, 10); //뿌리기 테스터 10명
				String roomId = "a001"; //대화방
				String token = UUID.randomUUID().toString().substring(0, 3);
				logger.debug("## hashCode : {}", token.hashCode());

				WalletTransferCTO walletTransferCTO = new WalletTransferCTO();
				walletTransferCTO.setToken(token);
				walletTransferCTO.setAmount(100000.0);
				walletTransferCTO.setRecipientCount(20); //20명에게

				try {
					/** @formatter:off */
					mockMvc.perform(MockMvcRequestBuilders.post("/wallet/v1/transfer")
															.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
															.header("X-USER-ID", userId)
															.header("X-ROOM-ID", roomId)
															.content(walletTransferCTO.serialize())
									)
								.andDo(MockMvcResultHandlers.print())
								.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
								.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
					/** @formatter:on */

				} catch (Exception e) {
					logger.error("{}", e.fillInStackTrace());
				}
			}
		});

		sw.stop();
		logger.debug("End of transfer : {} --------------------------------------", sw.getTotalTimeSeconds());
	}

	@Test
	void concurrencyDistributeForBlockingDupUserId() throws Exception {

		logger.debug("Start of concurrencyDistributeForBlockingDupUserId ++++++++++++++++++++++++++++++++++++++");
		StopWatch sw = new StopWatch();
		sw.start();

		blitzer.blitz(new Runnable() {
			public void run() {

				int userId = 3; //받기 - 동일한 유저
				String roomId = "a001"; //대화방

				WalletDispenseUTO walletDispenseUTO = new WalletDispenseUTO();
				walletDispenseUTO.setToken("1c9");

				try {
					/** @formatter:off */
					mockMvc.perform(MockMvcRequestBuilders.put("/wallet/v1/dispense")
																.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
																.header("X-USER-ID", userId)
																.header("X-ROOM-ID", roomId)
																.content(walletDispenseUTO.serialize())
										)
								.andDo(MockMvcResultHandlers.print())
								.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
								.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
					/** @formatter:on */

				} catch (Exception e) {
					logger.error("{}", e.fillInStackTrace());
				}
			}
		});

		sw.stop();
		logger.debug("End of concurrencyDistributeForBlockingDupUserId : {} --------------------------------------", sw.getTotalTimeSeconds());
	}

	@Test
	void distribute() throws Exception {

		logger.debug("Start of distribute ++++++++++++++++++++++++++++++++++++++");
		StopWatch sw = new StopWatch();
		sw.start();

		int uniqueNo = c.getAndIncrement() + 1;

		int userId = 0; //뿌린 사람
		String roomId = "a001"; //대화방
		String token = "1c9";

		/** @formatter:off */
		mockMvc.perform(MockMvcRequestBuilders.get("/wallet/v1/check")
													.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
													.header("X-USER-ID", userId)
													.header("X-ROOM-ID", roomId)
													.param("token", token)
							)
					.andDo(MockMvcResultHandlers.print())
					.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
					.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
		/** @formatter:on */

		sw.stop();
		logger.debug("End of distribute : {} --------------------------------------", sw.getTotalTimeSeconds());
	}
}
