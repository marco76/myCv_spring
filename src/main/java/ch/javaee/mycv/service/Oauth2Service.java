package ch.javaee.mycv.service;

import ch.javaee.mycv.model.ApplicationData;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by marco on 14/03/16.
 */
@Stateless
public class Oauth2Service {

    @Inject
    CvService cvService;
    @Inject
    ApplicationData applicationData;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    /** OAuth 2.0 scopes. */
    private static final List<String> SCOPES = Arrays.asList(
            "https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/userinfo.email");

    private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static String CALLBACK_URI = null;
    private static String GOOGLE_CALLBACK = "/rest/oauth2/callback";
    private static GoogleAuthorizationCodeFlow flow;

    private static DataStoreFactory dataStoreFactory ;

    {
        httpTransport = new NetHttpTransport();
        dataStoreFactory = MemoryDataStoreFactory.getDefaultInstance();

    }

    @PostConstruct
    private void postContruct(){
        String mongoOauthCredentials = cvService.getGoogleOauth();
        try {
            // load client secrets
            GoogleClientSecrets clientSecrets = null;

            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                    new StringReader(mongoOauthCredentials));
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, JSON_FACTORY, clientSecrets,
                    SCOPES).setDataStoreFactory(dataStoreFactory)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CALLBACK_URI = applicationData.getServerUrl() + GOOGLE_CALLBACK;
    }


    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    public  String requestLogin() throws Exception {
        GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
        return url.setRedirectUri(CALLBACK_URI).build();
    }

    /** Authorizes the installed application to access user's protected data. */
    public  String authorize(String token) throws Exception {

        return getUserInfoJson(token);
    }
    /**
     * Expects an Authentication Code, and makes an authenticated request for the user's profile information
     * @return JSON formatted user profile information
     * @param authCode authentication code provided by google
     */
    public String getUserInfoJson(final String authCode) throws IOException {
        // receive: access_token, id_token
         GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(CALLBACK_URI).execute();
        GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
        final HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credential);
        // Make an authenticated request
        final GenericUrl url = new GenericUrl(USER_INFO_URL);
        final HttpRequest request = requestFactory.buildGetRequest(url);
        request.getHeaders().setContentType("application/json");
        final String jsonIdentity = request.execute().parseAsString();
        return jsonIdentity;

    }



}
