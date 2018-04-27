package org.soasecurity.is.oauth.grant.password;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.dto.OAuth2AccessTokenReqDTO;
import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;
import org.wso2.carbon.identity.oauth2.token.handlers.grant.PasswordGrantHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
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
            userUid = getUid(username, password);
        } catch (Exception e) {
            throw new IdentityOAuth2Exception(e.getMessage(), e);
        }
        if (userUid != null) {
            AuthenticatedUser user = new AuthenticatedUser();
            //we assume that we are using super tenant
            user.setTenantDomain("carbon.super");
            user.setUserName(userUid);
            user.setAuthenticatedSubjectIdentifier(userUid);
            tokReqMsgCtx.setAuthorizedUser(user);
            return true;
        }
        return false;
    }

    private String getUid(String username, String password) {

        //we are using a dummy micro-service for demonstration purpose
        String url = "http://localhost:8080/authservice/getuid?username=" + username + "&password=" + password;
        BufferedReader in = null;
        JSONObject myResponse = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            myResponse = new JSONObject(response.toString());
        } catch (ProtocolException e) {
            log.error("ProtocolException " + e.getMessage());
        } catch (MalformedURLException e) {
            log.error("MalformedURLException " + e.getMessage());
        } catch (IOException e) {
            log.error("IOException " + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error("IOException when closing BufferedReader " + e.getMessage());
            }
        }
        return (myResponse != null) ? myResponse.get("UID").toString() : null;
    }
}
