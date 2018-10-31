package io.pivotal.pal.tracker;

import io.pivotal.pal.tracker.TimeEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    @Autowired
    TimeEntryRepository timeEntryRepository;


    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;

    }


    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {

        ResponseEntity<TimeEntry> r = new ResponseEntity<TimeEntry>(timeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);

        return r;


    }

    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {

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


        ResponseEntity<List<TimeEntry>> r = new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(), HttpStatus.OK);

        return r;
    }

    @PutMapping("{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {


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

        timeEntryRepository.delete(timeEntryId);
        ResponseEntity r = new ResponseEntity(HttpStatus.NO_CONTENT);

        return r;
    }
}
