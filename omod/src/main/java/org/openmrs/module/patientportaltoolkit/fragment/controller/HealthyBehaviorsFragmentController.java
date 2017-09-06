package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.simpleformservice.DataAccessPermission;
import org.openmrs.module.simpleformservice.api.DataAccessPermissionService;
import org.openmrs.ui.framework.annotation.SpringBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srikumma on 8/16/17.
 */
public class HealthyBehaviorsFragmentController {

    protected final Log log = LogFactory.getLog(getClass());
    //@Autowired
            //@Qualifier("")
   // DataAccessPermissionService permissionService;
    public void controller(@SpringBean("myBean") DataAccessPermissionService permissionService){

        Person currentPerson = Context.getAuthenticatedUser().getPerson();
        List<PatientPortalRelation> pprRelations=new ArrayList<>();
        pprRelations= Context.getService(PatientPortalRelationService.class).getPatientPortalRelationByPerson(currentPerson);
        for (PatientPortalRelation ppr:pprRelations){
            //if(ppr.getPerson().equals(currentPerson) && ppr.getRelatedPerson().getIsPatient() && ppr.getShareStatus()==1 && (ppr.getShareTypeA().getName().equals(PatientPortalToolkitConstants.CAN_SEE_MEDICAL) || ppr.getShareTypeA().getName().equals(PatientPortalToolkitConstants.CAN_SEE_BOTH))){
            if(ppr.getPerson().equals(currentPerson) && ppr.getRelatedPerson().getIsPatient() && ppr.getShareStatus()==1){
               checkAndAddPermission(ppr.getRelatedPerson(), currentPerson, permissionService);
            }
            //else if(ppr.getRelatedPerson().equals(currentPerson) && ppr.getPerson().getIsPatient() && ppr.getShareStatus()==1 && (ppr.getShareTypeB().getName().equals(PatientPortalToolkitConstants.CAN_SEE_MEDICAL) || ppr.getShareTypeB().getName().equals(PatientPortalToolkitConstants.CAN_SEE_BOTH))){
            else if(ppr.getRelatedPerson().equals(currentPerson) && ppr.getPerson().getIsPatient() && ppr.getShareStatus()==1){
                checkAndAddPermission(ppr.getPerson(),currentPerson, permissionService);
            }
        }
        //DataAccessPermissionService permissionService = Context.getService(DataAccessPermissionService.class);

        List<DataAccessPermission> dataAccessList = new ArrayList<>();
        dataAccessList = permissionService.getDataAccessPermissionByGrantedToPerson(currentPerson);
        boolean relationshipExists=false;
        if (dataAccessList!=null){
        for (DataAccessPermission dap: dataAccessList) {
            relationshipExists=false;
            Person getAccessToPerson=dap.getAccessToPerson();
            for (PatientPortalRelation ppr:pprRelations){
                if (getAccessToPerson.equals(ppr.getPerson()) || getAccessToPerson.equals(ppr.getRelatedPerson())){
                    relationshipExists=true;
                }
            }
            if(!relationshipExists){
                checkAndDeletePermission(currentPerson, dap.getAccessToPerson(), permissionService);
            }
        }
        }

    }

    private void checkAndAddPermission(Person person, Person secondPerson,DataAccessPermissionService permissionService){
        //DataAccessPermissionService permissionService = Context.getService(DataAccessPermissionService.class);
        // check to make sure the permission does not already exist
        System.out.println("Try to find an already existing permission...");
        DataAccessPermission nutritionPermission = permissionService.getDataAccessPermission(person, secondPerson, "nutrition_form", "read");
        if (nutritionPermission == null) {
            DataAccessPermission newNutritionPermission = new DataAccessPermission(person, secondPerson, "nutrition_form", "read");
            permissionService.saveDataAccessPermission(newNutritionPermission);
        }
        DataAccessPermission exercisePermission = permissionService.getDataAccessPermission(person, secondPerson, "exercise_form", "read");
        if (exercisePermission == null) {
            DataAccessPermission newExercisePermission = new DataAccessPermission(person, secondPerson, "exercise_form", "read");
            permissionService.saveDataAccessPermission(newExercisePermission);
        }
    }
    private void checkAndDeletePermission(Person person, Person secondPerson,DataAccessPermissionService permissionService){
        //DataAccessPermissionService permissionService = Context.getService(DataAccessPermissionService.class);
        // check to make sure the permission does not already exist
        System.out.println("Try to delete permissions not required");
        DataAccessPermission nutritionPermission = permissionService.getDataAccessPermission(person, secondPerson, "nutrition_form", "read");
        if (nutritionPermission != null) {
             permissionService.deleteDataAccessPermission(nutritionPermission);
        }
        DataAccessPermission exercisePermission = permissionService.getDataAccessPermission(person, secondPerson, "exercise_form", "read");
        if (exercisePermission != null) {
            permissionService.deleteDataAccessPermission(exercisePermission);
        }

        DataAccessPermission nutritionPermission2 = permissionService.getDataAccessPermission(secondPerson, person, "nutrition_form", "read");
        if (nutritionPermission2 != null) {
            permissionService.deleteDataAccessPermission(nutritionPermission2);
        }
        DataAccessPermission exercisePermission2 = permissionService.getDataAccessPermission(secondPerson, person, "exercise_form", "read");
        if (exercisePermission2 != null) {
            permissionService.deleteDataAccessPermission(exercisePermission2);
        }
    }
}
