import java.util.Arrays;

public class MyStack<E> {

    private final int DEFAULT_SIZE = 5;//栈初始大小
    private int maxSize;//栈的最大容量
    private E arrayObj[];//数组实现栈
    private int top;//栈顶指针  

    public MyStack() {
        this.maxSize = DEFAULT_SIZE;//初始化栈容量
        this.arrayObj = (E[]) new Object[this.maxSize];
        top = -1;
    }

    public MyStack(int size) {
        this.maxSize = size;
        this.arrayObj = (E[]) new Object[this.maxSize];
        top = -1;
    }

    public void push(E element) throws Exception {//入栈
        if (null == element) {
            throw new Exception("入栈的元素不能为空！");
        }
        if (isFull()) {
            //如果栈满，就扩容  
            extendSize();
        }
        this.arrayObj[++top] = element;//压入栈并栈顶指针上移
    }

    public E pop() throws Exception {//出栈
        if (empty()) {
            throw new Exception("栈空不能出栈");
        }
        return (E) this.arrayObj[top--];   //返回栈顶元素,栈顶指针下移,栈中元素减少一个
    }

    public E peek() throws Exception {//获取栈顶
        if (empty()) {
            throw new Exception("栈为空！");
        }
        return (E) this.arrayObj[getElementCount()];
    }

    public void extendSize() {//扩容
        this.maxSize = this.maxSize + this.DEFAULT_SIZE;
        Object[] newArray = new Object[this.maxSize];
        System.arraycopy(arrayObj, 0, newArray, 0, arrayObj.length);
        Arrays.fill(arrayObj, null);
        this.arrayObj = (E[]) newArray;
    }


    public boolean empty() {//判断栈是否为空
        return this.top == -1;
    }


    public boolean isFull() {//判断是否栈满
        return this.top + 1 == this.maxSize;
    }

    public int getElementCount() {
        return this.top;
    }
    public int size(){
        return top+1;
    }
}
