# SecretDiaryApp
3자리 비밀번호와 간단한 글 작성기능을 제공하는 간단한 

### 1. 개발환경
* IDE: Android Studio Arctic Fox | 2020.3.1 Canary 1
* Language : Kotlin
---
### 2. 사용 라이브러리
* SharedPrefrences 내장 라이브러리 사용
---
### 3. 지원기능
1. 3가지 비밀번호 설정이 가능하며 비밀번호 변경이 가능합니다.(초기 비밀번호 000)
2. 글을 작성하면 자동으로 기기내에 글을 저장해줍니다.(어플 재실행 시 글 데이터 존재)


---
### 4. 추가설명

기초적인 어플이기 때문에 ViewBinding 과 같은 라이브러리는 사용하지 않았으며 모두 각각 findViewById로 xml 뷰에 접근하였습니다.<br>

```kotlin
private val addButton: Button by lazy {
        findViewById(R.id.addButton)
    }
```
<br>
didAutoRun 라는 boolean형 변수를 만들어 사용자가 자동생성시작 버튼을 눌렀는지 체크하였습니다. 만약 눌렀다면 수동추가 버튼은 초기화버튼을 누르기 전까지 사용하지 못하게끔 예외 처리하였습니다.

```kotlin
private var didAutoRun:Boolean = false

if (didAutoRun){
                Toast.makeText(this@MainActivity, "자동생성 버튼을 이미 사용하였습니다. " +
                        "수동으로 번호 추가 버튼을 사용하기 위해서는 초기화 버튼을 눌러주세요", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
```


각각 번호마다 백그라운드 모양을 동적으로 처리하게끔 when문을 통하여 처리하였습니다.
  ```kotlin
  //버튼의 백그라운들와 텍스트를 번호의 범위마다 설정
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setNumberAndBackground(index: Int, value: Int) {
        when (value) {
            in 1..10 -> {
                numberTextViewList[index].background = getDrawable(R.drawable.circle_yellow)
                numberTextViewList[index].text = value.toString()
            }
            in 11..20 -> {
                numberTextViewList[index].background = getDrawable(R.drawable.circle_blue)
                numberTextViewList[index].text = value.toString()
            }
            in 21..30 -> {
                numberTextViewList[index].background = getDrawable(R.drawable.circle_red)
                numberTextViewList[index].text = value.toString()
            }
            in 31..40 -> {
                numberTextViewList[index].background = getDrawable(R.drawable.circle_gray)
                numberTextViewList[index].text = value.toString()
            }
            in 41..45 -> {
                numberTextViewList[index].background = getDrawable(R.drawable.circle_green)
                numberTextViewList[index].text = value.toString()
            }
            else -> {
                numberTextViewList[index].background = getDrawable(R.drawable.circle_green)
                numberTextViewList[index].text = value.toString()
            }
        }
    }
  ```
  
  로또번호를 생성할 때는 우선 set형식으로 변수를 선언해놓은뒤 중복되지 않도록 처리 하였습니다.<br>또한 수동으로 N개를 선택한뒤 자동생성버튼을 눌렀다고 가정하였을 때
  나머지 추가적으로 뽑아야 할 6-N개는 45개의 번호중 이미 선택한 N개의 번호를 제외한 45-N개를 새로운 리스트에 저장하였습니다.<br>그 후 shuffle이라는 리스트 내장 메서드를
  사용하여 랜덤으로 썩은뒤 6-N개를 추가적으로 뽑는 형식으로 처리하였습니다.
  ```kotlin
  //수동으로 선택 번호들을 저장할 변수를 set형으로 선언
  private val pickNumberSet = hashSetOf<Int>()
  
  // 로또번호 생성하는 함수
    private fun getRandomNumber(): List<Int> {

        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45) {
                if (i !in pickNumberSet){ //만약 해당숫자가 뽑은 숫자가 아니라면
                    this.add(i) //그 숫자를 넣는다.
                }
            }
        }
        
        //1~45까지의 수 중에서 랜덤으로 섞어 최대 6개를 뽑아 정렬한 후 리스트형으로 반환
        numberList.shuffle().let {
            return (pickNumberSet + numberList.subList(0, (6 - pickNumberSet.size))).sorted()
        }

    }
  ```
  
  


