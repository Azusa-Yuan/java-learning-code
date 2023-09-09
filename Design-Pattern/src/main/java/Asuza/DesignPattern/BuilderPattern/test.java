package Asuza.DesignPattern.BuilderPattern;
/**
 * 1. Builder（抽象建造者）——抽象接口，定义了**各种组件的创建方法**（buildPartX()），**将组件拼装为复杂对象的方法**
 * 2. ConcreteBuilder（具体建造者）——实现抽象建造者
 * 3. Product——复杂的产品，包含多个组件
 * 4. Director（指挥者）
 *    * 隔离Client与复杂对象的创建过程（Client通过Director，指定**具体的构造者**，就可以获得**对应的复杂对象**，不需要关心这个对象如何组装的）
 *    * 指挥者控制复杂对象的创建过程——组件的创建顺序，如果将组件组装成复杂对象
 *    * 指挥者其实也可以省略，**将Director与抽象建造者合并**，但这种方法加重了抽象建造者的职责
 * **/
public class test {

    public static void main(String[] args) {
        houseBuilder houseBuilder=new pingFangHouseBuilder();
        houseDirector houseDirector=new houseDirector();
        houseDirector.makeHouse(houseBuilder);
        house house = houseBuilder.getHouse();
        System.out.println(house.getFloor()+house.getWall()+house.getHouseTop()); //平房--->地板平房--->墙平房--->屋顶
    }
}
