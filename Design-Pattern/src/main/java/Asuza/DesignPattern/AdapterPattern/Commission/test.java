package Asuza.DesignPattern.AdapterPattern.Commission;
/**
 * * 客户端持有Target的引用，无法直接调用Adaptee中的方法
 * * Target的实现类实Adapter，**Adapter持有Adaptee的引用**，在request()中调用了Adaptee中的方法（转发调用）
 * * 客户端通过Target引用，注入Adapter对象，即可调用Adaptee的方法逻辑**/
public class test {
    public static void main(String[] args) {
        Adapte adapte=new Adapte(new use220V());
        adapte.use18V(); //220V的电压！ 使用适配器将220V电压改造成18V
    }
}
