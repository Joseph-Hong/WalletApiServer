package com.kakaopay.wallet.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import egovframework.rte.fdl.cmmn.trace.LeaveaTrace;
import egovframework.rte.fdl.cmmn.trace.handler.DefaultTraceHandler;
import egovframework.rte.fdl.cmmn.trace.handler.TraceHandler;
import egovframework.rte.fdl.cmmn.trace.manager.DefaultTraceHandleManager;
import egovframework.rte.fdl.cmmn.trace.manager.TraceHandlerService;

@Configuration
public class ContextCommon {
	private static final Logger logger = LoggerFactory.getLogger(ContextCommon.class);

	@Bean
	public LeaveaTrace leaveaTrace(DefaultTraceHandleManager traceHandlerService) {
		LeaveaTrace leaveaTrace = new LeaveaTrace();
		leaveaTrace.setTraceHandlerServices(new TraceHandlerService[] { traceHandlerService });
		return leaveaTrace;
	}

	@Bean
	public DefaultTraceHandleManager traceHandlerService(AntPathMatcher antPathMater, DefaultTraceHandler defaultTraceHandler) {
		DefaultTraceHandleManager defaultTraceHandleManager = new DefaultTraceHandleManager();
		defaultTraceHandleManager.setReqExpMatcher(antPathMater);
		defaultTraceHandleManager.setPatterns(new String[] { "*" });
		defaultTraceHandleManager.setHandlers(new TraceHandler[] { defaultTraceHandler });
		return defaultTraceHandleManager;
	}

	@Bean
	public AntPathMatcher antPathMater() {
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		return antPathMatcher;
	}

	@Bean
	public DefaultTraceHandler defaultTraceHandler() {
		DefaultTraceHandler defaultTraceHandler = new DefaultTraceHandler();
		return defaultTraceHandler;
	}
}
