import java.util.Scanner;
import java.util.Stack;


public class Caculator {
    private Scanner scanner = new Scanner(System.in);
    private final double PI = 3.14159265358;//pi
    private final double E = 2.71828182846;//e
    private double ans;//运算时暂存结果
    private int priority(char ch)//优先级判断
    {
        int p = -3;//初始化
        if ((int) ch >= 48 && (int) ch <= 57 || ch == 'e' || ch == 'p') p = -1; //令数字和代表自然数e和pi的字母优先级为-1
        else if (ch == '+' || ch == '-') p = 1;//令加减符号优先级为1
        else if (ch == '*' || ch == '/') p = 2; //令乘除符号优先级为2
        else if (ch == '^') p = 3; //令^符号优先级为3
        else if (ch == 's' || ch == 'c' || ch == 't' || ch == 'l' || ch == 'o')
            p = 5;//令指定的几个代表sin、cos、tan、lg和ln函数字母优先级为5。
        return p;
    }

    public String chage(String originalExpression) {//把表达式简化
        StringBuffer stbf = new StringBuffer();
        char original[] = originalExpression.toCharArray();
        for (int i = 0; i < originalExpression.length(); i++) {
            if (priority(original[i]) == -1 || priority(original[i]) >= 1 && priority(original[i]) < 5 || original[i] == '(' || original[i] == ')' || original[i] == '.') {
                stbf.append(original[i]);
            } else if (original[i] == 's' && original[i + 1] == 'i') {
                stbf.append('s');
            } else if (original[i] == 'c') {
                stbf.append('c');
            } else if (original[i] == 't') {
                stbf.append('t');
            } else if (original[i] == 'l' && original[i + 1] == 'g') {
                stbf.append('l');
            } else if (original[i] == 'l' && original[i + 1] == 'o') {
                stbf.append('o');
            } else if (original[i] == 'p' && original[i + 1] == 'i') {
                stbf.append('p');
            } else if (original[i] == 'e') {
                stbf.append('e');
            } else if (original[i] == 'a' && original[i + 1] == 'b') {
                stbf.append('a');
            }
        }
        String newExpression = stbf.toString();
        return newExpression;
    }

    //把中缀表达式转化为后缀表达式
    private String infixToSuffix(String infix) throws Exception {
        StringBuffer stbf = new StringBuffer();
        MyStack<Character> mystack = new MyStack<>();//建立一个符号栈暂存符号
        char s[] = infix.toCharArray();
        int mark = 0;
        if (s[0] == '-') {
            stbf.append("0");
        }
        for (int i = 0; i < s.length; i++) {
            if (s[i] == '(' && s[i + 1] == '-')//负数
            {
                stbf.append(' ');
                stbf.append('-');
                mark++;
            } else if (s[i] == '-' && i != 0 && s[i - 1] == '(') ;
            else {
                if (s[i] != ')') {
                    if (s[i] == '(') {
                        stbf.append(' ');
                        mystack.push('(');
                    } else if (priority(s[i]) == -1 || s[i] == '.')//数字和小数点
                    {
                        stbf.append(s[i]);
                    } else//符号
                    {
                        stbf.append(' ');
                        if (mystack.empty())
                            mystack.push(s[i]);
                        else {
                            if (priority(s[i]) > priority(mystack.peek())) {
                                stbf.append(' ');
                                mystack.push(s[i]);
                            } else if (priority(s[i]) == priority(mystack.peek())) {
                                stbf.append(mystack.peek());
                                mystack.pop();
                                stbf.append(' ');
                                mystack.push(s[i]);
                            } else {//优先级小于栈顶元素
                                while (mystack.size() != 0) {
                                    if (mystack.peek() == '(') break;
                                    stbf.append(' ');
                                    stbf.append(mystack.peek());
                                    mystack.pop();
                                }
                                stbf.append(' ');
                                mystack.push(s[i]);
                            }
                        }
                    }
                } else//当s[i]==')'
                {
                    if (mark != 0)//判断括号前为负数
                    {
                        mark--;
                    } else {
                        while (mystack.peek() != '(') {
                            stbf.append(' ');
                            stbf.append(mystack.peek());
                            mystack.pop();
                        }
                        mystack.pop();
                    }
                }
            }
        }
        while (mystack.size() != 0) {
            stbf.append(' ');
            stbf.append(mystack.peek());
            mystack.pop();
        }
        String suffix = stbf.toString();
        return suffix;
    }

    private void add(MyStack<Double> container) throws Exception {//加
        ans = container.pop();
        ans += container.peek();
        container.pop();
        container.push(ans);
    }

    private void minus(MyStack<Double> container) throws Exception {//减
        ans = container.peek();
        container.pop();
        ans = container.peek() - ans;
        container.pop();
        container.push(ans);
    }

    private void mul(MyStack<Double> container) throws Exception {//乘
        ans = container.peek();
        container.pop();
        ans *= container.peek();
        container.pop();
        container.push(ans);
    }

    private void div(MyStack<Double> container) throws Exception {//除
        ans = container.pop();
        if (ans == 0)
            throw new ArithmeticException();//除0错误
        ans = container.peek() / ans;
        container.pop();
        container.push(ans);
    }

    private void pow(MyStack<Double> container) throws Exception {//乘方
        ans = container.pop();
        ans = Math.pow(container.peek(), ans);
        container.pop();
        container.push(ans);
    }

    private void sin(MyStack<Double> container) throws Exception {//正弦
        ans = Math.sin(container.pop());
        container.push(ans);
    }

    private void cos(MyStack<Double> container) throws Exception {//余弦
        ans = Math.cos(container.pop());
        container.push(ans);
    }

    private void tan(MyStack<Double> container) throws Exception {//正切
        ans = Math.tan(container.pop());//出栈并计算tan
        container.push(ans);//压入栈
    }

    private void ln(MyStack<Double> container) throws Exception {//自然对数
        ans = Math.log(container.peek());
        container.pop();
        container.push(ans);
    }

    private void lg(MyStack<Double> container) throws Exception {//常用对数
        ans = Math.log10(container.peek());
        container.pop();
        container.push(ans);
    }

    private void abs(MyStack<Double> container) throws Exception {//绝对值
        ans = Math.abs(container.peek());
        container.pop();
        container.push(ans);
    }

    private boolean isNumber(String e) {
        return e.matches("^-?[0-9]+") || e.charAt(0) > 47 && e.charAt(0) < 58;//判断是否是数字(实数)
    }

    private double calculate(String suffix) throws Exception {
        MyStack<Double> container = new MyStack<>();//用于计算的栈
        String exp[] = suffix.split(" ");//分割出计算的数和运算符
        for (int i = 0; i < exp.length; i++) {
            if (exp[i].isEmpty())
                continue;
            if (isNumber(exp[i])) {
                container.push(Double.valueOf(exp[i]));
            } else if (exp[i].charAt(0) == '-' && exp[i].toCharArray().length != 1) {
                if (isNumber(exp[i]))
                    container.push(Double.valueOf(exp[i]));
                else if (exp[i].charAt(0) == 'p') {
                    container.push(-PI);
                } else if (exp[i].charAt(0) == 'e') {
                    container.push(-E);
                }
            } else if (exp[i].charAt(0) == 'p') {
                container.push(PI);
            } else if (exp[i].charAt(0) == 'e')
                container.push(E);
            else if (exp[i].charAt(0) == '+')
                add(container);
            else if (exp[i].charAt(0) == '-')
                minus(container);
            else if (exp[i].charAt(0) == '*')
                mul(container);
            else if (exp[i].charAt(0) == '/')
                div(container);
            else if (exp[i].charAt(0) == '^')
                pow(container);
            else if (exp[i].charAt(0) == 's')
                sin(container);
            else if (exp[i].charAt(0) == 'c')
                cos(container);
            else if (exp[i].charAt(0) == 't')
                tan(container);
            else if (exp[i].charAt(0) == 'l')
                lg(container);
            else if (exp[i].charAt(0) == 'l' && exp[i].charAt(1) == 'g')
                lg(container);
            else if (exp[i].charAt(0) == 'l' && exp[i].charAt(1) == 'n')
                ln(container);
            else if (exp[i].charAt(0) == 'a')
                abs(container);
        }
        return container.pop();//返回最终结果并清空栈
    }

    public void start() {
        System.out.println("———————— This is a culculator ————————\n可计算+ - * /,乘方a^b,三角函数sin(x),cos(x),tan(x),lgx,lnx,可用pi和e");
        double result;//存放结果
        while (true) {//持续运行
            System.out.println("请输入你想计算的表达式:  ");
            try {
                String suffix = infixToSuffix(chage(scanner.next()));//转化成后缀表达式
                result = calculate(suffix);//计算后缀表达式的结果
                if (Math.abs(result) < 1e-5)
                    System.out.println("计算结果是:  0");
                else
                    System.out.printf("计算结果是:%.8g\n", result);
            } catch (ArithmeticException e) {
                System.out.println("0不能做除数!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

