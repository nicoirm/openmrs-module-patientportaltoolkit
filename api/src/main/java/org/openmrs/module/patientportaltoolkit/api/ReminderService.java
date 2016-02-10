package org.openmrs.module.patientportaltoolkit.api;

import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.patientportaltoolkit.Reminder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Maurya on 08/06/2015.
 */
public interface ReminderService extends OpenmrsService {

    @Transactional(readOnly = true)
    Reminder getRemindersById(String Id);

    @Transactional(readOnly = true)
    List<Reminder> getAllRemindersByPatient(Patient patient);

    @Transactional(readOnly = true)
    List<Reminder> getRemindersCompleted(Patient patient);

    @Transactional
    List<Reminder> getReminders(Patient pat);

    @Transactional(readOnly = true)
    List<Reminder> getReminders(Patient pat, Date indexDate);

    @Transactional
    Reminder markCompletedReminder(Reminder reminder);

    @Transactional
    Reminder saveReminder(Reminder Reminder);

}
