# CICD 배포 전략

과거에는 개발자가 코드를 수정하고 테스트를 마친 후 빌드 후 배포까지 상당히 많은 시간이 소요됬습니다. 이 때문에 배포하는 주기가 수 개월씩 오래 걸렸지만 최근에는 MSA 방식으로 서비스를 만들고 자주 배포하는 방식으로 변화했고 CI/CD의 등장으로 인해서 빌드와 테스트 그리고 배포까지 자동화하고 있습니다.

CD인 지속적인 배포에는 다양한 배포 전략이 개발되고 발전됬습니다. In-place, Rolling, Blue/Green, Canary 같은 배포 전략이 등장했고 이를 통해서 무중단 배포 같은 장점을 가지고 있습니다.

## 1. In-place

<img src="https://user-images.githubusercontent.com/38122225/216860421-0a881900-2287-4071-ba05-47083549fc2b.png">

## 2. Rolling

<img src="https://user-images.githubusercontent.com/38122225/216859902-51de222f-a4a7-400a-b789-a9605ea21213.png">

## 3. Blue/Green

<img src="https://user-images.githubusercontent.com/38122225/216859896-aa0895fb-3a91-4e6e-a6a0-2a539a559af1.png">

## 4. Canary

<img src="https://user-images.githubusercontent.com/38122225/216859899-3b458a29-035b-43e3-80b3-e4e8131ee35e.png">

### Reference
* [배포 전략: Rolling, Blue/Green, Canary](https://onlywis.tistory.com/10)
* [매번 헷갈리는 CI/CD 배포 전략 정리해버리기](https://dev.classmethod.jp/articles/ci-cd-deployment-strategies-kr/)
* [[Server] In-place deploy / 현재위치 배포 방식](https://youngswooyoung.tistory.com/10)