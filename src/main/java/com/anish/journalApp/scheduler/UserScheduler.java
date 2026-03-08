package com.anish.journalApp.scheduler;

import com.anish.journalApp.entity.JournalEntry;
import com.anish.journalApp.entity.User;
import com.anish.journalApp.enums.Sentiment;
import com.anish.journalApp.repository.UserRepositoryImpl;
import com.anish.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail()
    {
        List<User> userForSA = userRepositoryImpl.getUserForSA();
        for(User user : userForSA)
        {
            List<JournalEntry> journalEntries = user.getJournalEntryList();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7 , ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment , Integer> sentimentCount = new HashMap<>();
            for(Sentiment sentiment : sentiments)
            {
                if(sentiment != null)
                {
                    sentimentCount.put(sentiment , sentimentCount.getOrDefault(sentiment , 0)+1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment , Integer> entry : sentimentCount.entrySet())
            {
                if(entry.getValue() > maxCount)
                {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if(mostFrequentSentiment != null)
            {
                emailService.sendEmail(user.getEmail() , "Sentiment for Last 7 days" , mostFrequentSentiment.toString());
            }
        }
    }
}
