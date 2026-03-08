package com.anish.journalApp.cache;

import com.anish.journalApp.entity.ConfigJournalAppEntity;
import com.anish.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Component
public class AppCache {

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    private Map<String,String> appCache;

    @PostConstruct
    public void init()
    {
        appCache = new HashMap();
        List<ConfigJournalAppEntity> configs = configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity x : configs)
        {
            appCache.put(x.getKey() , x.getValue());
        }
    }
}
