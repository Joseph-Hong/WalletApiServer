package com.kakaopay.wallet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import com.cmm.util.CommonUtil;

@SpringBootTest
class WalletApiServerApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(WalletApiServerApplicationTests.class);

	@Test
	void validateInputParameters() throws Throwable {

		logger.debug("Start of validateInputParameters ++++++++++++++++++++++++++++++++++++++");
		StopWatch sw = new StopWatch();
		sw.start();

		Assertions.assertTrue(CommonUtil.isEmpty(""));

		sw.stop();
		logger.debug("End of validateInputParameters : {} --------------------------------------", sw.getTotalTimeSeconds());
	}
}
