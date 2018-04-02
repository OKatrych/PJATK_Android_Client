package eu.warble.pjappkotlin.mvp.news

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.data.model.NewsItem
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BaseActivity
import eu.warble.pjappkotlin.mvp.BaseFragment
import eu.warble.pjappkotlin.utils.Injection
import kotlinx.android.synthetic.main.fragment_news.view.loading_screen
import kotlinx.android.synthetic.main.fragment_news.view.news_recycler_view


class NewsFragment : BaseFragment(), NewsContract.View {

    override val TAG: String = "NewsFragment"
    override val applicationNavigator: ApplicationNavigator by lazy { ApplicationNavigator(activity as BaseActivity) }
    override lateinit var presenter: NewsContract.Presenter
    private lateinit var newsList: RecyclerView
    private lateinit var loadingScreen: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        initNewsList(view)
        loadingScreen = view.loading_screen
        initPresenter()
        return view
    }

    private fun initNewsList(view: View) {
        newsList = view.news_recycler_view
        newsList.layoutManager = LinearLayoutManager(mContext)
        newsList.adapter = NewsListAdapter(
                emptyList(),
                onBookmarkClick = { url ->
                    openWebPage(url)
                },
                onItemClick = { newsItem ->
                    openWebPage(newsItem.url)
                }
        )
    }

    override fun showNewsItems(items: ArrayList<NewsItem>) {
        (newsList.adapter as NewsListAdapter).updateNewsList(items)
    }

    override fun showConnectionError(action: () -> Unit) {
        showErrorWithAction(R.string.connect_error, R.string.retry, action)
    }

    override fun showLoadingScreen(show: Boolean) {
        loadingScreen.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun openWebPage(url: String?) {
        applicationNavigator.openWebPage(url)
    }

    private fun initPresenter() {
        presenter = NewsPresenter(
                Injection.provideNewsDataRepository(),
                this,
                mContext as Context
        )
        presenter.start()
    }

    companion object {

        fun newInstance() = NewsFragment()

    }
}