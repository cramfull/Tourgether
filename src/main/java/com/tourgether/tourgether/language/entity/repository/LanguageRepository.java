package com.tourgether.tourgether.language.entity.repository;

import com.tourgether.tourgether.language.entity.Language;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    Optional<Language> findByLanguageCode(String languageCode);
}
