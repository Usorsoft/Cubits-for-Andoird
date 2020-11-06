package com.mp.example.pages.articledetails

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.mp.cubit.Cubit
import com.mp.cubit.cubit_owner.CubitActivity
import com.mp.cubit.foundation.NavigationAction
import com.mp.cubit.foundation.NavigateBack
import com.mp.cubit.of
import com.mp.example.pages.articles.models.ArticleEntryModel
import com.mp.example.utils.Args


class ArticleDetailsActivity : CubitActivity<ArticleDetailsCubit, ArticleDetailsState>() {

    private lateinit var view: ArticleDetailsView

    override fun onCreateCubit() = Cubit.of(this, ArticleDetailsCubit::class) {
        val articleModel = Args.consume(ArticleEntryModel::class)
        ArticleDetailsCubit(articleModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ArticleDetailsView(cubit)
        cubit.addActionListener(::onNavigationAction, condition = { it is NavigationAction })
        setContent { view.Compose() }
        cubit.init()
    }

    private fun onNavigationAction(action: NavigationAction) {
        when(action) {
            is NavigateBack -> onBackPressed()
        }
    }

    companion object {
        fun start(activity: AppCompatActivity, model: ArticleEntryModel) {
            Args.provide(model)
            val intent = Intent(activity.baseContext, ArticleDetailsActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
