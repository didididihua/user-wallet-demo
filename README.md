# 用实现Java面试题的功能
> by:唐崇杰 13087736087 1514176166@qq.com

> github:https://github.com/didididihua/user-wallet-demo/tree/master

# 实现功能简介：
* 数据库表介绍：
  * `user_wallet`
  用户的钱包数据，一个用户一条数据
  * `user`
  用户个人信息数据，存储个人用户信息
  * `user_wallet_detail`
  用户钱包变动明细表，记录用户钱包变动的详细信息（变动金额，变动原因（提现、充值、返现等），变动类型（收入、支出），时间等）

* **查询用户钱包余额**
  * 这个功能直接对表 `user_wallet` 进行根据 `user_id` 查询即可
* **用户消费100元的接口**
* **用户退款20元接口**
  * 上面两个的功能因为引如了两中枚举值 `WalletUpdateSourceEnum` 和 `WalletUpdateTypeEnum`必须传入，那么每次用户钱包的金额变动便都知道这次变动的原因是什么，
    这次变动时收入还是支出，进行判断后便可以进行计算得到变动后的结果
  * 在进行时使用了自定义注解与AOP实现了限流的功能
  * 对传入的参数进行Hash预算存储到redis中，实现幂等的功能
* **查询用户钱包金额变动明细的接口**
  * 提供了对 `user_wallet_detail` 表的分页查询

> 具体实现请从UserWalletController开始看


# 项目启动：
* **导入数据库sql:**
    * 直接使用本项目sql文件夹中的api_db_sql.sql文件进行数据导入即可
* **更改数数据库配置:**
```yaml
spring:
  datasource:
  driver-class-name: com.mysql.cj.jdbc.Driver
  url: jdbc:mysql://localhost:3306/diso_db
  username: xxxx # 你的mysql的用户名称
  password: xxxx # 你的mysql的密码
```
* **该功能需要使用redis,所以更改redis的配置:**
```yaml
spring:
  redis:
    database: 1
    host: xxx.xxx.xxx.xxx # 运行redis的主机ip
    port: xxxx # redis使用的端口
    timeout: 5000
    password: xxxxx # redis的密码
```

运行后访问 `loclhost:8101/api/doc.html` 即可看到接口文档
