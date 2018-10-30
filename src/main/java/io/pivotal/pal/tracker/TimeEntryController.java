package io.pivotal.pal.tracker;

import io.pivotal.pal.tracker.TimeEntry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {

    TimeEntryRepository timeEntryRepository;



    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;

    }



    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {

        ResponseEntity<TimeEntry> r = new ResponseEntity<TimeEntry>(timeEntryRepository.create(timeEntryToCreate) ,HttpStatus.CREATED);

        return r;



    }

    @GetMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {

        ResponseEntity<TimeEntry> r;
        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);

        if(timeEntry != null){
            r = new ResponseEntity<TimeEntry>(timeEntry,HttpStatus.OK);
        }
     else{
           r = new ResponseEntity<TimeEntry>(timeEntry,HttpStatus.NOT_FOUND);
        }



        return r;

        // ./gradlew cloudNativeDeveloperRest -PserverUrl=https://pal-tracker-thomas-ratnakar.apps.pikes.pal.pivotal.io

    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {


        ResponseEntity<List<TimeEntry>> r = new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(), HttpStatus.OK);

        return r;
    }

    @PutMapping("/time-entries/{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {


        ResponseEntity<TimeEntry> r;
        TimeEntry timeEntry = timeEntryRepository.update(timeEntryId,expected);

        if(timeEntry != null){
            r = new ResponseEntity<TimeEntry>(timeEntry,HttpStatus.OK);
        }
        else{
            r = new ResponseEntity<TimeEntry>(timeEntry,HttpStatus.NOT_FOUND);
        }



        return r;



    }

    @DeleteMapping("/time-entries/{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) {

        timeEntryRepository.delete(timeEntryId);
        ResponseEntity r = new ResponseEntity(HttpStatus.NO_CONTENT);

        return r;
    }
}
