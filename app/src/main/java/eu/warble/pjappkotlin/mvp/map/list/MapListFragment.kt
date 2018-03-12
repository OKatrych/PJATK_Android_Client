package eu.warble.pjappkotlin.mvp.map.list

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.indoorway.android.common.sdk.model.IndoorwayObjectId
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BaseActivity
import eu.warble.pjappkotlin.mvp.BaseFragment
import kotlinx.android.synthetic.main.fragment_map_list.view.btn_current_location
import kotlinx.android.synthetic.main.fragment_map_list.view.items_list
import kotlinx.android.synthetic.main.fragment_map_list.view.loading_screen

class MapListFragment : BaseFragment(), MapListContact.View {

    override val TAG: String = "MapListFragment"

    override lateinit var presenter: MapListContact.Presenter
    override val applicationNavigator: ApplicationNavigator by lazy {
        ApplicationNavigator(activity as BaseActivity)
    }
    private lateinit var itemsList: RecyclerView
    private lateinit var locationFAB: FloatingActionButton
    private lateinit var loadingScreen: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map_list, container, false)
        initViews(view)
        initAdapter()
        initListener()
        initPresenter()
        return view
    }

    private fun initViews(view: View) {
        itemsList = view.items_list
        locationFAB = view.btn_current_location
        loadingScreen = view.loading_screen
    }

    private fun initListener() {
        locationFAB.setOnClickListener {
            findMyLocation()
        }
    }

    private fun initAdapter() {

    }

    override fun updateMapListItems(items: List<Any>, type: MapListItemType) {
        when (type) {
            MapListItemType.BUILDING -> {

            }
            MapListItemType.MAP -> {

            }
        }
    }

    override fun showMap(buildingUUID: String, map: IndoorwayObjectId) {
        applicationNavigator.goToMapActivity(buildingUUID, map)
    }

    private fun findMyLocation() {
        applicationNavigator.goToMapActivity()
    }

    override fun showLoadingScreen(show: Boolean) {
        loadingScreen.visibility = if (show) View.VISIBLE else View.GONE
        locationFAB.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun initPresenter() {
        presenter = MapListPresenter(
                this,
                mContext as Context
        )
        presenter.start()
    }
}