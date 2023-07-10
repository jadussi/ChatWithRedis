# ChatWithRedis
</br>

### Redis 자료구조 설계부터 구현, 테스트가 포함되어있습니다.

#### period : 2023.06.13 ~

</br>
</br>


##### Java 11
##### Spring Boot 2.7.12
##### Gradle 7.6
##### My SQL
##### Redis

</br>
</br>
</br>

## Redis 설계
</br>

|  | 자료구조 | 자료구조 명 | KEY / MEMBER | VALUE / SCORE |
| --- | --- | --- | --- | --- |
| 읽은 위치 | HASH | CHAT_READ_POINT(모든 채팅방 이 저장됨) | 채팅방명_사용자 아이디 | {채팅내용, 보낸 사용자아이디, 채팅 보낸시각 등등} |
| 채팅 내용 | SORTED SET | 각 채팅방 ID (채팅방 갯수 마다 생성) | {채팅내용, 보낸 사용자아이디, 채팅 보낸시각 등등} | 채팅 보낸 시각(년월일시분초밀리초) |

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

## rabbit mq 설계
</br>

|  | 채팅방 |
| --- | --- |
| Queue | chat.queue |
| Exchange | TopicExchange |
| ExchangeName | chat.exchange |
| Routing Key | chat.key.* |

</br>

# Routing key

- "*"
    - 하나의 단어만 대체하는 와일드 카드
- "#"
    - 여러개의 단어를 대체하는 와일드 카드
 
</br>
</br>
</br>


# 테스트

</br>

#### 23.06.13 회원관련 컨트롤러 클래스 - 회원가입 서비스
</br>

[테스트]


![스크린샷 2023-06-14 212242](https://github.com/jadussi/ChatWithRedis/assets/136336510/3c8aac3d-f110-42c4-b8a5-6f5eadcab1cf)


[결과]

![스크린샷 2023-06-14 212448](https://github.com/jadussi/ChatWithRedis/assets/136336510/a868be48-d306-4b34-9cc6-09e67fadab74)

#

#### 23.06.14 Redis 연동, Redis Connection 테스 컨트롤러 클래스 - 레디스 연결 테스트 서비스
</br>

[테스트]

![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/c7d6c7e6-30de-46d3-9d61-15ec3fa5fb1e)


[결과]

![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/08ec5e2d-42c4-415e-9567-ea1376cbbec7)

#

#### 23.06.20 채팅방 전체 목록 조회 서비스, 채팅방 컨트롤러 클래스 - 채팅방 목록 조회 서비스
</br>

[결과]
![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/7f7038aa-1c5b-4d46-a264-d4bc9da2e784)

#

#### 23.06.21 채팅방 개설 서비스 , 채팅방 컨트롤러 클래스 - 채팅방 개설 서비스
</br>

[테스트]
![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/7db62780-560a-4ebd-a02a-abc6bb6b21d2)

[결과]
1. Swagger UI 테스트 결과
![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/5b643105-d9b7-4e4a-9c68-c0da3d358d4c)
2. DB 저장 결과
   
![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/5d9e280c-b370-4fbf-ad3d-810be9ba412f)

#

#### 23.06.24 기채팅방 다른 사용자 참여 서비스, 채팅방 컨트롤러 클래스 - 채팅방 참여 서비스
</br>

[테스트]
1. 테스트 성공 조건
![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/e5aef640-b96c-412e-8df8-29d700bc6559)

[결과]
1-1. Swagger UI 테스트 결과 (성공 조건)
![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/c4a24bfd-c3cc-4228-9a21-7a3a30c978fc)
1-2. Swagger UI 테스트 결과 (실패 조건 => DB 변동 없음)
![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/6a0fe2f7-f933-49cf-a0ee-7e9360f87ba7)




2-1. DB 저장 결과
2-1-1. 채팅방 정보 참여자 수 변경
![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/5f082732-ba1a-4215-9f6c-5eb3ca951569)
2-1-2. 채팅방 참여자 수 변경


![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/05b718ec-9cda-4e69-96b5-873695b0404b)

#

#### 23.07.02 채팅방 참여 서비스 Redis 에 데이터 추가
</br>

[테스트]
![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/5375aa26-3d23-4b3f-97ac-bcf3e4e58223)

[결과]
1. Swagger UI 테스트 결과

![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/ad3cad0f-f71d-4575-9648-dc9909fdc8f5)

2. Redis 데이터 추가 결과
   
   2-1. HASH
   
   ![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/d21b2a3d-0f6c-4e3a-be6d-1edc603c02d9)

   2-2. SORTED SET
   
   ![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/854adc86-9c63-4aee-b025-af0cea078552)

#   

#### 23.07.05 채팅방 메세지 전달 서비스, 채팅방 컨트롤러 클래스 - 채팅 메세지 전송 서비스
</br>

[테스트]
   ![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/57481438-611d-444b-9511-e12dd1d1ecbf)

[결과]
1. Swagger UI 테스트 결과
   
   ![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/9b22de07-d170-4069-8383-f2652285a928)

2. Redis 데이터 추가 결과
   
   ![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/219488f3-7fa2-423f-81a5-cb27ad86246c)

3. RabbitMQ 메세지 큐 전달 결과
   
   ![image](https://github.com/jadussi/ChatWithRedis/assets/136336510/55f5ad8c-c93c-4433-b169-2fe16c527c5e)


#

#### 23.07.10 채팅방 메세지 조회 서비스, 채팅방 컨트롤러 클래스 - 채팅 메세지 조회 서비스
</br>

[테스트]
</br>

   <img width="826" alt="image" src="https://github.com/jadussi/ChatWithRedis/assets/136336510/560c3f82-8a08-465f-9aa9-42aa0ed58909">

[결과]
</br>

   <img width="839" alt="image" src="https://github.com/jadussi/ChatWithRedis/assets/136336510/bcfdaca0-e833-46bc-9aef-715c6a813134">





