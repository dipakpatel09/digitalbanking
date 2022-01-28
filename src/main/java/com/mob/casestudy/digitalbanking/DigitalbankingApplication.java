package com.mob.casestudy.digitalbanking;

import com.mob.casestudy.digitalbanking.repository.*;
import com.mob.casestudy.digitalbanking.entity.*;
import com.mob.casestudy.digitalbanking.enumrator.Language;
import com.mob.casestudy.digitalbanking.enumrator.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class DigitalbankingApplication implements CommandLineRunner {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CustomerSecurityQuestionsRepo customerSecurityQuestionsRepo;

    @Autowired
    CustomerSecurityImagesRepo customerSecurityImagesRepo;

    @Autowired
    CustomerOTPRepo customerOTPRepo;

    @Autowired
    SecurityQuestionsRepo securityQuestionsRepo;

    @Autowired
    SecurityImagesRepo securityImagesRepo;

    public static void main(String[] args) {
        SpringApplication.run(DigitalbankingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Customer customer = Customer.builder().userName("Dipak004").firstName("Dipak").lastName("Patel").phoneNumber("0123456789").email("abc@gmail.com").status(Status.ACTIVE).preferredLanguage(Language.EN).externalId("dipak007").createdBy("Dipak1").createdOn(LocalDateTime.now()).updatedBy("Dipak2").updatedOn(LocalDateTime.now()).build();
        customerRepo.save(customer);

        CustomerOTP otp = new CustomerOTP("This is OTP", "123456", 1, LocalDateTime.now(), LocalDateTime.now());
        CustomerSecurityImages customerSecurityImages = new CustomerSecurityImages("phone", LocalDateTime.now());
        CustomerSecurityQuestions customerSecurityQuestions = new CustomerSecurityQuestions("iphone", LocalDateTime.now());

        otp.setCustomer(customer);

        SecurityQuestions questions = securityQuestionsRepo.save(new SecurityQuestions("what is your fav phone?"));

        SecurityImages images = securityImagesRepo.save(new SecurityImages("what is your fav phone?", "http://CustomerPhone"));

        customerSecurityQuestions.setCustomer(customer);
        customerSecurityQuestions.setSecurityQuestions(questions);

        customerSecurityImages.setCustomer(customer);

        customerSecurityImages.setSecurityImages(images);

        customerOTPRepo.save(otp);
        customerSecurityQuestionsRepo.save(customerSecurityQuestions);
        customerSecurityImagesRepo.save(customerSecurityImages);
    }
}
