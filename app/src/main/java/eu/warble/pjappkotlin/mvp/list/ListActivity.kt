package eu.warble.pjappkotlin.mvp.list

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BaseActivity
import eu.warble.pjappkotlin.utils.Injection
import kotlinx.android.synthetic.main.activity_list.recyclerView


class ListActivity : BaseActivity(), ListContract.View {

    override lateinit var presenter: ListContract.Presenter
    val applicationNavigator by lazy {
        ApplicationNavigator(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_list)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        presenter = ListPresenter(
                Injection.provideStudentDataRepository(applicationContext),
                this,
                applicationContext,
                intent.getStringExtra("adapterType")
        )

        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.invalidate()
    }

    override fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            applicationNavigator.goBack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}