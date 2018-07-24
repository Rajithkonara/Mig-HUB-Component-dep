# Digital Enable Platform (DEP)



| Branch | Build Status |
| :------------ |:-------------
| master | [![Build Status](http://ci.wso2telco.com/job/component-dep/badge/icon)](http://ci.wso2telco.com/job/component-dep/)


This is the platform for both the product Digital Enable Hub &  Digital Enable Gateway .This includes all the required components for managing web components , mediator component, analytic tools etc..


If you are using authorize-token-generator .jar file please plase the mig_aouth_token.properties file in <HUB HOME>/repository/conf/ location.
  
  To enbale Api invocation Handler you should add below configuration to Mig and Hub Configurations.
Below xml snippet should be added in mobile-connect.xml  

mobile-connect.xml
---------------------
```
<SupportedGrantType>
                <GrantTypeName>authorization_code</GrantTypeName>
                <!--GrantTypeHandlerImplClass>org.wso2.carbon.identity.oauth2.token.handlers.grant.AuthorizationCodeGrantHandler</GrantTypeHandlerImplClass-->
                <GrantTypeHandlerImplClass>com.wso2telco.grant.handler.CustomAuthCodeGrant</GrantTypeHandlerImplClass>
</SupportedGrantType>
  
 ```
------------------------


Copy the api-invocation-handler-<version>.jar file to <HUB_HOME>/repository/componenet/dropins/

Place the mig_aouth_token.properties file in /repository/conf/ location.

Edit the API synapse to add below handler at the top of handlers [on our auth token userinfo apis]
<handler class="com.wso2telco.dep.apihandler.ApiInvocationHandler"/>
For token, userinfo API synapse add the below snippet additionaly before <send> tag of <inSequence>


# Using for Basic Auth GW

Copy the api-invocation-handler-1.0.0.jar file to <HUB_HOME>/repository/componenet/dropins/

Edit the API synapse to add below handler at the top of handlers 

Example Syanpse file conating the API Invocation Handler
---------------------
```
   <handlers>
      <handler class="com.wso2telco.dep.apihandler.ApiInvocationHandler"/>
      <handler class="org.wso2.carbon.apimgt.gateway.handlers.common.APIMgtLatencyStatsHandler"/>
      <handler class="org.wso2.carbon.apimgt.gateway.handlers.security.CORSRequestHandler">
         <property name="apiImplementationType" value="ENDPOINT"/>
      </handler>
      <handler class="org.wso2.carbon.apimgt.gateway.handlers.security.APIAuthenticationHandler"/>
      <handler class="org.wso2.carbon.apimgt.gateway.handlers.throttling.APIThrottleHandler">
         <property name="policyKey" value="gov:/apimgt/applicationdata/tiers.xml"/>
         <property name="policyKeyApplication"
                   value="gov:/apimgt/applicationdata/app-tiers.xml"/>
         <property name="id" value="A"/>
         <property name="policyKeyResource"
                   value="gov:/apimgt/applicationdata/res-tiers.xml"/>
      </handler>
      <handler class="org.wso2.carbon.apimgt.usage.publisher.APIMgtUsageHandler"/>
      <handler class="org.wso2.carbon.apimgt.usage.publisher.APIMgtGoogleAnalyticsTrackingHandler">
         <property name="configKey" value="gov:/apimgt/statistics/ga-config.xml"/>
      </handler>
      <handler class="org.wso2.carbon.apimgt.gateway.handlers.ext.APIManagerExtensionHandler"/>
      <handler class="com.wso2telco.dep.validator.handler.APIInfoHandler"/>
   </handlers>
    ```
------------------------
