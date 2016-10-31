/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phucdk.emailsender;

import com.phucdk.emailsender.object.Canho;
import com.phucdk.emailsender.object.EmailConfig;
import com.phucdk.emailsender.utils.EmailUtils;
import com.phucdk.emailsender.utils.ExcelUtils;
import com.phucdk.emailsender.utils.TemplateUtils;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class EmailSender {

    public static void main(String[] agrs) throws Exception {
        List<Canho> listCanhos = ExcelUtils.readData("D:\\phucdk\\Projects\\EmailSender\\DanhsachCanho.xlsx");
        for (int i = 0; i < 1; i++) {
            Canho aCanho = listCanhos.get(i);
            HashMap<String, String> beans = new HashMap<>();
            setValueToBeans(beans, aCanho);
            EmailConfig emailConfig = new EmailConfig();

            emailConfig.setEmailAddress("phucdkbk@gmail.com");
            emailConfig.setPassword("Change23121988");

            emailConfig.setSmtpAuth("true");
            emailConfig.setSmtpSocketPort("465");
            emailConfig.setSmtpClass("javax.net.ssl.SSLSocketFactory");
            emailConfig.setSmtpHost("smtp.gmail.com");
            emailConfig.setSmtpPort("465");

            String content = TemplateUtils.getContent("D:\\phucdk\\Projects\\EmailSender\\NoidungUnicode.htm", beans);
            EmailUtils.sendEmail(emailConfig, content, "Thông báo nộp tiền", "phucdkbk@gmail.com", aCanho.getEmail());
        }
    }
    
    public static void setValueToBeans(HashMap<String, String> beans, Canho aCanho) {
        beans.put("soCanho", aCanho.getSoCanho());
        beans.put("tang", aCanho.getTang());
        beans.put("dientichSudung", aCanho.getDientichSudung());
        beans.put("dientichXaydung", aCanho.getDientichXaydung());
        beans.put("heso", aCanho.getHeso());
        beans.put("giatriHeso", aCanho.getGiatriHeso());
        beans.put("dongia", aCanho.getDongia());
        beans.put("giabanThongthuy", aCanho.getGiabanThongthuy());
        beans.put("giatriCanhoHoanthien", aCanho.getGiatriCanhoHoanthien());
        beans.put("tongGiatriHopdong", aCanho.getTongGiatriHopdong());
        beans.put("tongGiatriPhainopTheotiendo", aCanho.getTongGiatriPhainopTheotiendo());
        beans.put("soDanop", aCanho.getSoDanop());
        beans.put("soConphainopDotnay", aCanho.getSoConphainopDotnay());
        beans.put("dot1", aCanho.getDot1());
        beans.put("hovatenKhachang", aCanho.getHovatenKhachang());
        beans.put("soChungminh", aCanho.getSoChungminh());
        beans.put("ngaycap", aCanho.getNgaycap());
        beans.put("noicap", aCanho.getNoicap());
        beans.put("hokhauThuongtru", aCanho.getHokhauThuongtru());
        beans.put("diachiLienhe", aCanho.getDiachiLienhe());
        beans.put("dienthoai", aCanho.getDienthoai());
        beans.put("email", aCanho.getEmail());
        beans.put("sotienBangchu", aCanho.getSotienBangchu());
    }

}
