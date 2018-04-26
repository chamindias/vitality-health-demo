/*
 * Copyright (c) WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.soasecurity.is.oauth.grant.password;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.dto.OAuth2AccessTokenReqDTO;
import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;
import org.wso2.carbon.identity.oauth2.token.handlers.grant.PasswordGrantHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class VitalityCustomPasswordGrant extends PasswordGrantHandler {

    private static Log log = LogFactory.getLog(VitalityCustomPasswordGrant.class);

    @Override
    public boolean validateGrant(OAuthTokenReqMessageContext tokReqMsgCtx)
            throws IdentityOAuth2Exception {

        OAuth2AccessTokenReqDTO oAuth2AccessTokenReqDTO = tokReqMsgCtx.getOauth2AccessTokenReqDTO();
        String username = oAuth2AccessTokenReqDTO.getResourceOwnerUsername();
        String password = oAuth2AccessTokenReqDTO.getResourceOwnerPassword();

        String userUid = null;
        try {
            userUid = getUid(username,password);
        } catch (Exception e) {
            throw new IdentityOAuth2Exception(e.getMessage());
        }
        if (userUid != null) {
            AuthenticatedUser user = new AuthenticatedUser();
            user.setTenantDomain("carbon.super");
            user.setUserName(username);
            user.setAuthenticatedSubjectIdentifier(userUid);
            tokReqMsgCtx.setAuthorizedUser(user);
            return true;
        }
        return false;
    }

    private String getUid(String username, String password) throws Exception {
        String url = "http://localhost:8080/authservice/getuid?username="+ username +"&password=" + password;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONObject myResponse = new JSONObject(response.toString());
        return myResponse.get("UID").toString();
    }

}
