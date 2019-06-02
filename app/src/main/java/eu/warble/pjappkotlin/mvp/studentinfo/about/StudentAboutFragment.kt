package eu.warble.pjappkotlin.mvp.studentinfo.about

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.mvp.BaseFragment
import eu.warble.pjappkotlin.utils.Injection
import kotlinx.android.synthetic.main.fragment_student_about.groups
import kotlinx.android.synthetic.main.fragment_student_about.ratingBar
import kotlinx.android.synthetic.main.fragment_student_about.semester_counter
import kotlinx.android.synthetic.main.fragment_student_about.specialization
import kotlinx.android.synthetic.main.fragment_student_about.status
import kotlinx.android.synthetic.main.fragment_student_about.student_name
import kotlinx.android.synthetic.main.fragment_student_about.studies
import kotlinx.android.synthetic.main.fragment_student_about.year_counter


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

    companion object {

        fun newInstance() = StudentAboutFragment()

    }
}