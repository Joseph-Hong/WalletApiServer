package com.cmm.entity;

import java.util.Map;

import com.cmm.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.rte.psl.dataaccess.util.CamelUtil;

public class EgovMap extends egovframework.rte.psl.dataaccess.util.EgovMap {

	private static final long serialVersionUID = -369912354101606727L;
	private static final Logger logger = LoggerFactory.getLogger(EgovMap.class);

	public EgovMap() {
	}

	public EgovMap(Map dataMap) {
		super.putAll(dataMap);
	}

	@Override
	public Object get(Object key) {
		return super.get(CamelUtil.convert2CamelCase((String) key));
	}
}
