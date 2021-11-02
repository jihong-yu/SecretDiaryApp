package com.jimdac_todolist.secretdiaryapp

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit

class DiaryActivity : AppCompatActivity() {

    private lateinit var sp : SharedPreferences

    private val handler:Handler = Handler(Looper.getMainLooper())

    private val editText : EditText by lazy {
        findViewById(R.id.diaryEditText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        //oncreate함수(메모리 생성) 호출 이후 변수 및 뷰 초기화
        initMethod()

        val runnable = Runnable {
            //edit 에서 commit은 동기적으로실행되기때문에 main쓰레드의 처리중인 쓰레드가 블락킹이 될 수도
            //있다. apply는 비동기적으로 실행되기 때문에 blocking안됨
            sp.edit{
                this.putString("diaryText",editText.text.toString())
            }
        }

        editText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            //postDelayed로 인해 0.5초마다 해당 runnable를 실행하지만 removeCallbacks으로인해 계속해서 취소하게된다.
            //즉, 마지막 runnable만 실행하게 된다.
            override fun afterTextChanged(s: Editable?) {
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable,500L)
            }
        })
    }
    
    //oncreate함수(메모리 생성) 호출 이후 변수 및 뷰 초기화
    private fun initMethod(){
        sp = getSharedPreferences("diarySP", MODE_PRIVATE)
        editText.setText(sp.getString("diaryText",""))
    }
}