package com.itheima.constant;

public interface Constant {
	int ACTIVE_IS_CODE=1;
	int ACTIVE_NOT_CODE=0;
	int PRODUCT_IS_HOT=1;
	int PRODUCT_NOT_HOT=0;
	int PRODUCT_PUSH_UP=0;
	int PRODUCT_PUSH_DOMN=1;
	int INDEX_SHOW_COUNT=9;
	/**
	 * 存放在redis中分类信息的key
	 */
	String CATEGORY_LIST_JSON = "CATEGORYLISTJSON";
}
