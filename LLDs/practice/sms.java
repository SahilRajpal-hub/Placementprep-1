package Placementprep.LLDs.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class sms {
    public static final Integer MAX_ATTEMPT = 30;

    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 20, 2, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10),Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    
    public static void main(String[] args) {
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("123");
        phoneNumbers.add("1234");
        phoneNumbers.add("1235");
        // executor.allowCoreThreadTimeOut(true);

        phoneNumbers.stream().forEach(phoneNumber -> sendSms(phoneNumber,1));

        try{
            Thread.sleep(20000);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            executor.shutdown();
        }
    }
    public static void sendSms(String phoneNumber,int attempt){
        CompletableFuture
        .runAsync(() -> {
            try{
                // API call to send sms
                Thread.sleep(1000);
                if(Math.random()>0.2) throw new Exception("api error");
            }catch(Exception e){
                throw new RuntimeException(e.getMessage());
            }
        },executor)
        .handleAsync((res,execpton) -> {
            if(execpton!=null) {
                System.out.println("Current attemp for the number "+phoneNumber+ " is "+attempt);
                if(!(attempt>MAX_ATTEMPT)) sendSms(phoneNumber,attempt+1);
            }else{
                System.out.println("SMS sent successfully to the phone number "+phoneNumber);
            }

            return null;

        },executor);
    }

}
