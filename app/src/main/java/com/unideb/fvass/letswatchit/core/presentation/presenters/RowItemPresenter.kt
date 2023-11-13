package com.unideb.fvass.letswatchit.core.presentation.presenters

import android.view.Gravity
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.leanback.widget.BaseCardView
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import coil.load
import coil.size.Scale
import com.unideb.fvass.letswatchit.core.remote.Provider
import com.unideb.fvass.letswatchit.R
import com.unideb.fvass.letswatchit.core.domain.models.Movie

class RowItemPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val imageCardView = ImageCardView(parent.context).apply {
            isFocusable = true
            isFocusableInTouchMode = true

//            cardType = BaseCardView.CARD_TYPE_MAIN_ONLY
            with(mainImageView) {
                val posterWidth = parent.resources.getDimension(R.dimen.cardWith).toInt()
                val posterHeight = parent.resources.getDimension(R.dimen.cardHeight).toInt()
                layoutParams = BaseCardView.LayoutParams(posterWidth, posterHeight)
            }
        }
        return ViewHolder(imageCardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        val movie = item as Movie
        with(viewHolder?.view as ImageCardView) {
            val posterWidth = resources.getDimension(R.dimen.cardWith).toInt()
            val posterHeight = resources.getDimension(R.dimen.cardHeight).toInt()
            titleText = movie.title
            setInfoAreaBackgroundColor(ContextCompat.getColor(viewHolder.view.context,
                androidx.appcompat.R.color.material_blue_grey_800))
            foregroundGravity = Gravity.CENTER
            mainImageView.load(data = Provider.getImageURL(movie.id), builder = {
                scale(Scale.FIT)
                size(posterWidth, posterHeight)
                allowHardware(false)
            })
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        with(viewHolder?.view as ImageCardView) {
            mainImage = null
        }
    }
}