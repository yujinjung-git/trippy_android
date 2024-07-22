package com.example.trippy_android.User.Login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.trippy_android.User.UserRetrofitService
import com.example.trippy_android.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginView {
    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginbutton.setOnClickListener {
            login()
        }

        binding.here.setOnClickListener{
            loginExtension()
        }


    }
    private fun init(){
        binding.idedit.text = null
        binding.pwedit.text = null
    }

    private fun login(){
        if (binding.idedit.text.toString().isEmpty() || binding.pwedit.text.toString().isEmpty()){
            Toast.makeText(this,"Fill in all the blanks", Toast.LENGTH_LONG).show()
            Log.d("test", "fill in all the blanks")
            return
        }

        val memberId:String=binding.idedit.text.toString()
        val password:String=binding.pwedit.text.toString()
        saveId(memberId)
        val usService = UserRetrofitService(this)
        usService.setLoginView(this)
        usService.login(LoginReq(memberId, password))

    }

    private fun saveId(memberId:String?){
        val spf=getSharedPreferences("auth", MODE_PRIVATE)
        val editor=spf.edit()
        editor.putString("memberId", memberId)
        editor.apply()
        editor.commit()
    }

    private fun saveJwt(jwt:String?){
        val spf=getSharedPreferences("auth", MODE_PRIVATE)
        val editor=spf.edit()
        editor.putString("jwt", jwt)
        editor.apply()
        editor.commit()
    }

    private fun getJwt():String?{
        val spf = getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString("jwt","0")
    }




    override fun onLoginSuccess(code: String, jwt: String) {
        when(code){
            "COMMON200"->{
                Log.d("Success", code)
                saveJwt(jwt)
                init()
                Log.d("hi", getJwt()!!)
                Log.d("JWT", "Stored JWT in SharedPreferences: ${getJwt()!!}")
                Log.d("JWT", "Stored JWT in CustomCookieJar: ${CustomCookieJar(this).getJwtToken()!!}")

            }
            else->{
                Log.d("error", code)
                if(code=="MEMBER4002"){
                    Toast.makeText(this, "Incorrect memberId or password", Toast.LENGTH_LONG).show()
                    init()
                }
                else if(code=="MEMBER4001")
                {
                    Toast.makeText(this, "memberId does not exist", Toast.LENGTH_LONG).show()
                    init()
                }
                else if(code=="MEMBER4015")
                {
                    Toast.makeText(this, "Unsupported login format.", Toast.LENGTH_LONG).show()
                    init()
                }
            }
        }
    }
    private fun loginExtension() {
        val usService = UserRetrofitService(this)
        usService.loginExtension()
    }


    override fun onLoginFailure(code: String, msg: String) {
        Log.d("failure", code)
        if(code.toString()=="2003"){
            Toast.makeText(this, "Incorrect password", Toast.LENGTH_LONG).show()
            init()
        }
        else if(code.toString()=="2002")
        {
            Toast.makeText(this, "Email does not exist", Toast.LENGTH_LONG).show()
            init()
        }
    }
}