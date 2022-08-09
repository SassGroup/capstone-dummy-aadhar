package com.example.Controller;

import com.example.DAO.dummyDAO;
import com.example.Service.EmaiVarificationByOTP;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
/**
 * Author Rocky
 */
@Controller("/verifyemail")
public class DAController {

    HashMap<String,String> aadharOTP = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(DAController.class);
    private static Random random = new Random();

// .secretKeyFor(SignatureAlgorithm.HS256);
//    private static final String secret = "asdfSFS34wzDZhmCfz7QtRKXoyp1kprcVZC4EdSgSOWEK5354fdgdf4";
//    private static final String secretKey = "ssshhhhhhhhhhh!!!!dasdasfsafqfqwfqwfwfsafasfasfasfsasad";

    private static final ObjectMapper objMapper = new ObjectMapper();

    @Inject
    EmaiVarificationByOTP emaiVarificationByOTP;

    dummyDAO d = new dummyDAO();

    @Post(uri = "/", produces = "application/json")
    public CompletableFuture<Map<String, Object>> getPosidex(Map<String, Object> contextMap) throws IOException {
        int OTPMaxRandom = 9999;
        CompletableFuture<Map<String, Object>> cf = new CompletableFuture<Map<String, Object>>();
        String otp = String.format("%04d", random.nextInt(OTPMaxRandom));

// SendMail.testMail(cf,otp);
        Map<String,Object> otpMap = new HashMap<String, Object>(){{
            put("OTP",otp);
            put("random",String.format("%06d", random.nextInt(OTPMaxRandom)));
        }};
        String y = (String) contextMap.get("aadharNo");
        String emailID = (String) contextMap.get("emailID");
//System.out.println(contextMap.get("aadharNo"));
//System.out.println(contextMap.get("aadharNo").getClass());
// = new dummyDAO();
        String verified = d.checkAerospike(y);
        String otpJSON = objMapper.writeValueAsString(otpMap);
//  String otpEncrypted = AES.encrypt(otpJSON, secretKey);
        cf.complete(new HashMap<String, Object>(){{
            put("message","aadhar found");
            put("aadharNo",y);
            put("otp",otp);
        }});
        if (verified=="present"){
            System.out.println("The OTP generated is "+otp);
            aadharOTP.put(y,otp);
            emaiVarificationByOTP.EmailVerification(emailID, y, otp);
        }
        else{
            System.out.println("No OTP generated");
        }
        return cf;
// logger.info("Posidex Service Request thread started : payload = {}", contextMap);
// long start = System.currentTimeMillis();
// MDC.put(PosidexConstants.UUID, contextMap.get("UUID").toString());
// MDC.put(PosidexConstants.APPLICATION_ID, contextMap.get("applicationId").toString());
// CompletableFuture<Map<String, Object>> cf = new CompletableFuture<Map<String, Object>>();
// // IOThreadDispatcher<Map<String, Object>> iod = new IOThreadDispatcher<Map<String, Object>>(PosidexConstants.IO_THREAD_CORE_POOL_SIZE, PosidexConstants.IO_THREAD_MAX_POOL_SIZE, PosidexConstants.IO_THREAD_KEEP_ALIVE_TIME, PosidexConstants.POSIDEX_IO_THREAD_POOL_NAME, PosidexConstants.IO_THREAD_MAX_INPUT_WAIT_QUEUE);
// logger.info("IO Thread  Created for Posidex request ");
// PosidexProcessor processor = new PosidexProcessor();
// Map<String, Object> processorContextMap = new HashMap<>();
// processorContextMap.put(PosidexConstants.SERVICE_EXECUTOR, processor);
// processorContextMap.put(PosidexConstants.DATA, contextMap.get("payload"));
// processorContextMap.put(PosidexConstants.COMPLETABLE_FUTURE, cf);
// processorContextMap.put(PosidexConstants.UNIQUE_IDENTIFICATION_ID, contextMap.get("UUID").toString());
// processorContextMap.put(PosidexConstants.APPLICATION_ID, contextMap.get("applicationId").toString());
// processor.setContextMap(processorContextMap);
// // iod.executeProcess(processorContextMap);
// logger.info("Posidex Request thread released........");
// long end = System.currentTimeMillis();
// logger.info("Total time in Posidex Request thread releasing:  {}",(end - start));
// MDC.clear();
// return cf;

    }


    @Post(uri = "/verifyotp", produces = "application/json")
    public CompletableFuture<Map<String, Object>> VerifyGetPosidex(Map<String, Object> contextMap) throws IOException {
        CompletableFuture<Map<String, Object>> cf = new CompletableFuture<Map<String, Object>>();
//        String otpEcryptedData = (String) contextMap.get("encryptedOTPdata");
        int flag = 0;

// System.out.println(aadharOTP);
//   String otpDecrypted = AES.decrypt(otpEcryptedData, secretKey);
//   Map<String,Object> otpMap = objMapper.readValue(otpDecrypted, Map.class);
//   String otpSent = (String) otpMap.get("OTP");
        String otpReceived = (String) contextMap.get("otp");
        String aadharRecieved = (String) contextMap.get("aadharNo");
        System.out.println(otpReceived);
        if(aadharOTP.containsKey(aadharRecieved)){
            System.out.println(aadharOTP.get(aadharRecieved));
            if(aadharOTP.get(aadharRecieved).equals(otpReceived)){
                System.out.println("OTP is present and verified");
                Map<?,?> x = d.fetchAerospike(aadharRecieved);
                cf.complete(new HashMap<String, Object>(){{
//put("message","otp valid");
                    put("aadharData",x);
                }});
            }else{
                cf.complete(new HashMap<String, Object>(){{
                    put("message","incorrect otp entered for this aadhar no");
                }});}
        }
        else{
            cf.complete(new HashMap<String, Object>(){{
                put("message","aadhar invalid");
            }});
        }

//   Key key = Keys.hmacShaKeyFor(secret.getBytes());
        String jws = " ";
//   if(otpSent.equals(otpReceived)){
//        //    jws = Jwts.builder().setSubject("Shatru").claim("id", "007").signWith(key).compact();
//        }
//        String jwt =jws;
//        System.out.println("JWT   ----   "+jwt);
//        cf.complete(new HashMap<String,Object>(){{
//            put("JWT", jwt);
//        }});
//        //String otp = String.format("%06d", random.nextInt(999999));
//        //SendMail.testMail(cf,otp);
//        // cf.complete();
//        System.out.println("Controller end....");

        return cf;
    }




}