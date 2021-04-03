package com.example.mobilenetworkproject.data

import com.example.mobilenetworkproject.R
import com.example.mobilenetworkproject.model.ArticleModel

class Datasource {
    fun loadAffirmations(): List<ArticleModel> {
        return listOf<ArticleModel>(
            ArticleModel(R.string.article1_title, R.string.article1_sub_title, R.string.article1_star, R.drawable.image1),
            ArticleModel(R.string.article2_title, R.string.article2_sub_title, R.string.article2_star, R.drawable.image2)
        )
    }
}