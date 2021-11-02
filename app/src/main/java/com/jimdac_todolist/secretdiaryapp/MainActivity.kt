package com.jimdac_todolist.secretdiaryapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val numberPicker1: NumberPicker by lazy {
        findViewById(R.id.numberPicker1)
    }
    private val numberPicker2: NumberPicker by lazy {
        findViewById(R.id.numberPicker2)
    }
    private val numberPicker3: NumberPicker by lazy {
        findViewById(R.id.numberPicker3)
    }

    private val openButton: Button by lazy {
        findViewById(R.id.openButton)
    }

    private val changePasswordButton: Button by lazy {
        findViewById(R.id.changePasswordButton)
    }
    
    //password 라는 SharedPreferences가져오기
    private lateinit var sp : SharedPreferences

    private var passwordChanging = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        //초기 메모리 생성 후 변수 및 뷰 초기화
        initMethod()
        
        //오픈버튼 클릭 리스너
        openButtonListener()
        
        //체인지패스워드 버튼 클릭 리스너
        changePasswordButtonListener()
    }

    //초기 메모리가 생성되면서 필요한 변수 초기화 작업
    private fun initMethod(){
        numberPicker1.maxValue = 9
        numberPicker2.maxValue = 9
        numberPicker3.maxValue = 9
        sp = getSharedPreferences("password", Context.MODE_PRIVATE)
    }

    //openButton 클릭 리스너
    private fun openButtonListener(){
        openButton.setOnClickListener {
            val spPassword = sp.getString("password","000")
            val inputPassword = numberPicker1.value.toString() + numberPicker2.value.toString() +numberPicker3.value.toString()
            if(spPassword == inputPassword){
                Toast.makeText(this@MainActivity, "비밀 다이어리 오픈", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@MainActivity,DiaryActivity::class.java))
            }else {
                failedAlertDialogMessage()
            }
        }
    }

    //패스워드 변경버튼 클릭 리스너
    private fun changePasswordButtonListener(){
        changePasswordButton.setOnClickListener {
            
            //만약 패스워드를 변경중이라면
            if(passwordChanging){
                //새로운 패스워드를 SharedPreferences에 넣는다.
                sp.edit(commit = true) {
                    val inputPassword = numberPicker1.value.toString() + numberPicker2.value.toString() +numberPicker3.value.toString()
                    this.putString("password",inputPassword)
                }
                Toast.makeText(this@MainActivity, "비밀번호 변경 완료", Toast.LENGTH_SHORT).show()
                //패스워드 변경중이 완료되었으면 다시 false값으로 돌린다.
                passwordChanging = false
                //패스워드변경을 완료하였으면 배경색을 다시 검은색으로 복구시킨다.
                changePasswordButton.setBackgroundColor(getColor(R.color.black))
                return@setOnClickListener
            }
            
            val spPassword = sp.getString("password","000")
            val inputPassword = numberPicker1.value.toString() + numberPicker2.value.toString() +numberPicker3.value.toString()
            //만약 입력한패스워드와 저장되어있는 패스워드가 같아서 패스워드를 변경하기를 원한다면
            if(spPassword == inputPassword){
                //패스워드변경중 변수에 true값 대입
                passwordChanging = true
                Toast.makeText(this@MainActivity, "원하는 비밀번호를 입력 후 다시한번 버튼을 눌러주세요", Toast.LENGTH_LONG).show()
                //비밀번호 변경시 배경색을 빨간색으로 변경하여 변경중이라는 것을 사용자에게 알려준다.
                changePasswordButton.setBackgroundColor(getColor(R.color.red))
            }else {
                failedAlertDialogMessage()
            }

        }
    }
    
    //비밀번호가 잘못되었을때 알람 띄어주는 메서드
    private fun failedAlertDialogMessage(){
        val dialog = AlertDialog.Builder(this@MainActivity)
        dialog.setTitle("실패!!")
        dialog.setMessage("비밀번호가 잘못되었습니다.")
        dialog.setPositiveButton("확인"
        ) { _, _ ->  }
        dialog.show()
    }
}