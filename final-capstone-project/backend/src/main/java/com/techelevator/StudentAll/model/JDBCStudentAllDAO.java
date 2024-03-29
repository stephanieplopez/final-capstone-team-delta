package com.techelevator.StudentAll.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCStudentAllDAO implements StudentAllDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCStudentAllDAO (DataSource datasource) {
		jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	@Override
	public StudentAll insertAllChoicesIntoDatabase(StudentAll studentAll) {
		String insertStudentInStudenTable = "INSERT INTO student (student_id, first_name, last_name) "
												+ "VALUES (DEFAULT, ?, ?) RETURNING student_id;";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(insertStudentInStudenTable, studentAll.getFirstName(), studentAll.getLastName());
		results.next();
		Integer studentId = results.getInt(1);
		
		for(int i = 0; i < studentAll.getChoices().length; i++) {
			String updateStudentEmployerJoinTable = "INSERT INTO student_all_employer (selection_id, student_id, employer_id, choice_number) VALUES (DEFAULT, ?, ?, ?)";
			jdbcTemplate.update(updateStudentEmployerJoinTable, studentId, studentAll.getChoices()[i], i+1);
		}
		studentAll.setStudentId(studentId);
		return studentAll;
	}
	
    @Override
    public List<StudentAll> getAllRegisteredStudents() {
        List<StudentAll> listOfStudents = new ArrayList<>();
        String selectSql = "SELECT * FROM student";
        SqlRowSet results = jdbcTemplate.queryForRowSet(selectSql);
        
        while(results.next()) {
            listOfStudents.add(mapRowToSqlResults(results));
        }
        return listOfStudents;
    }
    
    private StudentAll mapRowToSqlResults(SqlRowSet results) {
    	StudentAll studentAll = new StudentAll();
    	
    	studentAll.setStudentId(results.getInt("student_id"));
        studentAll.setFirstName(results.getString("first_name"));
        studentAll.setLastName(results.getString("last_name"));
        
        return studentAll;
    }
	
}