package net.yorksolutions.jsontest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;

@Service
public class RequestService {

    public HashMap<String, String> ip(HttpServletRequest request) {

        var ipMap = new HashMap<String, String>();
        ipMap.put("ip", request.getRemoteAddr());

        return ipMap;
    }

    public HashMap<String, String> headers(HttpServletRequest request) {

        var headersMap = new HashMap<String, String>();
        var headerNameList = request.getHeaderNames();

        while (headerNameList.hasMoreElements()) {

            String headerName = headerNameList.nextElement();
            String headerData = request.getHeader(headerName);

            headersMap.put(headerName, headerData);
        }

        return headersMap;
    }

    //add Simple Date Formatter below for date and time
    //set date object rather than setting twice
    public HashMap<String, String> dateAndTime() {

        var dateMap = new HashMap<String, String>();
        Date date = new Date();
        Long milliseconds = date.getTime();

        dateMap.put("date", java.time.LocalDate.now().toString());
        dateMap.put("time", java.time.LocalTime.now().toString());
        dateMap.put("milliseconds", String.valueOf(milliseconds));

        return dateMap;
    }

//    public HashMap<String, String> time() {
//        var timeMap = new HashMap<String, String>()
//    }

    public HashMap<String, String> echo(HttpServletRequest request) {

        var echoMap = new HashMap<String, String>();
        //creates a String type variable which will hold the split URI (url request)
        //it will split starting at "position zero" of "/echo/" and split at every "/"
        String [] splitURI = request.getRequestURI()
                .split(request.getPathInfo() + "/echo/")[0]
                .split("/");

        //loop through the split URI to get our key:value pairs
        //and put them on the map

        for (int i = 2; i < splitURI.length; i += 2) {
            //TODO simplify this, check out Adam's version
            //this is to deal with exception of odd-numbered items in array
            if (i != splitURI.length - 1) {
                echoMap.put(splitURI[i], splitURI[i + 1]);
                //when it hits end of loop it puts the final pair
            } else {
                echoMap.put(splitURI[i], "");
                }
        }
        return echoMap;
    }

    public HashMap validate(String json) {
        var validateMap = new HashMap();

        ObjectMapper mapper = new ObjectMapper();
        try {
            var startTime = Instant.now().getNano();
            JsonNode jsonObj = mapper.readTree(json);
            var endTime = Instant.now().getNano();

            validateMap.put("validate", true);
            validateMap.put("size", json.size());
            validateMap.put("empty", json.size() == 0);
            validateMap.put();
            validateMap.put();

        } catch (Exception exception) {

        }
        return validateMap;
    }

    //TODO - fix end of getRemoteAddress below
    //TODO study "/ concatenation below
    public String code(HttpServletRequest request) {
        return "alert(\"Your IP address is:" + request.getRemoteAddr() + "\");";

    }

    //TODO make sure cookie is setting correct ms since epoch
    public HashMap<String, String> cookie(HttpServletResponse response) {

        var cookieMap = new HashMap<String, String>();
        Cookie cookie = new Cookie("jsontestdotcom", String.valueOf(System.currentTimeMillis()));

        response.addCookie(cookie);

        cookieMap.put("cookie_status", "Cookie set with name jsontestdotcom");

        return cookieMap;
    }


    //TODO compare to Adam's version spec. line 138/139
    public HashMap<String, String> md5(String text) throws NoSuchAlgorithmException {

        var md5Map = new HashMap<String, String>();

        MessageDigest md = MessageDigest.getInstance("md5");
        byte[] messageDigest = md.digest(text.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        String hashText = number.toString(16);

        md5Map.put("original", text);
        md5Map.put("md5", hashText);

        return md5Map;
    }
}