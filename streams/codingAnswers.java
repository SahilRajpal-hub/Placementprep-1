package Placementprep.streams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class codingAnswers {

    public static class Employee{
        String department;
        String name;

        Employee(String d,String name){
            this.department=d;
            this.name = name;
        }

        String getDepartment(){
            return department;
        }
    }
    
    public static void main(String[] args) {

        // 1.	Convert a list of strings to uppercase.
        List<String> list = new ArrayList<>();
        list.add("sahil");
        list.add("manu");
        list.stream().map(s -> s.toUpperCase()).forEach(System.out::println);

        // 2.	Find all even numbers from a list of integers.
        List<Integer> list2 = new ArrayList<>();
        list2.add(1); list2.add(2); list2.add(3); list2.add(4);
        list2.stream().filter(i -> i%2==0).forEach(System.out::println);

        // 3.	Count the number of elements greater than 10 in a list.
        List<Integer> list3 = new ArrayList<>();
        list3.add(1); list3.add(11); list3.add(10); list3.add(3); list3.add(17);
        System.out.println(list3.stream().filter(i -> i>10).count());

        // 4.	Find the first element that starts with a given prefix.
        List<String> list4 = new ArrayList<>();
        String prefxString = "sah";
        list4.add("sahil");
        list4.add("sahji");
        list4.add("manu");
        list4.add("sehli");
        list4.stream().filter(s -> s.startsWith(prefxString)).forEach(System.out::println);

        // 5.	Remove null values from a list.
        List<String> list5 = new ArrayList<>();
        list5.add("sahil");
        list5.add("null");
        list5.add("manu");
        list5.add(null);
        list5.stream().filter(s -> Objects.nonNull(s)).forEach(System.out::println);

        // 6.	Sort a list of integers using Stream.
        List<Integer> list6 = new ArrayList<>();
        list6.add(1); list6.add(11); list6.add(10); list6.add(3); list6.add(17);
        list6.stream().sorted(Collections.reverseOrder()).forEach(System.out::println);

        // 7.	Calculate the sum of all elements in a list.
        List<Integer> list7 = new ArrayList<>();
        list7.add(1); list7.add(11); list7.add(10); list7.add(3); list7.add(17);
        System.out.println(list7.stream().reduce((a,b) -> a+b).get());

        // 8.	Create a list of squares from a list of integers.
        List<Integer> list8 = new ArrayList<>();
        list8.add(1); list8.add(11); list8.add(10); list8.add(3); list8.add(17);
        list8.stream().map(x -> x*x).collect(Collectors.toList()).forEach(System.out::println);

        // 9.	Check if any element in a list is negative.
        list3.add(-1);
        System.out.println(list3.stream().anyMatch(x -> x<0));

        // 10.	Join a list of strings with a comma separator.
        System.out.println(list4.stream().reduce((x,y) -> x+","+y).get());



        // 11.	Get the top 3 highest numbers from a list.
        list7.stream().sorted(Collections.reverseOrder()).limit(3).forEach(System.out::println);

        // 12.	Find the frequency of each character in a string.
        String s = "sshhggtys";
        System.out.println(
            s.chars().mapToObj(c -> (char)c).collect(Collectors.groupingBy(c -> c, Collectors.counting()))
        );

        // 13.	Group a list of employees by department.
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("eng","sahl"));
        employees.add(new Employee("bd","rekha"));
        Map<String, List<Employee>> byDept = 
            employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));
        System.out.println(byDept);


    }
}
