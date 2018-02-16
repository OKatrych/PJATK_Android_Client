package eu.warble.pjappkotlin.utils


object Constants {
    val PJAPP_PREFS = "Pjapp-prefs"

    val CREDENTIALS_ERROR = "CREDENTIALS_ERROR"
    val CONNECTION_ERROR = "CONNECTION_ERROR"
    val UNKNOWN_ERROR = "UNKNOWN_ERROR"
    val EMPTY_ERROR = "EMPTY_ERROR"
    val CONTEXT_ERROR = "CONTEXT_ERROR"

    val SFTP_SERVER_URL = "sftp.pjwstk.edu.pl"

    val INDOORWAY_TRAFFIC_API_KEY = "acdd0179-3ea5-421b-8035-e96009d61a77"

    val API_STUDENT_PERSONAL_DATA_JSON =
            "https://ws.pjwstk.edu.pl/test/Service.svc/XmlService/GetStudentPersonalDataJson"
    val API_STUDENT_SCHEDULE =
            "https://ws.pjwstk.edu.pl/test/Service.svc/XmlService/GetStudentSchedule"
    val API_CHECK_CREDENTIALS =
            "https://ws.pjwstk.edu.pl/test/Service.svc/XmlService/tester"

    object ListActivityAdapterType {
        val PAYMENT = "PAYMENT"
        val RECEIVABLE = "RECEIVABLE"
        val MARKS = "MARKS"
        val DEFAULT = "DEFAULT"
    }

}