package ru.zotov.carracing.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zotov.carracing.entity.RaceTemplate;

/**
 * @author Created by ZotovES on 10.08.2021
 */
public interface RaceTemplateRepo extends JpaRepository<RaceTemplate, Long> {
}
