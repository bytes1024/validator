##### 参数校验工具
```
 <mirror>
	<mirrorOf>*</mirrorOf>
	<name>bytes1024-profile</name>
	<url>http://nexus.bytes1024.cn/repository/maven-public/</url>
	<id>bytes1024-profile</id>
</mirror>
```

* 当前版本
```
 <dependency>
    <groupId>cn.bytes1024</groupId>
    <artifactId>bytes-validator</artifactId>
    <version>1.0.0-Final</version>
 </dependency>
```
##### >> 基于spring-boot-starter 使用
*  <font color=red> @EnableParamValidator </font> 开启自动校验
*  开启校验以后对@Proof标注的类或者方法将会进行方法参数校验（javax.validation），如下标注【1】或者【2】将会校验 param1，param2 参数（参数添加了javax.validation 注解），【1】为该类的方式都会使用验证【2】只是该方法
	```
	@Proof 【1】
	public class TestController {
		@Proof 【2】
		public String test1(Param param1,Param2 param2){
			return null;
		}
	}
	```
*  过滤掉不需要校验的参数
	*  如下所示 test1方法具有两个参数 通过标注`@Ignore`可以过滤该参数的校验,两种方式【1】该方法使用过滤
	【2】任何方法调用过滤
	```
	public String test1(Param param1,Param2 param2){};

	【1】
	public String test1(@Ignore Param param1,Param2 param2){};

	【2】
	@Ignore
	public class Param{
		......
	}
	```
* 验证实体中自定义验证方法(<font color=red>未标注</font>`@Ignore`)
	* 在实体中自定义方法 标注`@Invoker` ，`@Order`(自然序)
	* 方法的修饰符一定是`public` 的
	```
	@Invoker
	@Order(1)
	public void test1(){
		if(Objects.isNull(name)) {
			throw new ValidationException("name is null");
		}
	}
	```
* 校验顺序
	* 当字段跟校验方法同时存在时，校验字段优先
	* 当方法都没有标注排序字段，根据定义位置进行执行（排序以后根据自然循序执行）	

	---
##### >> 使用 ValidatorHelper 手动校验（创建实例）
*	ValidatorHelper 手动校验<font color=red>不支持</font>`@Ignore` 注解使用,使用 `ValidatorHelper` 即可

	```
        
	@Bean
	public Validator validator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		return factory.getValidator();
	}

	@Bean
	@ConditionalOnBean(Validator.class)
	public ValidatorHelper validatorHelper(Validator validator) {
		return new ValidatorHelper(validator);
	}
	```
	---
##### >> TODO添加支持分组 [目前意义不大]
		
