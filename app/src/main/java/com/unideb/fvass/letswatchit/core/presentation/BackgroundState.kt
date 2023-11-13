package com.unideb.fvass.letswatchit.core.presentation

import android.graphics.drawable.BitmapDrawable
import androidx.fragment.app.Fragment
import androidx.leanback.app.BackgroundManager
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.launch

class BackgroundState(private val fragment: Fragment) {

    private val backgroundManager by lazy {
        val activity = fragment.requireActivity()
        BackgroundManager.getInstance(activity).apply {
            attach(activity.window)
            isAutoReleaseOnStop = false
        }
    }

    fun loadUrl(url: String) {
        fragment.lifecycleScope.launch{
            val imageLoader = ImageLoader.Builder(fragment.requireContext()).build()
            val request = ImageRequest.Builder(fragment.requireContext()).data(url).target(onError = { _ ->
            }, onSuccess = { result ->
                val bitmap = (result as BitmapDrawable).bitmap
                backgroundManager.setBitmap(bitmap)
            }).build()
            imageLoader.enqueue(request)
        }


    }
}