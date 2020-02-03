package ru.otus.hw07.financial.service;

import java.util.ArrayList;
import java.util.List;

public class BankATMDepartmentImpl implements BankATMDepartment {
    List<BankATMService> departmentATM = new ArrayList<>();

    @Override
    public void addATMToDepartment(BankATMService atm) {
        departmentATM.add(atm);
    }

    @Override
    public long totalSumInDepartment() {
        long count = 0;
        for (BankATMService atm : departmentATM) {
            count += atm.getCurrentMoneySum();
        }
        return count;
    }

    @Override
    public void restoreInitialState() {
        for (BankATMService atm : departmentATM) {
            atm.restoreATMInitialState();
        }
    }
}
