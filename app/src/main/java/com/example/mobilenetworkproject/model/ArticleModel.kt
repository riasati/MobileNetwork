package com.example.mobilenetworkproject.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ArticleModel(
    @StringRes val titleResourceId: Int,
    @StringRes val subTitleResourceId: Int,
    @StringRes val starResourceId: Int,
    @DrawableRes val imageResourceId: Int

)