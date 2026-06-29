package fr.alium.dndapi.repository;

import fr.alium.dndapi.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
