package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.app.Activity
import android.graphics.Rect
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import ru.skillbranch.devintensive.models.Bender


class MainActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView
    lateinit var benderObj: Bender


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("M_MainActivity", "onCreate ")

        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        benderImage.setOnClickListener{
            Log.d("M_MainActivity", "isKeyboardOpen ${isKeyboardOpen()}" )
            Log.d("M_MainActivity", "isKeyboardClosed ${isKeyboardClosed()}" )
        }

        val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name
        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))
        val (r, g, b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)

        textTxt.text = benderObj.askQuestion()

        sendBtn.setOnClickListener(this)

        messageEt.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString())
                    messageEt.setText("")
                    val (r, g, b) = color
                    benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
                    textTxt.text = phrase
                    hideKeyboard()
                    return true
                }
                return false
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.d("M_MainActivity", "onCreate ")
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_MainActivity", "onPause ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_MainActivity", "onDestroy ")
    }

    override fun onStart() {
        super.onStart()
        Log.d("M_MainActivity", "onStart ")
    }

    override fun onResume() {
        super.onResume()
        Log.d("M_MainActivity", "onResume ")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.d("M_MainActivity", "onSaveInstanceState ")
        outState?.putString("STATUS", benderObj.status.name)
        outState?.putString("QUESTION", benderObj.question.name)
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_MainActivity", "onStop ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("M_MainActivity", "onRestart ")
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_send) {
            val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString())
            messageEt.setText("")
            val (r, g, b) = color
            benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
            textTxt.text = phrase


        }
    }
}

fun AppCompatActivity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = this.getCurrentFocus()
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
}

fun Activity.isKeyboardOpen(): Boolean {
    val r = Rect()
    val rootview = this.window.decorView
    rootview.getWindowVisibleDisplayFrame(r)
    val screenHeight = rootview.getHeight()
    val heightDifference = screenHeight - (r.bottom - r.top)
//    Log.d("M_MainActivity", "Rect ${r.bottom} ${r.top}" )
//    Log.d("M_MainActivity", "screenHeight $screenHeight" )
//    Log.d("M_MainActivity", "heightDifference $heightDifference" )
    return (heightDifference > 300)
}

fun Activity.isKeyboardClosed() = !isKeyboardOpen()
