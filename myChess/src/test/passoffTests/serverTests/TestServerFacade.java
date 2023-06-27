package passoffTests.serverTests;

import com.google.gson.Gson;
import passoffTests.testClasses.TestModels;
import passoffTests.testClasses.WebValues;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Provides access to the ChessServer
 * Is obfuscated into a Jar to keep hidden from students
 */
public class TestServerFacade {

    private String hostname;
    private String port;

    public TestModels.TestResult verifyJoinPlayer(TestModels.TestJoinRequest request, String authToken) {
        try {
            URL url = new URL("http://" + hostname + ":" + port + WebValues.JOIN_GAME_CONTEXT);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty(WebValues.AUTH_TOKEN_PARAM, authToken);
            http.addRequestProperty("Accept", "application/json");
            String reqData = serialize(request);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();
            http.connect();
            setStatusCode(http.getResponseCode());
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                TestModels.TestResult result = deserialize(respData, TestModels.TestResult.class);
                respBody.close();
                return result;
            } else {
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                TestModels.TestResult result = deserialize(respData, TestModels.TestResult.class);
                respBody.close();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int statusCode;

    public TestServerFacade(String hostname, String port) {
        this.hostname = hostname;
        this.port = port;
    }

    public int getStatusCode() {
        return statusCode;
    }

    private void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public TestModels.TestLoginRegisterResult register(TestModels.TestRegisterRequest request) {
        try {
            URL url = new URL("http://" + hostname + ":" + port + WebValues.REGISTER_CONTEXT);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            String reqData = serialize(request);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();
            http.connect();
            setStatusCode(http.getResponseCode());
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                TestModels.TestLoginRegisterResult result = deserialize(respData, TestModels.TestLoginRegisterResult.class);
                respBody.close();
                return result;
            } else {
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                TestModels.TestLoginRegisterResult result = deserialize(respData, TestModels.TestLoginRegisterResult.class);
                respBody.close();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TestModels.TestLoginRegisterResult login(TestModels.TestLoginRequest request) {
        try {
            URL url = new URL("http://" + hostname + ":" + port + WebValues.LOGIN_CONTEXT);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            String reqData = serialize(request);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();
            http.connect();
            setStatusCode(http.getResponseCode());
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                TestModels.TestLoginRegisterResult result = deserialize(respData, TestModels.TestLoginRegisterResult.class);
                respBody.close();
                return result;
            } else {
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                TestModels.TestLoginRegisterResult result = deserialize(respData, TestModels.TestLoginRegisterResult.class);
                respBody.close();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TestModels.TestResult logout(String authToken) {
        try {
            URL url = new URL("http://" + hostname + ":" + port + WebValues.LOGOUT_CONTEXT);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(false);
            http.addRequestProperty("Accept", "application/json");
            http.addRequestProperty(WebValues.AUTH_TOKEN_PARAM, authToken);
            http.connect();
            setStatusCode(http.getResponseCode());
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                TestModels.TestResult result = deserialize(respData, TestModels.TestResult.class);
                respBody.close();
                return result;
            } else {
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                TestModels.TestResult result = deserialize(respData, TestModels.TestResult.class);
                respBody.close();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TestModels.TestListResult listGames(String authToken) {
        try {
            URL url = new URL("http://" + hostname + ":" + port + WebValues.LIST_GAME_CONTEXT);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty(WebValues.AUTH_TOKEN_PARAM, authToken);
            http.addRequestProperty("Accept", "application/json");
            http.connect();
            setStatusCode(http.getResponseCode());
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                TestModels.TestListResult result = deserialize(respData, TestModels.TestListResult.class);
                respBody.close();
                return result;
            } else {
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                TestModels.TestListResult result = deserialize(respData, TestModels.TestListResult.class);
                respBody.close();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TestModels.TestCreateResult createGame(TestModels.TestCreateRequest request, String authToken) {
        try {
            URL url = new URL("http://" + hostname + ":" + port + WebValues.CREATE_GAME_CONTEXT);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty(WebValues.AUTH_TOKEN_PARAM, authToken);
            http.addRequestProperty("Accept", "application/json");
            String reqData = serialize(request);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();
            http.connect();
            setStatusCode(http.getResponseCode());
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                TestModels.TestCreateResult result = deserialize(respData, TestModels.TestCreateResult.class);
                respBody.close();
                return result;
            } else {
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                TestModels.TestCreateResult result = deserialize(respData, TestModels.TestCreateResult.class);
                respBody.close();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TestModels.TestResult verifyJoinObserver(TestModels.TestWatchRequest request, String authToken) {
        try {
            URL url = new URL("http://" + hostname + ":" + port + WebValues.OBSERVE_GAME_CONTEXT);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty(WebValues.AUTH_TOKEN_PARAM, authToken);
            http.addRequestProperty("Accept", "application/json");
            String reqData = serialize(request);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();
            http.connect();
            setStatusCode(http.getResponseCode());
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                TestModels.TestResult result = deserialize(respData, TestModels.TestResult.class);
                respBody.close();
                return result;
            } else {
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                TestModels.TestResult result = deserialize(respData, TestModels.TestResult.class);
                respBody.close();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TestModels.TestResult clear() {
        try {
            // send request
            URL url = new URL("http://" + hostname + ":" + port + WebValues.CLEAR_CONTEXT);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            // read result
            setStatusCode(http.getResponseCode());
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                TestModels.TestResult result = deserialize(respData, TestModels.TestResult.class);
                respBody.close();
                return result;
            } else {
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                TestModels.TestResult result = deserialize(respData, TestModels.TestResult.class);
                respBody.close();
                return result;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    private String serialize(Object obj) {
        return new Gson().toJson(obj);
    }

    private <T> T deserialize(String obj, Class<T> type) {
        return new Gson().fromJson(obj, type);
    }
}
