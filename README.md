# 一、题目

# Welcome to PowerDale

PowerDale is a small town with around 100 residents. Most houses have a smart meter installed that can save and send information about how much power a house is drawing/using.

There are three major providers of energy in town that charge different amounts for the power they supply.

- _Dr Evil's Dark Energy_
- _The Green Eco_
- _Power for Everyone_

# Introducing JOI Energy

JOI Energy is a new start-up in the energy industry. Rather than selling energy they want to differentiate themselves from the market by recording their customers' energy usage from their smart meters and recommending the best supplier to meet their needs.

You have been placed into their development team, whose current goal is to produce an API which their customers and smart meters will interact with.

Unfortunately, two members of the team are on annual leave, and another one has called in sick! You are left with another ThoughtWorker to progress with the current user stories on the story wall. This is your chance to make an impact on the business, improve the code base and deliver value.

## Story Wall

At JOI energy the development team use a story wall or Kanban board to keep track of features or "stories" as they are worked on.

The wall you will be working from today has 7 columns:

- Backlog
- Ready for Dev
- In Dev
- Ready for Testing
- In Testing
- Ready for sign off
- Done

Examples can be found here [https://leankit.com/learn/kanban/kanban-board/](https://leankit.com/learn/kanban/kanban-board/)

## Users

To trial the new JOI software 5 people from the JOI accounts team have agreed to test the service and share their energy data.

| User    | Smart Meter ID  | Power Supplier        |
| ------- | --------------- | --------------------- |
| Sarah   | `smart-meter-0` | Dr Evil's Dark Energy |
| Peter   | `smart-meter-1` | The Green Eco         |
| Charlie | `smart-meter-2` | Dr Evil's Dark Energy |
| Andrea  | `smart-meter-3` | Power for Everyone    |
| Alex    | `smart-meter-4` | The Green Eco         |

These values are used in the code and in the following examples too.

## Requirements

The project requires [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or higher.

The project makes use of Gradle and uses the [Gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html), which means you don't need Gradle installed.

## Useful Gradle commands

The project makes use of Gradle and uses the Gradle wrapper to help you out carrying some common tasks such as building the project or running it.

### List all Gradle tasks

List all the tasks that Gradle can do, such as `build` and `test`.

```console
$ ./gradlew tasks
```

### Build the project

Compiles the project, runs the test and then created an executable JAR file

```console
$ ./gradlew build
```

Run the application using Java and the executable JAR file produced by the Gradle `build` task. The application will be listening to port `8080`.

```console
$ java -jar build/libs/tw-energy.jar
```

### Run the tests

There are two types of tests, the unit tests and the functional tests. These can be executed as follows.

- Run unit tests only

  ```console
  $ ./gradlew test
  ```

- Run functional tests only

  ```console
  $ ./gradlew functionalTest
  ```

- Run both unit and functional tests

  ```console
  $ ./gradlew check
  ```

### Run the application

Run the application which will be listening on port `8080`.

```console
$ ./gradlew bootRun
```

## API

Below is a list of API endpoints with their respective input and output. Please note that the application needs to be running for the following endpoints to work. For more information about how to run the application, please refer to [run the application](#run-the-application) section above.

### Store Readings

Endpoint

```text
POST /readings/store
```

Example of body

```json
{
    "smartMeterId": <smartMeterId>,
    "electricityReadings": [
        { "time": <time>, "reading": <reading> },
        { "time": <time>, "reading": <reading> },
        ...
    ]
}
```

Parameters

| Parameter      | Description                                          |
| -------------- | ---------------------------------------------------- |
| `smartMeterId` | One of the smart meters' id listed above             |
| `time`         | The date/time (as epoch) when the _reading_ is taken |
| `reading`      | The consumption in `kW` at the _time_ of the reading |

Example readings

| Date (`GMT`)      | Epoch timestamp | Reading (`kW`) |
| ----------------- | --------------: | -------------: |
| `2020-11-11 8:00` |      1605081600 |         0.0503 |
| `2020-11-12 8:00` |      1605168000 |         0.0213 |

In the above example, `0.0213 kW` were being consumed at `2020-11-12 8:00`. The reading indicates the powered being used at the time of the reading. If no power is being used at the time of reading, then the reading value will be `0`. Given that `0` may introduce new challenges, we can assume that there is always some consumption and we will never have a `0` reading value.

Posting readings using CURL

```console
$ curl \
  -X POST \
  -H "Content-Type: application/json" \
  "http://localhost:8080/readings/store" \
  -d '{"smartMeterId":"smart-meter-0","electricityReadings":[{"time":1605081600,"reading":0.0503},{"time":1605168000,"reading":0.0213}]}'
```

The above command does not return anything.

### Get Stored Readings

Endpoint

```text
GET /readings/read/<smartMeterId>
```

Parameters

| Parameter      | Description                              |
| -------------- | ---------------------------------------- |
| `smartMeterId` | One of the smart meters' id listed above |

Retrieving readings using CURL

```console
$ curl "http://localhost:8080/readings/read/smart-meter-0"
```

Example output

```json
[
  { "time": "2020-11-11T08:00:00.000000Z", "reading": 0.0503 },
  { "time": "2020-11-12T08:00:00.000000Z", "reading": 0.0213 },
  ...
]
```

### View Current Price Plan and Compare Usage Cost Against all Price Plans

Endpoint

```text
GET /price-plans/compare-all/<smartMeterId>
```

Parameters

| Parameter      | Description                              |
| -------------- | ---------------------------------------- |
| `smartMeterId` | One of the smart meters' id listed above |

Retrieving readings using CURL

```console
$ curl "http://localhost:8080/price-plans/compare-all/smart-meter-0"
```

Example output

```json
{
  "pricePlanComparisons": {
    "price-plan-2": 13.824,
    "price-plan-1": 27.648,
    "price-plan-0": 138.24
  },
  "pricePlanId": "price-plan-0"
}
```

### View Recommended Price Plans for Usage

Endpoint

```text
GET /price-plans/recommend/<smartMeterId>[?limit=<limit>]
```

Parameters

| Parameter      | Description                                          |
| -------------- | ---------------------------------------------------- |
| `smartMeterId` | One of the smart meters' id listed above             |
| `limit`        | (Optional) limit the number of plans to be displayed |

Retrieving readings using CURL

```console
$ curl "http://localhost:8080/price-plans/recommend/smart-meter-0?limit=2"
```

Example output

```json
[{ "price-plan-2": 13.824 }, { "price-plan-1": 27.648 }]
```

# 二、题目翻译

## 欢迎来到PowerDale

PowerDale是一个大约有100名居民的小镇。大多数家庭都安装了智能电表，可以节省和发送关于一个房子正在使用多少电力的信息。

镇上有三家主要的能源供应商，他们对他们提供的电力收取不同的费用。

- _Dr Evil's Dark Energy_  邪恶博士的暗能量
- _The Green Eco_	绿色生态
- _Power for Everyone_ 众人的力量

## 介绍 JOI Energy

JOI 能源是能源行业的新兴企业。他们希望通过记录客户的智能电表的能源使用情况，并推荐最佳供应商来满足他们的需求，而不是销售能源，从而使自己有别于市场。

你已经加入了他们的开发团队，他们目前的目标是生产一个他们的客户和智能电表可以交互的APl。

不幸的是，团队中有两名成员正在休年假，还有一名成员打电话请病假!您需要另一位TWer来处理故事墙上的当前用户故事。这是您影响业务、改进代码库和交付价值的机会。

## 故事墙

在jQI energy，开发团队使用故事墙或看板来跟踪他们正在处理的特性或“故事”。

你今天要工作的墙有7个栏目:

- Backlog 待办事项
- Ready for Dev 准备开发
- In Dev 开发中
- Ready for Testing  准备测试
- In Testing 测试中
- Ready for sign off  准备签字
- Done 完成

Examples can be found here [https://leankit.com/learn/kanban/kanban-board/](https://leankit.com/learn/kanban/kanban-board/)

## Users

To trial the new JOI software 5 people from the JOI accounts team have agreed to test the service and share their energy data.

为了测试新的JOl软件，来自JOl账户团队的5个人同意测试服务，并分享他们的能源数据。

| User    | Smart Meter ID  | Power Supplier        |
| ------- | --------------- | --------------------- |
| Sarah   | `smart-meter-0` | Dr Evil's Dark Energy |
| Peter   | `smart-meter-1` | The Green Eco         |
| Charlie | `smart-meter-2` | Dr Evil's Dark Energy |
| Andrea  | `smart-meter-3` | Power for Everyone    |
| Alex    | `smart-meter-4` | The Green Eco         |

These values are used in the code and in the following examples too.

这些值在代码和下面的示例中也会用到。

## Requirements

The project requires [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or higher.

The project makes use of Gradle and uses the [Gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html), which means you don't need Gradle installed.

需求：

​	JDK1.8

​	该项目使用Gradle并使用Gradle包装器，这意味着你不需要安装Gradle。

## Useful Gradle commands

The project makes use of Gradle and uses the Gradle wrapper to help you out carrying some common tasks such as building the project or running it.

该项目使用Gradle，并使用Gradle包装器来帮助你执行一些常见的任务，如构建项目或运行项目。

### List all Gradle tasks

List all the tasks that Gradle can do, such as `build` and `test`.

```console
$ ./gradlew tasks
```

### Build the project

Compiles the project, runs the test and then created an executable JAR file

```console
$ ./gradlew build
```

Run the application using Java and the executable JAR file produced by the Gradle `build` task. The application will be listening to port `8080`.

```console
$ java -jar build/libs/tw-energy.jar
```

### Run the tests

There are two types of tests, the unit tests and the functional tests. These can be executed as follows.

有两种测试，单元测试和功能测试。这些可以按以下方式执行。

- Run unit tests only

  ```console
  $ ./gradlew test
  ```

- Run functional tests only

  ```console
  $ ./gradlew functionalTest
  ```

- Run both unit and functional tests

  ```console
  $ ./gradlew check
  ```

### Run the application

Run the application which will be listening on port `8080`.

```console
$ ./gradlew bootRun
```

## API

Below is a list of API endpoints with their respective input and output. Please note that the application needs to be running for the following endpoints to work. For more information about how to run the application, please refer to [run the application](#run-the-application) section above.

下面是APl端点及其输入和输出的列表。请注意，应用程序需要在以下端点运行才能正常工作。有关如何运行应用程序的更多信息，请参阅上面的运行应用程序一节。

### Store Readings 

存储读数

Endpoint

```text
POST /readings/store
```

Example of body

```json
{
    "smartMeterId": <smartMeterId>,
    "electricityReadings": [
        { "time": <time>, "reading": <reading> },
        { "time": <time>, "reading": <reading> },
        ...
    ]
}
```

Parameters

| Parameter      | Description                                          |
| -------------- | ---------------------------------------------------- |
| `smartMeterId` | One of the smart meters' id listed above             |
| `time`         | The date/time (as epoch) when the _reading_ is taken |
| `reading`      | The consumption in `kW` at the _time_ of the reading |

Example readings

| Date (`GMT`)      | Epoch timestamp | Reading (`kW`) |
| ----------------- | --------------: | -------------: |
| `2020-11-11 8:00` |      1605081600 |         0.0503 |
| `2020-11-12 8:00` |      1605168000 |         0.0213 |

In the above example, `0.0213 kW` were being consumed at `2020-11-12 8:00`. The reading indicates the powered being used at the time of the reading. If no power is being used at the time of reading, then the reading value will be `0`. Given that `0` may introduce new challenges, we can assume that there is always some consumption and we will never have a `0` reading value.

在上面的例子中，0.0213 kw在2020年11月12日8:00被消耗。读数表示读取时所使用的电源。如果在读取的时候没有电力被使用，那么读取值将是o。鉴于o可能会引入新的挑战，我们可以假设总有一些消耗，我们永远不会有o的读取值。

Posting readings using CURL

```console
$ curl \
  -X POST \
  -H "Content-Type: application/json" \
  "http://localhost:8080/readings/store" \
  -d '{"smartMeterId":"smart-meter-0","electricityReadings":[{"time":1605081600,"reading":0.0503},{"time":1605168000,"reading":0.0213}]}'
```

The above command does not return anything.

### Get Stored Readings

获取存储的读表信息

Endpoint

```text
GET /readings/read/<smartMeterId>
```

Parameters

| Parameter      | Description                              |
| -------------- | ---------------------------------------- |
| `smartMeterId` | One of the smart meters' id listed above |

Retrieving readings using CURL

```console
$ curl "http://localhost:8080/readings/read/smart-meter-0"
```

Example output

```json
[
  { "time": "2020-11-11T08:00:00.000000Z", "reading": 0.0503 },
  { "time": "2020-11-12T08:00:00.000000Z", "reading": 0.0213 },
  ...
]
```

### View Current Price Plan and Compare Usage Cost Against all Price Plans

查看当前价格计划，并与所有价格计划比较使用成本

Endpoint

```text
GET /price-plans/compare-all/<smartMeterId>
```

Parameters

| Parameter      | Description                              |
| -------------- | ---------------------------------------- |
| `smartMeterId` | One of the smart meters' id listed above |

Retrieving readings using CURL

```console
$ curl "http://localhost:8080/price-plans/compare-all/smart-meter-0"
```

Example output

```json
{
  "pricePlanComparisons": {
    "price-plan-2": 13.824,
    "price-plan-1": 27.648,
    "price-plan-0": 138.24
  },
  "pricePlanId": "price-plan-0"
}
```

### View Recommended Price Plans for Usage

查看推荐的使用价格计划

Endpoint

```text
GET /price-plans/recommend/<smartMeterId>[?limit=<limit>]
```

Parameters

| Parameter      | Description                                          |
| -------------- | ---------------------------------------------------- |
| `smartMeterId` | One of the smart meters' id listed above             |
| `limit`        | (Optional) limit the number of plans to be displayed |

Retrieving readings using CURL

```console
$ curl "http://localhost:8080/price-plans/recommend/smart-meter-0?limit=2"
```

Example output

```json
[{ "price-plan-2": 13.824 }, { "price-plan-1": 27.648 }]
```

# 三、源码分析

```
domain：
	耗电量读取
		时间、电表读数
	读表集合
		电表读数集合
		电表ID
	价格计划列表
		电力供应商
		计划名称
		价格单位
		高峰时段列表
generator
	启动时生成耗电量读取列表，默认每个电表读取20次	
Configuration：
	启动时填充数据
		价格计划列表：价格计划（计划ID，供应商，价格单位，时段列表）
		推荐价格计划
		智能表价格计划列表
		每个智能表的信息读取(默认读取20次)		
service：
	账户服务
		根据智能电表ID获取价格计划ID
	电表读取：
		存储读取到的耗电信息
	价格计划：
		每个价格计划的用电成本读数
		计算成本
		计算平均耗电量
		计算时间段总耗电量	
controller：
	读表相关接口：
		存储读表信息
		检验读取智能电表信息是否有效
		根据智能电表ID读取该表信息
	价格计划比较相关接口：
		查看当前价格计划并将使用成本与所有价格计划进行比较
		根据智能电表获取推荐的用电计划
```

# 四、解题

```

- Given I have a smart meter ID with price plan attached to it and usage data stored,
	when I request the usage cost then I am shown the correct cost of last week's usage

- Given I have a smart meter ID without a price plan attached to it and usage data stored, 
	when I request the usage cost then an error message is displayed
	我有一个智能电表ID，附带价格计划，并存储了使用数据。
	时，我就会看到上周使用的正确成本
	
	假设我有一个智能电表ID，没有附加价格计划和存储的使用数据。
		当我请求使用成本时，一个错误消息是displayec
			
条件一：
	1、
		SamartMeterID 
		价格计划
		耗电量列表
		
	如果有数据：
		lastweek:
			耗电量对象 time 限制
				时间戳相减
				
		计算成本：
			能耗  KW * H 
			多次读表  平均能耗
				时间段内总耗电量/这段时间  
			成本： 0.2 * 能耗
			
	没有数据：
		报异常
		
	
```

