package ru.otus.hw05.testing;

public class ClazzNeedToBeTested {
    private String interestingFieldOne;
    private long interestingFieldTwo;

    public ClazzNeedToBeTested(String interestingFieldOne, long interestingFieldTwo) {
        this.interestingFieldOne = interestingFieldOne;
        this.interestingFieldTwo = interestingFieldTwo;
    }

    public String getInterestingFieldOne() {
        return interestingFieldOne;
    }

    public void setInterestingFieldOne(String interestingFieldOne) {
//        this.interestingFieldOne = interestingFieldOne;
    }

    public long getInterestingFieldTwo() {
        return interestingFieldTwo;
    }

    public void setInterestingFieldTwo(long interestingFieldTwo) {
//        this.interestingFieldTwo = interestingFieldTwo;
    }

    @Override
    public String toString() {
        return "ClazzNeedToBeTested{" +
                "interestingFieldOne='" + interestingFieldOne + '\'' +
                ", interestingFieldTwo=" + interestingFieldTwo +
                '}';
    }
}
