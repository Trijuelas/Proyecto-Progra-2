package proyecto2.LogicaNegocio;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import proyecto2.Entidades.Empleado;
import proyecto2.Entidades.Nomina;

public class MailService {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_TLS_PROTOCOL = "TLSv1.2";

    public void enviarCorreo(String remitente, String password, Empleado empleado, Nomina nomina, Path rutaPdf) throws Exception {
        if (remitente == null || remitente.trim().isEmpty()) {
            throw new IllegalArgumentException("Debes indicar el correo remitente.");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Debes indicar la contrasena de aplicacion.");
        }

        if (empleado == null || empleado.getCorreo() == null || empleado.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException("El empleado no tiene un correo valido.");
        }

        if (rutaPdf == null || !rutaPdf.toFile().exists()) {
            throw new FileNotFoundException("No se encontro el PDF adjunto que se iba a enviar.");
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.protocols", SMTP_TLS_PROTOCOL);
        props.put("mail.smtp.ssl.trust", SMTP_HOST);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(remitente));
        try {
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(empleado.getCorreo(), true));
        } catch (AddressException ex) {
            throw new IllegalArgumentException("El correo destino no tiene un formato valido.", ex);
        }
        message.setSubject("Comprobante de nomina para " + empleado.getNombre());

        MimeBodyPart texto = new MimeBodyPart();
        texto.setText(
                "Hola " + empleado.getNombre() + ",\n\n"
                + "Adjunto encontraras tu comprobante de nomina.\n"
                + String.format("Salario neto: %.2f\n\n", nomina.getSalarioNeto())
                + "Saludos."
        );

        MimeBodyPart adjunto = new MimeBodyPart();
        DataSource source = new FileDataSource(rutaPdf.toFile());
        adjunto.setDataHandler(new DataHandler(source));
        adjunto.setFileName(rutaPdf.getFileName().toString());

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(texto);
        multipart.addBodyPart(adjunto);

        message.setContent(multipart);
        try {
            Transport.send(message);
        } catch (javax.mail.AuthenticationFailedException ex) {
            throw new Exception("Google rechazo la autenticacion. Verifica tu Gmail y usa una contrasena de aplicacion valida.", ex);
        } catch (javax.mail.SendFailedException ex) {
            throw new Exception("No se pudo entregar el correo. Revisa la direccion del destinatario.", ex);
        } catch (NoClassDefFoundError ex) {
            throw new Exception("Falta una libreria requerida para correo o PDF en tiempo de ejecucion.", ex);
        } catch (javax.mail.MessagingException ex) {
            Throwable causaRaiz = obtenerCausaRaiz(ex);

            if (causaRaiz instanceof javax.net.ssl.SSLException) {
                throw new Exception("Fallo la conexion segura con Gmail. Prueba de nuevo y verifica Internet, tu cuenta Gmail y la contrasena de aplicacion.", ex);
            }

            if (causaRaiz instanceof java.net.UnknownHostException) {
                throw new Exception("No se pudo resolver el servidor SMTP. Revisa tu conexion a Internet.", ex);
            }

            if (causaRaiz instanceof java.net.SocketException) {
                throw new Exception("La conexion de red con el servidor SMTP fue interrumpida.", ex);
            }

            throw new Exception("Fallo la comunicacion con el servidor SMTP: " + obtenerMensaje(ex), ex);
        }
    }

    private String obtenerMensaje(Throwable error) {
        Throwable actual = error;

        while (actual != null) {
            if (actual.getMessage() != null && !actual.getMessage().trim().isEmpty()) {
                return actual.getMessage();
            }
            actual = actual.getCause();
        }

        return "sin detalle adicional";
    }

    private Throwable obtenerCausaRaiz(Throwable error) {
        Throwable actual = error;

        while (actual.getCause() != null) {
            actual = actual.getCause();
        }

        return actual;
    }
}
