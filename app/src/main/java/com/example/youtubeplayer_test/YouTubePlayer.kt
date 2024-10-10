package com.example.youtubeplayer_test

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun YouTubePlayer(videoId: String, webView: WebView) {
    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density

    // 16:9 비율로 높이 계산
    val aspectRatioHeight = (screenWidth * 9 / 16).dp

    AndroidView(
        factory = {
            webView.apply {
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    mediaPlaybackRequiresUserGesture = false // 자동 재생 켜기
                    allowFileAccess = true
                    allowContentAccess = true
                    settings.mixedContentMode = 0
                }
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
                setLayerType(View.LAYER_TYPE_HARDWARE, null)
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                loadUrl("https://www.youtube.com/embed/$videoId")
            }
        },
        update = { it.loadUrl("https://www.youtube.com/embed/$videoId") },
        modifier = Modifier.fillMaxWidth().height(aspectRatioHeight),
    )
}