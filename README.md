# ChatWithRedis

###### Java 11
###### Spring Boot 2.7.12
###### Gradle 7.6
###### My SQL
###### Redis

# Redis 설계

|  | 자료구조 | 자료구조 명 | KEY / SCORE | VALUE / MEMBER |
| --- | --- | --- | --- | --- |
| 읽은 위치 | HASH | CHAT_READ_POINT(모든 채팅방 이 저장됨) | 채팅방명_사용자 아이디 | {채팅내용, 보낸 사용자아이디, 채팅 보낸시각 등등} |
| 채팅 내용 | SORTED SET | 각 채팅방 ID (채팅방 갯수 마다 생성) | 채팅 보낸 시각(년월일시분초밀리초) | 위와 동일 |

1. A 사용자가 채팅을 보내면 SORTED SET에 저장됨
2. B 사용자는 A사용자와 같이하는 채팅방의 마지막 읽은위치를 HASH에서 채팅방명_사용자 KEY로 VALUE 를 조회
3. B 사용자가 조회한 HASH 의 VALUE는 SORTED SET의 MEMBER와 동일하므로 SORTED SET에 저장된 MEMBER 의 index를 조회 

   `redisTemplate.opsForZset().rank(”SORTED SET 자료구조명” , “HASH에서 조회한 데이터”)`
5. HASH로 구한 index를 기준으로 조회를 시작할 index, 조회를 끝낼 index 구하기
6. 조회한 SORTED SET의 MEMBER index부터 정해진 갯수만큼 조회하여 출력

   `redisTemplate.opsForZSet().rangeWithScores(”SORTED SET 자료구조명”, “4에서 구한 시작 index”,”4에서 구한 끝나는 index”)`

<aside>
   💡 HASH와 SORTED SET 을 같이 쓰는이유
  
    SORTED SET의 SCORE에 저장 된 채팅을 보낸사람의 보낸 시각을 기준으로 조회 할 수있지만 
    HASH에 저장된 VALUE 를 가지고 SORTED SET의 MEMBER 를 조회해서 가지고 오는 것이 시간복잡도가 좋기 때문

</aside>

# rabbit mq 설계

|  | 단체톡 | 개인톡 |
| --- | --- | --- |
| Queue | privateChat.queue | publicChat.queue |
| Exchange | DiretctExchange | TopicExchange |
| ExchangeName | privateChat.exchange | publicChat.exchange |
| Routing Key | privateChat.key.* | publicChat.key* |

# Routing key

- "*"
    - 하나의 단어만 대체하는 와일드 카드
- "#"
    - 여러개의 단어를 대체하는 와일드 카드
 


  
      
#



#### 23.06.13 회원관련 컨트롤러 클래스 - 회원가입 서비스
[테스트]

![스크린샷 2023-06-14 212242](https://github.com/jadussi/ChatWithRedis/assets/136336510/3c8aac3d-f110-42c4-b8a5-6f5eadcab1cf)


[결과]

![스크린샷 2023-06-14 212448](https://github.com/jadussi/ChatWithRedis/assets/136336510/a868be48-d306-4b34-9cc6-09e67fadab74)



#### 23.06.14 Redis 연동, Redis Connection 테스 컨트롤러 클래스 - 레디스 연결 테스트 서비스
[테스트]

![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/c7d6c7e6-30de-46d3-9d61-15ec3fa5fb1e)


[결과]

![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/08ec5e2d-42c4-415e-9567-ea1376cbbec7)


#### 23.06.20 채팅방 전체 목록 조회 서비스, 채팅방 컨트롤러 클래스 - 채팅방 목록 조회 서비스
[결과]
![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/7f7038aa-1c5b-4d46-a264-d4bc9da2e784)

#### 23.06.21 채팅방 개설 서비스 , 채팅방 컨트롤러 클래스 - 채팅방 개설 서비스
[테스트]
![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/7db62780-560a-4ebd-a02a-abc6bb6b21d2)

[결과]
1. Swagger UI 테스트 결과
![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/5b643105-d9b7-4e4a-9c68-c0da3d358d4c)
2. DB 저장 결과
   
![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/5d9e280c-b370-4fbf-ad3d-810be9ba412f)





