package eu.warble.pjappkotlin.mvp.list

import android.support.v7.widget.RecyclerView
import eu.warble.pjappkotlin.mvp.BasePresenter
import eu.warble.pjappkotlin.mvp.BaseView


interface ListContract {

    interface View : BaseView<Presenter> {

        fun setAdapter(adapter: RecyclerView.Adapter<*>)

        fun setTitle(title: String)

    }

    interface Presenter : BasePresenter {
        //no-op
    }

}