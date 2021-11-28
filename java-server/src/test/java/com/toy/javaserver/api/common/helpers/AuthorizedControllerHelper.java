package com.toy.javaserver.api.common.helpers;

import org.springframework.mock.web.MockFilterConfig;
import org.springframework.security.config.BeanIds;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class AuthorizedControllerHelper {
    /**
     * MockMvc 생성 코드
     * @param context WebApplicationContext
     * @return Spring Security Filter가 적용된 MockMvc 객체
     * @throws Exception
     */
    public MockMvc getSecurityAppliedMockMvc(WebApplicationContext context) throws Exception {
        DelegatingFilterProxy delegateProxyFilter = new DelegatingFilterProxy();
        MockFilterConfig secFilterConfig = new MockFilterConfig(context.getServletContext(),
                BeanIds.SPRING_SECURITY_FILTER_CHAIN);
        delegateProxyFilter.init(secFilterConfig);

        return MockMvcBuilders.webAppContextSetup(context)
                .alwaysDo(print())
                .build();
    }
}

