package com.example.youtubeplayer_test

import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.view.WindowManager
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    private lateinit var webView: WebView
    private var wasPlaying = false  // 재생 상태 추적 변수
    private var videoId = "oI9mlhGkt-U"  // 테스트 중인 비디오 ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        )

        webView = WebView(this)  // WebView 객체 초기화

        setContent {
            YouTubePlayer(videoId = videoId, webView = webView)  // WebView 객체 전달
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()

        // PiP 모드로 들어가기 전의 재생 상태를 저장
//        wasPlaying = isVideoPlaying()

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
            if (wasPlaying) {
//                resumeVideo()
                Log.d("test123", "재생중")
            }
        } else {
            // PiP 모드에서 빠져나올 때 재생 중이던 영상은 일시정지
//            pauseVideo()
            Log.d("test123", "재생중X")
        }
    }

//    private fun isVideoPlaying(): Boolean {
//        var isPlaying = false
//        webView.evaluateJavascript(
//            """
//            (function() {
//                var playerState = document.querySelector('iframe').contentWindow.postMessage(
//                    '{"event":"command","func":"getPlayerState","args":""}', '*');
//                return playerState;
//            })();
//            """
//        ) { result ->
//            // 1: 재생 중 (Playing), 2: 일시정지 (Paused)
//            if (result == "1") {
//                isPlaying = true
//            }
//        }
//        return isPlaying
//    }
//
//    private fun resumeVideo() {
//        webView.evaluateJavascript(
//            """
//            document.querySelector('iframe').contentWindow.postMessage(
//                '{"event":"command","func":"playVideo","args":""}', '*');
//            """, null
//        )
//    }
//
//    private fun pauseVideo() {
//        webView.evaluateJavascript(
//            """
//            document.querySelector('iframe').contentWindow.postMessage(
//                '{"event":"command","func":"pauseVideo","args":""}', '*');
//            """, null
//        )
//    }
}
