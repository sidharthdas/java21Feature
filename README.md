# java21Feature

If processing takes longer time then the treads will not wait.


Output:

```
Virtual Thread : VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1
Virtual Thread : Thread[#23,Thread-0,5,main]
millis used to launch 10000vthreads:87ms
VirtualThread[#10031]/runnable@ForkJoinPool-1-worker-2
VirtualThread[#10031]/runnable@ForkJoinPool-1-worker-7
```

![image](https://github.com/sidharthdas/java21Feature/assets/36167954/1f23bab3-afe1-49a6-9af9-1bf9deb6da26)



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
