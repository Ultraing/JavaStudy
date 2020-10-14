package threadState;

/**
 * 示例1 - JAVA程序执行分析
 * 可以用javac命令将.java文件编译成.class文件
 * 再用javap命令对.class文件进行反编译
 */
public class Demo1 {
	public int x;

	public int sum(int a, int b) {
		return a + b;
	}

	public static void main(String[] args) {
		Demo1 demo1 = new Demo1();
		demo1.x = 3;
		int y = 2;
		int z = demo1.sum(demo1.x, y);
		System.out.println("person age is " + z);
	}


}
