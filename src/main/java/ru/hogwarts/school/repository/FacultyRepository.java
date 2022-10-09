package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    Collection<Faculty> findByColorIgnoreCase(String color);
    Faculty findByNameIgnoreCase(String name);

    Faculty findByStudentsId(Long student_id);
}
