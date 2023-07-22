package com.example.touristguide.model

import android.content.Context
import android.os.AsyncTask
import javax.mail.Session
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

//import android.annotation.SuppressLint
//import com.google.api.Advice.getDefaultInstance
//import io.reactivex.rxjava3.core.Completable
//import java.io.*
//
//import java.util.*
//import javax.mail.*
//import javax.mail.internet.InternetAddress
//import javax.mail.internet.MimeMessage
//
object Config {
    val EMAIL = "karanversingh42@gmail.com"
    val PASSWORD ="teyzuohaltrduexo"
}
//
//object Mailer {
//    @SuppressLint("CheckResult")
//    fun sendMail(email: String, subject: String, message: String): Completable {
//
//        return Completable.create { emitter ->
//
//            //configure SMTP server
//            val props: Properties = Properties().also {
//                it.put("mail.smtp.host", "smtp.gmail.com")
//                it.put("mail.smtp.socketFactory.port", "465")
//                it.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
//                it.put("mail.smtp.auth", "true")
//                it.put("mail.smtp.port", "465")
//            }
//
//            //Creating a session
//            val session =
//                Session.getDefaultInstance(props, object : Authenticator() {
//                override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication? {
//                    return PasswordAuthentication(Config.EMAIL, Config.PASSWORD)
//                }
//            })
//
//            try {
//
//                MimeMessage(session).let { mime ->
//                    mime.setFrom(InternetAddress(Config.EMAIL))
//                    //Adding receiver
//                    mime.addRecipient(javax.mail.Message.RecipientType.TO, InternetAddress(email))
//                    //Adding subject
//                    mime.setSubject(subject)
//                    //Adding message
//                    mime.setText(message)
//                    //send mail
//                    Transport.send(mime)
//                }
//
//            } catch (e: MessagingException) {
//
//
//                if (!emitter.isDisposed()) {
////                    if (e instanceof IOException()) {
////                        emitter.onError(e); //pass error to emitte
////                    }
//                } else{
//                }
//
//
//
//
//                emitter.onError(e)
//            }
//
//            //ending subscription
//            emitter.onComplete()
//        }
//
//    }
//
//}

public class Mailer(var context:Context,var email:String,var subject:String,var message:String) :
    AsyncTask<Void, Void, Void>() {



    override fun doInBackground(vararg params: Void?):Void?{
        var properties = Properties()
        properties.put("mail.smtp.host", "smtp.gmail.com")
        properties.put("mail.smtp.socketFactory.port", "465")
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
        properties.put("mail.smtp.auth", "true")
        properties.put("mail.smtp.port", "465")
        val session =
            Session.getDefaultInstance(properties, object : Authenticator() {
                override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication? {
                    return PasswordAuthentication(Config.EMAIL, Config.PASSWORD)
                }
            })

        var mimeMessage:MimeMessage = MimeMessage(session)
        try{
            mimeMessage.setFrom(InternetAddress(Config.EMAIL))
            mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, InternetAddress(email))
                    //Adding subject
                    mimeMessage.setSubject(subject)
                    //Adding message
                    mimeMessage.setText(message)
                    //send mail
                    Transport.send(mimeMessage)

        }catch (e:MessagingException){
            e.printStackTrace()
        }

        return null



    }

}

