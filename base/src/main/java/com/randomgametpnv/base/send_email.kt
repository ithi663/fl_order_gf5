package com.randomgametpnv.base

import java.util.*
import javax.activation.DataHandler
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import javax.mail.util.ByteArrayDataSource


class Mailer(): Authenticator() {

    private val user: String = "test123test123qwert1@gmail.com"
    private val password: String = "test123test123"
    private lateinit var session: Session
    val props = Properties()

    init {

        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props["mail.smtp.starttls.enable"] = "true";
        props["mail.smtp.auth"] = "true";
        props["mail.smtp.port"] = "465";
        props["mail.smtp.socketFactory.port"] = "465";
        props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory";
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");
        session = Session.getDefaultInstance(props, this);
    }

    override fun getPasswordAuthentication(): PasswordAuthentication? {
        return PasswordAuthentication(user, password)
    }


    suspend fun sendEmail(subject: String, body: String, /*sender: String,*/ recipients: String ) {
        val message = MimeMessage(session)
        //val handler = DataHandler(ByteArrayDataSource(body.toByteArray(), "text/plain"))
        message.sender = InternetAddress(user)
        message.subject = subject;
        message.setText(body)
        message.setRecipient(Message.RecipientType.TO, InternetAddress(recipients))
    }
}