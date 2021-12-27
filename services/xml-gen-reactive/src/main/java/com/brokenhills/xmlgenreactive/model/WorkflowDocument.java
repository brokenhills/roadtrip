package com.brokenhills.xmlgenreactive.model;

import com.brokenhills.xmlgenreactive.model.workflow.ObjectFactory;
import com.brokenhills.xmlgenreactive.model.workflow.Workflow;
import com.brokenhills.xmlgenreactive.model.workflow.WorkflowState;
import com.brokenhills.xmlgenreactive.model.workflow.WorkflowType;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

public class WorkflowDocument implements XmlDocument {

    private Workflow workflow;

    public WorkflowDocument() {
    }

    public WorkflowDocument withParams(XmlGenParams params) {
        ObjectFactory factory = new ObjectFactory();
        workflow = factory.createWorkflow();
        workflow.setId(params.getWorkflowId());
        workflow.setName(params.getWorkflowName());
        workflow.setState(WorkflowState.fromValue(params.getWorkflowState()));
        workflow.setType(WorkflowType.fromValue(params.getWorkflowType()));
        try {
            workflow.setCreated(DatatypeFactory.newInstance().newXMLGregorianCalendar(params.getWorkflowCreated()));
        } catch (
                DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        Workflow.User user = factory.createWorkflowUser();
        user.setId(params.getUserId());
        user.setUsername(params.getUserUsername());
        user.setFirstName(params.getUserFirstName());
        user.setLastName(params.getUserLastName());
        user.setMiddleName(params.getUserMiddleName());
        workflow.setUser(user);
        Workflow.Department department = factory.createWorkflowDepartment();
        department.setId(params.getDepartmentId());
        department.setValue(params.getDepartmentName());
        workflow.setDepartment(department);
        workflow.setContent(params.getContent());
        workflow.setParent(params.getParentId());
        Workflow.Child child = factory.createWorkflowChild();
        child.getId().addAll(params.getChildIds());
        workflow.setChild(child);
        return this;
    }

    @Override
    public Workflow build() {
        return workflow;
    }
}
