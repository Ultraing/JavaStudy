package threadState;

/**
 * 示例2 - 多线程运行状态切换示例 <br/>
 * New: 尚未启动的线程的线程状态
 * Runnable: 可运行线程的线程状态, 等待CPU调度或者正在被调度的状态
 * Blocked: 线程阻塞, 等待监视器锁定的线程状态
 * Waiting: 等待线程的线程状态, 下列不带超时的方式: Object.wait, Thread.join, LockSupport.park
 * Timed Waiting: 具有指定等待时间的等待线程的线程状态, 下列带超时的方式: Thread.sleep, Object.wait, Thread.join, LockSupport.parkNaons, LockSupport.parkUntil
 * Terminated: 终止线程的线程状态, 线程正常执行完或者出现异常
 */
public class Demo2 {
	public static Thread thread1;
	public static Demo2 obj;

	public static void main(String[] args) throws Exception {
		// 第一种状态切换 - 新建 -> 运行 -> 终止
		System.out.println("#######第一种状态切换  - 新建 -> 运行 -> 终止################################");
		Thread thread1 = new Thread(() -> {
				System.out.println("thread1当前状态：" + Thread.currentThread().getState().toString());
				System.out.println("thread1 执行了");
		});
		System.out.println("没调用start方法，thread1当前状态：" + thread1.getState().toString());
		thread1.start();
		Thread.sleep(2000L); // 等待thread1执行结束，再看状态
		System.out.println("等待两秒，再看thread1当前状态：" + thread1.getState().toString());
		// thread1.start(); TODO 注意，线程终止之后，再进行调用，会抛出IllegalThreadStateException异常

		System.out.println();
		System.out.println("############第二种：新建 -> 运行 -> 等待 -> 运行 -> 终止(sleep方式)###########################");
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {// 将线程2移动到等待状态，1500后自动唤醒
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("thread2当前状态：" + Thread.currentThread().getState().toString());
				System.out.println("thread2 执行了");
			}
		});
		System.out.println("没调用start方法，thread2当前状态：" + thread2.getState().toString());
		thread2.start();
		System.out.println("调用start方法，thread2当前状态：" + thread2.getState().toString());
		Thread.sleep(200L); // 等待200毫秒，再看状态
		System.out.println("等待200毫秒，再看thread2当前状态：" + thread2.getState().toString());
		Thread.sleep(3000L); // 再等待3秒，让thread2执行完毕，再看状态
		System.out.println("等待3秒，再看thread2当前状态：" + thread2.getState().toString());

		System.out.println();
		System.out.println("############第三种：新建 -> 运行 -> 阻塞 -> 运行 -> 终止###########################");
		Thread thread3 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (Demo2.class) {
					System.out.println("thread3当前状态：" + Thread.currentThread().getState().toString());
					System.out.println("thread3 执行了");
				}
			}
		});
		synchronized (Demo2.class) {
			System.out.println("没调用start方法，thread3当前状态：" + thread3.getState().toString());
			thread3.start();
			System.out.println("调用start方法，thread3当前状态：" + thread3.getState().toString());
			Thread.sleep(200L); // 等待200毫秒，再看状态
			System.out.println("等待200毫秒，再看thread3当前状态：" + thread3.getState().toString());
		}
		Thread.sleep(3000L); // 再等待3秒，让thread3执行完毕，再看状态
		System.out.println("等待3秒，让thread3抢到锁，再看thread3当前状态：" + thread3.getState().toString());

	}
}