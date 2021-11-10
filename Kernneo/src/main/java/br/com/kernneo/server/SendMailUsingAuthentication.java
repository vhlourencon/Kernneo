package br.com.kernneo.server;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.kernneo.client.model.EmpresaModel;

public class SendMailUsingAuthentication {

    // sem anexo
    public void postMail(EmpresaModel empresa, String destinatario[], String assunto, String mensagem) throws MessagingException, UnsupportedEncodingException {
	boolean debug = true;

	Properties props = new Properties();
	props.put("mail.smtp.host", empresa.getEmailSMTP());
	//props.put("mail.smtp.starttls.enable","true"); 
	props.put("mail.smtp.port", "587");  
	if (empresa.isEmailRequerAutenticacao()) {
	    props.put("mail.smtp.auth", "true");
	  //  props.put("mail.smtp.starttls.enable","true");

	}

	Authenticator auth = new SMTPAuthenticator(empresa.getEmailLogin().trim(), empresa.getEmailSenha());

	Session session = Session.getDefaultInstance(props, auth);
	session.setDebug(debug);

	// create a message
	MimeMessage msg = new MimeMessage(session);

	// set the from and to address
	InternetAddress addressFrom = new InternetAddress(empresa.getEmailEmail(), empresa.getEmailNomeExibicao());
	msg.setFrom(addressFrom);

	InternetAddress[] addressTo = new InternetAddress[destinatario.length];
	for (int i = 0; i < destinatario.length; i++) {
	    addressTo[i] = new InternetAddress(destinatario[i]);
	}
	msg.setRecipients(Message.RecipientType.TO, addressTo);

	// Setting the Subject and Content Type

	msg.setSubject(assunto, "UTF-8");
	msg.setContent(mensagem, "text/html; charset=utf-8");
	Transport.send(msg);

    }

    // // com anexo
    // public void postMail(UsuarioModel usuarioRemetente, String
    // destinatario[], String arquivos[], String assunto, String mensagem)
    // throws MessagingException, UnsupportedEncodingException {
    // boolean debug = true;
    //
    // Properties props = new Properties();
    // props.put("mail.smtp.host",
    // usuarioRemetente.getEmailConfig().getServidorSaida());
    //
    // if (usuarioRemetente.getEmailConfig().isAutenticacao() == true) {
    // System.out.println("##############################################################################");
    // System.out.println("##############################################################################");
    // System.out.println("##############################################################################");
    // System.out.println("##############################################################################");
    //
    // System.out.println("Entrooo");
    // System.out.println("##############################################################################");
    // System.out.println("##############################################################################");
    // System.out.println("##############################################################################");
    // System.out.println("##############################################################################");
    // System.out.println("##############################################################################");
    // System.out.println("##############################################################################");
    // System.out.println("##############################################################################");
    // System.out.println("##############################################################################");
    //
    // props.put("mail.smtp.auth", "true");
    // }
    //
    // System.out.println(usuarioRemetente.getEmailConfig().getLogin() +
    // " $$$$ " + usuarioRemetente.getEmailConfig().getSenha());
    // Authenticator auth = new
    // SMTPAuthenticator(usuarioRemetente.getEmailConfig().getLogin().trim(),
    // usuarioRemetente.getEmailConfig().getSenha().trim());
    //
    // Session session = Session.getDefaultInstance(props, auth);
    //
    // session.setDebug(debug);
    //
    // // create a message
    // MimeMessage msg = new MimeMessage(session);
    //
    // // set the from and to address
    // InternetAddress addressFrom = new
    // InternetAddress(usuarioRemetente.getEmailConfig().getEndereco().trim(),
    // usuarioRemetente.getEmailConfig().getNomeExibicao());
    // msg.setFrom(addressFrom);
    //
    // InternetAddress[] addressTo = new InternetAddress[destinatario.length];
    // for (int i = 0; i < destinatario.length; i++) {
    // addressTo[i] = new InternetAddress(destinatario[i]);
    // }
    // msg.setRecipients(Message.RecipientType.TO, addressTo);
    //
    // // cria a mp
    // Multipart mp = new MimeMultipart();
    //
    // List<File> listaArquivos = new ArrayList<File>();
    //
    // // adiciona os anexos no mp
    // for (int i = 0; i < arquivos.length; i++) {
    //
    // File file = new File("/home/myeptus/webapps/anexos/" + arquivos[i]);
    // System.out.println(file.exists() +
    // "   ----------------------------------------" + file.getAbsolutePath());
    // if (file.exists()) {
    // listaArquivos.add(file);
    // MimeBodyPart bodyPartAnexo = new MimeBodyPart();
    // bodyPartAnexo.setDataHandler(new DataHandler(new FileDataSource(file)));
    // bodyPartAnexo.setFileName(file.getName());
    // mp.addBodyPart(bodyPartAnexo);
    // }
    // }
    //
    // // adicona o texto no mp;
    // MimeBodyPart mbpTexto = new MimeBodyPart();
    // mbpTexto.setContent(mensagem, "text/html; charset=utf-8");
    //
    // mp.addBodyPart(mbpTexto);
    // // Setting the Subject and Content Type
    // msg.setSubject(assunto);
    // msg.setContent(mp);
    // Transport.send(msg);
    //
    // for (File file : listaArquivos) {
    // file.delete();
    // }
    //
    // }

    /**
     * SimpleAuthenticator is used to do simple authentication when the SMTP
     * server requires it.
     */
    private class SMTPAuthenticator extends javax.mail.Authenticator {
	private String usuario;
	private String senha;

	public SMTPAuthenticator(String usuario, String senha) {
	    this.usuario = usuario;
	    this.senha = senha;
	}

	public PasswordAuthentication getPasswordAuthentication() {

	    return new PasswordAuthentication(usuario, senha);
	}
    }

    public String getEmailHTML(String mensagem) {

	String imgSrc = "http://www.kernneo.com.br/YouPadManager/imagens/YouPad_Logo3.png";
	String width = "380";
	String height = "150";

	String rodape = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
	rodape += "	     <html xmlns=\"http://www.w3.org/1999/xhtml\">";
	rodape += "	    	 <head>";
	rodape += "   			<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />";
	rodape += "			<title>YouPad - Kernneo Tecnologia</title>";
	rodape += "		 </head>";
	rodape += "	     <body> ";
	rodape += "		<div width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"> ";
	rodape += "		   <tr>";
	rodape += "		     <td>";
	rodape += "			<img src=\"" + imgSrc + "\" width=\"" + width + "\" height=\"" + height + "\" alt=\"YouPad-Logo\" />";
	rodape += "	    	     </td>";
	rodape += "		   </tr>";
	rodape += "		</div>";
	rodape += "</br>";
	rodape += "<hr>";
	rodape += mensagem;
	rodape += "</br>";
	/*
	 * Antes de Imprimir Pense em sua responsabilidade com o MEIO AMBIENTE
	 */
	rodape += "		<p style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto'>";
	rodape += "			<hr>";
	rodape += "			<b>";
	rodape += "			   <span style='font-size:22.0pt;font-family:Webdings;color:green'>P</span>";
	rodape += "			</b>";
	rodape += "			<span style='font-size:8.0pt;font-family:\"Tahoma\",\"sans-serif\";color:green'>Antes de imprimir, pense em sua <b>responsabilidade</b> e <b>compromisso</b> com o MEIO AMBIENTE</span>";

	rodape += "		</p>";

	rodape += "	     </body>";
	rodape += " 	     </html>";
	return rodape;
    }

}