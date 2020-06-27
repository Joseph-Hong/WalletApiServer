# WalletApiServer
The assignment for the server side development job offer.

● 프로젝트 구성 방법 및 관련된 시스템 아키텍쳐 설계 방법이 적절한가?
1) Software architecture
- API 서버 프로젝트 구성을 빠르고 직관적으로 할 수 있는 Spring Boot 2.3 으로 셋업하였습니다.
API delivery 시에 트래픽에 따라 scale-out 이 용이하고 WAS(컨테이너)에 대한 의존성이 없는 부분이 장점이라고 생각합니다.

2) Database architecture
- 뿌리기(wallet_transfer)와 받기(wallet_dispense)의 Table을 master-detail 관계의 2개의 테이블로 분리하여 설계하였습니다.
뿌리기에서 master 테이블에 INSERT 작업과 detail 테이블에 INSERT 작업 중간에 예외가 발생할 경우 transaction roll back 이 발생하도록 처리하였습니다.
다수의 받기 API 요청이 동일 쓰레드로 요청될 경우 race condition 이 발생하므로 이를 제어하기 위해 row level transaction lock [비관적락]으로 흐름 제어를 하였습니다.

● 요구사항을 잘 이해하고 구현하였는가?
- 기본적인 입력 파라마터 체크 기능은 Hibernate 기반의 validation 을 사용하였고 여러 로직에서 서로 다른 rule 로 정의하기 위해 annotation rule 대신 validation.xml 파일로 별도 정의하였습니다.

● 작성한 어플리케이션 코드가 간결하고 가독성 좋게 작성되었는가?
- API parameter, Method parameter 등을 model class 기반으로 데이터를 주고 받도록 구현하였으며
Database 쿼리 결과는 유연하게 받을 수 있도록 Map 으로 처리하였으며 model 과 map 객체 사이에 변환을 편리하게 할 수 있도록 Jacks의 ObjectMapper 와 reflection 기능을 Base class에서 제공하도록 상속하였습니다.

● 작성한 테스트 코드는 적절한 범위의 테스트를 수행하고 있는가?
- 제한된 시간에 진행된 작업이서 숫적으로 다양한 test case scenario 를 작성하지 못한 것이 아쉽습니다.
일단 최종 결과물을 시간 안에 완료하는 것을 제 일 목적으로 하면서 MVP(minimum value product) 원칙에 따라 작성된 API를 Unit test로 호출해 보면서 요구 사항을 하나씩 추가해 나가는 방식으로 작업하였습니다.
Junit5(junit-jupiter)와 jmock 을 기반으로 API 호출 (get, post, put) 시에 validation check 과 로직 검증을 진행하였습니다.
무엇보다 concurrency 상황을 가정하여 대량의 동일 데이터 입력이 요청되는 API 호출을 테스트 하였고 특히 받기 API에서 이러한 검증이 꼼꼼하게 요구되었습니다.
기회가 된다면 각각의 요구 사항들에 대한 scenario case들을 개별 test case 로 작성하면 좋을 것 같습니다.

● 어플레케이션은 다량의 트래픽에도 무리가 없도록 효율적으로 작성되었는가?
- 뿌리기 API 요청을 동시 1000개 발생시켜 각 20명에게 분배하는 작업 시 개발 PC 1대 기준으로 12초 가량 소요되었습니다.

● 작성하신 어플리케이션이 다수의 서버에 다수의 인스턴스로 동작하더라도 기능에 문제가 없도록 설계되어야 합니다.
- 받기 API 의 경우 대량 API 요청을 다수의 서버에서 동작될 때에도 database level 의 transaction lock 으로 흐름제어가 되기 때문에 안정적으로 처리가 가능합니다.

● 설계한 Database scheme 을 공유합니다. 개발에 사용한 Database server 의 접속 정보도 아래에 공유합니다.
DBMS : MariaDB
HOST : str2352.iptime.org
PORT : 6033
ID : kakaopay
PW : kakaopay.123

CREATE TABLE `wallet_transfer` (
  `transfer_no` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Auto increment transfer number',
  `token` varbinary(3) NOT NULL COMMENT 'Unique token per 7 days',
  `room_id` varchar(255) NOT NULL COMMENT 'chat room id',
  `sender_user_id` int(10) unsigned NOT NULL COMMENT 'Sending user id',
  `amount` decimal(18,0) unsigned NOT NULL COMMENT 'Total dispensing amount',
  `recipient_count` int(10) unsigned NOT NULL COMMENT 'Count for distribution',
  `reg_date` datetime DEFAULT current_timestamp(),
  `mod_date` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`transfer_no`),
  KEY `IDX_WALLET_TRANSFER_01` (`room_id`,`token`),
  KEY `IDX_WALLET_TRANSFER_02` (`room_id`,`sender_user_id`,`token`)
) ENGINE=InnoDB AUTO_INCREMENT=2197 DEFAULT CHARSET=utf8mb4
;

CREATE TABLE `wallet_dispense` (
  `dispense_no` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Auto increment distribution number',
  `transfer_no` bigint(20) unsigned NOT NULL COMMENT 'PK of transfer number(FK)',
  `token` varbinary(3) NOT NULL COMMENT 'Unique token per 7 days',
  `room_id` varchar(255) NOT NULL COMMENT 'chat room id',
  `sender_user_id` int(10) unsigned NOT NULL COMMENT 'Sending user id',
  `amount` decimal(18,0) unsigned NOT NULL COMMENT 'Each dispensing amount',
  `recipient_user_id` int(10) unsigned DEFAULT NULL COMMENT 'Received user id',
  `reg_date` datetime DEFAULT current_timestamp(),
  `mod_date` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`dispense_no`),
  KEY `IDX_WALLET_DISPENSE_01` (`transfer_no`),
  KEY `IDX_WALLET_DISPENSE_02` (`token`),
  KEY `IDX_WALLET_DISPENSE_03` (`room_id`,`sender_user_id`),
  KEY `IDX_WALLET_DISPENSE_04` (`transfer_no`,`recipient_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41941 DEFAULT CHARSET=utf8mb4
;

감사합니다.
