# java21Feature



Virtual Threads:

![image](https://github.com/sidharthdas/java21Feature/assets/36167954/6e0660cd-56eb-40b3-891c-12aef867fc84)

VT: Virtual Thread.

PT: Platform Thread.

When any VT is busy working and it takes longer time, it will break the connection with PT as shown in the above picture. So this means the tread can work on different tasks rather than waiting for the current task to be complete.

If processing takes longer time then the treads will not wait.


Output:

```
Virtual Thread : VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1
Virtual Thread : Thread[#23,Thread-0,5,main]
millis used to launch 10000vthreads:87ms
VirtualThread[#10031]/runnable@ForkJoinPool-1-worker-2
VirtualThread[#10031]/runnable@ForkJoinPool-1-worker-7
```



Note:
Virtual thread works in an async way, so if you have a normal Java program like below it may or may not print in the console
```
Thread.ofVirtual().start(() -> {
            System.out.println(Thread.currentThread());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread());

        });
```
To avoid that use
```
public static void main(String[] args) throws InterruptedException {

        Thread.ofVirtual().start(() -> {
            System.out.println(Thread.currentThread());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread());

        });

        int s = 0;

        while(s != 10){
            System.out.println(s);
            Thread.sleep(1000);
            s++;
        }
}
```


Before java21, at line no 82, when we do Thread.sleep(), it blocks the thread for that many milliseconds, but in java21, the virtual thread cuts the relation and work on other assigned task, and once the milliseconds is passed, it will assign another virtual thread to do the duty. In this scenario, the virtual thread might be the same or different, it completely depends on the JVM.

Execute the below code to see the Thread names
```
public class MainClass {

    public static void main(String[] args) throws InterruptedException {

        var t = IntStream.range(0,5)
                        .mapToObj(x -> Thread.ofVirtual().unstarted(() -> {
                            if(x == 0) {

                                System.out.println("sid : " + Thread.currentThread());
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }

                                System.out.println("sid 2 : " + Thread.currentThread());
                            }
                            else {
                                System.out.println("sidharth : " + Thread.currentThread());
                            }

                        }))
                                .toList();

        t.forEach(Thread::start);
        for(Thread t1 : t) {
            t1.join();
        }

        //Thread.sleep(3000);
    }
```

# Sealed class: 
Parent and child will be in the same package. And sealed class means, only the class can permits the classes who can extend the parent class

```
Parent:
public sealed class LivingBeing permits Human, Animal{
    public void eat() {
        System.out.println("Living being eats");
    }
}

Child:
public final class Animal extends LivingBeing {

    @Override
    public void eat() {
        System.out.println("In Aminal");
        super.eat();
    }
}

public final class Human extends LivingBeing{

    @Override
    public void eat() {
        System.out.println("in human");
        super.eat();
    }
}
```

# Instance of :

```
public class MainClass4 {

    public static void main(String[] args) {

        var studentX = new StudentX("Sidharth", "Odisha");

        if (studentX instanceof StudentX x) {
            x.print();
        }
    }
}

record StudentX(String studentName, String studentAddress) {
    public StudentX(String studentName) {
        this(studentName, null);
    }

    public void print() {
        System.out.println("Student name is : " + this.studentName + " and student address is : " + this.studentAddress);
    }
}
```

# Enhanced Switch Case:

```
public class EnhancedSwitchCase {

    public static void main(String[] args) {
        //Classic Switch test:
        System.out.println(classicSwitchCase(DayOfWeek.MONDAY));
        System.out.println(classicSwitchCase(DayOfWeek.SATURDAY));

        //Enhanced Switch test:
        System.out.println(enhancedSwitchCase(DayOfWeek.MONDAY));
        System.out.println(enhancedSwitchCase(DayOfWeek.SATURDAY));
    }


    public static int enhancedSwitchCase(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> 16;
            case SATURDAY, SUNDAY -> 24;
        };
    }

    public static int classicSwitchCase(DayOfWeek dayOfWeek) {
        int time = 0;
        switch (dayOfWeek) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY:
                time = 16;
                break;

            case SATURDAY, SUNDAY:
                time = 24;
                break;

            default:
                throw new RuntimeException("Not valid");
        }

        return time;
    }
}
```



# Poducer Consumer using java threading:

```
public class MainClass {

    static List<String> l = new ArrayList<>();
    static int counter = 0;
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while(true) {
                l.add ("Sidharth - " + counter);
                counter++;
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(() -> {
            int count = 0;

            while(true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(count != counter) {
                    System.out.println(l);
                    count = counter;
                }

            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();




    }
}
```

























