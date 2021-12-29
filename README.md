## 알람앱 만들기

사고의 흐름 #1,#2,#3

### 1.기능 구현시 사전 체크 사항

- 싱글 액티비티 + 네비게이션 컴포넌트
    - [DeepLink 로 구성하여 화면 이동 및 데이터 전달]((https://developer.android.com/guide/navigation/navigation-multi-module))

- Navigation action
    - popUpTo 속성은 주어진 도착점(destination)이 나올 때까지 백 스택을 팝업
    - popUpToInclusive 속성은 주어진 목적지를 포함해서 모든 목적지들을 백 스택에서 제거

- [알람 매니저 가이드](https://developer.android.com/training/scheduling/alarms?hl=ko)
    - UTC : 실제 경과 시간은 시간대/언어의 영향을 받지 않기 때문에 경과 시간에 기반한 알람(예: 30초마다 실행되는 알람)을 설정하는 데 적합
    - RTC : 실시간 시계 유형은 현재 언어에 따라 달라지는 알람에 더 적합
        - 시간대 알림
- [MediaPlayer 가이드](https://developer.android.com/guide/topics/media/mediaplayer)
- [RingToneManager 가이드](https://developer.android.com/reference/kotlin/android/media/RingtoneManager)
- [암시적 브로드 캐스트 예외](https://developer.android.com/guide/components/broadcast-exceptions)
- 재생은 MediaPlayer, RingToneManager 가 있는것으로 확인됨
    - 링톤 타입의 아이템을 재생할 것이기에 RingToneManager를 사용하기로 선택

### 2.Alarm Table 컬럼 정의
- 알람이름,
- 알람시간,
- 알람설정여부,
- 링톤 Uri

### 3.기능구현 순서 작성
- 아키텍처 설계
    - AppModule(Activity)
        - common
            - ui
        - feature
            - alarmList
            - settings
            - alarm
            - dataBase
        
    - CommonModule
        - UI 구현시 재사용이 많은 BaseActivity, BaseFragment, ListAdapter, ViewHolder
    

- Data 정의
- DataTable 구현 및 테스트 코드
    - Room 의 In Memory Database를 생성하여 UnitTest
- UseCase 작성
- Viewmodel 기능 구현 및 테스트 코드 작성
- UI 구현
- 알람매니저 기능 테스트
- [리마인드 알림] UI 구현

### 예제 화면
![ezgif-3-18ef57df60](https://user-images.githubusercontent.com/7857824/147532181-9585b0ab-f2fe-4e33-a5aa-0e938d5d0b7f.gif)


### 기타
- (이미지 사용)[https://fonts.google.com/icons?selected=Material+Icons]

### 회고
- 네비게이션 컴포넌트의 활용
- 알람 등록/해제(AlarmManager, PendingIntent)
- 알람이 울리는 조건
	- 알람시간이 현재시간보다 이전이면 바로 울리기 때문에 알람 재등록시에는 +1 Day가 필요함
- BroadCastReceiver action 처리
- 사용자 디바이스 재부팅시 알람 재등록 관련 [암시적 브로드 캐스트 예외](https://developer.android.com/guide/components/broadcast-exceptions)


### 네비게이션 컴포넌트 관련 참고자료
---

- 네비게이션 가이드
  https://developer.android.com/guide/navigation/navigation-getting-started

대상 간 이동을 위해 Safe Args Gradle 플러그인을 사용하는 것이 좋습니다.
이 플러그인은 대상 간 유형 안전 탐색 및 인수 전달을 사용 설정하는 간단한 객체 및 빌더 클래스를 생성합니다.

- classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version") 추가

아래와 같이 사용 가능
override fun onClick(view: View) {
val action =
SpecifyAmountFragmentDirections
.actionSpecifyAmountFragmentToConfirmationFragment()
view.findNavController().navigate(action)
}

- [대상간 데이터 전달](https://developer.android.com/guide/navigation/navigation-pass-data)

```
전달
override fun onClick(v: View) {
   val amountTv: EditText = view!!.findViewById(R.id.editTextAmount)
   val amount = amountTv.text.toString().toInt()
   val action = SpecifyAmountFragmentDirections.confirmationAction(amount)
   v.findNavController().navigate(action)
}
```

```
수신
val args: ConfirmationFragmentArgs by navArgs()

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val tv: TextView = view.findViewById(R.id.textViewAmount)
    val amount = args.amount
    tv.text = amount.toString()
}
```

---

