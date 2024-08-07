# 05. 레디스 운용 관리

## 5.1 [데이터 영속성](https://redis.io/docs/latest/operate/oss_and_stack/management/persistence/)
* RDBMS는 주로 디스크에 데이터를 저장하지만, 레디스는 인메모리 데이터베이스로 모든 데이터를 메모리에서 처리하기 때문에 서버가 재시작되면 데이터가 유실될 수 있다.
* 캐시 서버로 사용하는 경우 데이터 휘발성 문제가 크지 않으나, 데이터 저장소처럼 사용할 때는 데이터 유실 시 성능에 미치는 영향을 충분히 고려해야 하며, 이를 고려하지 않으면 유지보수 시 문제가 발생할 수 있다.
* 레디스에도 영속성을 위한 설정이 있지만, 모두 활성화하는 것은 권장하지 않고 성능과 내구성의 타협점을 찾아야하며 이런 `트레이드 오프를 고려하여 절절한 영속성 전략을 짜는 것이 중요`하다.
* 레디스는 두 가지 방법으로 데이터 영속성을 보장하는데 스냅숏(snapshot)과 AOF(Append-Only File)이 존재한다.

#### 영속성 전략
* 스냅숏: 기본 설정으로 특정 조건 충족 시 데이터 저장
* AOF: 비활성화 기본, 쓰기 작업 시마다 데이터 기록
* 스냅숏 + AOF: 두 방법 조합
* 영속성 사용하지 않음: 데이터 휘발성 허용

#### 스냅숏
* 특정 시점의 데이터베이스 내용을 RDB 형식의 파일로 저장
* 기본 설정 조건
  * 1시간 내에 최소 하나 이상의 키가 변경되는 경우
  * 5분 내에 최소 100개 이상의 키가 변경되는 경우
  * 1분 내에 최소 10,000개 이상의 키가 변경되는 경우
* 장단점
  * 장점: 시스템 부하 적음, 데이터 복구 시 빠름
  * 단점: 주기적인 전체 덤프, 데이터 손실 가능

#### AOF
* 레디스에 쓰기 작업을 수행하면 해당 데이터를 추가 전용 파일에 차례로 기록
* 로그 파일 데이터를 재생하는 방식으로 데이터를 복원
* 장단점 
  * 장점: 데이터 손실 최소화
  * 단점: 시스템 부하 큼, 복구 시 시간 소요

#### 스냅숏 VS AOF
* 스냅숏
  * 주기적으로 데이터 전체를 덤프하여 저장
  * 시스템 부하가 적고, 데이터 복구 시 빠름
* AOF
  * 모든 쓰기 작업을 기록하여 데이터 손실 최소화
  * 시스템 부하가 크고, 복구 시 시간이 더 걸림

### 5.1.1 스냅숏
* 특정 시점의 데이터베이스 내에 있는 내용을 RDB라는 형식의 파일로 저장
* 자동 혹은 수동으로 생성할 수 있다. 
* 수동 생성
  * SAVE(동기 명령어)
    * 기본적으로 싱글 스레드로 요청을 처리하기 때문에 동일한 스레드 내에 RDB 파일을 생성한다.
    * 덤프 중에 다른 요청을 차단하기 때문에 운영환경에서는 사용을 권장하지 않는다.
  * BGSAVE(비동기 명령어)
    * 동작 순서
      * 1.프로세스 포크로 자식 프로세스 생성
      * 2.자식 프로세스가 임시 RDB 파일로 덤프
      * 3.덤프 완료 후 설정된 RDB 파일 이름으로 변경
    * 요청을 처리하는 스레드와 별도로 스냅숏을 생성해서 요청 처리 작업에 미치는 영향을 줄인다.
* 자동 생성
  * 설정 파일의 save 지시자를 통해 설정
  * 조건 충족 시 BGSAVE 명령어 실행
  * 설정 파일의 dir 지시자로 출력 디렉터리, dbfilename 지시자로 RDB 파일 이름 지정
* RDB 파일을 덤프하기 위한 메모리 관리
  * 덤프 시 CoW(Copy on Write) 메커니즘 사용
  * 부모 프로세스와 자식 프로세스 간 메모리 차이 발생 시 추가 메모리 사용
* BGSAVE 명령어처럼 RDB를 생성하여 백업하는 방식을 포크로 처리할 때의 성능 영향 고려 사항
  * 1.스냅숏을 사용하기 위한 충분한 메모리 확인
  * 2.레클리카에서 스냅숏을 가져오기 가능 여부 확인
  * 3.서비스에서 지장이 없는 시간에 스냅숏 생성하는지 확인
  * 1번의 경우 AWS ElastiCache같은 관리형 서비스 사용히 매개변수를 사용 가능
  * 3번의 경우 ElastiCache 자동 백업 기능 사용 시 백업 윈도우로 백섭 시작 시간 설정 가능
* 백업 및 복원
  * 생성된 파일을 Amazon S3 등 외부 스토리지에 정기적으로 저장
  * 지정된 디렉터리에 RDB 파일 배치하여 복원
* 튜닝 옵션
  * 파일 압축: rdbcompression 지시자, 기본값 yes, 대신 CPU 사용량 증가되는 단점
  * 파일 체크섬: CRC64 체크섬, 활성화 시 성능 10% 저하
  * stop-writes-on-bgsave-error 지시자: 스냅숏 저장 실패 시 쓰기 작업 수락 여부 설정
  * rdb-save-incremental-fsync 매개변수: 스냅숏 생성 중 데이터 커밋을 단계적으로 진행
* 스냅숏 생성 중 4MB(레디스 7.0 이전에는 32MB)의 데이터가 생성될 때마다 fsync를 수행할 수 있고, 이 방식을 통해 파일에서 디스크로 데이터 커밋을 단계적으로 진행함으로써 지연이 커지는 것을 피할 수 있으며 rdb-save-incremental-fsync 매개변수로 제어할 수 있다.

### 5.1.2 AOF
* AOF (Append-Only File)
  * 특징: 쓰기 작업을 파일에 순차적으로 기록, 내구성 높음
  * 생성 방식: 자동만 가능
  * 장점: 데이터 손실 최소화
  * 단점: 파일 크기 증가, 재시작 시 시간 소요
  * 설정:
    * appendonly 지시자: AOF 활성화
    * appendfilename 지시자: AOF 파일 이름 설정
    * appenddirname 지시자: AOF 출력 디렉터리 (레디스 7.0 이후)
* AOF 동작 원리
  * 버퍼 관리: AOF 버퍼에 데이터 유지, 운영체제가 플러시
  * 파일 기록:
    * always: 쓰기 작업마다 디스크에 플러시, 데이터 손실 최소화, 성능 저하 큼
    * everysec: 매초마다 백그라운드 스레드가 플러시, 성능과 내구성 균형, 기본값
    * no: 운영체제가 적절한 시점에 플러시, 성능 영향 적음
* AOF 재작성
  * 목적: 파일 크기 최적화, 삭제/만료된 키 및 중복 작업 제거
  * 트리거 조건:
    * auto-aof-rewrite-percentage 지시자: 파일 크기 변화 비율
    * auto-aof-rewrite-min-size 지시자: 재작성 최소 파일 크기
* 성능 최적화
  * 백그라운드 작업:
    * no-appendfsync-on-rewrite 지시자: 스냅숏 생성/AOF 재작성 시 성능 저하 방지
    * rdb-save-incremental-fsync 지시자: 스냅숏 생성 중 단계적 디스크 커밋

### AOF 메커니즘
* 레디스 7.0 이전의 AOF 재작성 처리
  * 자식 프로세스 생성: AOF 재작성을 위해 요청 처리 중인 프로세스에서 자식 프로세스를 포크하여 생성.
  * AOF 파일 생성: 자식 프로세스가 새로운 AOF 파일을 생성하고 재작성 결과를 저장.
  * 신호 전송: 자식 프로세스가 재작성을 완료하면 부모 프로세스에 신호를 보냄.
  * 차이 반영: 부모 프로세스는 포크 이후의 쓰기 작업 내용을 AOF 재작성 버퍼에 저장하고 이를 자식 프로세스에 전송. 자식 프로세스는 이 데이터를 새 AOF 파일에 반영.
  * 파일 교체: 새로운 AOF 파일이 완성되면 오래된 파일을 교체.
  * 메모리 할당: 스냅숏과 마찬가지로 CoW(Copy-on-Write) 메커니즘 사용.
  * 오류 처리: AOF 파일 불러오기 중 손실 확인 시 최대한 많은 데이터 복구, aof-load-truncated 지시자로 설정.
  * 증분 플러시: 4MB(레디스 7.0 미만에서는 32MB)마다 디스크로 플러시, aof-rewrite-incremental-fsync 지시자로 제어.
* 레디스 7.0 이후의 멀티 파트 AOF 처리
  * 목적: 메모리 소비 및 디스크 입출력 문제 해결.
  * 파일 구조: 하나의 AOF 파일을 기본 파일(전체 데이터 세트)과 추가 파일(이후 변경 내용)로 분할.
  * 멀티 파트 AOF 재작성 처리 과정
    * 자식 프로세스 생성: AOF 재작성을 위해 요청 처리 중인 프로세스에서 자식 프로세스를 포크하여 생성.
    * 추가용 AOF 파일 생성: 부모 프로세스는 추가용 AOF 파일을 생성.
    * 새로운 베이스 파일 생성: 자식 프로세스는 재작성 로직을 실행하여 새로운 베이스 AOF 파일 생성.
    * 매니페스트 파일 업데이트: 부모 프로세스가 새로운 베이스 파일과 추가 파일 정보를 임시 매니페스트 파일에 업데이트.
    * 파일 교체: 새로운 베이스 파일과 추가 파일 준비 후 매니페스트 파일 반영 및 교체.
    * 히스토리 파일 처리: 오래된 파일들을 히스토리 파일로 변환하고 원래 파일 삭제.
  * 효율성: 부모 프로세스와 자식 프로세스 간 데이터 전송 과정 불필요, CPU 처리 시간 감소.
  * 백업 주의사항: 백업 시 AOF 재작성 비활성화 필요, auto-aof-rewrite-percentage 지시자를 0으로 설정하고 INFO * persistence 명령으로 재작성 상태 확인 후 백업.

### 5.1.3 스냅숏과 AOF 비교

#### 장단점
* 스냅숏
  * 생성 방식: 메모리 내 데이터를 덤프하여 파일로 저장.
  * 장점:
    * 특정 시점의 상태를 저장하며, 파일 크기가 작음.
    * 자식 프로세스의 포크 처리 시 성능에 영향을 미치지만 나머지 처리는 백그라운드에서 수행되어 성능 영향이 작음.
    * AOF보다 서버 시작 시간이 빠름.
    * 레디스 5.0 이상에서는 LFU(Least Frequently Used)나 LRU(Least Recently Used) 정보를 포함하여 복원 직후 더 정확한 * 데이터를 관리.
  * 단점:
    * 정기적인 스냅숏 생성 후 문제가 발생하면 그 기간의 데이터 손실 가능.
    * 데이터 손실에 대한 내구성이 AOF에 비해 낮음.
* AOF (Append-Only File)
  * 생성 방식: 실행된 명령을 추가 방식으로 기록.
  * 장점:
    * 데이터 내구성이 높아, 문제가 발생해도 직전까지의 데이터가 파일에 기록됨.
  * 단점:
    * 트랜잭션 로그를 REDO 로그 형태로 기록하여 AOF 파일 크기가 커질 수 있음 (재작성 기능으로 크기 줄일 수 있음).
    * 스냅숏보다 성능 저하에 영향을 미칠 수 있음 (appendfsync 지시자의 정책에 따라 다름).
    * 서버 시작 시 파일 로딩 시간이 길어질 수 있음.
    * 특정 명령어 버그로 인해 데이터 세트의 정확한 복원이 어려운 경우가 있었음 (스냅숏에는 없음).
    * 관리형 서비스 사용 시 AOF 복원 효과가 없을 수 있음 (예: Elasticache).
    * (레디스 7.0 미만) AOF 재작성 중 많은 쓰기 작업이 발생하면 메모리 소비 증가.
    * (레디스 7.0 미만) AOF 재작성 중 모든 쓰기 작업이 디스크에 두 번 기록되는 중복 처리 발생.
    * (레디스 7.0 미만) 재작성 과정 중 쓰기 작업을 마지막에 새로운 AOF 파일에 기록하고 fsync할 경우 작업이 멈출 수 있음.
* 스냅숏과 AOF 병행 사용
  * 병행 사용: 스냅숏과 AOF를 동시에 활성화하여 데이터 손실 위험 최소화 가능.
  * 우선 순위: AOF가 데이터 일관성을 더 잘 유지하므로 AOF가 먼저 적재됨.
  * 레디스 기본 설정: 오픈소스 레디스는 기본적으로 스냅숏/AOF 혼합 형식이 활성화되어 있음. 혼합 형식은 aof-use-rdb-preamble * 지시자로 설정 가능 (기본값: yes).
* 유스케이스별 권장 사항
  * 캐시 서버: 데이터 지속성에 대해 RDBMS와 병행 사용 권장.
  * 주 데이터베이스: 데이터 손실 위험을 피하고자 할 경우 AOF 활성화와 빈도 재검토 필요.
  * 백업: 스냅숏을 정기적으로 생성하는 것이 권장되며, 스냅숏 생성 빈도는 비즈니스 요구와 성능 영향을 고려하여 조정.
* AWS MemoryDB
  * MemoryDB: 지속성 있는 레디스 호환 인메모리 데이터베이스로, 캐시 용도뿐만 아니라 주 데이터베이스로 사용 가능. 마이크로 초 단위 * 읽기 작업과 밀리 초 단위 쓰기 작업을 제공하며, 데이터 내구성 제공. AOF의 문제점과 데이터 내구성이 염려되는 경우 사용 고려.
* 스냅숏: 빠른 시작 시간, 작은 파일 크기, 성능 영향 적음. 그러나 데이터 손실 가능성 있음.
* AOF: 높은 데이터 내구성, 데이터 손실 최소화. 그러나 성능 저하, 서버 시작 시간 지연, 파일 크기 증가 문제 있음.
* 혼합 사용: 두 가지 방식을 병행 사용하여 각 방식의 단점을 보완하고 데이터 내구성을 높일 수 있음.

### 5.1.4 데이터 삭제 패턴
* 레디스는 데이터 영속화 기능이 있지만, 데이터가 메모리에만 저장된 상태에선 예기치 않게 데이터가 삭제될 가능성이 있다.
* 레디스의 내구성을 고려하면서 내부적으로 어떻게 데이터를 삭제하는지도 알아둘 필요가 있고, 원인을 특정한 뒤 왜 발생했는지 이해해서 사전에 충분히 검토하고 예방하는 것이 중요하다.

#### 데이터가 손실될 수 있는 몇 가지 상황
* 엔진 재시작
  * 메모리에만 존재하는 경우 데이터는 엔진 재시작 시 사라진다.
  * 마스터를 재시작할 때 마스터의 데이터를 레플리카에 복제하고 전체 캐시 노드가 비워질 수 있다.
  * 관리형 서비스에선 설정에 따라 엔진 재시작을 제한하는 경우도 있다.
* 레디스 서버 전체 장애
  * 드밀지만 데이터 손실 가능성도 있고, 의도치 않게 삭제 명령이 실행돼서 데이터가 사라질 수 있다.
  * INFO Commandstats를 실행하면 실행된 명령 통계를 확인할 수 있다.
* 명령어 실행
  * DEL/HDEL/XDEL 명령어
  * FLUSHALL/FLUSHDB 명령어
  * UNLINK 명령어
* TTL 만료
  * EXPRIE/EXPIREAT/PEXPIRE/PEXPIREDAT 명령어
  * SET 명령어의 EX 옵션
* 강제 제거
  * 데이터 사용량이 maxmemory를 초과하고 maxmemory-policy 지시자의 값이 noeviction 이 외로 설정된 경우, 데이터 제거가 이뤄진다.
  * INFO Stats 명령의 evicted_keys 항목으로 제거 상황 확인이 가능하다.
* 비동기 레플리케이션
  * 레디스의 레필리케이션은 비동기 형태를 취하고 아직 데이터가 레플리카에 반영되지 않은 상태에서 서버 강제 중단 등 마스터 다운 시 데이터 손실이 발생할 수 있다.
* 레디스 클러스터의 네트워크 단절
  * 하나의 클러스터가 네트워크 단절로 두개로 나뉘었을 때 레디스 클러스터가 과반수의 마스터가 작동하는 것을 전제로 하기 때문에 그렇지 않은 클러스터의 데이터는 손실될 수 있다.
* 기타
  * 키 이름 재설정(RENAME 명령어로 이름을 변경한 후 예전 키 이름을 지정하는 경우)
    * 키 공간 알림, MONITOR 명령어를 사용해 작업 상태를 모니터링할 ㅅ ㅜ있다.
  * 잘못된 데이터베이스 선택
  * 데이터가 아예 삽입되지 않은 경우

#### 클라우드 관리형 서비스를 사용하는 경우 추가 고려사항
* 데이터 삭제 방지 패턴
  * ElastiCache 데이터 보존 패턴
  * 장애 자동 복구에 따른 캐시 노드 교체
  * 엔진 버전 업그레이드 과정
  * 스케일업 과정(캐시 노드 타입 변경)
  * 장애 조치 시 동기화
* 데이터 삭제 원인
  * 캐시 노드 및 스토리지 고장과 같은 장애
  * 유지보수 등 인위적 조작 또는 시스템에 의한 기계적 처리
  * 운영 실수 등 의도치 않은 인위적 조작
  * 애플리케이션 버그

1. 장애 발생 시 기본 대응
   * 캐시 서버 장애: 백엔드 RDBMS에서 데이터를 가져오는 방식으로 애플리케이션을 구현하여 일시적인 지연을 감수.
   * 관리형 서비스 사용 시: 자동으로 노드가 교체되므로 재시도 처리 등으로 대처 가능.
2. 데이터 영속성 요구 시 대응
   * 레디스를 데이터 저장소로 사용하는 경우, 추가적인 대처 방안이 필요.
   * 백업 및 복원: RDB 파일의 백업을 복사해두고 복원할 수 있도록 준비.
   * 운영 체계 구축: 예측할 수 없는 장애에 대비해 내부 부서 간 연락 체계 마련.
   * 권한 관리: 필요 이상의 권한 부여를 지양, Redis 6 이후의 ACL 기능을 활용하여 사용자별 명령어 제어.
   * 명령어 보호: rename-Command 명령어로 의도하지 않은 조작 방지.
3. 백업 및 복원 전략
   * 스냅숏 및 AOF 백업: 주기적으로 스냅숏이나 AOF 생성하여 백업.
   * AOF를 통한 PITR(Point in Time Recovery): Redis 7.0 이후 도입된 기능으로 특정 시점으로 데이터 복원 가능, aof-timestamp-enabled 명령어로 활성화.
   * 기타 백업: PITR을 사용할 수 없는 환경에서는 스냅숏이나 AOF 파일에서 복원.
4. 애플리케이션 버그 대응
   * 애플리케이션의 버그로 인한 데이터 손실 방지를 위해 주기적인 백업 필요.
   * 버그 방지: 스냅숏 및 AOF 백업을 통해 데이터 삭제 또는 업데이트 실수에 대비.

> 장애 발생 시 데이터를 복구하고 서비스 연속성을 유지하기 위해서는 다양한 백업 및 복원 전략, 권한 관리, 그리고 사전 준비가 중요합니다. 특히, 레디스를 데이터 저장소로 사용할 경우, 추가적인 대응 방안을 철저히 마련하여 운영상의 실수나 예상치 못한 장애에 대비해야한다.