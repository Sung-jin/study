## DDD

* 도메인 주도 설계 (Domain Driven Design)
* 말 그대로 도메인이 중심이 되는 개발 방식을 말한다
* 소프트웨어의 연관된 부분들을 연결하여 계속 해서 진화하는 새로운 모델을 만들어 나가 복잡한 어플리케이션을 만드는 것을 쉽게 해주는 것이 목적이다
* **Loose Coupling** (낮은 결합성), **High Cohesion** (높은 응집도) 를 목표로 한다
* DDD 는 Strategic Design 과 Tactical Design 으로 나눌 수 있다
* DDD 의 세가지 주요 원리
  1. 핵심 도메인과 그 기능에 집중하라
  1. 도메인의 모델의 정교하게 구축하라
  3. 어플리케이션 모델을 발전시키고 새롭게 생기는 도메인 관련 이슈를 해결하기 위해 도메인 전문가와 끊임없이 협력하라

### Domain

* 사전적의미로는 **영역**,**집합**이며, DDD 에서는 **비지니스**이다
* 비즈니스란 유사한 업무의 집합이며, 어플리케이션은 비즈니스 Domain 별로 나누어 설계 및 개발 될 수 있다

### Strategic Design

* 비즈니스의 도메인의 상황 (Context) 에 맞게 설계하는 컨셉이다
  * 선물구매라는 도메인을 설계할 때, 대상이 애인/부모/자식 등의 대상에 따라 달라진다
* 전략적 설계를 위해 비즈니스 도메인의 상황을 Event Storming 으로 공유하고, 비즈니스 목적별로 서비스들을 그룹핑 한다
* Bounded Context
  * 비즈니스 도메인의 사용자/프로세스/정책/규정 등을 고유한 비즈니스 목적별로 그룹핑 한 것
  * 도메인안의 서비스를 경계 지은 Context 의 집합이라 할 수 있다
  * 1 개의 Bounded Context 는 최소한 1개 이상의 Micro service 로 구성된다
* domain Model
  * 비즈니스 도메인의 서비스를 추상화한 설계도
* Context Map
  * Bounded Context 간의 관계를 나타낸 도식화한 Diagram
* Ubiquitous Language
  * 개발자/디자이너/기획자 등 참여자들이 동일한 의미로 이해하는 언어
  * 비즈니스 도메인에 따라 동음이의어가 많을 수 있기 때문에, 정확한 커뮤니케이션을 위해 공통언어를 정의하고 사용해야 한다
* Problem Space - 비즈니스 도메인 분할
  1. Core Sub-Domain: 비즈니스 목적 달성을 위한 핵심 도메인으로 차별화를 위해 가장 많은 투자가 필요
  1. Supporting Sub-Domain: 핵심ㄷ 도메인을 지원하는 도메인
  1. Generic Subdomains: 공통 기능 도메인

#### Event Storming
* 비즈니스 도메인 내에서 일어나는 것의 Bounded Context 를 식별하는 방법론
* Step
  1. 도메인 이벤트 정의 - 비즈니스 도메인내에서 발생하는 모든 이벤트를 과거형으로 기술
     * 이벤트는 Actor 가 Action 에 발생한 결과이며, "xxx 가 되었다" 와 같은 형태의 문장이 된다
     * 각자가 생각나는 Event 를 순서없이 생각이 안날 때 까지 상의없이 붙인다
     * 완료 후, 중복된 내용을 제거/병합을 한다
     * 이벤트가 발생하는 시간 순서대로 수정하고, 동시에 수행되는 이벤트는 수직으로 붙인다
     * 이슈/개선사항/관심/재논의 사항이 있다면, 구분할 수 있는 다른 색으로 이벤트 옆에 붙인다
  1. 도출된 이벤트로 도메인의 업무 흐름을 이해하고 토론하여 보완
     * 도메인 전문가가 도출된 이벤트를 가지고 업무 흐름을 설명한다
     * 상호 질문을 통해 도메인 이벤트를 추가하거나 조정한다
     * 이슈/개선사항/관심/재논의 사항이 있다면, 구분할 수 있는 다른 색으로 이벤트 옆에 붙인다
  1. 프로세스로 그룹핑 - 이벤트들을 프로세스로 그룹핑한다
     * 동일한 비즈니스 주제(업무 프로세스)로 이벤트들을 설명과 함께 그룹핑한다
     * 비즈니스적으로 중요한 핵심 프로세스에 집중하며, 핵심 프로세스에 중요한 이벤트가 누락되지 않았는지 검토한다
     * 이슈/개선사항/관심/재논의 사항이 있다면, 구분할 수 있는 다른 색으로 이벤트 옆에 붙인다
  1. Command 정의
     * 각 도메인 이벤트를 발생시키는 명령을 현재형으로 정의하며 명령형으로 기술
     * 사용자 행위가 Command 가 된다
     * Command 는 일반적으로 '무엇을 요청한다'/'무엇을 xx 한다' 의 형태가 된다
     * Event 별로 해당 해당 Event 를 발생시키는 Command 를 Event 옆에 붙이며, Command 에 1개 이상의 Event 가 발생할 수 있다
  1. 트리거 정의 - Command 를 일으키는 Actor 와 Event 를 일으키는 External System 과 Policy/Role 을 정의
     * Command 를 수행하는 Actor 를 정의한다
     * Event 를 발생시키는 외부 시스템이 있다면, Event 우측에 겹쳐서 표시한다
     * Event 와 관련된 Policy/Role 이 있다면, 우측 하단에 붙인다
     * Policy/Role 은 Event 를 발생시킬 수 있다
  1. Aggregate 정의 - Command 수행을 위해 CRUD 를 하는 데이터 객체 정의
     * Aggregate 는 Entity/VO 의 집합
     * 한 단위로 취급 가능한 경계 내부의 도메인 객체로서 한개의 root entity 와 기타 entity, VO 로 구성한다
     * 고유의 비즈니스 목적 수행을 위한 데이터 객체들의 집합
  1. Bounded Context 정의
     * Entity/Command/Event/Actor/Policy/Role 을 보면서 어떤 주제와 관련되었는지를 논의한다
     * Bounded Context 는 사용자/프로세스/정책/구정 등을 고유한 비즈니스 목적별로 그룹핑 한 것이다
  1. Context Map 작성
     * Bounded Context 간의 관계를 도식화 한다
     * 관계의 종류
       * Shared Kernel - 복수의 Bounded Context 가 공통으로 사용하는 Bounded Context 간의 관계
       * Upstream-Downstream - Publisher(upstream) 와 Subscriber(downstream) 관계
     * Bounded Context 의 특성 종류
       * Open Host Service - 여러 종류의 Downstream Bounded Context 를 고려하여 설계되는 upstream bounded context
       * Anti Corruption Layer - 다른 Bounded Context 에서 받는 데이터를 본인에 맞게 구조/타입/통신프로토콜 등을 변환해 주는 모듈 계층을 가지는 Bounded Context
  
### Tactical Design

* 개발을 위한 구체적인 설계도
* Strategic Design 에서 설계한 각 Sub Domain 별 Domain Model(Context Map) 을 중심으로 설계하는 것
* Layered Architecture
  * Tactical Design 의 목적별 계층으로 나누어 설계하는 것을 의미
  * Presentation, Service, Domain, Data Layer 가 있음
    * Presentation: UI Layer (@Controller/@RestController)
    * Service Layer: Domain Layer 와 data Layer 의 Class 간의 제어 또는 연결을 한다 (@Service)
    * Domain Layer: Domain Object 별로 비즈니스 로직 처리를 담당하는 Layer
    * Data Layer: Database 와의 CRUD 처리 Layer (@Repository)
* Service Layer 와 Domain Model Layer 를 나누는 이유는 새로운 비즈니스 요구에 대응할 때 기존 소스에 영향도를 최소화하고, Domain Layer 의 재활용성을 극대화하기 위함이다
* Entity & VO - 식별설과 가변성으로 구별
  * Entity 는 각 레코드간에 구별이 필요한 객체이고, VO 는 각 레코드간에 구별이 필요없는 객체이다
  * Entity 는 가변적이고, VO 는 불변성을 가진다
  * VO 를 권고하는 이유는 속성 자체도 객체화하여 어플리케이션의 유연성을 높이는 취지이며, 속성이 또 다른 하위 속성들로 구성된다면 VO 로 구성하는게 좋다
  * 개발시 Entity 에 기본형 타입이 아닌, VO 객체로 필드를 구성하는게 좋다
    * 해당 VO 클래스에 자체적으로 속성에 대한 type 변환과 validation 체크를 구현하는 것이 좋다
  * VO vs Map
    * VO 는 속성의 type/validation 등이 변경 되었을 때 유연하나, 복잡성이 증가될 수 있고 DB 스키마 변경시 관련된 VO 도 변경이 필요하다
    * SQL 에 많이 의존하거나 DB 스키마의 변환이 빈번하게 예상된다면 Map 이 더 효율적일 수 있다
* Aggregate & Factory
  * Aggregate 는 Entity 들을 대표하는 추상화된 객체이다
  * 유연한 어플리케이션 개발을 위해 Entity 간에 직접 커뮤니케이션 하지 않고, Aggregate 간에 커뮤니케이션 하도록 설계해야 한다
  * Factory 는 Aggregate 의 생성 처리를 담당하는 객체이며, 개발에 따라 Entity 생성을 담당할 수 있다
* Repository
  * Data Access 를 처리하는 객체
  * Aggregate, Entity 를 위해 데이터 CRUD 를 담당
