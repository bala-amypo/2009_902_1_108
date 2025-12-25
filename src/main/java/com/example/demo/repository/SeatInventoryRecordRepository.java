public interface SeatInventoryRecordRepository
        extends JpaRepository<SeatInventoryRecord, Long> {

    List<SeatInventoryRecord> findByEventId(Long eventId);
}
