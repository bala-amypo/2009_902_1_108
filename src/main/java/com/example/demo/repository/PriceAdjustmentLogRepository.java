public interface PriceAdjustmentLogRepository
        extends JpaRepository<PriceAdjustmentLog, Long> {

    List<PriceAdjustmentLog> findByEventId(Long eventId);
}
