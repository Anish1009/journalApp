package com.anish.journalApp.repository;

import com.anish.journalApp.entity.ConfigJournalAppEntity;
import com.anish.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {



}
