package com.wso2telco.dep.tokenregenerator;

import org.wso2.carbon.apimgt.impl.APIManagerConfigurationService;
import org.wso2.carbon.user.core.service.RealmService;

public class DataHolder {

    private static RealmService realmService;
    private static volatile DataHolder dataHolder;
    private static APIManagerConfigurationService apiManagerConfigurationService;
    private static TokenRegenerationEventListener tokenRegenerationEventListener;

    private DataHolder() {

    }

    public static DataHolder getInstance() {

        if (dataHolder == null) {

            synchronized (DataHolder.class) {
                if (dataHolder == null) {
                    dataHolder = new DataHolder();
                    tokenRegenerationEventListener = new TokenRegenerationEventListener();
                }
            }

        }

        return dataHolder;
    }

    public void setRealmService(RealmService realmService) {
        this.realmService = realmService;
    }

    public RealmService getRealmService() {
        return realmService;
    }

    public TokenRegenerationEventListener getCustomUserOperationEventListener() {
        return tokenRegenerationEventListener;
    }

    public void setApiManagerConfigurationService(APIManagerConfigurationService apiManagerConfigurationService) {
        this.apiManagerConfigurationService = apiManagerConfigurationService;
    }

    public APIManagerConfigurationService getApiManagerConfigurationService() {return apiManagerConfigurationService;}


}
