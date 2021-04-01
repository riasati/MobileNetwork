package com.example.mobilenetworkproject.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ArticleModel(
    @StringRes val articleTitle: Int,
    @StringRes val articleSubTitle: Int,
    @DrawableRes val imageResourceId: Int

)