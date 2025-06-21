package Placementprep.LLDs.practice;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class SMSQuestion {
    public static final Integer MAX_ATTEMPT = 3;

    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 7, 10L,TimeUnit.MINUTES , new ArrayBlockingQueue<>(10),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
    public static void main(String[] args) {

        List<String> phoneNumberList = List.of("12345","223344","123334");

        for(String number:phoneNumberList){
            sendSmsWithRetry(number,0);
        }


        // sleeping main thread to give time to send sms function to work
        try {
            Thread.sleep(20000);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private static void sendSmsWithRetry(String number,int attempt){
        try{
            CompletableFuture.supplyAsync(() -> {
                try {
                    sendSms(number);
                } catch (Exception e) {
                    throw new RuntimeException("Error ocuured");
                }
                return "SMS request successfully added to the queue with thread "+Thread.currentThread().getName();
            },executor).handleAsync((String res,Throwable err) -> {
                System.out.println(res);
                if(err!=null){
                    if(attempt<MAX_ATTEMPT){
                        sendSmsWithRetry(number, attempt+1);
                    }else{
                        System.out.println("Max attempt reached for sending sms to the number "+number);
                    }
                }

                return null;
            },executor);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void sendSms(String number) throws Exception{
        try{
            if(Math.random()>0.2){
                throw new Exception("SMS error");
            }
            System.out.println("sending sms to the "+number+ " with thread "+ Thread.currentThread().getName() );
            Thread.sleep(1000);
            System.out.println("SMS sent to the "+number+ " with thread "+ Thread.currentThread().getName() );
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
