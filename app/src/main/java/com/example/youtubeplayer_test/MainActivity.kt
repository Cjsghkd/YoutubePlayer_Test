package com.example.youtubeplayer_test

import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Rational
import android.view.WindowManager
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    private lateinit var webView: WebView
//    private var videoId = "oI9mlhGkt-U"  // 테스트 중인 비디오 ID
    private var videoId = "FwGfKHuDinM"  // 테스트 중인 비디오 ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        )

        webView = WebView(this)  // WebView 객체 초기화

        setContent {
            YouTubePlayer(videoId, webView)
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val aspectRatio = Rational(16, 9)
            val params = PictureInPictureParams.Builder()
                .setAspectRatio(aspectRatio)
                .build()
            enterPictureInPictureMode(params)
        } else {
            Toast.makeText(this, "PIP 모드는 안드로이드 O 이상에서만 가능합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)

        if (isInPictureInPictureMode) {
            // PiP 모드로 들어갔을 때 재생 중이었다면 계속 재생
        } else {
            // PiP 모드에서 빠져나올 때 재생 중이던 영상은 일시정지
        }
    }
}