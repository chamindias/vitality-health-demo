Micro-service
-------------

1. Goto VitalityAuth-Service folder.

2. Type the following commands, one after the other.
mvn package
java -jar target/VitalityAuth-Service-*.jar

3. The service will start (port 8080)



Custom Grant
------------

1. Goto custom-grant folder.

2. Type the following command.

mvn clean install

3. Copy custom-grant/target/org.soasecurity.is.oauth.grant-1.0.0.jar file into Product_Home/repository/components/lib folder.

4. Find the below section in Product_Home/repository/conf/identity/identity.xml

&lt;SupportedGrantType>
&lt;GrantTypeName>password&lt;/GrantTypeName>
&lt;GrantTypeHandlerImplClass>org.wso2.carbon.apimgt.keymgt.handlers.ExtendedPasswordGrantHandler&lt;/GrantTypeHandlerImplClass>
&lt;/SupportedGrantType>

Replace "org.wso2.carbon.apimgt.keymgt.handlers.ExtendedPasswordGrantHandler" with "org.soasecurity.is.oauth.grant.password.VitalityCustomPasswordGrant"

5. Save it and start the server.

Referance : https://docs.wso2.com/display/IS530/Writing+a+Custom+OAuth+2.0+Grant+Type

6. Create an application. Then generate a token using password grant type.

Eg : curl -k -d "grant_type=password&username=admin&password=admin" -H "Authorization: Basic bWF5ZjRpRnJpOXV2aXNETzJ0VWVJWEdNdUR3YTpJVEhoUjJaV1dvbXlubERqTEhjd0pESDdvTjRh" https://localhost:8243/token

Here, bWF5ZjRpRnJpOXV2aXNETzJ0VWVJWEdNdUR3YTpJVEhoUjJaV1dvbXlubERqTEhjd0pESDdvTjRh is the base64 encoded value of the concatenation - consumerKey:consumerSecret

7. Observer the SUBJECT_IDENTIFIER column in IDN_OAUTH2_ACCESS_TOKEN table. You will see the UID returned from the micro-service in that column.
