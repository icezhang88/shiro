package com.shiro;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author IceZhang
 * Copyright  2019-2020  创捷运维智能科技有限公司
 * All rights reserved.
 * @ClassName Test
 * Description: TODO
 */
@Slf4j
public class Test {

    public static void main(String[] args) {
        String str="";
       log.info(String.valueOf(StringUtils.isNoneBlank(str)));
       log.info(String.valueOf(StrUtil.isNotEmpty(str)));

    }

}
