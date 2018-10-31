package com.wso2telco.dep.tokenregenerator;

import org.apache.log4j.Logger;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.oauth.IdentityOAuthAdminException;
import org.wso2.carbon.identity.oauth.OAuthAdminService;
import org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO;
import org.wso2.carbon.identity.oauth2.OAuth2Service;
import org.wso2.carbon.identity.oauth2.dto.OAuth2AccessTokenReqDTO;
import org.wso2.carbon.identity.oauth2.model.RequestParameter;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.user.core.UserStoreManager;
import org.wso2.carbon.user.core.common.AbstractUserOperationEventListener;

import static org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.AUDIT_MESSAGE;
import static org.wso2.carbon.registry.core.session.CurrentSession.getUser;

public class TokenRegenerationEventListener extends AbstractUserOperationEventListener {

    private Logger log = Logger.getLogger(TokenRegenerationEventListener.class);

    @Override
    public int getExecutionOrderId() {
        //This listener should execute before the IdentityMgtEventListener
        //Hence the number should be < 1357 (Execution order ID of IdentityMgtEventListener)
        return 1356;
    }

    @Override
    public boolean doPostUpdateCredential(String userName, Object credential, UserStoreManager userStoreManager) throws UserStoreException {


        OAuthAdminService oAuthAdminService = new OAuthAdminService();
        String[] applicationScope;
        String instancr=  DataHolder.getInstance().getApiManagerConfigurationService().getAPIManagerConfiguration().getFirstProperty(APIConstants.
                APPLICATION_TOKEN_SCOPE);

        applicationScope = new String[] {instancr};

        try {
            OAuth2AccessTokenReqDTO tokenReqDTO = new OAuth2AccessTokenReqDTO();
            RequestParameter[] requestParameters = new RequestParameter[1];
            RequestParameter requestParameter = new RequestParameter("validity_period","-2");

            PrivilegedCarbonContext.startTenantFlow();
            PrivilegedCarbonContext.getThreadLocalCarbonContext().setUsername(userName);
            PrivilegedCarbonContext.getThreadLocalCarbonContext().setTenantDomain("carbon.super");
            PrivilegedCarbonContext.getThreadLocalCarbonContext().setTenantId(-1234);
            OAuthConsumerAppDTO[] outhapps0 = oAuthAdminService.getAllOAuthApplicationData();

            tokenReqDTO.setGrantType("client_credentials");
            tokenReqDTO.setClientId(outhapps0[0].getOauthConsumerKey());
            tokenReqDTO.setClientSecret(outhapps0[0].getOauthConsumerSecret());
            tokenReqDTO.setCallbackURI(outhapps0[0].getCallbackUrl());
            tokenReqDTO.setScope(applicationScope);
            tokenReqDTO.setTenantDomain(null);
            tokenReqDTO.setPkceCodeVerifier(null);

            requestParameters[0] = requestParameter;
            tokenReqDTO.setRequestParameters(requestParameters);
            tokenReqDTO.setRequestParameters(requestParameters);
            OAuth2Service service = (OAuth2Service) PrivilegedCarbonContext.getThreadLocalCarbonContext().getOSGiService(OAuth2Service.class);
            service.issueAccessToken(tokenReqDTO);
            PrivilegedCarbonContext.endTenantFlow();
            return true;

        } catch (IdentityOAuthAdminException e) {
            log.error("Service Error");
        }

        return true;
    }
}
