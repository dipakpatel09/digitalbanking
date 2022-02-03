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
    public void run(String... args) {

        Customer customer = Customer.builder().userName("Dipak004").firstName("Dipak").lastName("Patel").phoneNumber("0123456789").email("abc@gmail.com").status(Status.ACTIVE).preferredLanguage(Language.EN).externalId("dipak007").createdBy("Dipak1").createdOn(LocalDateTime.now()).updatedBy("Dipak2").updatedOn(LocalDateTime.now()).build();
        customerRepo.save(customer);

        CustomerOTP otp = new CustomerOTP("This is OTP", "123456", 1, LocalDateTime.now(), LocalDateTime.now());
        CustomerSecurityImages customerSecurityImages = new CustomerSecurityImages("Apple", LocalDateTime.now());
        CustomerSecurityQuestions customerSecurityQuestions = new CustomerSecurityQuestions("Iphone", LocalDateTime.now());
        CustomerSecurityQuestions customerSecurityQuestions1 = new CustomerSecurityQuestions("Ahmedabad", LocalDateTime.now());
        CustomerSecurityQuestions customerSecurityQuestions2 = new CustomerSecurityQuestions("Red", LocalDateTime.now());

        otp.setCustomer(customer);

        SecurityQuestions questions = securityQuestionsRepo.save(new SecurityQuestions("What is your favourite Phone?"));
        SecurityQuestions questions1 = securityQuestionsRepo.save(new SecurityQuestions("What is your favourite Place?"));
        SecurityQuestions questions2 = securityQuestionsRepo.save(new SecurityQuestions("What is your favourite Color?"));
        securityQuestionsRepo.save(new SecurityQuestions("What is your favourite Food?"));

        SecurityImages images = securityImagesRepo.save(new SecurityImages("What is your favourite Logo?", "http://CustomerLogo"));
        securityImagesRepo.save(new SecurityImages("What is your favourite TV show?", "http://CustomerTVshow"));

        customerSecurityQuestions.setCustomer(customer);
        customerSecurityQuestions1.setCustomer(customer);
        customerSecurityQuestions2.setCustomer(customer);
        customerSecurityQuestions.setSecurityQuestions(questions);
        customerSecurityQuestions1.setSecurityQuestions(questions1);
        customerSecurityQuestions2.setSecurityQuestions(questions2);

        customerSecurityImages.setCustomer(customer);

        customerSecurityImages.setSecurityImages(images);

        customerOTPRepo.save(otp);
        customerSecurityQuestionsRepo.save(customerSecurityQuestions);
        customerSecurityQuestionsRepo.save(customerSecurityQuestions1);
        customerSecurityQuestionsRepo.save(customerSecurityQuestions2);
        customerSecurityImagesRepo.save(customerSecurityImages);
    }
}
