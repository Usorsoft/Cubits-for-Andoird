package com.mp.example.pages.articles

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.mp.cubit.Cubit
import com.mp.cubit.cubit_owner.CubitActivity
import com.mp.cubit.foundation.NavigateBack
import com.mp.cubit.foundation.NavigationAction
import com.mp.cubit.of
import com.mp.example.NavigateNext
import com.mp.example.pages.articledetails.ArticleDetailsActivity
import com.mp.example.repositories.FakeArticlesRepository
import com.mp.example.repositories.PreferencesRepository
import org.koin.java.KoinJavaComponent.get

@kotlinx.coroutines.ExperimentalCoroutinesApi
class ArticlesActivity : CubitActivity<ArticlesCubit, ArticlesState>() {

    private lateinit var stateView: MainView

    override fun onCreateCubit() = Cubit.of(this, ArticlesCubit::class) {
        ArticlesCubit(
            get(FakeArticlesRepository::class.java),
            get(PreferencesRepository::class.java)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stateView = MainView(cubit)
        cubit.addActionListener(::onNavigationAction, condition = { it is NavigationAction })
        cubit.loadData()
        setContent { stateView.Compose() }
    }

    private fun onNavigationAction(action: NavigationAction) {
        when(action) {
            is NavigateNext -> start(this, "second_cubit_instance")
            is NavigateBack -> onBackPressed()
            is ShowArticleDetails -> ArticleDetailsActivity.start(this, action.model)
        }
    }

    companion object {
        fun start(activity: AppCompatActivity, cubitId: String? = null) {
            val intent = Intent(activity.baseContext, ArticlesActivity::class.java)
            cubitId?.let { intent.putExtras(Bundle().apply { putString("cubit_id", it) }) }
            activity.startActivity(intent)
        }
    }
}






































//    override fun onCreateCubit() = Cubit.ofAppScope(MainCubit::class)
//    override fun onCreateCubit() = Cubit.ofManualManagedScope(MainCubit::class)

//    fun disposeCubit() {
//        cubit.disposeFromManualManagedScope()
//        // or
//        Cubit.disposeFromManualManagedScope(MainCubit::class)
//    }







//class MainActivity : CubitActivity<MainCubit, MainState>() {
//
//    private val faqAdapter = FaqAdapter()
//    override fun onCreateCubit() = inject(MainCubit::class)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        cubit.addActionListener(::onNavigationAction, callIf = { it is NavAction })
//        cubit.loadData()
//    }
//
//    private fun setupViewComponents() {
////        recycler.adapter = faqAdapter
////        recycler.layoutManager = LinearLayoutManager(this)
////        swipe_refresh.setOnRefreshListener(cubit::loadData)
////        button_add.setOnClickListener { cubit.generateContent() }
////        button_error.setOnClickListener { cubit.showErrorCountDown() }
//    }
//
//    private fun onNavigationAction(action: NavAction) = when (action) {
//        is NavActionBack -> onBackPressed()
//        else -> faqAdapter.addEntry(FaqItem("Delayed navigation state"))
//    }
//
//    override fun onStateChange(state: MainState) {
//        setContent {
//            MainScreen(state)
//        }
//    }
//    = when(state) {
//        is Initial -> Unit
//        is InitialLoading -> {
//            loader.visibility = VISIBLE
//            text_empty.visibility = GONE
//        }
//        is InitialFail -> {
//            loader.visibility = GONE
//            text_empty.visibility = VISIBLE
//            text_empty.text = state.errorMessage
//        }
//        is Content -> {
//            loader.visibility = GONE
//            swipe_refresh.isRefreshing = state.isRefreshing
//            text_empty.visibility = state.textVisibility
//            text_empty.text = state.text
//            recycler.visibility = state.listVisibility
//            faqAdapter.setEntries(state.listData)
//            text_error.text = state.errorMessage
//            error_container.visibility = state.errorMessage.isNullOrBlank().toGoneElseVisible
//        }
//        else -> Unit
//    }


































//
//
//@kotlinx.coroutines.ExperimentalCoroutinesApi
//class MainActivity : CubitActivity<MainCubit, MainState>() {
//
//    private lateinit var stateView: MainView
//
//    override fun onCreateCubit() = Cubit.withManagedState(
//        stateStore = AppStore,
//        stateKey = "main_state",
//        cubit = inject(MainCubit::class)
//    )
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        stateView = MainView(cubit)
//        cubit.addActionListener(::onNavigationAction, callIf = { it is NavAction })
//        cubit.loadData()
//        setContent { stateView.Compose() }
//    }
//
//    private fun onNavigationAction(action: NavAction) {
//        when(action) {
//            is NavActionNext -> {
//                val intent = Intent(baseContext, MainActivity::class.java)
//                startActivity(intent)
//            }
//            else -> onBackPressed()
//        }
//    }
////
////    override fun onStateChange(state: MainState) {
//////        cubit.mutableState.value = state
//////        setContent { view.draw(state) }
////    }
//}

