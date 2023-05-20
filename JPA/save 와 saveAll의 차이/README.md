# Spring Data JPA의 save와 saveAll의 차이

Spring Data JPA에서 엔티티의 데이터를 DB에 저장하기 위해서 save와 saveAll 메서드를 제공해주고 있습니다.

평소에 별 생각없이 데이터를 1개만 저장할 때는 save를 N개의 데이터를 한 번에 저장할 때는 saveAll을 저장했는데 그럼 save와 saveAll 메서드의 동착 차이를 알고 있나요? 라는 질문을 받게 되었는데 내부적으로 벌크연산을 사용하긴 할텐데 왜 어떻게 벌크 연산으로 적용되는거지? 라고 생각을 했지만 제대로 된 대답을 하지 못하고 추측만 했고, 이를 통해서 어떻게 동작하는지 알아볼려고 합니다.

## 내부 코드
save와 saveAll은 SimpleJpaRepository에 구현된 메서드로 코드는 아래와 같습니다.

* save 메서드
```
@Transactional
@Override
public <S extends T> List<S> saveAll(Iterable<S> entities) {

    Assert.notNull(entities, "Entities must not be null");

    List<S> result = new ArrayList<>();

    for (S entity : entities) {
        result.add(save(entity));
    }

    return result;
}
```

* saveAll 메서드
```
@Transactional
@Override
public <S extends T> List<S> saveAll(Iterable<S> entities) {

    Assert.notNull(entities, "Entities must not be null");

    List<S> result = new ArrayList<>();

    for (S entity : entities) {
        result.add(save(entity));
    }

    return result;
}
```

## 동작 원리
현재 두 메서드에서는 @Transactional이라는 어노테이션이 걸려있기 때문에 Spring의 프록시 로직을 동작하게 됩니다.

<img src="https://github.com/anwjrrp33/TIL/blob/main/JPA/save%20%EC%99%80%20saveAll%EC%9D%98%20%EC%B0%A8%EC%9D%B4/Transaction%EC%9D%B4%EB%AF%B8%EC%A7%80.png?raw=true">

실제로 트랜잭션이 동작하는지를 로그를 확인하기 위해서 application.yml에 아래 값을 적용해줍니다.
```
logging:
  level:
    org:
      springframework:
        transaction:
          interceptor: TRACE
```


save의 경우 @Transactional 프록시가 정상적으로 동작 하지만 saveAll의 경우 this.save을 호출해서 target을 바라보게되서 `내부 호출은 프록시를 거치지 않기 때문에` saveAll에서 호출되는 save는 saveAll이 종료되는 시점에 commit을 하게됩니다.

<img src="https://github.com/anwjrrp33/TIL/blob/main/JPA/saveLog.png?raw=true">

즉 save는 호출때마다 매번 데이터베이스와 커넥션을 맺어서 insert를 하지만 saveAll의 경우 벌크연산을 통해서 단 한번의 커넥션만 맺어서 insert를 호출하게 됩니다.

<img src="https://github.com/anwjrrp33/TIL/blob/main/JPA/saveAllLog.png?raw=true">

## 결론
* 1건의 데이터를 insert할 때는 save를 다건의 데이터를 insert할 때는 saveAll을 사용하는게 좋습니다.
* saveAll은 내부적으로 save를 호출하지만 내부 호출로인 인해서 save의 프록시 로직을 타지 않습니다.