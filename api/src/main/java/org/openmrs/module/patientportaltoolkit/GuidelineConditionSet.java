/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit;

import org.openmrs.Concept;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by maurya on 1/23/17.
 */
public class GuidelineConditionSet {
    private Integer id;
    private String conditionName;
    private Set<Concept> conceptSet = new HashSet<Concept>(0);
    private Set<Guideline> guidelines = new HashSet<Guideline>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public Set<Concept> getConceptSet() {
        return conceptSet;
    }

    public void setConceptSet(Set<Concept> conceptSet) {
        this.conceptSet = conceptSet;
    }

    public Set<Guideline> getGuidelines() {
        return guidelines;
    }

    public void setGuidelines(Set<Guideline> guidelines) {
        this.guidelines = guidelines;
    }
}
