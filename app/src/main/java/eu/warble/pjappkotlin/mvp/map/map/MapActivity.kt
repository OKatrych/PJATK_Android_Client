package eu.warble.pjappkotlin.mvp.map.map

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import android.view.View
import com.indoorway.android.common.sdk.listeners.generic.Action0
import com.indoorway.android.common.sdk.listeners.generic.Action1
import com.indoorway.android.common.sdk.model.IndoorwayMap
import com.indoorway.android.common.sdk.model.IndoorwayPosition
import com.indoorway.android.map.sdk.listeners.OnRoomSelectedListener
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BaseActivity
import eu.warble.pjappkotlin.utils.Injection
import kotlinx.android.synthetic.main.activity_map.*
import permissions.dispatcher.*

@RuntimePermissions
class MapActivity : BaseActivity(), MapContract.View {

    override lateinit var presenter: MapContract.Presenter
    override val applicationNavigator: ApplicationNavigator by lazy {
        ApplicationNavigator(this)
    }
    private val mapView by lazy { map_view }
    private val loadingScreen by lazy { loading_screen }
    private val determiningLocationText by lazy { determining_loc_text }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_map)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        initPresenter()
        onExtraReceived()
    }

    private fun onExtraReceived() {
        val buildingUUID = intent.getStringExtra("buildingUUID")
        val mapUUID = intent.getStringExtra("mapUUID")
        val mapName = intent.getStringExtra("mapName")
        if (buildingUUID != null && mapUUID != null && mapName != null) {
            setTitle(mapName)
            presenter.loadMap(buildingUUID, mapUUID, mapName)
        } else {
            findLocationWithPermissionCheck()
        }
    }

    override fun loadMap(buildingUUID: String,
                         mapUUID: String,
                         onMapLoadCompletedListener: Action1<IndoorwayMap>,
                         onMapLoadFailedListener: Action0,
                         onRoomSelectedListener: OnRoomSelectedListener
    ) {
        mapView.onMapLoadCompletedListener = onMapLoadCompletedListener
        mapView.onMapLoadFailedListener = onMapLoadFailedListener
        mapView.selection.onRoomSelectedListener = onRoomSelectedListener
        mapView.load(buildingUUID, mapUUID)
    }

    @NeedsPermission(
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.ACCESS_FINE_LOCATION
    )
    override fun findLocation() {
        presenter.findLocationAndLoadMap()
    }

    override fun printCurrentPosition(position: IndoorwayPosition) {
        mapView.position.clearPosition()
        mapView.position.setPosition(position, false)
    }

    override fun showLoadingScreen(show: Boolean) {
        loadingScreen.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showDeterminingLocationScreen(show: Boolean) {
        showLoadingScreen(show)
        determiningLocationText.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun startNavigationToObject(objectId: String) {
        mapView.navigation.start(objectId)
    }

    override fun stopNavigation() {
        mapView.navigation.stop()
    }

    @OnShowRationale(
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.ACCESS_FINE_LOCATION
    )
    fun showRationaleForPerms(request: PermissionRequest) {
        AlertDialog.Builder(this)
                .setMessage(R.string.permission_rationale_location_service_message)
                .setPositiveButton(R.string.button_allow, { _, _ -> request.proceed() })
                .setNegativeButton(R.string.button_deny, { _, _ -> request.cancel() })
                .setCancelable(false)
                .show()
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showDeniedForStorage() {
        AlertDialog.Builder(this)
                .setMessage(R.string.permissions_declined)
                .setPositiveButton(R.string.ok, { _, _ -> applicationNavigator.goBack() })
                .setCancelable(false)
                .show()
    }


    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            applicationNavigator.goBack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initPresenter() {
        presenter = MapPresenter(
                this,
                Injection.provideStudentDataRepository(applicationContext)
        )
    }
}