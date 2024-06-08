package in.itkaran.product_service_110524.services;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@EnableScheduling
public class ScheduledTasks {

    @Scheduled(cron = "1 * * * * *")
    void sendEmail() {
        // business logic to send email goes here
        //System.out.println("Sending email");
        printLogWithTime("Sending email");
    }


    public static void printLogWithTime(String logMessage) {
        // Get the current time
        LocalDateTime now = LocalDateTime.now();

        // Define the format for the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Format the current time
        String formattedNow = now.format(formatter);

        // Print the log message with the current time
        System.out.println("[" + formattedNow + "] " + logMessage);
    }
}
