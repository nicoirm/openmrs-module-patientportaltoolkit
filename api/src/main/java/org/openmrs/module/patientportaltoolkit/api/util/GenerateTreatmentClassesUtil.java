/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
 */

package org.openmrs.module.patientportaltoolkit.api.util;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Maurya on 01/07/2015.
 */
public class GenerateTreatmentClassesUtil {

    public static  List<GeneralHistory> generateGeneralHistory(Patient patient){
        List<Encounter> encounters = getEncountersByTreatment(patient, PatientPortalToolkitConstants.TREATMENTSUMMARY_ENCOUNTER);
        List<GeneralHistory> generalHistoryList=new ArrayList<GeneralHistory>();
        for(Encounter e: encounters){
            GeneralHistory generalHistory=new GeneralHistory();
            generalHistory.setEncounterUuid(e.getUuid());
            Set<Obs> obsList= e.getObs();
            for(Obs o: obsList){
                if(o.getConcept().getUuid().equals("efa3f9eb-ade4-4ddb-92c9-0fc1119d112d"))
                    generalHistory.setCancerStage(o.getValueCoded().getName().getName());
                if(o.getConcept().getUuid().equals("cdf6d767-2aa3-40b6-ae78-0386eebe2411"))
                    generalHistory.setCancerType(o.getValueCoded().getName().getName());
                if(o.getConcept().getUuid().equals("395878ae-5108-4aad-8ad8-9b88e812d278")){
                    if(o.getValueCoded().getUuid().equals("1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))
                        generalHistory.setHasGeneticOrPredisposingAbnormality(true);
                    else
                        generalHistory.setHasGeneticOrPredisposingAbnormality(false);
                }
                if(o.getConcept().getUuid().equals("8719adbe-0975-477f-a95f-2fae4d6cbdae"))
                    generalHistory.setGeneticOrPredisposingAbnormality(o.getValueCoded().getName().getName());
                if(o.getConcept().getUuid().equals("654e32f0-8b57-4d1f-845e-500922e800f6"))
                    generalHistory.setDiagnosisDate(o.getValueDate());
                //doctors name
                if(o.getConcept().getUuid().equals("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"))
                    generalHistory.setPcpName(o.getValueText());
                //doctors email
                if(o.getConcept().getUuid().equals("898a0028-8c65-4db9-a802-1577fce59864"))
                    generalHistory.setPcpEmail(o.getValueText());
                //doctors phone
                if(o.getConcept().getUuid().equals("9285b227-4054-4830-ac32-5ea78462e8c4"))
                    generalHistory.setPcpPhone(o.getValueText());

            }
            generalHistoryList.add(generalHistory);
        }
        return generalHistoryList;
    }

    public static  List<Surgery> generateSurgeries(Patient patient){
        List<Encounter> encounters = getEncountersByTreatment(patient, PatientPortalToolkitConstants.SURGERY_ENCOUNTER);
        List<Surgery> surgeriesList=new ArrayList<Surgery>();
        for(Encounter e: encounters){
            Surgery surgery=new Surgery();
            List<String> surgeryTypes= new ArrayList<String>();
            Set<Obs> obsList= e.getObs();
            surgery.setEncounterUuid(e.getUuid());
            for(Obs o: obsList){
                if(o.getConcept().getUuid().equals("d409122c-8a0b-4282-a17f-07abad81f278"))
                    surgeryTypes.add(o.getValueCoded().getName().getName());
                if(o.getConcept().getUuid().equals("99ef1d68-05ed-4f37-b98b-c982e3574138")){
                    if(o.getValueCoded().getUuid().equals("1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))
                        surgery.setHasMajorComplications(true);
                    else
                        surgery.setHasMajorComplications(false);
                }
                if(o.getConcept().getUuid().equals("c2d9fca3-1e0b-4007-8c3c-b3ebb4e67963"))
                    surgery.setMajorComplications(o.getValueText());
                if(o.getConcept().getUuid().equals("87a69397-65ef-4576-a709-ae0a526afd85"))
                    surgery.setSurgeryDate(o.getValueDate());
                //doctors name
                if(o.getConcept().getUuid().equals("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"))
                    surgery.setPcpName(o.getValueText());
                //doctors email
                if(o.getConcept().getUuid().equals("898a0028-8c65-4db9-a802-1577fce59864"))
                    surgery.setPcpEmail(o.getValueText());
                //doctors phone
                if(o.getConcept().getUuid().equals("9285b227-4054-4830-ac32-5ea78462e8c4"))
                    surgery.setPcpPhone(o.getValueText());
                if(o.getConcept().getUuid().equals("47d58999-d3b5-4869-a52e-841e2e6bdbb3"))
                    surgery.setInstitutionName(o.getValueText());
                if(o.getConcept().getUuid().equals("bfa752d6-2037-465e-b0a2-c4c2d485ec32"))
                    surgery.setInstitutionCity(o.getValueText());
                if(o.getConcept().getUuid().equals("34489100-487e-443a-bf27-1b6869fb9332"))
                    surgery.setInstitutionState(o.getValueText());
            }
            surgery.setSurgeryTypes(surgeryTypes);
            surgeriesList.add(surgery);
        }
        return surgeriesList;
    }

    public static  List<Chemotherapy> generateChemotherapies(Patient patient){
        List<Encounter> encounters = getEncountersByTreatment(patient, PatientPortalToolkitConstants.CHEMOTHERAPY_ENCOUNTER);
        List<Chemotherapy> chemotherapiesList=new ArrayList<Chemotherapy>();
        for(Encounter e: encounters){
            Chemotherapy chemotherapy=new Chemotherapy();
            List<String> chemomedications= new ArrayList<String>();
            Set<Obs> obsList= e.getObs();
            chemotherapy.setEncounterUuid(e.getUuid());
            for(Obs o: obsList){
                if(o.getConcept().getUuid().equals("8481b9da-74e3-45a9-9124-d69ab572d636"))
                    chemomedications.add(o.getValueCoded().getName().getName());
                if(o.getConcept().getUuid().equals("85c3a99e-0598-4c63-912b-796dee9c75b2"))
                    chemotherapy.setChemoStartDate(o.getValueDate());
                if(o.getConcept().getUuid().equals("7dd8b8aa-b0f1-4eb1-862d-b6d737bdd315"))
                    chemotherapy.setChemoEndDate(o.getValueDate());
                if(o.getConcept().getUuid().equals("361b7f9b-a985-4b18-9055-03af3b41b8b3")){
                    if(o.getValueCoded().getUuid().equals("1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))
                        chemotherapy.setCentralLine(true);
                    else
                        chemotherapy.setCentralLine(false);
                }
                //doctors name
                if(o.getConcept().getUuid().equals("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"))
                    chemotherapy.setPcpName(o.getValueText());
                //doctors email
                if(o.getConcept().getUuid().equals("898a0028-8c65-4db9-a802-1577fce59864"))
                    chemotherapy.setPcpEmail(o.getValueText());
                //doctors phone
                if(o.getConcept().getUuid().equals("9285b227-4054-4830-ac32-5ea78462e8c4"))
                    chemotherapy.setPcpPhone(o.getValueText());
                if(o.getConcept().getUuid().equals("47d58999-d3b5-4869-a52e-841e2e6bdbb3"))
                    chemotherapy.setInstitutionName(o.getValueText());
                if(o.getConcept().getUuid().equals("bfa752d6-2037-465e-b0a2-c4c2d485ec32"))
                    chemotherapy.setInstitutionCity(o.getValueText());
                if(o.getConcept().getUuid().equals("34489100-487e-443a-bf27-1b6869fb9332"))
                    chemotherapy.setInstitutionState(o.getValueText());
            }
            chemotherapy.setChemoMedications(chemomedications);
            chemotherapiesList.add(chemotherapy);
        }
        return chemotherapiesList;
    }

    public static  List<Radiation> generateRadiations(Patient patient){
        List<Encounter> encounters = getEncountersByTreatment(patient, PatientPortalToolkitConstants.RADIATION_ENCOUNTER);
        List<Radiation> radiationsList=new ArrayList<Radiation>();
        for(Encounter e: encounters){
            Radiation radiation=new Radiation();
            List<String> radiationTypes= new ArrayList<String>();
            Set<Obs> obsList= e.getObs();
            radiation.setEncounterUuid(e.getUuid());
            for(Obs o: obsList){
                if(o.getConcept().getUuid().equals("42fb7bb5-f840-4518-814c-893813211cba"))
                    radiationTypes.add(o.getValueCoded().getName().getName());
                if(o.getConcept().getUuid().equals("85c3a99e-0598-4c63-912b-796dee9c75b2"))
                    radiation.setStartDate(o.getValueDate());
                if(o.getConcept().getUuid().equals("7dd8b8aa-b0f1-4eb1-862d-b6d737bdd315"))
                    radiation.setEndDate(o.getValueDate());
                //doctors name
                if(o.getConcept().getUuid().equals("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"))
                    radiation.setPcpName(o.getValueText());
                //doctors email
                if(o.getConcept().getUuid().equals("898a0028-8c65-4db9-a802-1577fce59864"))
                    radiation.setPcpEmail(o.getValueText());
                //doctors phone
                if(o.getConcept().getUuid().equals("9285b227-4054-4830-ac32-5ea78462e8c4"))
                    radiation.setPcpPhone(o.getValueText());
                if(o.getConcept().getUuid().equals("47d58999-d3b5-4869-a52e-841e2e6bdbb3"))
                    radiation.setInstitutionName(o.getValueText());
                if(o.getConcept().getUuid().equals("bfa752d6-2037-465e-b0a2-c4c2d485ec32"))
                    radiation.setInstitutionCity(o.getValueText());
                if(o.getConcept().getUuid().equals("34489100-487e-443a-bf27-1b6869fb9332"))
                    radiation.setInstitutionState(o.getValueText());
            }
            radiation.setRadiationTypes(radiationTypes);
            radiationsList.add(radiation);
        }
        return radiationsList;
    }

    public static List<Encounter> getEncountersByTreatment(Patient patient,String treatmentType) {
        List<Encounter> encounters = Context.getEncounterService().getEncountersByPatient(patient);
        List<Encounter> treatmentEncounters = new ArrayList<Encounter>();
        for (Encounter encounter : encounters) {
            if (!encounter.isVoided() && treatmentType.equals(encounter.getEncounterType().getName())) {
                treatmentEncounters.add(encounter);
            }
        }
        return treatmentEncounters;
    }

    public static  Encounter generateLatestGeneralHistory(Patient patient){
        return getLatestEncounterByTreatment(patient, PatientPortalToolkitConstants.TREATMENTSUMMARY_ENCOUNTER);
    }

    public static Encounter getLatestEncounterByTreatment(Patient patient,String treatmentType) {
        List<Encounter> encounters = Context.getEncounterService().getEncountersByPatient(patient);
        Encounter treatmentEncounter = null;
        for (Encounter encounter : encounters) {
            if (!encounter.isVoided() && treatmentType.equals(encounter.getEncounterType().getName())) {
                if(treatmentEncounter==null)
                treatmentEncounter=encounter;
                else if(treatmentEncounter.getDateCreated().before(encounter.getDateCreated()))
                    treatmentEncounter=encounter;
            }
        }
        return treatmentEncounter;
    }
}
