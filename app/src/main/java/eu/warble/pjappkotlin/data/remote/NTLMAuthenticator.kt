package eu.warble.pjappkotlin.data.remote

import jcifs.ntlmssp.NtlmFlags
import jcifs.ntlmssp.Type1Message
import jcifs.ntlmssp.Type2Message
import jcifs.ntlmssp.Type3Message
import jcifs.util.Base64
import okhttp3.Authenticator
import okhttp3.Credentials
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.io.IOException

class NTLMAuthenticator(
        val login: String, val password: String, val domain: String = "", val workstation: String = ""
) : Authenticator {

    private val TYPE_1_FLAGS = NtlmFlags.NTLMSSP_NEGOTIATE_56 or
            NtlmFlags.NTLMSSP_NEGOTIATE_128 or
            NtlmFlags.NTLMSSP_NEGOTIATE_NTLM2 or
            NtlmFlags.NTLMSSP_NEGOTIATE_ALWAYS_SIGN or
            NtlmFlags.NTLMSSP_REQUEST_TARGET
    private var counter = 0

    override fun authenticate(route: Route?, response: Response?): Request? {
        //if counter > 3 then we know Credentials are incorrect
        if (counter > 3) {
            return null
        }
        val authHeaders = response?.headers("WWW-Authenticate")
        if (authHeaders != null) {
            var negotiate = false
            var ntlm = false
            var ntlmValue: String? = null
            for (authHeader in authHeaders) {
                if (authHeader.equals("Negotiate", ignoreCase = true)) {
                    counter++
                    negotiate = true
                }
                if (authHeader.equals("NTLM", ignoreCase = true)) {
                    ntlm = true
                }
                if (authHeader.startsWith("NTLM ")) {
                    ntlmValue = authHeader.substring(5)
                }
            }
            if (negotiate && ntlm) {
                val type1Msg = generateType1Msg(domain, workstation)
                val header = "NTLM " + type1Msg
                return response.request().newBuilder().header("Authorization", header).build()
            } else if (ntlmValue != null) {
                val type3Msg = generateType3Msg(login, password, domain, workstation, ntlmValue)
                val ntlmHeader = "NTLM " + type3Msg
                return response.request().newBuilder().header("Authorization", ntlmHeader).build()
            }
        }
        if (responseCount(response) <= 3) {
            val credential = Credentials.basic(login, password)
            return response?.request()?.newBuilder()
                    ?.header("Authorization", credential)?.build()
        }
        return null
    }

    private fun generateType1Msg(domain: String, workstation: String): String {
        val type1Message = Type1Message(TYPE_1_FLAGS, domain, workstation)
        val source = type1Message.toByteArray()
        return Base64.encode(source)
    }

    private fun generateType3Msg(login: String, password: String,
                                 domain: String, workstation: String, challenge: String): String? {
        val type2Message: Type2Message
        try {
            val decoded = Base64.decode(challenge)
            type2Message = Type2Message(decoded)
        } catch (exception: IOException) {
            exception.printStackTrace()
            return null
        }

        val type2Flags = type2Message.flags
        val type3Flags = type2Flags and (-0x1 xor (NtlmFlags.NTLMSSP_TARGET_TYPE_DOMAIN or NtlmFlags.NTLMSSP_TARGET_TYPE_SERVER))
        val type3Message = Type3Message(type2Message, password, domain,
                login, workstation, type3Flags)
        return Base64.encode(type3Message.toByteArray())
    }

    private fun responseCount(response: Response?): Int {
        var responseTmp = response
        var result = 1
        while (responseTmp != null) {
            responseTmp = responseTmp.priorResponse()
            result++
        }
        return result
    }

}