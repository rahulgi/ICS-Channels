package mobisocial.musubi.social;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DispatchLoginClient {
    public static final String TAG = "DispatchLoginClient";
    
    private static final String APP_NAME = "Dispatch";
    
    // Request parameters
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String NEW_PASSWORD = "new_password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String KEY = "key";
    
    // Response parameters
    private static final String SUCCESS = "success";
    private static final String TOKEN = "token";
    private static final String ERROR = "error";
    
    // URLs
    private static final String URL_BASE = "https://dispatchlogin.appspot.com/";
    private static final String REGISTER = "register";
    private static final String LOGIN = "login";
    private static final String RESET = "reset";
    private static final String NEWPASS = "newpass";
    private static final String SENDEMAIL = "sendemail";
    private static final String CHECKEMAIL = "checkemail";
    
    private DispatchLoginResult mCb;
    
    public static interface DispatchLoginResult {
        public void onToken(String method, String username, String token);
        public void onError(String method, String error);
        public void onRequestFailure(String method);
    }
    
    public DispatchLoginClient(DispatchLoginResult cb) {
        mCb = cb;
    }
    
    public void register(String username, String password) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(USERNAME, username);
        params.put(PASSWORD, password);
        asyncRequest(REGISTER, username, params);
    }
    
    public void login(String username, String password) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(USERNAME, username);
        params.put(PASSWORD, password);
        asyncRequest(LOGIN, username, params);
    }
    
    public void reset(String username, String password) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(USERNAME, username);
        params.put(PASSWORD, password);
        asyncRequest(RESET, username, params);
    }
    
    public void newpass(String username, String password, String newPassword) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(USERNAME, username);
        params.put(PASSWORD, password);
        params.put(NEW_PASSWORD, newPassword);
        asyncRequest(NEWPASS, username, params);
    }
    
    // Run a Dispatch request on a new thread
    private void asyncRequest(
            final String method,
            final String username,
            final Map<String, String> params) {
        new Thread() {
            @Override
            public void run() {
                // No need to compute a result if no callback
                if (mCb == null) return;
                
                // Try getting the result
                String url = URL_BASE + method;
                JSONObject result = request(url, params);
                if (result == null) { // Request failure
                    mCb.onRequestFailure(method);
                } else if (result.optBoolean(SUCCESS)) {
                    if (result.has(TOKEN)) { // Success
                        mCb.onToken(method, username, result.optString(TOKEN));
                    } else { // This should never happen
                        mCb.onRequestFailure(method);
                    }
                } else if (result.has(ERROR)) { // Error
                    mCb.onError(method, result.optString(ERROR));
                } else { // This should never happen
                    mCb.onRequestFailure(method);
                }
            }
        }.start();
    }
    
    public JSONObject sendEmail(final String email, final String key) {
        String url = URL_BASE + SENDEMAIL;
        Map<String, String> params = new HashMap<String, String>();
        params.put(NAME, APP_NAME);
        params.put(EMAIL, email);
        params.put(KEY, key);
        return request(url, params);
    }
    
    public JSONObject checkEmail(final String token) {
        String url = URL_BASE + CHECKEMAIL;
        Map<String, String> params = new HashMap<String, String>();
        params.put(TOKEN, token);
        return request(url, params);
    }
    
    // Generic HTTP request helper
    private static JSONObject request(String url, Map<String, String> params) {
        HttpClient client = new DefaultHttpClient();
        HttpPost method = new HttpPost(url);
        try {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            method.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            HttpResponse response = client.execute(method);
            String serResponse = IOUtils.toString(response.getEntity().getContent());
            JSONObject json = new JSONObject(serResponse);
            Log.d(TAG, "response: " + json.toString());
            return json;
        } catch (JSONException e) {
            Log.w(TAG, "Could not parse response");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "encoding error", e);
        } catch (ClientProtocolException e) {
            Log.e(TAG, "protocol error", e);
        } catch (IOException e) {
            Log.e(TAG, "request error", e);
        }
        return null;
    }
}