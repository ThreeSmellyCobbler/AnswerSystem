package com.tsco.common.dubboService;

import com.alibaba.dubbo.config.annotation.Service;
import com.tsco.api.domain.dto.MailSenderDTO;
import com.tsco.api.dubboService.MailSenderDubboService;
import com.tsco.common.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "1.0.0")
public class MailSenderDubboServiceImpl implements MailSenderDubboService {

    @Autowired
    private MailService mailService;

    @Override
    public void sendSimpleMail(MailSenderDTO mailSenderDTO) {
        mailService.sendSimpleMail(mailSenderDTO.getTo(), mailSenderDTO.getSubject(), mailSenderDTO.getContent());
    }

    @Override
    public void sendHtmlEmailWithTemplate(MailSenderDTO mailSenderDTO) {
        mailService.sendHtmlEmailWithTemplate(mailSenderDTO.getTo(), mailSenderDTO.getSubject(), mailSenderDTO.getTemplateEnum(), mailSenderDTO.getParams());
    }
}
