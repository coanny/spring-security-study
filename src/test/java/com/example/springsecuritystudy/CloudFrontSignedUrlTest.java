package com.example.springsecuritystudy;

import com.amazonaws.services.cloudfront.CloudFrontCookieSigner;
import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import com.amazonaws.util.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalUnit;
import java.util.Date;

public class CloudFrontSignedUrlTest {

    @Test
    public void createSignedUrl() throws InvalidKeySpecException, IOException {
        SignerUtils.Protocol protocol = SignerUtils.Protocol.http;
        String distributionDomain = "vod.seongho.xyz";
        Resource resource = new ClassPathResource("pk-APKAIV5PWVCOO6NNC77Q.pem");
        File privateKeyFile = resource.getFile();//new File("/path/to/cfcurlCloud/rsa-private-key.pem");
        String s3ObjectKey = "test/Default/HLS/test_720.m3u8";
        //String s3ObjectKey = "test/Default/MP4/test.mp4";

        String keyPairId = "APKAIV5PWVCOO6NNC77Q";
        Date dateLessThan = DateUtils.parseISO8601Date(DateUtils.formatISO8601Date(DateTime.now(DateTimeZone.UTC).plusMinutes(5)));
                //DateUtils.parseISO8601Date("2020-11-14T22:20:00.000Z");
//        Date dateGreaterThan = DateUtils.parseISO8601Date("2011-11-14T22:20:00.000Z");
//        String ipRange = "192.168.0.1/24";

        String url1 = CloudFrontUrlSigner.getSignedURLWithCannedPolicy(
                protocol, distributionDomain, privateKeyFile,
                s3ObjectKey, keyPairId, dateLessThan);
        System.out.println(url1);
//        String url2 = CloudFrontUrlSigner.getSignedURLWithCustomPolicy(
//                protocol, distributionDomain, privateKeyFile,
//                s3ObjectKey, keyPairId, dateLessThan,
//                dateGreaterThan, ipRange);
    }

    @Test
    public void t() throws IOException, InvalidKeySpecException {
        SignerUtils.Protocol protocol = SignerUtils.Protocol.https;
        Resource resource = new ClassPathResource("pk-APKAIV5PWVCOO6NNC77Q.pem");
        File privateKeyFile = resource.getFile();
        String s3ObjectKey = "test/Default/HLS/test_720.m3u8";
        //String s3ObjectKey = "test/Default/MP4/test.mp4";
        Date dateLessThan = DateUtils.parseISO8601Date(DateUtils.formatISO8601Date(DateTime.now(DateTimeZone.UTC).plusMinutes(1)));

        CloudFrontCookieSigner.CookiesForCannedPolicy cookiesForCannedPolicy =
                CloudFrontCookieSigner.getCookiesForCannedPolicy(protocol, "vod.seongho.xyz", privateKeyFile, s3ObjectKey, "APKAIV5PWVCOO6NNC77Q", dateLessThan);
        System.out.println(cookiesForCannedPolicy.getSignature().getKey());
        System.out.println(cookiesForCannedPolicy.getSignature().getValue());
        System.out.println(cookiesForCannedPolicy.getKeyPairId().getKey());
        System.out.println(cookiesForCannedPolicy.getKeyPairId().getValue());
    }
}
