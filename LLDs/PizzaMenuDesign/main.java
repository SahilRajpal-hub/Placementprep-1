package Placementprep.LLDs.PizzaMenuDesign;

public class main {
    public interface BasePizza{
        int getCost();
    }

    public static class VeggiePizza implements BasePizza{
        public int getCost(){
            return 10;
        }
    }

    public static class MushroomPizza implements BasePizza{
        public int getCost(){
            return 15;
        }
    }

    public static abstract class AddOnDecorator implements BasePizza{
        BasePizza basePizza;
        AddOnDecorator(BasePizza basePizza){
            this.basePizza = basePizza;
        }
    }

    public static class CheeseAddonPizza extends AddOnDecorator{
        CheeseAddonPizza(BasePizza basePizza){
            super(basePizza);
        }

        public int getCost(){
            return basePizza.getCost() + 5;
        }
    } 

    public static class ToppinsAddonPizza extends AddOnDecorator{
        ToppinsAddonPizza(BasePizza basePizza){
            super(basePizza);
        }

        public int getCost(){
            return basePizza.getCost() + 3;
        }
    } 

    public static class OriganoAddonPizza extends AddOnDecorator{
        OriganoAddonPizza(BasePizza basePizza){
            super(basePizza);
        }

        public int getCost(){
            return basePizza.getCost() + 3;
        }
    } 

    public static void main(String[] args) {
        BasePizza order = new MushroomPizza();
        order = new CheeseAddonPizza(order);
        order = new CheeseAddonPizza(order);
        order = new OriganoAddonPizza(order);

        System.out.println(order.getCost());

    }

}
