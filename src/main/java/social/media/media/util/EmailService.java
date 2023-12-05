package social.media.media.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(String to, String formattedNumber) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Địa chỉ email người gửi
        helper.setFrom("tranbuuquyen2002@gmail.com");


        helper.setTo(to);

        // Tiêu đề email
        helper.setSubject("[HQSocial] - Xác Thực Tài Khoản");

        String htmlContent = "<html>\r\n"
                + "    <body>\r\n"
                + "    <div style='border-top: 4px solid transparent; border-image: linear-gradient(to right, blue, orangered, green) 1; border-image-slice: 1;'>\r\n"
                + "        <div style=\"display: flex;\">\r\n"
                + "            <img src='https://th.bing.com/th/id/R.2c9bd348ee7a312a9687317ae9c20c73?rik=FHe2MSJoJWl1pA&pid=ImgRaw&r=0' alt='Logo' width='70' height='70'>\r\n"
                + "            <h2>Xác Thực Tài Khoản</h2>\r\n"
                + "           \r\n"
                + "        </div>\r\n"
                + "        \r\n"
                + "        <p>Bạn đã thực hiện thao tác xác thực tài khoản trên hệ thống mạng xã hội HQSocial</p>\r\n"
                + "         <p>Mã xác thực của bạn là: <strong>" + formattedNumber + "</strong></p>\r\n"
                + "    </div>\r\n"
                + "           \r\n"
                + "   \r\n"
                + "    </body>\r\n"
                + "</html>";

        // Đặt nội dung email dưới dạng HTML
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
    public void WarningMail(String email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Địa chỉ email người gửi
        helper.setFrom("tranbuuquyen2002@gmail.com");


        helper.setTo(email);

        // Tiêu đề email
        helper.setSubject("[HQSocial] - Cảnh Báo Bảo Mật - Hãy Hành Vi Đổi Mật Khẩu");
        String logoUrl = "https://t4.ftcdn.net/jpg/03/99/07/29/360_F_399072966_ToIRP0vHfRGUrDcAosht9J0xDasxr1hA.jpg";
        String htmlContent = "<html>\r\n"
                + "    <body>\r\n"
                + "   	<img src='" + logoUrl + "' alt='Logo FAMS' alt='Logo' width='70' height='70'>\r\n"
                + "   	<p>	Chào bạn, <strong>" + email + "</strong></p>\r\n"
                + "		<p>Chúng tôi gửi đến bạn một cảnh báo bảo mật quan trọng.</p>\n"
                + "			<p>Thông tin đăng nhập vào hệ thống HQSocial của bạn đã được thay đổi, vui lòng kiểm tra và đảm bảo an toàn thông tin cá nhân của bạn.</p>"
                + "			<p>Thông tin đăng nhập:</p>"
                + "				<ul>"
                + "					<li>Username: " + email + " </li>"
                + "					<li>Password: ********* (Thông tin này đã được che đi vì lý do bảo mật)</li>"
                + "				</ul>"
                + "           <p>Nếu bạn không thực hiện hành động này, hãy liên hệ với chúng tôi ngay lập tức.</p>"
                + "    </body>\r\n"
                + "</html>";

        // Đặt nội dung email dưới dạng HTML
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
}
