package io.pivotal.pal.tracker;

import io.pivotal.pal.tracker.TimeEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

   private final CounterService counter;
    private final GaugeService gauge;
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(
            TimeEntryRepository  timeEntriesRepo,
            CounterService counter,
            GaugeService gauge
    ) {
        this.timeEntryRepository = timeEntriesRepo;
        this.counter = counter;
        this.gauge = gauge;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {

        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());

        ResponseEntity<TimeEntry> r = new ResponseEntity<TimeEntry>(timeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);

        return r;


    }

    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {

        System.out.print("in read");
        counter.increment("TimeEntry.read");
        ResponseEntity<TimeEntry> r;
        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);

        if (timeEntry != null) {
            r = new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
        } else {
            r = new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.NOT_FOUND);
        }


        return r;

        // ./gradlew cloudNativeDeveloperRest -PserverUrl=https://pal-tracker-thomas-ratnakar.apps.pikes.pal.pivotal.io

    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {

        System.out.print("in list");
        counter.increment("TimeEntry.listed");
        ResponseEntity<List<TimeEntry>> r = new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(), HttpStatus.OK);

        return r;
    }

    @PutMapping("{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {

        counter.increment("TimeEntry.updated");
        ResponseEntity<TimeEntry> r;
        TimeEntry timeEntry = timeEntryRepository.update(timeEntryId, expected);

        if (timeEntry != null) {
            r = new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
        } else {
            r = new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.NOT_FOUND);
        }


        return r;


    }

    @DeleteMapping("{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) {

        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());

        timeEntryRepository.delete(timeEntryId);
        ResponseEntity r = new ResponseEntity(HttpStatus.NO_CONTENT);

        return r;
    }
}
