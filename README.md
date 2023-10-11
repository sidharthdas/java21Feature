# java21Feature

Virtual Threads:

![image](https://github.com/sidharthdas/java21Feature/assets/36167954/6e0660cd-56eb-40b3-891c-12aef867fc84)


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
