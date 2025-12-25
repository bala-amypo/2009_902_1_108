public interface DynamicPriceRecordRepository
        extends JpaRepository<DynamicPriceRecord, Long> {

    DynamicPriceRecord findFirstByEventIdOrderByComputedAtDesc(Long eventId);

    List<DynamicPriceRecord> findByEventIdOrderByComputedAtDesc(Long eventId);
}
