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


object Config {
    val EMAIL = "karanversingh42@gmail.com"
    val PASSWORD ="teyzuohaltrduexo"
}

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

