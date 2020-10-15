package com.abuseapp.speaknativeappapijava.endpoints.phrases.repositories;

import com.abuseapp.speaknativeappapijava.endpoints.phrases.models.Phrase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhraseRepository extends JpaRepository<Phrase, UUID> {

    @Query("SELECT p FROM Phrase p where p.user.id=?1")
    Page<Phrase> findAllByUserId(UUID userId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Phrase p where p.user.id=?1 AND p.id IN ?2")
    void deleteByUserIdAndIds(UUID userId, List<UUID> phraseIds);
}
