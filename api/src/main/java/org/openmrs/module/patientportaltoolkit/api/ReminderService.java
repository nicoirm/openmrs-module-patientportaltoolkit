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
    List<Reminder> getAllRemindersByPatient(Patient patient);

    @Transactional(readOnly = true)
    List<Reminder> getRemindersCompleted(Patient patient);

    @Transactional
    public List<Reminder> getReminders(Patient pat);

    @Transactional(readOnly = true)
    public List<Reminder> getReminders(Patient pat, Date indexDate);

    @Transactional
    Reminder saveReminder(Reminder Reminder);

}
