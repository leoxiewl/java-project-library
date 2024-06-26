假设要开发一个购物车下单的功能，针对不同用户进行不同处理：
- 普通用户需要收取运费，运费是商品价格的 10%，无商品折扣；
- VIP 用户同样需要收取商品价格 10% 的快递费，但购买两件以上相同商品时，第三件开始享受一定折扣；
- 内部用户可以免运费，无商品折扣。

我们的目标是实现三种类型的购物车业务逻辑，**把入参 Map 对象（Key 是商品 ID，Value 是商品数量），转换为出参购物车类型 Cart**。

转换出参购物车类型要做的事情
- 将Map购物车转换为列表
- 处理运费和商品优惠——不同用户规则不一样
- 计算商品总价
- 计算运费总价
- 计算总优惠

代码重复本身不可怕，可怕的是漏改或改错。比如，写 VIP 用户购物车的同学发现商品总价计算有 Bug，不应该是把所有 Item 的 price 加在一起，而是应该把所有 Item 的 price*quantity 加在一起。这时，他可能会只修改 VIP 用户购物车的代码，而忽略了普通用户、内部用户的购物车中，重复的逻辑实现也有相同的 Bug。

电商的营销玩法是多样的，以后势必还会有更多用户类型，需要更多的购物车。我们应该优化这个代码，减少代码的重复出现。
把重复的逻辑定义在抽象类中，三个购物车只要分别实现不同的那份逻辑。
解决方案：
- 模板方法模式
  我们在父类中实现了购物车处理的流程模板，然后把需要特殊处理的地方留空白也就是留抽象方法定义，让子类去实现其中的逻辑。由于父类的逻辑不完整无法单独工作，因此需要定义为抽象类。
- 
