package com.efimchick.ifmo.web.jdbc;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.efimchick.ifmo.web.jdbc.domain.Employee;
import com.efimchick.ifmo.web.jdbc.domain.FullName;
import com.efimchick.ifmo.web.jdbc.domain.Position;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SetMapperFactory {
    
    public Employee rowMapper(ResultSet resultSet){
        try{
            String id = resultSet.getString("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String middleName = resultSet.getString("middleName");
            String position = resultSet.getString("position");
            String hireDate = resultSet.getString("hireDate");
            String salary = resultSet.getString("salary");


            Employee manager = null;

            if (resultSet.getString("manager") != null) {
                int managerId = resultSet.getInt("manager");
                int curRow = resultSet.getRow();
                resultSet.absolute(0);

                while (resultSet.next()) {
                    if (managerId == resultSet.getInt("ID"))
                        break;
                }

                manager = rowMapper(resultSet);

                resultSet.absolute(curRow);
            }


            Employee employee =  new Employee(
                    new BigInteger(id),
                    new FullName(firstName, lastName, middleName),
                    Position.valueOf(position),
                    LocalDate.parse(hireDate),
                    resultSet.getBigDecimal(salary),
                    manager);
            return employee;
        }
        catch (SQLException e){
            return null;
        }
    }
    
    public SetMapper<Set<Employee>> employeesSetMapper() {
        SetMapper<Set<Employee>> resultMap = resultSet -> {
            Set<Employee> employeeSet = new HashSet<>();
            try {
                while (resultSet.next())
                    employeeSet.add(rowMapper(resultSet));
                return employeeSet;
            }
            catch (SQLException e){
                return null;
            }
        };
        return resultMap;
    }
    

    
    
}
