package Placementprep.LLDs.AmazonNotifyFeature;

import java.util.ArrayList;
import java.util.List;

public class main {

    public interface Observer {
        void update(Product p);
    }

    public interface Subject {
        void registerObserver(Observer o);
        void removeObserver(Observer o);
        void setData(int data);
    }

    public static class User implements Observer{
        public void update(Product product){
            System.out.println("Stock is updated for product : "+ product.name);
        }
    }

    public static class Product implements Subject{
        List<Observer> observers;
        int quantity;
        String name;

        Product(String name){
            this.quantity = 0;
            observers = new ArrayList<>();
            this.name = name;
        }
        
        public void registerObserver(Observer o){
            observers.add(o);
        }

        public void removeObserver(Observer o){
            observers.remove(o);
        }

        public void setData(int data){
            this.quantity = data;
            notifyAllObservers();
        }

        void notifyAllObservers(){
            observers.stream().forEach(o -> o.update(this));
        }
    }

    public static void main(String[] args) {
        Product iphone = new Product("IPhone");

        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        iphone.registerObserver(user1);
        iphone.registerObserver(user2);
        iphone.registerObserver(user3);

        iphone.setData(10);
    }
    
}
