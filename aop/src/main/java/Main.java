import applications.Application;
import profiler.Profiler;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Application testApplication = new Application();

        testApplication.a();
        testApplication.b();
        testApplication.c();

        Profiler.printStatistics();
    }

}
