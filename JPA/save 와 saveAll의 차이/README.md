# Spring Data JPA의 save와 saveAll의 차이

Spring Data JPA에서 엔티티의 데이터를 DB에 저장하기 위해서 save와 saveAll 메서드를 제공해주고 있습니다.

평소에 별 생각없이 데이터를 1개만 저장할 때는 save를 N개의 데이터를 한 번에 저장할 때는 saveAll을 저장했는데 그럼 save와 saveAll 메서드의 동착 차이를 알고 있나요? 라는 질문을 받게 되었는데 내부적으로 벌크연산을 사용하긴 할텐데 왜 어떻게 벌크 연산으로 적용되는거지? 라고 생각을 했지만 제대로 된 대답을 하지 못하고 추측만 했고, 이를 통해서 어떻게 동작하는지 알아볼려고 합니다.

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

현재 두 메서드에서는 @Transactional이라는 어노테이션이 걸려있기 때문에 Spring의 프록시 로직을 동작하게 됩니다.

<img src="https://github.com/anwjrrp33/TIL/JPA/save 와 saveAll의 차이/Transaction이미지.png">