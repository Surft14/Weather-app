package com.example.weather.logic.network

import android.content.Context
import com.android.volley.toolbox.HurlStack
import com.example.weather.R
import java.net.HttpURLConnection
import java.net.URL
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory

class CustomHurlStack(private val context: Context) : HurlStack() {

    override fun createConnection(url: URL): HttpURLConnection {
        val connection = super.createConnection(url)

        if (connection is HttpsURLConnection) {
            connection.sslSocketFactory = getCustomSSLSocketFactory()
        }

        return connection
    }

    private fun getCustomSSLSocketFactory(): SSLSocketFactory {
        val cf = CertificateFactory.getInstance("X.509")
        val caInput = context.resources.openRawResource(R.raw.mycert)
        val ca = cf.generateCertificate(caInput)
        caInput.close()

        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType).apply {
            load(null, null)
            setCertificateEntry("ca", ca)
        }

        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(keyStore)

        val context = SSLContext.getInstance("TLS")
        context.init(null, tmf.trustManagers, null)

        return context.socketFactory
    }
}
