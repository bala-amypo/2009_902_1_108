public interface EventRecordRepository
        extends JpaRepository<EventRecord, Long> {

    EventRecord findByEventCode(String eventCode);
}
