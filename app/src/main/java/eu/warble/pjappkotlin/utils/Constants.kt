package eu.warble.pjappkotlin.utils

import org.threeten.bp.format.DateTimeFormatter


object Constants {
    const val PJAPP_PREFS = "Pjapp-prefs"

    const val EMPTY_STRING = ""

    const val CREDENTIALS_ERROR = "CREDENTIALS_ERROR"
    const val CONNECTION_ERROR = "CONNECTION_ERROR"
    const val UNKNOWN_ERROR = "UNKNOWN_ERROR"
    const val EMPTY_ERROR = "EMPTY_ERROR"
    const val CONTEXT_ERROR = "CONTEXT_ERROR"

    const val SFTP_SERVER_URL = "sftp.pjwstk.edu.pl"

    const val INDOORWAY_TRAFFIC_API_KEY = "acdd0179-3ea5-421b-8035-e96009d61a77"

    const val API_STUDENT_PERSONAL_DATA_JSON =
            "https://ws.pjwstk.edu.pl/test/Service.svc/XmlService/GetStudentPersonalDataJson"
    const val API_STUDENT_SCHEDULE =
            "https://ws.pjwstk.edu.pl/test/Service.svc/XMLService/GetStudentScheduleJson?"
    const val API_CHECK_CREDENTIALS =
            "https://ws.pjwstk.edu.pl/test/Service.svc/XmlService/tester"

    val API_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    const val API_NEWS_JSON = "https://pja-rss.herokuapp.com/?format=json"

    const val PJATK_SITE = "https://pja.edu.pl"
    const val PJATK_FB_SITE = "https://facebook.com/polskojaponska"
    const val PJATK_INSTAGRAM_SITE = "https://instagram.com/polskojaponska"
    const val PJATK_MYKHI_SITE = "https://pja.mykhi.org"

    const val SUM_S9_URL = "http://sum-tech.pjwstk.edu.pl"
    const val SUM_SHAREPOINT_URL = "https://pejot.sharepoint.com/sites/tomaszew/SUM/SitePages/Home.aspx"
    const val SUM_SLACK_URL = "https://pjsum.slack.com"

    const val PRIVACY_POLICY_URL =
        "http://sum.pjwstk.edu.pl/docs/Polityka-prywatno%C5%9Bci-aplikacji-mobilnej.pdf"

    object ListActivityAdapterType {
        const val PAYMENT = "PAYMENT"
        const val RECEIVABLE = "RECEIVABLE"
        const val MARKS = "MARKS"
        const val DEFAULT = "DEFAULT"
    }

}