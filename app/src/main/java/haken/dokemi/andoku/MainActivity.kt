package haken.dokemi.andoku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import haken.dokemi.andoku.auth.LoginActivity
import haken.dokemi.andoku.common.StorageService
import haken.dokemi.andoku.ui.MainHomeActivity
import java.util.Timer
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar
    var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        initView()
    }

    private fun initView() {
        progressBar = findViewById(R.id.progress_bar)

        val token = StorageService.instance?.token
        Timer().schedule(2000) {
            if (token.isNullOrEmpty() || token == "") {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@MainActivity, MainHomeActivity::class.java))
                finish()
            }
        }

        setUpView()
    }

    private fun setUpView() {
        //progressBar
        Thread(Runnable {
            while (count < 100) {
                try {
                    count += 25
                    Thread.sleep(500)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progressBar.progress = count
            }
        }).start()
    }

    override fun onBackPressed() {
        return
    }

}