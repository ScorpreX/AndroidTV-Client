package com.unideb.fvass.letswatchit.core.presentation.view

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.media3.common.util.UnstableApi
import com.unideb.fvass.letswatchit.databinding.ActivityMainBinding

@UnstableApi class MainActivity : FragmentActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding?.mainBrowseFragment?.let {
            supportFragmentManager.beginTransaction().replace(it.id, MainFragment()).commitNow()
        }
    }
}