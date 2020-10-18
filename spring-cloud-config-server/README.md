# spring-cloud-config-server

## config-server
- 스프링 클라우드 config 서버
- port는 8888이다.

## config-repo
- 여러 마이크로 서비스의 configuration을 모아 놓은 git backend repo

## greetings
- config-server로 부터 configuration을 받는 client 서버
- spring.application.name이 greetings 이다.
- port는 config-server에 의해 설정된다.


## docker-compose
- db
  - mysql로 되어 있다.
  - gogs의 운영 데이터를 저장하기 위해 필요하다.
- repo-gogs
  - [gogs](https://gogs.io/) : local에 github, gitlab 같은 git 서버를 구축할 수 있게 해준다.
  - gogs(dir)를 mount한다.
- repo-vault
  - [Vault](https://www.vaultproject.io/)는 보안 관리 도구로, Git 서버와 다르게 보안 정보를 자체적으로 처리한다.
  - vault/config(dir)를 mount한다.
  - vault/policies(dir)를 mount한다.
  - vault/data(dir)를 mount한다.
- message-broker
  - [rabbitmq](https://www.rabbitmq.com/)로 되어 있다.
  - configuration의 변경 알림(update notification)을 config-server에서 client 전파하기 위해 필요

## 사전 설치
- /encrypted endpoint를 사용하기 위해서는 암호화 관련 파일을 다운 받아 jdk 하위의 특정 폴더에 위치시켜야 한다.
  - 참고 : https://www.oracle.com/java/technologies/javase-jce8-downloads.html

## 사용법
- docker container를 실행한다.
  ```sh
  docker-compose up -d
  ```
- DB에 접속하여 gogs 운영 데이터를 위한 database를 생성한다.
  - db container에 접속 및 db 로그인
    ```sh
    $ docker exec -it spring-cloud-config-server_db_1 /bin/bash
    # mysql -uroot -proot!
    ```
  - database 생성
    ```mysql
	create database gogs;
    ```
- internet browser를 통해 localhost:10080로 gogs에 접속한다.
  - 이때, DB의 host는 'db:3306' 이다.
- gogs에 회원가입 및 로그인한다.
  - id : root
  - password : root!
- 'config-repo' repository를 gogs에 올린다.
  - 주의) 이때, container의 port는 3000이지만, host의 port는 10080이니 헷갈리지 말자.
- gogs 'config-repo' repository의 Webhooks 생성한다.
  - 설정 > Webhooks에서 type을 gogs로 선택한다.
  - Payload URL을 'http://host.docker.internal:8888/monitor'로 설정한다.
  - '단순 push 이벤트'를 선택한다.
  - '활성'을 체크한다.
- vault 설정을 한다.
  - container에 접속한다.
    ```sh
    $ docker exec -it spring-cloud-config-server_repo-vault_1 sh
	```
  - vault 명령어를 입력했을 때, 'missing client token' error가 발생하면 TOKEN 값으로 'roottoken' 입력하여 login을 해야 한다.
    ```sh
    # vault login
    # vault secrets disable secret
    # vault secrets enable -path=secret kv
    ```
  - 보안이 필요한 데이터를 저장한다.
    ```sh
    # vault write secret/application vault.password=s3cr3t
    ```
- config-server(8080)를 실행한다.

- gogs Webhooks에서 '전달 시험'을 통해 테스트한다.

- greetings를 실행한다.
  - config-server로부터 greetings.yml을 받아 9090 port로 설정된다

- greetings.yml의 greeting.message 속성의 값을 변경하여 push하면 현재 실행중인 greetings의 message가 변경되는지 확인한다.
