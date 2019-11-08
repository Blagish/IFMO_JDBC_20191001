package com.efimchick.ifmo.web.jdbc;

import com.efimchick.ifmo.web.jdbc.domain.Employee;
import com.efimchick.ifmo.web.jdbc.domain.FullName;
import com.efimchick.ifmo.web.jdbc.domain.Position;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RowMapperFactory {

    public RowMapper<Employee> employeeRowMapper() {
        return new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet resultSet) {
                try{
                    String id = resultSet.getString("id");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String middleName = resultSet.getString("middleName");
                    String position = resultSet.getString("position");
                    String hireDate = resultSet.getString("hireDate");
                    String salary = resultSet.getString("salary");
                    
                    Employee employee = new Employee(
                            new BigInteger(id),
                            new FullName(firstName, lastName, middleName),
                            Position.valueOf(position),
                            LocalDate.parse(hireDate),
                            resultSet.getBigDecimal(salary)
                            );
                    
                    return employee;
                    
                }
                catch (SQLException e){
                    return null;
                }
            }
        };
        
    }
}
