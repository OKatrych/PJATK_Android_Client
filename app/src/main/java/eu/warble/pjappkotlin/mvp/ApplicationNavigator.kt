package eu.warble.pjappkotlin.mvp

import android.content.Intent
import com.indoorway.android.common.sdk.model.IndoorwayObjectId
import eu.warble.pjappkotlin.mvp.list.ListActivity
import eu.warble.pjappkotlin.mvp.login.LoginActivity
import eu.warble.pjappkotlin.mvp.main.MainActivity
import eu.warble.pjappkotlin.mvp.map.map.MapActivity
import eu.warble.pjappkotlin.utils.Constants

class ApplicationNavigator(private val baseActivity: BaseActivity) {

    fun goBack() {
        baseActivity.finish()
    }

    fun goToLoginActivity() {
        val intent = Intent(baseActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        baseActivity.startActivity(intent)
    }

    fun goToMainActivity() {
        val intent = Intent(baseActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        baseActivity.startActivity(intent)
    }

    fun goToMainActivityWithNoInternetMode() {
        val intent = Intent(baseActivity, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("internet", false)
        }
        baseActivity.startActivity(intent)
    }

    fun goToMainActivityWithGuestMode() {
        val intent = Intent(baseActivity, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("guestMode", true)
        }
        baseActivity.startActivity(intent)
    }

    fun goToListActivity(adapterType: String) {
        val intent = Intent(baseActivity, ListActivity::class.java).apply {
            putExtra("adapterType", adapterType)
        }
        baseActivity.startActivity(intent)
    }


    /**
     * Use when need to load specific map
     */
    fun goToMapActivity(buildingUUID: String, map: IndoorwayObjectId) {
        val intent = Intent(baseActivity, MapActivity::class.java).apply {
            putExtra("buildingUUID", buildingUUID)
            putExtra("mapUUID", map.uuid)
            putExtra("mapName", map.name)
        }
        baseActivity.startActivity(intent)
    }

    /**
     * Use when need to load map by current location
     */
    fun goToMapActivity() {
        val intent = Intent(baseActivity, MapActivity::class.java)
        baseActivity.startActivity(intent)
    }
}