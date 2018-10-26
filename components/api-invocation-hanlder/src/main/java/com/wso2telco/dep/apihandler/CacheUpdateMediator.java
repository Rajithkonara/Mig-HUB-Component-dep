package com.wso2telco.dep.apihandler;

import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.wso2.carbon.apimgt.impl.APIConstants;

import javax.cache.Cache;
import javax.cache.Caching;

public class CacheUpdateMediator extends AbstractMediator {

    private static final String CONSUMER_KEY = "app_consumer_key";
    private static final String SP_TOKEN_CACHE = "spTokenCache";

    @Override
    public boolean mediate(MessageContext messageContext) {

        String consumerKey = (String) messageContext.getProperty(CONSUMER_KEY);

        if (log.isDebugEnabled())
        {
            log.debug("consumerKey: " + consumerKey);
        }

        Cache spToken =  Caching.getCacheManager(APIConstants.API_MANAGER_CACHE_MANAGER).getCache(SP_TOKEN_CACHE);

        if (spToken.containsKey(consumerKey)) {
            spToken.remove(consumerKey);
        }

        return true;
    }
}
