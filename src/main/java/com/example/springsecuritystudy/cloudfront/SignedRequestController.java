package com.example.springsecuritystudy.cloudfront;

import com.amazonaws.services.cloudfront.CloudFrontCookieSigner;
import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import com.amazonaws.util.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping(path = "/cloudfront/")
@Controller
public class SignedRequestController {
    String distributionDomain = "vod.seongho.xyz";
    String keyPairId = "APKAIV5PWVCOO6NNC77Q";

    @GetMapping(path = "createSignedUrl", produces = "application/json")
    @ResponseBody
    public Map<String, Object> createSignedUrl(HttpServletResponse servletResponse) throws InvalidKeySpecException, IOException {
        SignerUtils.Protocol protocol = SignerUtils.Protocol.http;
        Resource resource = new ClassPathResource("pk-APKAIV5PWVCOO6NNC77Q.pem");
        File privateKeyFile = resource.getFile();
        String s3ObjectKey = "test/Default/HLS/test_720.m3u8";
        //String s3ObjectKey = "test/Default/MP4/test.mp4";
        Date dateLessThan = DateUtils.parseISO8601Date(DateUtils.formatISO8601Date(DateTime.now(DateTimeZone.UTC).plusMinutes(10)));

        String url = CloudFrontUrlSigner.getSignedURLWithCannedPolicy(
                protocol, distributionDomain, privateKeyFile,
                s3ObjectKey, keyPairId, dateLessThan);

        Map<String, Object> result = new HashMap<>();
        result.put("url", "http://" + distributionDomain + "/" + s3ObjectKey);


        CloudFrontCookieSigner.CookiesForCannedPolicy cookiesForCannedPolicy =
                CloudFrontCookieSigner.getCookiesForCannedPolicy(protocol, distributionDomain, privateKeyFile, s3ObjectKey, keyPairId, dateLessThan);

        Cookie cookie = new Cookie(cookiesForCannedPolicy.getSignature().getKey(), cookiesForCannedPolicy.getSignature().getValue());
        cookie.setDomain("seongho.xyz");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(600);
        servletResponse.addCookie(cookie);

        return result;
    }

    @GetMapping(path = "setCookie", produces = "application/json")
    @ResponseBody
    public ResponseEntity setCookie(HttpServletResponse servletResponse) throws InvalidKeySpecException, IOException {
        SignerUtils.Protocol protocol = SignerUtils.Protocol.http;
        Resource resource = new ClassPathResource("pk-APKAIV5PWVCOO6NNC77Q.pem");
        File privateKeyFile = resource.getFile();
        String s3ObjectKey = "test/Default/HLS/test_720.m3u8";
        //String s3ObjectKey = "test/Default/MP4/test.mp4";
        Date dateLessThan = DateUtils.parseISO8601Date(DateUtils.formatISO8601Date(DateTime.now(DateTimeZone.UTC).plusMinutes(1)));

        CloudFrontCookieSigner.CookiesForCannedPolicy cookiesForCannedPolicy =
                CloudFrontCookieSigner.getCookiesForCannedPolicy(protocol, distributionDomain, privateKeyFile, s3ObjectKey, keyPairId, dateLessThan);

        Cookie cookie = new Cookie(cookiesForCannedPolicy.getSignature().getKey(), cookiesForCannedPolicy.getSignature().getValue());
        cookie.setDomain(distributionDomain);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        return new ResponseEntity(HttpStatus.OK);

    }
}
