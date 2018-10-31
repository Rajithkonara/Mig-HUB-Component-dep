package com.wso2telco.dep.tokenregenerator;

import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.user.core.UserStoreManager;
import org.wso2.carbon.user.core.common.AbstractUserOperationEventListener;

public class TokenRegenerationEventListener extends AbstractUserOperationEventListener {

    @Override
    public int getExecutionOrderId() {

        //This listener should execute before the IdentityMgtEventListener
        //Hence the number should be < 1357 (Execution order ID of IdentityMgtEventListener)
        return 1356;
    }

    @Override
    public boolean doPreUpdateCredential(String userName, Object newCredential, Object oldCredential, UserStoreManager userStoreManager) throws UserStoreException {
        //return super.doPreUpdateCredential(userName, newCredential, oldCredential, userStoreManager);
        return true;
    }

    @Override
    public boolean doPostUpdateCredential(String userName, Object credential, UserStoreManager userStoreManager) throws UserStoreException {


       // Read TOKEN_SCOPE
        DataHolder.getInstance().getApiManagerConfigurationService().getAPIManagerConfiguration().getFirstProperty(APIConstants.
                APPLICATION_TOKEN_SCOPE);
        return true;
    }
}
