package com.hanlzz.findqr.common;

/**
 *
 * @author liets
 */
public class StepNode {
    private IStep step;
    private StepNode[] branch;
    private String[] branchName;
    private StepNode next;

    public StepNode(){}

    public StepNode(IStep step){
        this.step = step;
    }

    public StepNode(IStep step,StepNode next){
        this.step = step;
        this.next = next;
    }

    public void setNext(StepNode next) {
        this.next = next;
    }

    public IStep getStep() {
        return step;
    }

    public StepNode getNext() {
        return next;
    }

    public StepNode[] getBranch() {
        return branch;
    }

    public String[] getBranchName() {
        return branchName;
    }

    public void setBranchAndName(String[] branchName, StepNode[] branch) {
        this.branchName = branchName;
        this.branch = branch;
    }

    boolean tranStart = false;
    boolean tranEnd = false;
}
