package net.yorksolutions.jsontest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

@RestController
@RequestMapping("/")
public class RequestController {

    public RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/ip")
    public HashMap<String, String> ip(HttpServletRequest request) {
        return requestService.ip(request);
    }

    @GetMapping("/headers")
    public HashMap<String, String> headers(HttpServletRequest request) {
        return requestService.headers(request);
    }
//add separate time function
    @GetMapping(value = {"/date", "/time"})
    public HashMap<String, String> date() {
        return requestService.dateAndTime();
    }

    @GetMapping ("/echo/**")
    public HashMap<String, String> echo(HttpServletRequest request) {
        return requestService.echo(request);
    }

    //TODO current: validate
    //jsontest.com recommends using POST request rather than GET request
    @PostMapping ("/validate")
    public HashMap validate(@RequestParam String json) {
        return requestService.validate(json);
    }

    //"code" is hooked up but doesn't function yet
    @GetMapping("/code")
    public String code(HttpServletRequest request) {
        return requestService.code(request);
    }

    @GetMapping("/cookie")
    public HashMap<String, String> cookie(HttpServletResponse response) {
        return requestService.cookie(response);
    }

    @GetMapping("/md5")
    public HashMap<String, String> md5(@RequestParam String text) throws NoSuchAlgorithmException {
        return requestService.md5(text);
    }
}