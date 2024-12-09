package ee.taltech.iti03022024backend.repositories.review;

import ee.taltech.iti03022024backend.entities.review.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>, JpaSpecificationExecutor<ReviewEntity> {
    Optional<ReviewEntity> findByBookId(Long bookId);
}
