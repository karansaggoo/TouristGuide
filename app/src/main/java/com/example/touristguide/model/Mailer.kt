package com.example.touristguide.model

import android.annotation.SuppressLint
import javax.mail.Session
import com.google.api.Advice.getDefaultInstance
import io.reactivex.rxjava3.core.Completable
import javax.mail.Authenticator
import javax.mail.PasswordAuthentication
import java.util.*
import javax.mail.MessagingException
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object Config {
    val EMAIL = "karanversingh42@gmail.com"
    val PASSWORD = "KH@12345"
}

object Mailer {
    @SuppressLint("CheckResult")
    fun sendMail(email: String, subject: String, message: String): Completable {

        return Completable.create { emitter ->

            //configure SMTP server
            val props: Properties = Properties().also {
                it.put("mail.smtp.host", "smtp.gmail.com")
                it.put("mail.smtp.socketFactory.port", "465")
                it.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
                it.put("mail.smtp.auth", "true")
                it.put("mail.smtp.port", "465")
            }

            //Creating a session
            val session =
                Session.getDefaultInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication? {
                    return PasswordAuthentication(Config.EMAIL, Config.PASSWORD)
                }
            })

            try {

                MimeMessage(session).let { mime ->
                    mime.setFrom(InternetAddress(Config.EMAIL))
                    //Adding receiver
                    mime.addRecipient(javax.mail.Message.RecipientType.TO, InternetAddress(email))
                    //Adding subject
                    mime.setSubject(subject)
                    //Adding message
                    mime.setText(message)
                    //send mail
                    Transport.send(mime)
                }

            } catch (e: MessagingException) {
                emitter.onError(e)
            }

            //ending subscription
            emitter.onComplete()
        }

    }

}