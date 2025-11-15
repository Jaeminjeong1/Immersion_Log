# Immersion_Log
woowacourse - Open Mission


# 📘 몰입로그 (Immersion Log)

## 1. 왜 이 프로젝트를 진행했는가

프리코스를 진행하며 하루의 학습 패턴, 집중도, 몰입 흐름을 관찰하는 일이 매우 중요하다는 것을 느꼈습니다. 하지만 기록을 체계적으로
정리할 도구가 부족했고, 일관된 방식으로 데이터화하기가 쉽지 않았습니다. 이를 해결하기 위해, **하루의 몰입을 입력하고 저장하며, 
스스로의 성장 흐름을 확인할 수 있는 모바일 앱**을 직접 만들어 보기로 했습니다. 또한 이번 프로젝트는 **Kotlin + Android + 앱
아키텍처(MVVM)** 를 학습하기 위한 실전 경험을 목표로 합니다.

---

## 2. 프로젝트 소개 (1–3줄)

- **몰입로그(Immersion Log)** 는 하루의 몰입 점수,집중 시간,메모를 기록하여 ‘어제보다 나은 나’를 확인할 수 있게 돕는 개인
성장 기록 앱입니다. 간단한 화면 구성과 깔끔한 아키텍처를 기반으로 한 Kotlin Android 앱입니다.

---

## 3. 주요 기능 (MVP)

###  1) 오늘의 몰입 기록 입력

- 몰입 점수(1~5)
- 집중 시간(분)
- 메모 입력
- 저장

###  2) 기록 목록 조회

- 날짜별 리스트
- 점수 + 집중시간 표시
- 최신 순 정렬

###  3) 기록 상세 보기

- 하루 기록 전체 확인
- 수정 / 삭제 기능 포함

###  4) 간단한 성장 메시지

- 어제와 비교해 점수·집중 시간이 증가했는지 안내

---

## 4. 구성 UI

| 화면            | 설명                           |
|---------------|------------------------------|
| **홈 화면**      | 오늘 기록 입력 버튼 / 성장 메시지 표시      |
| **기록 입력 화면**  | 점수 버튼(1~5), 시간 입력, 메모 입력, 저장 |
| **기록 목록 화면**  | 날짜별 기록 리스트 (RecyclerView)    |
| **상세 화면**     | 기록 확인 및 수정/삭제                |

---

## 5. 기술 스택

### **Language & Framework**

- Kotlin
- Android SDK
- ViewBinding

### **Architecture**

- MVVM (ViewModel + StateFlow)
- Repository Pattern
- UseCase Pattern
- Clean Architecture 레이어 분리

### **Data**

- Room Database
- Coroutine / Flow
- Hilt (DI)

---

## 6. 앱 패키지 구조

```
com.immersionlog.app
 ├─ data
 │   ├─ local
 │   │   ├─ dao
 │   │   └─ database
 │   └─ repository
 │
 ├─ di
 │
 ├─ domain
 │   ├─ entity
 │   ├─ repository
 │   └─ usecase
 │
 ├─ ui
 │   ├─ home
 │   ├─ record
 │   ├─ list
 │   └─ detail
 │
 └─ utils
     
```
