package eu.warble.pjappkotlin.mvp.studentinfo.about

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.mvp.BaseActivity
import eu.warble.pjappkotlin.mvp.BaseFragment
import eu.warble.pjappkotlin.mvp.main.MainActivity
import eu.warble.pjappkotlin.utils.Constants
import eu.warble.pjappkotlin.utils.Injection
import kotlinx.android.synthetic.main.card_about.*
import kotlinx.android.synthetic.main.card_about.specialization
import kotlinx.android.synthetic.main.card_specialization_sum.*
import kotlinx.android.synthetic.main.fragment_student_about.*

const val PALPATINE_GIF_DURATION = 4500L

class StudentAboutFragment : BaseFragment(), StudentAboutContract.View {

    override val TAG: String = ""
    override lateinit var presenter: StudentAboutContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_student_about, container, false)
        initPresenter()
        return view
    }

    private fun initPresenter() {
        presenter = StudentAboutPresenter(
                Injection.provideStudentDataRepository(mContext as Context),
                this,
                mContext as Context
        )
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setStudentName(studentName: String?) {
        student_name?.text = studentName
    }

    override fun setStudies(studies: String?) {
        this.studies?.text = studies
    }

    override fun setYear(year: String?) {
        this.year_counter.text = year
    }

    override fun setSemester(semester: String?) {
        this.semester_counter?.text = semester
    }

    override fun setGroups(groups: String?) {
        this.groups?.text = groups
    }

    override fun setStatus(status: String?) {
        this.status?.text = status
    }

    override fun setAvgMark(avgMark: Float?) {
        this.ratingBar?.rating = avgMark ?: 0f
    }

    override fun setSpecialization(specialization: String?) {
        this.specialization?.text =
                if (specialization != null && specialization.isNotEmpty())
                    specialization
                else
                    getString(R.string.no)
    }

    override fun showHiddenSUMCard() {
        val hiddenImage = hidden_palpatine_image

        card_specialization_sum.visibility = View.VISIBLE

        btn_s9_room.setOnClickListener { openURL(Constants.SUM_S9_URL) }
        btn_sharepoint.setOnClickListener { openURL(Constants.SUM_SHAREPOINT_URL) }
        btn_slack.setOnClickListener { openURL(Constants.SUM_SLACK_URL) }

        //show easter egg of Palpatine

        var isGifShowing = false
        empire_logo.setOnLongClickListener {
            if (!isGifShowing) {
                isGifShowing = true
                hiddenImage.visibility = View.VISIBLE
                Glide.with(this)
                        .load(R.raw.palpatine)
                        .signature(ObjectKey("${System.currentTimeMillis()}"))
                        .into(hiddenImage)

                //Hide image after gif finished
                Handler().postDelayed({
                    isGifShowing = false
                    hiddenImage.visibility = View.GONE
                }, PALPATINE_GIF_DURATION)
            }
            return@setOnLongClickListener true
        }
    }

    private fun openURL(url: String) {
        (activity as? MainActivity)?.applicationNavigator?.openWebPage(url)
    }

    companion object {

        fun newInstance() = StudentAboutFragment()

    }
}