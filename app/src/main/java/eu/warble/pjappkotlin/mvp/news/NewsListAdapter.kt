package eu.warble.pjappkotlin.mvp.news

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.data.model.NewsItem
import eu.warble.pjappkotlin.utils.Constants
import eu.warble.pjappkotlin.utils.inflate
import kotlinx.android.synthetic.main.bookmarks.view.image_bookmark1
import kotlinx.android.synthetic.main.bookmarks.view.image_bookmark2
import kotlinx.android.synthetic.main.bookmarks.view.image_bookmark3
import kotlinx.android.synthetic.main.bookmarks.view.image_bookmark4
import kotlinx.android.synthetic.main.bookmarks.view.item_bookmark1
import kotlinx.android.synthetic.main.bookmarks.view.item_bookmark2
import kotlinx.android.synthetic.main.bookmarks.view.item_bookmark3
import kotlinx.android.synthetic.main.bookmarks.view.item_bookmark4
import kotlinx.android.synthetic.main.bookmarks.view.title_bookmark1
import kotlinx.android.synthetic.main.bookmarks.view.title_bookmark2
import kotlinx.android.synthetic.main.bookmarks.view.title_bookmark3
import kotlinx.android.synthetic.main.bookmarks.view.title_bookmark4
import kotlinx.android.synthetic.main.list_item_news.view.date
import kotlinx.android.synthetic.main.list_item_news.view.title


class NewsListAdapter(
        news: List<NewsItem>,
        private val onItemClick: (NewsItem) -> Unit,
        private val onBookmarkClick: (url: String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    private var items = ArrayList<NewsItem>(news)

    fun updateNewsList(newData: List<NewsItem>) {
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(parent.inflate(R.layout.bookmarks))
            TYPE_ITEM -> ItemViewHolder(parent.inflate(R.layout.list_item_news))
            else -> throw RuntimeException("Wrong ViewHolder type : $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(items[position - 1], onItemClick)
            is HeaderViewHolder -> holder.bind(onBookmarkClick)
        }
    }

    override fun getItemCount(): Int {
        return items.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return TYPE_ITEM
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.title
        private val date: TextView = itemView.date

        fun bind(newsItem: NewsItem, onItemClick: (NewsItem) -> Unit) {
            itemView.setOnClickListener { onItemClick(newsItem) }
            title.text = newsItem.title
            date.text = newsItem.date
        }

    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(onBookmarkClick: (url: String) -> Unit) = with(itemView) {
            item_bookmark1.setOnClickListener { onBookmarkClick(Constants.PJATK_SITE) }
            title_bookmark1.text = context.getString(R.string.pjatk)
            image_bookmark1.setImageResource(R.drawable.pja_logo_svg)
            item_bookmark2.setOnClickListener { onBookmarkClick(Constants.PJATK_FB_SITE) }
            title_bookmark2.text = context.getString(R.string.pjatk_facebook)
            image_bookmark2.setImageResource(R.drawable.facebook)
            item_bookmark3.setOnClickListener { onBookmarkClick(Constants.PJATK_INSTAGRAM_SITE) }
            title_bookmark3.text = context.getString(R.string.pjatk_instagram)
            image_bookmark3.setImageResource(R.drawable.instagram)
            item_bookmark4.setOnClickListener { onBookmarkClick(Constants.PJATK_MYKHI_SITE) }
            title_bookmark4.text = context.getString(R.string.mykhi)
            image_bookmark4.setImageResource(R.drawable.lifebuoy)
        }
    }
}
