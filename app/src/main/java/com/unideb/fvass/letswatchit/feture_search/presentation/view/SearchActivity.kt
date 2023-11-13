package com.unideb.fvass.letswatchit.feture_search.presentation.view


import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.media3.common.util.UnstableApi
import com.unideb.fvass.letswatchit.databinding.ActivitySearchBinding

@UnstableApi class SearchActivity : FragmentActivity() {

    private var binding: ActivitySearchBinding? = null
    private lateinit var searchFragment: SearchFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)

        searchFragment = SearchFragment()
        setContentView(binding?.root)
        if (savedInstanceState == null) {
            binding?.container?.id?.let {
                supportFragmentManager.beginTransaction().replace(it, searchFragment).commitNow()
            }
        }
    }
}