package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    //private static Map<Long, TimeEntry> timeEntries = new HashMap<>();

    private List<TimeEntry> timeEntries = new ArrayList<>();

    private Long key = 1L;


    public TimeEntry create(TimeEntry timeEntry) {
        // Copy of the value
        TimeEntry timeEntryCopy =  timeEntry;

        // Always assign the ID


        timeEntryCopy.setId(key);
            key = key +1;


        timeEntries.add(timeEntryCopy);
//     if(timeEntry.getId() == null){
//
//         boolean doesContainsKey =  true;
//         Integer key = 0;
//         while(doesContainsKey){
//
//             key = key + 1;
//             doesContainsKey = timeEntries.containsKey(key);
//
//
//         }
//
//         timeEntry.setId(Long.valueOf(key));
//
//
//
//     }
//        timeEntries.put(timeEntry.getId(), timeEntry);


        return timeEntryCopy;
    }

    public TimeEntry find(long id) {

        for (TimeEntry timeEntry: timeEntries) {
            if(timeEntry.getId() == id){
                return timeEntry;
            }
            
        }

//        return  timeEntries.get(id);

        return null;
    }

    public List<TimeEntry> list() {

//        List<TimeEntry> timeEntryList = new ArrayList<TimeEntry>(timeEntries.values());
//        return timeEntryList;

        return timeEntries.stream().collect(Collectors.toList());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {


//        int index = -1;
        for (TimeEntry entry: timeEntries) {
            if(entry.getId() == id){


//                index = timeEntries.indexOf(entry);

                entry.setDate(timeEntry.getDate());
                entry.setHours(timeEntry.getHours());
                entry.setProjectId(timeEntry.getProjectId());
                entry.setUserId(timeEntry.getUserId());
                timeEntry.setId(entry.getId());

            }

        }

//        if(index != -1) {
//
//
//
//            timeEntries.set(index,timeEntry);
//        }


    return timeEntry;
    }

    public void delete(long id) {

//        int index = -1;
//        for (TimeEntry timeEntry: timeEntries) {
//            if(timeEntry.getId() == id){
//                index = timeEntries.indexOf(timeEntry);
//            }
//        }
//
//        if(index != -1) {
//            timeEntries.remove(index);
//        }


        Iterator<TimeEntry> i = timeEntries.iterator();
        while (i.hasNext()) {
            TimeEntry s = i.next(); // must be called before you can call i.remove()
            // Do something
            i.remove();
        }


    }
    }
