# doc和tools

```JAVA
1. https://pay.weixin.qq.com/wiki/doc/api/index.html  微信开发文档

2. https://natapp.cn  内网穿透工具  也可以购买服务器和域名
内网穿透可以实现自己电脑就是一个服务器，本机的服务，外网也可以使用
.\natapp -authtoken=bda5258f4334604a

3. https://github.com/Wechat-Group/weixin-java-tools  微信第三方SDK 获取openid用于授权

4. fiddler  抓包工具 可以通过手机修改ip和端口号，然后使用抓包工具在电脑监听端口号，实现手机通过转换ip变成pc端来访问目标地址

5.  https://github.com/Pay-Group/best-pay-sdk  包含微信支付和支付宝支付的sdk

6.  ibootstrap.cn bootstrap前端快速搭建工具
```



# Mysql数据库

### mysql5.7安装

错误解决:

```JAVA
如果报错：mysqld: Can't create directory 'F: oftware\mysql-5.7.26-winx64\data' 可以将my.ini中的basedir和datadir中的“\”改为“/”
```

1.将包放到指定目录

2.配置bin环境变量

3.新建my.ini文件（解压包里是没有my-dafault.ini或自带my.ini文件，需自己创建）编辑写入以下信息

```JAVA
[mysql]
# 设置mysql客户端默认字符集
default-character-set=utf8 
[mysqld]
# 设置3306端口
port = 3306 
# 设置mysql的安装目录
basedir=C:\Program Files\mysql-5.7.21-winx64
# 设置mysql数据库的数据的存放目录
datadir=C:\Program Files\mysql-5.7.21-winx64\data
# 允许最大连接数
max_connections=200
# 设置mysql服务端默认字符集
character-set-server=utf8
# 创建新表时将使用的默认存储引擎
default-storage-engine=INNODB 
```

4.mysql初始化，以**管理员权限**打开cmd命令窗口，切换到”C:\Program Files\mysql-5.7.21-winx64\bin”路径下，输入指令 **mysqld --initialize --user=mysql --console** ，该命令会在mysql-5.7.21-winx64目录下创建data文件夹及初始数据库，生成root用户和临时密码
5.安装mysql服务，继续在窗口输入指令`mysqld install MySQL --defaults-file="C:\Program Files\mysql-5.7.21-winx64\my.ini"`路径是你my.ini文件的绝对路径 

6.启动mysql服务，输入 net start mysql，启动成功，会出现下面的截图。**如果服务一直处于启动中，说明上一步的操作有误，核实my.ini文件路径是否正确** 

7.修改root用户密码 输入 mysql -u root -p，**然后输入5步骤中生成的临时密码**。修改密码命令：set password = password(‘新密码’); 注意语句末尾带分号，show databases; 查看一下初始的数据库。 



### Mysql自动时间创建

5.6.5之后的MySQL才可以

`create_time` timestamp not null **default current_timestamp** comment '创建时间',
`update_time` timestamp not null **default current_timestamp  on update current_timestamp** comment  '更新时间',

### utf-8 小知识

utf8mb4 -- UTF-8 Unicode  可以存放表情

UTF-8  不可以



### 索引

**根据什么查就给这个字段建索引**

```JAVA
create table `order_detail`(
	`detail_id` varchar(32) not null,
	`order_id` varchar(32) not null,
	`product_id` varchar(32) not null,
	`product_name` varchar(64) not null comment '商品名称',
	`product_price` decimal(8,2) not null comment '商品价格',
	`product_quantity` int not null comment '商品数量',
	`product_icon` varchar(512)  comment '商品小图',
	`create_time` timestamp not null default current_timestamp comment '创建时间',
	`update_time` timestamp not null default current_timestamp  on update current_timestamp comment '修改时间',
	primary key(`detail_id`),
	key `idx_order_id` (`order_id`)
)comment '订单详情表'

```



key 是数据库的**物理结构**，它包含两层意义和作用，

**一是约束（偏重于约束和规范数据库的结构完整性），**

**二是索引（辅助查询用的）。**



**primary key** 有两个作用，一是约束作用（constraint），用来**规范一个存储主键和唯一性**，但同时也在此key上建立了一个主键索引；    

 PRIMARY KEY 约束：唯一标识数据库表中的每条记录；

​                                     主键必须包含唯一的值；

​                                     主键列不能包含 NULL 值；

​                                     每个表都应该有一个主键，并且每个表只能有一个主键。（PRIMARY KEY 拥有自动定义的 UNIQUE 约束）

```JAVA
  primary key(`product_id`)
```





**unique key** 也有两个作用，一是约束作用（constraint），规范数据的**唯一性**，但同时也在这个key上建立了一个唯一索引；

```JAVA
uique key `uqe_category_type` (category_type)
```



**key** 是普通索引

```JAVA
key `idx_buyer_openid` (`buyer_openid`)
```



### MySQL运行存储过程出现1292错误

mysql 默认是不允许出现伪日期的，关闭NO_ZERO_IN_DATE和NO_ZERO_DATE

```JAVA
set global sql_mode=’STRICT_TRANS_TABLES,STRICT_ALL_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER’;
```

如果在执行过程中出现1055错误

```JAVA
show variables like "sql_mode";
set sql_mode=''；
set sql_mode='NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES';

```

show variables like ‘%sql_mode%’;查看关于sql_mode的变量，如果没有NO_ZERO_IN_DATE和NO_ZERO_DATE就说明修改成功

### hibernate_sequence' doesn't exist

设置id为@GeneratedValue(strategy = GenerationType.IDENTITY)  默认是递增

默认是@GeneratedValue(strategy = GenerationType.AUTO)  自动



# 虚拟机

首先要保证你的网络和你的虚拟机网络在同一网段，可以ping通

# BUG

1. ```
   BeanUtils.copyProperties(orderMaster,orderdto);
   拷贝属性后之前赋值的会被覆盖
   
   
   ```

# 日志框架

springBoot自带的logback和slf4j   logback和log4j是同一个作者，logback是它的升级版。  



### yml中的使用 

```JAVA
logging:
  pattern:
    console: "%d - %msg%n"                      // %d 是时间  %msg是信息  %n是换行
  file: /var/log/tomcat/sell/					// 在根目录下生成日志文件
  level:
    com.example.sell.SellApplicationTests: info  // 设置等级是info info一下的级别就不会打印
        
 spring:      
   jackson:
    default-property-inclusion: non_null  为空的话不返回

```

### logback-spring.xml

```JAVA
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
    <!--控制台输出-->
    <appender name="ConsoleLog" class="ch.qos.logback.core.ConsoleAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <pattern>
                %d - %msg%n
            </pattern>
        </layout>
    </appender>

    <!--打印到本地文件 只输出info信息-->
    <appender name="fileInfoLog" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <!--匹配的话  deny被拒绝 也就是不显示-->
            <onMatch>DENY</onMatch>
            <!--不匹配的话就执行accept（接受） 命中当前规则
             如果是NEUTRAL（中立） 就是忽略当前appender的规则 匹配下一条规则-->
            <onMismatch>ACCEPT</onMismatch>
        </filter>
        
        <encoder>
            <pattern>
                %msg%n
            </pattern>
        </encoder>
        <!--滚动策略 每天输出一个文件-->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--路径-->
            <fileNamePattern>/var/log/tomcat/sell/info.%d.log</fileNamePattern>
        </rollingPolicy>
        
    </appender>

    <!--打印到本地文件 只打印错误日志-->
    <appender name="fileErrorLog" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>ERROR</level>
        </filter>
        <encoder>
            <pattern>
                %msg%n
            </pattern>
        </encoder>
        <!--滚动策略 每天输出一个文件-->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--路径-->
            <fileNamePattern>/var/log/tomcat/sell/error.%d.log</fileNamePattern>
        </rollingPolicy>
    </appender>
    
            
    <!--使用上面的定义-->
    <root level="info">
        <appender-ref ref="ConsoleLog"/>
        <appender-ref ref="fileInfoLog"/>
        <appender-ref ref="fileErrorLog"/>
    </root>
</configuration>


```



# springBoot

### application.yml

```JAVA
spring
	server:
		context_path:  /sell
# 日志配置
logging.config=classpath:logback.xml

定义： server.context-path= # Context path of the application. 应用的上下文路径，也可以称为项目路径，是构成url地址的一部分。

如果没有这个配置访问controller就是  8080/user
配置的话就是    8080/test/user

```

### 解决mysql时间无法正常更新

```JAVA
     @Autowired
    private ProductCategoryRepository repository;	

	@Test
    public void Test2() {
//        先查询再修改会出现时间无法正常更新的问题
        ProductCategory productCategory = repository.findById(2).orElse(null);
        productCategory.setCategoryType(3);
        repository.save(productCategory);
    }


@Data
@Entity
@DynamicUpdate     // 动态更新
public class ProductCategory {

    /* 类目id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    /* 类目名字*/
    private String categoryName;
   
}


```

### 测试回滚

如果只是想要看是否执行成功，并不想在数据库留下垃圾数据，就可以使用这两个注解测试，不管怎样都回滚

```
    @Test
    @Transactional
    public void Test2() {
			
			
    }

```

### 断言测试判断

使用单元测试的时候可以使用断言进行测试 Assert是junit包下面的类

```JAVA
//       断言判断集合是否为null
        Assert.assertNotEquals(0,byCategoryTypeIn.size());
//       如果为false 显示输出信息
		Page<OrderMaster> list = orderService.findList(PageRequest.of(0, 2));
        Assert.assertTrue("查询订单失败",list.getTotalElements() < 0);

```

# 项目

### 买家商品展示

```JAVA
入参：无
返回: 不同的类别，以及类别下所有的商品信息

/*查询所有的上架商品*/
 List<ProductInfo> productInfoList = productService.findUpAll();

 /*查询类目*/
   List<Integer> list = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
    List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(list);

/*数据拼装*/
name
type
List<ProductInfoVO>  

ProductInfoVO中是商品的具体信息  productId,productName，productPrice等

```

### 创建订单

```JAVA
入参是 
name: "张三"
phone: "18868822111"
address: "慕课网总部"
openid: "ew3euwhd7sjw9diwkq" //用户的微信openid
items: [{
    productId: "1423113435324",
    productQuantity: 2 //购买数量
}]


1. 根据商品id查询商品信息
2. 计算总价
3. 插入到订单详情数据库
4. 插入到订单master数据库
5. 减库存
6. 发送websocket到后端 前端进行弹窗提示

```

### 取消订单

```JAVA
入参是订单对象

1.判断订单状态 如果不是新的订单直接抛出异常
2.修改订单状态 并保存到数据库 
3.加库存
4.判断支付状态是否已经支付，如果支付发起退款  


```

### 完结订单

```JAVA
入参是订单对象

1. 判断订单状态 如果不是新的订单直接抛出异常
2. 修改订单状态 并保存到数据库 
3. 发送模板消息 订单完结

```

### 支付订单

```JAVA
入参是订单对象

1. 判断订单状态 如果不是新的订单直接抛出异常
2. 判断支付状态
3. 修改支付状态

```

### 买家微信授权获取openid

```JAVA
1. 配置WxMpService  将appid和secret写入
2. 调用oauth2buildAuthorizationUrl方法，会返回code，并重定向userInfo
3. 在userInfo接口中调用oauth2getAccessToken方法，获取token。 token.getOpenid 获取用户openid
4. 重定向到returnUrl，并携带openid

```

### 微信支付

```JAVA
参数： orderId,returnUrl

1. 查询订单 判断订单是否存在
2. 发起支付   使用的第三方sdk
	# 配置wechatConfig
	# BestPayServiceImpl 类将配置set进去  
	# BestPayServiceImpl 类调用pay方法发起支付
	# 跳转到pay/create  
	# create.ftl 使用的是springBoot自带的freemarker模板引擎，将响应数据写入模板中
	
3. 微信支付异步通知地址notify接口，入参是支付返回的json数据	 
   这个接口就是判断支付状态并且修改数据库的支付状态
	# 验证签名
	# 验证支付的状态
	# 验证支付的金额
	# 验证支付人 （随情况而定）
4. 修改状态
5. 重定向到success.html  xml中有两个field <return_code>  <return_msg>

```

### 微信退款

```JAVA
直接是service 
使用第三方sdk 直接调用refund接口，传入订单号，订单总金额，订单支付类型就可以完成退款

```

### 登录

```JAVA
参数openid

1. 判断当前商户是否存在,不存在就抛出异常，找不到该用户
2. 将token写入redis
3. 将token写入cookie 
4. 跳转到首页 list

```

### 退出

```JAVA
1. 清除cookie数据 将原来的key设置为null 过期时间改为0
2. 清除redis 数据 
3. 跳转到首页

```

### 切面异常效验登录

```JAVA
1. 切面切所有的seller卖家Controller
@Pointcut("execution(public * com.example.sell.controller.Seller*.*(..))" +
"&& !execution(public * com.example.sell.controller.SellerUserController*.*(..))")
2. 前置通知检查cookie是否有token，没有抛出自定义异常
3. 前置通知检查redis是否有token,没有抛出自定义异常
4. 捕捉我们自定义的异常类，发现异常就跳转到微信登录的界面，并携带returnUrl

```

### 微信模板消息

```JAVA
1. 配置WxMpService  将appid和secret写入 第三方sdk
2. 配置模板id，接收者openid，设置模板数据
3. 通过sendTemplateMsg方法发送 

```



#### Cookie工具类

```JAVA
  /**
    * 根据name获取cookie
    *@param  [request, name]
    *@return javax.servlet.http.Cookie
    */
    public static Cookie get(HttpServletRequest request,
                             String name){
        HashMap<String, Cookie> cookieMap = getCookieMap(request);
        if (cookieMap!=null){
            for (String key : cookieMap.keySet()) {
                if (key.equals(name)){
                    return cookieMap.get(key);
                }
            }
        }
        return null;
    }


 private static HashMap<String,Cookie> getCookieMap(HttpServletRequest request){
        HashMap<String, Cookie> hashMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                if (cookie!=null){
                    hashMap.put(cookie.getName(),cookie);
                }
            }
            return hashMap;
        }
      return null;
    }

```

#### 接口常量的使用



```JAVA
/**
 * redis 常量
 * @Author: baiyj
 * @Date: 2019/10/23
 */

public interface RedisConstant {

    String TOKEN = "token_%s";

    Integer EXPIRE = 7200;

}

```



#### 枚举的使用

```JAVA
@Getter
public enum RestEnum {
    product_no_exist(10,"商品不存在");
    
    private Integer code;
    private String message;

    RestEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

```



#### 异常的使用

```JAVA
public class SellException extends RuntimeException {

    private Integer code;

    SellException(RestEnum restEnum){
        super(restEnum.getMessage());
        this.code = restEnum.getCode();
    }
}

```

#### 返回结果集的封装

```JAVA
package com.example.sell.utils;

import com.example.sell.VO.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object obj){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMessage("成功");
        resultVO.setData(obj);
        return resultVO;
    }

    public static  ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(msg);
        return resultVO;
    }
}


```



#### 生成唯一id（高并发）

多线程并发情况下可能会重复，所以添加一个synchronized关键字

```JAVA
public class KeyUtils {
      /**
       *  生成唯一的主键
       *@return
       */
      public static synchronized String getUniqueKey(){
          Random random = new Random();
          int i = random.nextInt(90000) + 100000;
          return System.currentTimeMillis() + String.valueOf(i);
      }
}

```

#### @Valid 和BindingResult的使用

**Controller**层通过**对标注有@Valid的JavaBean进行校验，并将校验结果封装在BindResult中。**

**@Valid 和 BindingResult 是一 一对应的，如果有多个@Valid，那么每个@Valid后面都需要跟一个BindingResult用于接收JavaBean的校验信息。**

```JAVA
package com.example.sell.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderForm {

    @NotEmpty(message = "姓名必填")
    private String name;

    @NotEmpty(message = "手机号必填")
    private String phone;

    @NotEmpty(message = "地址必填")
    private String address;

    @NotEmpty(message = "微信号必填")
    private String openid;

    @NotEmpty(message = "购物车不能为空")
    private String items;
}



 public ResultVO<Map<String,String>> create(@Valid  OrderForm form,
                                               BindingResult bindResult){
if (bindResult.hasErrors()){
  log.error("【创建订单】 请求参数不正确,orderForm={}",form);
  throw new             SellException(RestEnum.PARAM_ERROR.getCode(),bindResult.getFieldError().getDefaultMessage());}
     


```

#### 实体类字段返回为null的不显示

```JAVA
spring:
	jackson:
    default-property-inclusion: non_null

```

实体类日期序列化

1. 继承JsonSerializer类 ，指定类型，修改序列化方式
2. 在实体类中添加注解  @JsonSerialize(using = Date2LongSerializer.class) 指定自定义的序列化类

```JAVA
package com.example.sell.utils.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

public class Date2LongSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(date.getTime() / 1000);
    }
}


 	/*创建时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /*更新时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;



```

#### BigDecimal，Double比较

BigDecimal 对应数据库的decamal 

new BigDecimal() .doubleValue()  变成double值比较

#### 自定义日期格式的返回

```JAVA
public class Date2LongSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(date.getTime() / 1000);
    }
}


    /*创建时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /*更新时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;


```

#### 根据code获取枚举对象的通用工具类

前提是都实现同一个接口: 接口有枚举公有的方法  getCode()

参数是: integer code，xxxEnum.class

```JAVA
package com.example.sell.utils;

import com.example.sell.enums.GetCode;

public class GetEnumUtils {

    public static <T extends GetCode>T getEnumByCode(Integer code, Class<T> enumClass){
        /*getEnumConstants() 获取当前枚举类的每一个枚举值 如果当前对象不表示枚举类则返回null*/
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}

```

如果希望这个方法不进行序列化和反序列化，

方法使用@JsonIgnore注解

属性使用@Transient

```JAVA
    @Transient
    private List<OrderDetail> orderDetails;

    @JsonIgnore
    public OrderStatusEnum getOrderStatus(){
      return GetEnumUtils.getEnumByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatus(){
        return GetEnumUtils.getEnumByCode(payStatus,PayStatusEnum.class);
    }

```

#### 分布式系统下的session

session作用: 

 	验证用户身份信息和保存用户身份信息

将数据保存在服务器，默认过期时间是30分钟。每一个请求都会创建一个单独的session对象

session需要依赖cookie来识别用户的唯一标识，可以通过token或者jsessionid来识别

```JAVA
request.getSession.setAttr
request.getSession.getAttr
每一个request都是独立的

```

分布式系统下的session

![session](E:/studyAll/sell/springBoot2.0_image/session.png)

系统水平扩展:

​	将项目部署到多个服务器

系统垂直扩展：

​	在不同服务器部署不同的模块，减少每台服务器的压力

问题:

​	这些都会出现session在不同服务器丢失问题

解决:

​	将session信息保存在单独的服务器或者redis，只用来进行用户信息的保存和注销。其他服务器都从存储用户信息的服务器获取

#### 分布式系统

三个特点:  多节点 ， 消息通信 ，不共享内存

三个概念: 分布式系统 ， 集群 ， 分布式计算（并行计算）

分布式系统： 	

​	前后端分离，前端后端在不同节点，前端通过http等协议和后端进行通信，这就属于分布式系统

集群:

​	前后端都在同一个项目，将这个项目部署到多个节点，这些节点统称为集群

分布式计算:

​	并行计算，一起计算，最后将结果统一返回  hadoop

#### websocket前后端消息推送

前端

```JAVA
<#--websocket-->
<script>
    var websocket = null;
    /*如果浏览器支持websocket 创建一个新的连接*/
    if ('WebSocket' in window) {
        websocket = new WebSocket('ws://8pnvzw.natappfree.cc/sell/websocket');
    } else {
        alert("该浏览器不支持websocket")
    }

    /*建立连接*/
    websocket.onopen = function (event) {
        console.log("建立连接")
    }

    /*关闭连接*/
    websocket.onclose = function (event) {
        console.log("关闭连接")
    }

    /*收到消息*/
    websocket.onmessage = function (event) {
        console.log("收到消息:" + event.data)
        //弹窗提醒，播放音乐
        $('#myModal').modal('show');
    }

    /*websocket发生错误*/
    websocket.onerror = function (event) {
        console.log("websocket 发生错误")
    }

    /*window窗口关闭关闭websocket*/
    window.onbeforeunload = function (ev) {
        websocket.close();
    }

```

配置websocketConfig

```java
@Component
public class webSocketConfig {

    @Bean
    public ServerEndpointExporter getServerEndpointExporter(){
            return new ServerEndpointExporter();
    }
}

```

后端

```JAVA
package com.example.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Service
@ServerEndpoint("/websocket")
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> websocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        websocketSet.add(this);
        log.info("【websocket 消息发送】 有新连接的总数，总数:{}",websocketSet.size());
    }

    @OnClose
    public void OnClose(){
        websocketSet.remove(this);
        log.info("【websocket 消息发送】 连接断开，总数:{}",websocketSet.size());
    }

    @OnMessage
    public void OnMessage(String message){
        log.info("【websocket 消息发送】 收到客户端的消息,消息={}",message);
    }

    /*广播消息*/
    public void sendMessage(String message){
        for (WebSocket webSocket : websocketSet) {
            log.info("【websocket 消息】广播消息,message={}",message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.info("【websocket 消息】广播消息,message={}",message);
                e.printStackTrace();
            }
        }
    }
}


```

#### 异常捕获

**返回自定义消息和状态码**

```JAVA
  /**
    * 异常捕捉 返回自定义状态码
    *@param
    *@return 
    */
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO CatchResult(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

 throw new SellException(RestEnum.PRODUCT_NO_EXIST);

```

只返回指定状态码

```JAVA
throw new ResponseBankException();

    /**
     * 异常捕捉 返回自定义状态码
     *@param
     *@return
     */
    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void CatchResponseBankException(ResponseBankException e){

    }

```

#### synchronized

1. **synchronized并不是对代码片段进行加锁，而是对对象进行加锁！**
2. 无法做到细粒度控制
3. 只适合单点操作

#### 基于redis的分布式锁

www.redis.cn  命令大全  setnx  /  getset

博客推荐: https://www.cnblogs.com/bozzzhdz/p/9678347.html

1. 支持分布式
2. 可以更细粒度的控制 productid
3. 多台机器对一个数据进行操作的互斥

```JAVA
package com.example.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis 分布式锁
 * @Author: baiyj
 * @Date: 2019/10/28
 */

@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
    *加锁  key是商品id  value是当前时间加超时时间
    *@param  [key, value]
    *@return boolean
    */
    public boolean lock(String key,String value){
        // 想当于redis命令  setnx key存在什么都不做 可以不存在set值
        if (redisTemplate.opsForValue().setIfAbsent(key, value)){
            return true;
        }
        String currentTime = redisTemplate.opsForValue().get(key);
        // 如果锁过期
        if (!StringUtils.isEmpty(currentTime) && Long.parseLong(currentTime) > System.currentTimeMillis()){
            // 获取上一个锁的值
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentTime)){
                return true;
            }
        }
        return  false;
    }

    /**
    * 解锁
    *@param  [key, value]
    *@return void
    */
    public void unlock(String key,String value){
        try {
           String currentValue = redisTemplate.opsForValue().get(key);
           if (!StringUtils.isEmpty(currentValue) && value.equals(currentValue)){
               redisTemplate.opsForValue().getOperations().delete(key);
           }
        } catch (Exception e) {
            log.error("【redis分布式锁】 解锁异常,{}",e);
        }
    }
}


```

加锁解锁

![img](https://img2018.cnblogs.com/blog/1491023/201809/1491023-20180920230355920-1997987293.png)

![img](https://img2018.cnblogs.com/blog/1491023/201809/1491023-20180920230424344-1807657285.png)

死锁的出现和解决

​	那么在加锁过程中，如果锁顺利加上了，在接下来下单过程中如果出现了一些错误，就会抛出异常，会导致这个解锁步骤不会执行，正是因为没有解锁，那么下次请求进来时，因为有锁的存在，这个方法不会返回true，直接返回false，那么就会一直停留在加锁这一步骤，造成死锁的局面。

​	解决就是上面的代码:  判断过期时间，然后进行getAndSet操作，它会获取上一个锁的值，并将当前值set进去。**它既解决了死锁的问题，又让多线程访问时，只让一个线程拿到锁。**



#### Redis缓存

**注意点**（结合业务场景，禁止滥用）： 

1. 返回的数据格式要一致
2. key 如果不填或者为空  那么key默认是当前参数的值

**@Cacheable**  添加缓存

1. 在启动类添加@EnableCaching 开启缓存

2. ```
   @Cacheable(cacheNames = "product",key = "123")
   
   ```

3. **返回的类必须序列化**

   ```JAVA
   @Data
   public class ResultVO<T> implements Serializable {
   
   
       private static final long serialVersionUID = -1080063998721312799L;
   
   ```

**@CachePut  修改之后更新**

1. 修改和查询的返回类型必须一致  否则就是缓存滥用

```JAVA
 @CachePut(cacheNames = "productName",key = "123")

```

**@CacheEvict 删除缓存**

可以加在任意方法上面，执行方法的时候如果命中缓存就会删除。

```JAVA
@CacheEvict (cacheNames = "product",key = "123")

```

**@CacheConfig注解 作用域**

写在类的头上，类里面的缓存可以直接使用key。

```JAVA
@Service
@CacheConfig(cacheNames = "productName")
public class ProductServiceImpl implements ProductService 

@Cacheable(key = "123")

```

**condition    unless** 

condition 可以用来进行参数判断

unless 结果集判断   本身就是如果不的意思  可以用来不缓存错误的数据

```JAVA
@Cacheable(cacheNames = "product",key = "#sellerId",condition = "#sellerId.length()>3",unless = "#result.getCode() !=0")

```

结果集等于0命中缓存



#### 项目部署  

centos  7  service 百度搜索这些关键字查看

通过-D 相当于项目中yml的配置

1. java  -jar  -Dserver.port = 8090  sell.jar       指定端口
2. java  -jar  -Dserver.port = 8090 -Dspring.profiles.active=prod  sell.jar   指定配置文件  生产/测试

#### 库存超买解决

可以使用redis分布式锁解决

